package com.leyou.search.client;

import com.leyou.item.api.GoodsApi;
import com.leyou.item.api.ItemServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * GoodsFeignClient
 *
 * @author xieweisong
 * @date 2019/8/9 17:09
 */
@FeignClient("item-service")
public interface GoodsFeignClient extends GoodsApi {
}
