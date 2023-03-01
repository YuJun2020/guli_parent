package com.yj.edu.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.yj.edu.mapper")
public class EduConfig {

    @Bean
    public ISqlInjector iSqlInjector(){
        return new LogicSqlInjector();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor(){return new PaginationInterceptor();}
}
