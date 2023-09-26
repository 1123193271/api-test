package com.example.authtoken.entity;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;

    // 省略构造函数和getter/setter
}
