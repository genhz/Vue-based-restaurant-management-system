package com.restaurant.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String VALID_TOKEN = "80c483d59ca86ad0393cf8a98416e2a1";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("token");
        if (VALID_TOKEN.equals(token)) {
            return true;
        }
        response.setStatus(401);
        response.getWriter().write("{\"message\":\"Unauthorized\"}");
        return false;
    }
}
