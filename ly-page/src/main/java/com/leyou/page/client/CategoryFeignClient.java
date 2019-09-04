package com.leyou.page.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * CategoryFeignClient
 *
 * @author xieweisong
 * @date 2019/8/9 16:59
 */
@FeignClient("item-service")
public interface CategoryFeignClient extends CategoryApi {

}
