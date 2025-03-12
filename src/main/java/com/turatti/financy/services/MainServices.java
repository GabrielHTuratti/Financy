package com.turatti.financy.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class MainServices {   

    @Autowired
    RabbitTemplate rabbitTemplate;

    public String saltString(int size){
        String alpString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder stb = new StringBuilder();
        for(int i = 0; i < size; i++){
            int index = (int)(alpString.length() * Math.random());

            stb.append(alpString.charAt(index));
        }
        return stb.toString();

    }

    public Boolean validateUserToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return false;
        }
        Optional<Cookie> tokenCookie = Arrays.stream(cookies)
                                       .filter(cookie -> "jj_tid".equals(cookie.getName()))
                                       .findFirst();
        if(tokenCookie.isEmpty()){
            return false;
        }
        String token = tokenCookie.get().getValue();
        @SuppressWarnings("unchecked")
        String verificationData = (String) rabbitTemplate.convertSendAndReceive("financy.auth", "financy.auth.validate.key",token);
        if(verificationData == null){
            return false;
        }else{
            request.setAttribute("username", verificationData);
            return true;
        }

    }
}
