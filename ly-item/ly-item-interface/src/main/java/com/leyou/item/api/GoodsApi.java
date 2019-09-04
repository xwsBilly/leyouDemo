package com.leyou.item.api;

import com.leyou.common.vo.PageResultVO;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * GoodsApi
 *
 * @author xieweisong
 * @date 2019/8/9 17:17
 */
public interface GoodsApi {
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
     * 根据spuId查询spu
     * @param id
     * @return
     */
    @GetMapping("/spu/{id}")
    Spu querySpuById(@PathVariable("id") Long id);
}
