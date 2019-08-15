package com.leyou.search.client;

import com.leyou.item.api.ItemServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * ItemServiceFeignClient
 *
 * @author xieweisong
 * @date 2019/8/12 10:06
 */
@FeignClient(name = "item-service")
public interface ItemServiceFeignClient extends ItemServiceApi {
}
