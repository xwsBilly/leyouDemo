package com.leyou.item.api;

import com.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * BrandApi
 *
 * @author xieweisong
 * @date 2019/8/9 17:24
 */
public interface BrandApi {
    /**
     * 根据品牌id查询品牌
     * @param id
     * @return
     */
    @GetMapping("/brand/{id}")
    Brand queryBrandById(@PathVariable("id") Long id);

    /**
     * 根据多个品牌id查询品牌
     * @param ids
     * @return
     */
    @GetMapping("/brand/list")
    List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);
}
