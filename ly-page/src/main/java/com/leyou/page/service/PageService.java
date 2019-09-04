package com.leyou.page.service;

import java.util.Map;

/**
 * PageService
 *
 * @author xieweisong
 * @date 2019/9/2 17:27
 */
public interface PageService {
    /**
     * 加载视图模型
     * @param spuId
     * @return
     */
    Map<String, Object> loadModel(Long spuId);

    /**
     * 判断是否存在静态html文件
     * @param spuId
     * @return
     */
    boolean exists(Long spuId);

    /**
     * 异步创建静态html文件
     * @param spuId
     */
    void syncCreateHtml(Long spuId);

    /**
     * 删除静态html文件
     * @param spuId
     */
    void deleteHtml(Long spuId);
}
