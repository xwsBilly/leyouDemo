package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.pojo.UserVo;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * UserController
 *
 * @author Billy
 * @date 2019/9/19 10:45
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验数据
     *
     * @param data 数据
     * @param type 数据类型
     * @return true or false
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        return ResponseEntity.ok(userService.checkData(data, type));
    }

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @return void
     */
    @PostMapping("/code")
    public ResponseEntity<Void> sendCode(@RequestParam("phone") String phone) {
        userService.sendCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 注册用户
     *
     * @param userVo 前台传值
     * @return void
     */
    @PostMapping("/register")
    // 在@Valid后面加一个BindingResult result 用来接收验证出错时的信息
    public ResponseEntity<Void> register(@Valid UserVo userVo, BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException(result.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("|")));
        }
        userService.register(userVo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据用户名和密码查询用户
     * @param username  用户名
     * @param password  密码
     * @return  用户
     */
    @GetMapping("/query")
    public ResponseEntity<User> queryUserByUsernameAndPassword(@RequestParam("username") String username,
                                                               @RequestParam("password") String password) {
        return ResponseEntity.ok(userService.queryUserByUsernameAndPassword(username, password));
    }
}
