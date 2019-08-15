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
}
