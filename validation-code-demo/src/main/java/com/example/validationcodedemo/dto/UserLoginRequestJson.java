package com.example.validationcodedemo.dto;

import lombok.Data;

/**
 * @author qzz
 */
@Data
public class UserLoginRequestJson {

    /**
     * 用户账号
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String code;
}
