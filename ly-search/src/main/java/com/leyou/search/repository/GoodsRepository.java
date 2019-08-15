package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * GoodsRepository
 *
 * @author xieweisong
 * @date 2019/8/13 14:11
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
