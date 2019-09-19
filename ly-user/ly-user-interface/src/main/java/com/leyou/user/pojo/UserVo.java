package com.leyou.user.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * UserVo
 *
 * @author Billy
 * @date 2019/9/19 15:04
 */
@Data
public class UserVo {
    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 4, max = 32, message = "用户名长度必须在4~32位")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "用户名长度必须在6~16位")
    private String password;

    /**
     * 电话
     */
    @NotEmpty(message = "电话不能为空")
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}&", message = "手机号不正确")
    private String phone;

    /**
     * 验证码
     */
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
