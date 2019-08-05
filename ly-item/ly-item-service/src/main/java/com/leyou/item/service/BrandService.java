package com.leyou.item.service;

import com.leyou.common.vo.PageResultVO;
import com.leyou.item.pojo.Brand;

import java.util.List;

/**
 * 品牌服务接口
 * @author xieweisong
 */
public interface BrandService {
    /**
     * 查询品牌
     * @param page  查询页数
     * @param rows  查询条数
     * @param sortBy    排序
     * @param desc  升降序，true为降序，false为升序
     * @param key   查询关键字
     * @return
     */
    PageResultVO<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key);

    /**
     * 新增品牌
     * @param brand 品牌
     * @param cids  商品分类
     */
    void saveBrand(Brand brand, List<Long> cids);

    /**
     * 根据品牌id查询品牌
     * @param id
     * @return
     */
    Brand queryById(Long id);

    /**
     * 根据分类Id查询品牌
     * @param cid   分类id
     * @return
     */
    List<Brand> queryBrandByCid(Long cid);
}
