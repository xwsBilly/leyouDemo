package com.leyou.page.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * SpecificationFeignClient
 *
 * @author xieweisong
 * @date 2019/8/9 17:27
 */
@FeignClient("item-service")
public interface SpecificationFeignClient extends SpecificationApi {
}
