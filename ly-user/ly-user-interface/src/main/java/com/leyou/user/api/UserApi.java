package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * UserApi
 *
 * @author Billy
 * @date 2019/12/5 17:03
 */
public interface UserApi {
    @GetMapping("/query")
    User queryUserByUsernameAndPassword(@RequestParam("username") String username,
                                        @RequestParam("password") String password);
}
