package com.leyou.search.pojo;

import com.leyou.common.vo.PageResultVO;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * SearchResult
 *
 * @author xieweisong
 * @date 2019/8/15 15:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchResult extends PageResultVO<Goods> {
    /**
     * 分类待选项
     */
    private List<Category> categories;
    /**
     * 品牌待选项
     */
    private List<Brand> brands;
    /**
     * 规格参数过滤条件
     * key和待选项
     */
    private List<Map<String, Object>> specs;

    public SearchResult() {
    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Category> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
