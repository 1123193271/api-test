package com.example.authtoken.Lang;

import lombok.Setter;

public class TokenApiException extends RuntimeException{
    private String code = "0";
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TokenApiException(String message) {
        super(message);
    }

}
