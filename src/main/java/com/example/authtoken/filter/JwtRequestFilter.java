package com.example.authtoken.filter;

import com.example.authtoken.service.UserDetailsServiceImpl;
import com.example.authtoken.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        //提取header
//        Enumeration<String> headerNames = request.getHeaderNames();
//        Map<String, String> headersMap = new HashMap<>();
//
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            headersMap.put(headerName, headerValue);
//        }
//        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }



        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null) {
            jwt = authorizationHeader.substring(7);
//            jwt = authorizationHeader;
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("jwt验证没有通过");
            }
        }

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Error in JWT filter: " + e.getMessage(), e);
        }
    }

//    private boolean shouldSkipAuthentication(HttpServletRequest request) {
//        // 在这里定义哪些路径应该跳过JWT验证
//        String path = request.getServletPath();
//        return "/authenticate".equals(path) || "/register".equals(path);
//    }

}
