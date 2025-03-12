package com.turatti.financy.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turatti.financy.services.MainServices;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class FinancyAuthController {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    MainServices service;

    @PostMapping("v1/auth/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody Map<String, String> data, HttpServletResponse response) throws Exception {
        try{
            PasswordEncoder passEncode = new BCryptPasswordEncoder();
            String encryptedPass = passEncode.encode(data.get("senha"));
            data.replace("senha", encryptedPass);
            String token = (String) rabbitTemplate.convertSendAndReceive("financy.auth", "financy.auth.register.key", data);
            Cookie tokenCookie = new Cookie("jj_tid", token);
            Cookie temporarySessionCookie = new Cookie("SSID", service.saltString(15));
            tokenCookie.setHttpOnly(true);
            tokenCookie.setSecure(true);
            temporarySessionCookie.setHttpOnly(true);
            tokenCookie.setMaxAge(14400);
            temporarySessionCookie.setSecure(false);
            response.addCookie(temporarySessionCookie);
            response.addCookie(tokenCookie);
            return ResponseEntity.ok("token: " + token);
        }catch(AmqpException ex){
            throw new Exception("Erro: 505" + ex);
        }
    }   
    
    @PostMapping("v1/auth/logar")
    public ResponseEntity<String> logarUsuario(@RequestBody Map<String, String> data, HttpServletResponse response) throws Exception{
        try{
            String token = (String) rabbitTemplate.convertSendAndReceive("financy.auth", "financy.auth.login.key", data);
            Cookie tokenCookie = new Cookie("jj_tid", token);
            Cookie temporarySessionCookie = new Cookie("SSID", service.saltString(15));
            tokenCookie.setHttpOnly(true);
            tokenCookie.setSecure(true);
            temporarySessionCookie.setHttpOnly(true);
            tokenCookie.setMaxAge(14400);
            temporarySessionCookie.setSecure(false);
            response.addCookie(temporarySessionCookie);
            response.addCookie(tokenCookie);
            return ResponseEntity.ok("token: " + token);
        }catch(AmqpException ex){
            throw new Exception("Erro: 505" + ex);
        }

    }
}
