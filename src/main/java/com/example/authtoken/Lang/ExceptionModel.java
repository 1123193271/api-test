package com.example.authtoken.Lang;

import lombok.Getter;
import lombok.Setter;

/**
 *异常数据模型封装
 */
@Setter
@Getter
public class ExceptionModel {
    public static final String EXCEPTION_TYPE_SYS = "SysException";

    public static final String EXCEPTION_TYPE_API = "TokenApiException";

    private String type = EXCEPTION_TYPE_SYS;
    private String code = "0";
    private String message;

}
