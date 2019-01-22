package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.example.controller"))
				.paths(postPaths())
				.build()
				.apiInfo(metaInfo());
	}

	private Predicate<String> postPaths() {
		
		return or(regex("/api.*"),regex("/user.*"),regex("/login"),regex("/token"));
	}

	private ApiInfo metaInfo() {
		ApiInfo apiInfo=new ApiInfo("Local-Search-Directory", "A spring boot Rest API using Redis,JWT,MySql,Spring Boot Admin", "1.0", "Terms of Service",  
				new Contact("Kanav Mehra", "",
                "kanavmehra957@gmail.com") , "Apache License Version 2.0",
                "https://www.apache.org/licesen.html");
		return apiInfo;
	}
	
}
