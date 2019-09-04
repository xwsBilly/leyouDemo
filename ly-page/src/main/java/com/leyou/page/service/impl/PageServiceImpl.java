package com.leyou.page.service.impl;

import com.leyou.item.pojo.*;
import com.leyou.page.client.BrandFeignClient;
import com.leyou.page.client.CategoryFeignClient;
import com.leyou.page.client.GoodsFeignClient;
import com.leyou.page.client.SpecificationFeignClient;
import com.leyou.page.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PageServiceImpl
 *
 * @author xieweisong
 * @date 2019/9/2 17:28
 */
@Slf4j
@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private CategoryFeignClient categoryFeignClient;

    @Autowired
    private BrandFeignClient brandFeignClient;

    @Autowired
    private GoodsFeignClient goodsFeignClient;

    @Autowired
    private SpecificationFeignClient specificationFeignClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public Map<String, Object> loadModel(Long spuId) {
        Map<String, Object> model = new HashMap<>();
        // 查询spu
        Spu spu = goodsFeignClient.querySpuById(spuId);
        // 查询skus
        List<Sku> skus = spu.getSkus();
        // 查询详情
        SpuDetail spuDetail = spu.getSpuDetail();
        // 查询品牌
        Brand brand = brandFeignClient.queryBrandById(spu.getBrandId());
        // 查询商品分类
        List<Category> categories = categoryFeignClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        // 查询规格参数
        List<SpecGroup> specs = specificationFeignClient.querySpecGroupAllByCid(spu.getCid3());

        //model.put("spu", spu);
        model.put("title", spu.getTitle());
        model.put("subTitle", spu.getSubTitle());
        model.put("skus", skus);
        model.put("detail", spuDetail);
        model.put("brand", brand);
        model.put("categories", categories);
        model.put("specs", specs);
        return model;
    }

    @Override
    public boolean exists(Long spuId) {
        File dir=new File("C:\\Users\\Billy\\IdeaProjects\\leyou\\upload\\item",spuId+".html");
        return dir.exists();
    }

    @Override
    public void syncCreateHtml(Long spuId) {
        // 上下文
        Context context = new Context();
        context.setVariables(loadModel(spuId));
        // 输出流
        File file = new File("C:\\Users\\Billy\\IdeaProjects\\leyou\\upload\\item", spuId + ".html");
        if (file.exists()) {
            file.delete();
        }
        try {
            PrintWriter printWriter = new PrintWriter(file,"UTF-8");
            // 生成HTML
            templateEngine.process("item",context,printWriter);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("[静态页服务] 生成静态页面失败 ",e);
        }
    }

    @Override
    public void deleteHtml(Long spuId) {
        File file = new File("C:\\Users\\Billy\\IdeaProjects\\leyou\\upload\\item", spuId + ".html");
        if (file.exists()) {
            file.delete();
        }
    }

    private File getDestFile(Long spuId){
        // 目标目录
        File dir=new File("C:\\Users\\Billy\\IdeaProjects\\leyou\\upload\\item");
        if (!dir.exists()){
            dir.mkdirs();
        }
        // 返回文件地址
        return new File(dir, spuId + ".html");
    }

    public void createHtml(Long spuId) {

    }
}
