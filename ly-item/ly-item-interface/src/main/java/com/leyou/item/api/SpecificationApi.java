package com.leyou.item.api;

import com.leyou.item.pojo.SpecParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * SpecificationApi
 *
 * @author xieweisong
 * @date 2019/8/9 17:26
 */
public interface SpecificationApi {
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
