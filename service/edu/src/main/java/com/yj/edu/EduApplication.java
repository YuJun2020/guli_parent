package com.yj.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yj"})
public class EduApplication {
    public static void main(String[] args){
        SpringApplication.run(EduApplication.class,args);
    }
}
