package com.leyou.item.api;

import com.leyou.common.vo.PageResultVO;
import com.leyou.item.pojo.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ItemServiceApi
 * 一个服务对应一个FeignClient接口，将所有的item-service服务下的需要使用feign远程调用的方法都写在同一个接口中
 *
 * @author xieweisong
 * @date 2019/8/12 10:03
 */
public interface ItemServiceApi {
    /**
     * 根据品牌id查询品牌
     * @param id
     * @return
     */
    @GetMapping("/brand/{id}")
    Brand queryBrandById(@PathVariable("id") Long id);

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

    /**
     * 分页查询Spu
     *
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/spu/page")
    PageResultVO<Spu> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key);

    /**
     * 根据spuId查询商品详情SpuDetail
     *
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    SpuDetail querySpuDetailBySpuId(@PathVariable("id") Long spuId);

    /**
     * 根据spuId查询下面所有的sku
     *
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long spuId);

    /**
     * 查询参数列表
     *
     * @param gid       商品组id
     * @param cid       分类id
     * @param searching 是否搜索
     * @return
     */
    @GetMapping("/spec/params")
    List<SpecParam> querySpecParamList(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching);
}
