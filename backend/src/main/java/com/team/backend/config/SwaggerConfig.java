package com.team.backend.config;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.config.SwaggerConfig
 * @Description : Swagger配置类
 * @date : 2021-04-22 23:44:24 Copyright  2021 user. All rights reserved.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .groupName("yangyu")
        .enable(true)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.team.backend.controller"))
        .build();
  }

  //配置Swagger信息
  private ApiInfo apiInfo() {
    Contact contact = new Contact("yangyu", "https://www.cnblogs.com/yangyu-huang/",
        "1849588816@qq.com");
    return new ApiInfo(
        "yangyu的SwaggerAPI文档",
        "世界那么大，出去看看吧",
        "v1.0",
        "https://www.cnblogs.com/yangyu-huang/",
        contact,
        "Apache 2.0",
        "http://www.apache.org/licenses/LICENSE-2.0",
        new ArrayList<>()
    );
  }
}

