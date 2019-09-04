package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * SpecificationService
 * 商品规格参数服务接口
 *
 * @author xieweisong
 * @date 2019/8/4 14:43
 */
public interface SpecificationService {
    /**
     * 根据商品分类id查询商品规格组
     * @param cid   商品分类id
     * @return
     */
    List<SpecGroup> querySpecGroupByCid(Long cid);

    /**
     * 根据组id查询商品规格参数
     *
     * @param gid   商品规格组id
     * @param cid   商品分类id
     * @param searching 是否搜索
     * @return
     */
    List<SpecParam> querySpecParamList(Long gid, Long cid, Boolean searching);

    /**
     * 根据商品分类id，查询规格参数组
     * @param cid
     * @return
     */
    @GetMapping("/spec/group")
    List<SpecGroup> querySpecGroupAllByCid(@RequestParam("cid") Long cid);
}
