package com.leyou.user.service;

import com.leyou.user.pojo.User;
import com.leyou.user.pojo.UserVo;

/**
 * UserService
 *
 * @author Billy
 * @date 2019/9/19 10:43
 */
public interface UserService {
    Boolean checkData(String data, Integer type);

    void sendCode(String phone);

    void register(UserVo userVo);

    User queryUserByUsernameAndPassword(String username, String password);
}
