package com.leyou.auth.service.impl;

import com.leyou.auth.client.UserClient;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.MyException;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * AuthServiceImpl
 *
 * @author Billy
 * @date 2019/12/5 16:27
 */
@Slf4j
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String login(String username, String password) {

        // 校验用户名和密码
        User user = userClient.queryUserByUsernameAndPassword(username, password);
        if (user == null) {
            throw new MyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
        try {
            // 生成token
            return JwtUtils.generateToken(new UserInfo(user.getId(), username), jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        } catch (Exception e) {
            log.error("[授权中心] 生成token失败，用户名:{},{}", username, e);
            throw new MyException(ExceptionEnum.CREATE_TOKEN_ERROR);
        }
    }
}
