package com.example.temanbelajar.exeption;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.temanbelajar.dto.ResponseError;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * AuthExceptionHandler
 */
public class AuthExceptionHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res,
            org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {

        ResponseError response = new ResponseError();

        response.setCode(HttpStatus.UNAUTHORIZED.value());
        response.setStatus(false);
        response.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());

        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(response.toString());

    }

    
}