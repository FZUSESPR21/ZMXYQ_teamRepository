package com.team.backend.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author : yangyu
 * @ClassName : com.team.backend.config.MybatisPlusConfig
 * @Description : MybatisPlus配置类
 * @date : 2021-04-22 23:40:54
 * Copyright  2021 user. All rights reserved.
 */

@MapperScan("com.team.backend.mapper")
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    // 分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

