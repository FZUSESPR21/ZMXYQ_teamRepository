package com.team.backend;

import com.team.backend.util.Base64Util;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ServletComponentScan("com.team.backend.filter")
@SpringBootApplication
public class BackendApplication implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("file:"+Paths.get(System.getProperty("user.dir"),"static",".").toString());
    registry.addResourceHandler("/public/**").addResourceLocations("file:"+Paths.get(System.getProperty("user.dir"),"public",".").toString());
  }

}
