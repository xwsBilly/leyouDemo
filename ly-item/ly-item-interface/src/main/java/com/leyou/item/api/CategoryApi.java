package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * CategoryApi
 *
 * @author xieweisong
 * @date 2019/8/9 17:23
 */
public interface CategoryApi {
    /**
     * 根据多个id查询商品分类
     *
     * 可以选择使用ResponseEntity<List<Category>>作为接受，但是需要判断返回的状态码是否为200还是其他。
     * 只使用List<Category>接受，就只有状态码为200时才能成功，否则报错，抛异常
     * @param ids
     * @return
     */
    @GetMapping("/category/list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
