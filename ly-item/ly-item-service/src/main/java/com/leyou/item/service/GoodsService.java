package com.leyou.item.service;

import com.leyou.common.vo.PageResultVO;
import com.leyou.item.pojo.Spu;

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
}
