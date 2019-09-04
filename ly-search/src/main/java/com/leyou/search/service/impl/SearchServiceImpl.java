package com.leyou.search.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.MyException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.common.utils.NumberUtils;
import com.leyou.common.vo.PageResultVO;
import com.leyou.item.pojo.*;
import com.leyou.search.client.BrandFeignClient;
import com.leyou.search.client.CategoryFeignClient;
import com.leyou.search.client.GoodsFeignClient;
import com.leyou.search.client.SpecificationFeignClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.pojo.SkuDTO;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SearchService
 *
 * @author xieweisong
 * @date 2019/8/13 14:18
 */

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private CategoryFeignClient categoryFeignClient;

    @Autowired
    private BrandFeignClient brandFeignClient;

    @Autowired
    private GoodsFeignClient goodsFeignClient;

    @Autowired
    private SpecificationFeignClient specificationFeignClient;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate template;

    public Goods buildGoods(Spu spu) {
        // 查询分类
        List<Category> categories = categoryFeignClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if (CollectionUtils.isEmpty(categories)) {
            throw new MyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());
        // 查询品牌
        Brand brand = brandFeignClient.queryBrandById(spu.getBrandId());
        if (brand == null) {
            throw new MyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        // 搜索字段
        String all = spu.getTitle() + StringUtils.join(names, " ") + brand.getName();

        // 查询sku
        List<Sku> skuList = goodsFeignClient.querySkuBySpuId(spu.getId());
        if (CollectionUtils.isEmpty(skuList)) {
            throw new MyException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }

        // 对sku进行处理
        List<SkuDTO> skuDTOList = new ArrayList<>();
        // sku价格的集合
        Set<Long> priceSet = new HashSet<>();

        for (Sku sku : skuList) {
            SkuDTO skuDTO = new SkuDTO();
            skuDTO.setId(sku.getId());
            skuDTO.setTitle(sku.getTitle());
            // 截取","前的第一个，substringBefore()做了非空判断，不会空指针异常
            skuDTO.setImage(StringUtils.substringBefore(sku.getImages(), ","));
            skuDTO.setPrice(sku.getPrice());
            skuDTOList.add(skuDTO);
            // 处理价格
            priceSet.add(sku.getPrice());
        }
        /*
        List<Map<String ,Object>> skus=new ArrayList<>();
        for (Sku sku : skuList) {
            Map<String ,Object> map=new HashMap<>();
            map.put("id",sku.getId());
            map.put("title",sku.getTitle());
            map.put("image",StringUtils.substringBefore(sku.getImages(),","));
            map.put("price",sku.getPrice());
            skus.add(map);
        }*/

        // Set<Long> priceSet = skuList.stream().map(Sku::getPrice).collect(Collectors.toSet());

        // 查询规格参数
        List<SpecParam> specParams = specificationFeignClient.querySpecParamList(null, spu.getCid3(), true);
        if (CollectionUtils.isEmpty(specParams)) {
            throw new MyException(ExceptionEnum.SPECIFICATION_PARAM_NOT_FOUND);
        }
        // 查询商品详情
        SpuDetail spuDetail = goodsFeignClient.querySpuDetailBySpuId(spu.getId());
        // 获取通用规格参数
        String genericSpec = spuDetail.getGenericSpec();
        Map<Long, String> genericSpecMap = JsonUtils.toMap(genericSpec, Long.class, String.class);
        // 获取特有规格参数
        String specialSpec = spuDetail.getSpecialSpec();
        // 特有规格参数中有list
        Map<Long, List<String>> specialSpecMap = JsonUtils.nativeRead(specialSpec,
                new TypeReference<Map<Long, List<String>>>() {
                });

        // 规格参数,key是规格参数的名称，值是规格参数的值
        Map<String, Object> specs = new HashMap<>();
        for (SpecParam specParam : specParams) {
            // 规格参数名称
            String key = specParam.getName();
            Object value = "";
            // 判断是否是通用规格参数
            if (specParam.getGeneric()) {
                if (genericSpecMap != null) {
                    value = genericSpecMap.get(specParam.getId());
                    // 判断是否是数值类型
                    if (specParam.getNumeric()) {
                        // 处理成价格区间(段)
                        value = chooseSegment(value.toString(), specParam);
                    }
                }
            } else {
                if (specialSpecMap != null) {
                    value = specialSpecMap.get(specParam.getId());
                }
            }
            // 存入map
            specs.put(key, value);
        }

        // 构建Goods对象
        Goods goods = new Goods();
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setId(spu.getId());
        goods.setSubTitle(spu.getSubTitle());

        // 搜索字段，包含标题、分类、品牌等等
        goods.setAll(all);
        // 所有spu的价格set集合
        goods.setPrice(priceSet);
        // 所有sku集合的json格式
        goods.setSkus(JsonUtils.toString(skuDTOList));
        // 所有可以搜索的规格参数
        goods.setSpecs(specs);

        return goods;
    }

    private String chooseSegment(String value, SpecParam specParam) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : specParam.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + specParam.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + specParam.getUnit() + "以下";
                } else {
                    result = segment + specParam.getUnit();
                }
                break;
            }
        }
        return result;
    }

    @Override
    public PageResultVO<Goods> search(SearchRequest searchRequest) {
        // es中第一页从0开始
        int page = searchRequest.getPage() - 1;
        int size = searchRequest.getDefaultSize();
        // 创建查询构建器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 0 结果过滤
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "subTitle", "skus"}, null));
        // 1 分页
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page, size));
        // 2 过滤
        // QueryBuilder basicQuery = QueryBuilders.matchQuery("all", searchRequest.getKey());
        QueryBuilder basicQuery = buildBasicQuery(searchRequest);
        nativeSearchQueryBuilder.withQuery(basicQuery);
        // 3 聚合分类和品牌信息
        // 3.1 聚合分类
        String categoryAggName = "category_agg";
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        // 3.2 聚合品牌
        String brandAggName = "brand_agg";
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        // 4 查询
        // Page<Goods> result = goodsRepository.search(nativeSearchQueryBuilder.build());

        // 使用聚合查询需要用到ElasticsearchTemplate
        AggregatedPage<Goods> result = template.queryForPage(nativeSearchQueryBuilder.build(), Goods.class);

        // 5 解析结果
        // 5.1 解析分页结果
        long total = result.getTotalElements();
        int totalPages = result.getTotalPages();
        List<Goods> content = result.getContent();
        // 5.2 解析聚合结果
        Aggregations aggs = result.getAggregations();
        List<Category> categories = parseCategoryAgg(aggs.get(categoryAggName));
        List<Brand> brands = parseBrandAgg(aggs.get(brandAggName));

        // return new PageResultVO<>(total, totalPages, content);
        List<Map<String, Object>> specs = null;
        if (categories != null && categories.size() == 1) {
            // 商品分类存在并且数量为1，可以聚合参数
            // 在原先搜索条件的基础上进行 聚合
            specs = buildSpecificationAgg(categories.get(0).getId(), basicQuery);
        }

        // 6 完成规格参数聚合
        return new SearchResult(total, totalPages, content, categories, brands, specs);
    }

    private QueryBuilder buildBasicQuery(SearchRequest searchRequest) {
        // 创建布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 查询条件
        boolQueryBuilder.must(QueryBuilders.matchQuery("all",searchRequest.getKey()));
        // 过滤条件
        Map<String, String> filter = searchRequest.getFilter();
        for (Map.Entry<String, String> entry : filter.entrySet()) {
            String key = entry.getKey();
            // 处理key的情况,如果过是分类或者品牌时，不需要处理
            if (!"cid3".equals(key)&&!"brandId".equals(key)){
                key="specs."+key+".keyword";
            }
            String value = entry.getValue();
            boolQueryBuilder.filter(QueryBuilders.termQuery(key,value));
        }
        return boolQueryBuilder;
    }

    private List<Map<String, Object>> buildSpecificationAgg(Long cid, QueryBuilder basicQuery) {
        List<Map<String, Object>> specs = new ArrayList<>();
        // 1 查询需要聚合的规格参数
        List<SpecParam> params = specificationFeignClient.querySpecParamList(null, cid, true);
        // 2 聚合
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 2.1 带上查询条件
        nativeSearchQueryBuilder.withQuery(basicQuery);
        // 2.2 聚合
        for (SpecParam param : params) {
            String name = param.getName();
            // 用规格参数名，作为聚合名
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(name).field("specs." + name + ".keyword"));
        }
        // 3 获取结果
        AggregatedPage<Goods> result = template.queryForPage(nativeSearchQueryBuilder.build(), Goods.class);
        // 4 解析结果
        Aggregations aggregations = result.getAggregations();
        for (SpecParam param : params) {
            // 规格参数名称
            String name = param.getName();
            StringTerms terms = aggregations.get(name);
            // 待选项
            List<String> options = terms.getBuckets().stream().map(StringTerms.Bucket::getKeyAsString).collect(Collectors.toList());
            // 准备map
            Map<String, Object> map = new HashMap<>();
            map.put("k", name);
            map.put("options", options);
            specs.add(map);
        }

        return specs;
    }

    private List<Brand> parseBrandAgg(LongTerms longTerms) {
        try {
            List<Long> ids = longTerms.getBuckets().stream().map(bucket -> bucket.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<Brand> brands = brandFeignClient.queryBrandByIds(ids);
            return brands;
        } catch (Exception e) {
            log.error("[搜索服务]查询品牌失败：", e);
            return null;
        }
    }

    private List<Category> parseCategoryAgg(LongTerms longTerms) {
        try {
            List<Long> ids = longTerms.getBuckets().stream().map(bucket -> bucket.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<Category> categories = categoryFeignClient.queryCategoryByIds(ids);
            return categories;
        } catch (Exception e) {
            log.error("[搜索服务]查询分类失败：", e);
            return null;
        }
    }

    @Override
    public void createOrUpdateIndex(Long spuId) {
        // 根据spuId查询spu
        Spu spu = goodsFeignClient.querySpuById(spuId);
        // 构建goods
        Goods goods = buildGoods(spu);
        // 存入索引库
        goodsRepository.save(goods);
    }

    @Override
    public void deleteIndex(Long spuId) {
        goodsRepository.deleteById(spuId);
    }
}
