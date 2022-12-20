package com.sm.service;

import com.sm.routes.CommonExceptionHandlerRoute;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class CheckService {

    public String pass() {
        System.out.println("Pass Data = " + CommonExceptionHandlerRoute.COUNTER.get());
        return "Pass Data:" + new Date();
    }

    public String fail() {
        System.out.println("Fail Data = " + CommonExceptionHandlerRoute.COUNTER.get());
        throw new RuntimeException("Exception for " + CommonExceptionHandlerRoute.COUNTER.get());
    }
}