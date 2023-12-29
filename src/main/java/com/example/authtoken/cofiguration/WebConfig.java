package com.example.authtoken.cofiguration;

import com.example.authtoken.Lang.TokenApiException;
import com.example.authtoken.context.UserContext;
import com.example.authtoken.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import java.util.Objects;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    JwtUtil jwtUtil;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userContextInterceptor());
    }

    @Bean
    public WebRequestHandlerInterceptorAdapter userContextInterceptor() {
        return new WebRequestHandlerInterceptorAdapter(new WebRequestInterceptor() {
            @Override
            public void preHandle(WebRequest request) throws Exception {
                try{
                    if (Objects.nonNull(request.getHeader("Authorization"))) {
                        String jwt = Objects.requireNonNull(request.getHeader("Authorization")).substring(7);
                        String s = jwtUtil.extractUsername(jwt);
                        UserContext.setUser(s);
                    }
                }catch (NullPointerException e) {
                    throw new TokenApiException("无认证信息");
                }

            }
            @Override
            public void postHandle(WebRequest request, ModelMap model) throws Exception {

            }

            @Override
            public void afterCompletion(WebRequest request, Exception ex) throws Exception {

            }
        });
    }
}
