package com.team.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author : ccreater
 * @ClassName : com.team.backend.config.CORSConfig
 * @Description : 类描述
 * @date : 2021-05-10 20:01 Copyright  2021 ccreater. All rights reserved.
 */
@Configuration
public class CORSConfig {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH").allowedOrigins("http://127.0.0.1").allowCredentials(true).allowedHeaders("*");
      }
    };
  }
}
