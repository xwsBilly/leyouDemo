package com.leyou.search.service;

import com.leyou.common.vo.PageResultVO;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;

/**
 * SearchService
 *
 * @author xieweisong
 * @date 2019/8/13 17:54
 */
public interface SearchService {
    /**
     * 搜索功能
     * @param searchRequest
     * @return
     */
    PageResultVO<Goods> search(SearchRequest searchRequest);

    /**
     * 接受MQ消息，创建或修改索引库
     * @param spuId
     */
    void createOrUpdateIndex(Long spuId);

    /**
     * 接受MQ消息，删除索引库中的索引
     * @param spuId
     */
    void deleteIndex(Long spuId);
}
