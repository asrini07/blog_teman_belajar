package com.example.temanbelajar.config;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateTimeImpl implements DateTime{

    @Override
    public Date getCurrentDate() {
        return new Date();
    }
    
}