package com.example.authtoken.controller;

import com.example.authtoken.Lang.ExceptionModel;
import com.example.authtoken.Lang.TokenApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionModel handleBizEx(TokenApiException ex) {
        log.error("TokenApiException", ex);
        ExceptionModel em = new ExceptionModel();
        em.setMessage(ex.getMessage());
        em.setCode(ex.getCode());
        em.setType(ExceptionModel.EXCEPTION_TYPE_API);
        return em;
    }
}
