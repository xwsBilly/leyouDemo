package com.leyou.search.controller;

import com.leyou.common.vo.PageResultVO;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.service.SearchService;
import com.leyou.search.service.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SearchController
 *
 * @author xieweisong
 * @date 2019/8/13 17:51
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索功能
     * @param searchRequest
     * @return
     */
    @PostMapping("/page")
    public ResponseEntity<PageResultVO<Goods>> search(@RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok(searchService.search(searchRequest));
    }
}
