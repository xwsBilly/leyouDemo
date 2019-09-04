package com.leyou.page.client;

import com.leyou.item.api.GoodsApi;
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
