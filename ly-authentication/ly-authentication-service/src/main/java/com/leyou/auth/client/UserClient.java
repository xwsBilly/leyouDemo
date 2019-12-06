package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * UserClient
 *
 * @author Billy
 * @date 2019/12/5 17:07
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
