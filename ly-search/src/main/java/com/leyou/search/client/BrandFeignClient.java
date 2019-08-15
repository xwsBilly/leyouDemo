package com.leyou.search.client;

import com.leyou.item.api.BrandApi;
import com.leyou.item.api.ItemServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * BrandFeignClient
 *
 * @author xieweisong
 * @date 2019/8/9 17:28
 */
@FeignClient(name = "item-service")
public interface BrandFeignClient extends BrandApi {

}
