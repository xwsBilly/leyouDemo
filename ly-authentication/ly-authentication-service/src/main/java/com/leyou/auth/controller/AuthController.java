package com.leyou.auth.controller;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AuthController
 *
 * @author Billy
 * @date 2019/12/5 16:24
 */
@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {
    @Autowired
    private AuthService authService;

    @Value("${ly.jwt.cookieName}")
    private String cookieName;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录授权
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestParam String username, @RequestParam String password,
                                      HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        // 登录
        String token = authService.login(username, password);
        // 写入cookie
        CookieUtils.newBuilder(httpServletResponse).httpOnly().request(httpServletRequest)
                .bulid(cookieName, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 用户验证
     *
     * @param token 用户token
     * @return 用户信息
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verifyUser(@CookieValue("LY_TOKEN") String token,
                                               HttpServletResponse httpServletResponse,
                                               HttpServletRequest httpServletRequest) {
        try {
            // 从token中解析token信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            // 刷新token
            String newToken = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            CookieUtils.newBuilder(httpServletResponse).httpOnly().request(httpServletRequest)
                    .bulid(cookieName, newToken);
            // 解析成功返回用户信息
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 出现异常,相应401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
