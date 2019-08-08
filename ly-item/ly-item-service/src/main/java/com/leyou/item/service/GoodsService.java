package com.leyou.item.service;

import com.leyou.common.vo.PageResultVO;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * GoodsService
 *
 * @author xieweisong
 * @date 2019/8/4 18:09
 */
public interface GoodsService {
    /**
     * 分页查询Spu
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    PageResultVO<Spu> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key);

    /**
     * 新增商品
     * @param spu
     */
    void saveGoods(Spu spu);

    /**
     * 根据spuId查询商品详情SpuDetail
     * @param spuId
     * @return
     */
    SpuDetail querySpuDetailBySpuId(Long spuId);

    /**
     * 根据spuId查询下面所有的sku
     * @param spuId
     * @return
     */
    List<Sku> querySkuBySpuId(Long spuId);

    /**
     * 修改商品
     * @param spu
     */
    void updateGoods(Spu spu);
}
