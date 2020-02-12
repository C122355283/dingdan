package com.xie.dingdan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@WebAppConfiguration
public class DingdanApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DingdanApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DingdanApplication.class);
    }
}
