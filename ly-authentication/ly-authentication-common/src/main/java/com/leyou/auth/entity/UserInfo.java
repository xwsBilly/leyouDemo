package com.leyou.auth.entity;

/**
 * UserInfo
 * 用户信息
 *
 * @author Billy
 * @date 2019/9/23 14:20
 */
public class UserInfo {

    private Long id;

    private String username;

    public UserInfo() {
    }

    public UserInfo(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}