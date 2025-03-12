package com.turatti.financy.data.config.Web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.turatti.financy.services.MainServices;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    MainServices mainServices;

    public AuthenticationInterceptor(MainServices mainServices) {
        this.mainServices = mainServices;
    }

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (mainServices.validateUserToken(request)) {
            if (uri.startsWith("/v1/auth")) {
                return false;
            }else{
                return true;
            }
        }else {
            response.sendRedirect("/v1/auth");
           return false; 
        }        

    }
}