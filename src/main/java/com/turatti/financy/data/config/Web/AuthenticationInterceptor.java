package com.turatti.financy.data.config.Web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.turatti.financy.services.MainServices;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final MainServices mainServices;

    public AuthenticationInterceptor(MainServices mainServices) {
        this.mainServices = mainServices;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.startsWith("/auth") || uri.equals("/")) {
            return true;
        }

        // Validate user token for protected routes
        // if (mainServices.validateUserToken(request)) {
        //     return true;
        // } else {
        //     response.sendRedirect("/auth");
            return false; // Stop further processing
        // }
    }
}