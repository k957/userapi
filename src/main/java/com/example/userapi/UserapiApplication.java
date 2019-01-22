package com.example.userapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.model.User;

@SpringBootApplication
@ComponentScan("com.example.controller")
@EntityScan("com.example.model")
@EnableJpaRepositories("com.example.repository")
@ComponentScan("com.example.service")
@EnableJpaAuditing
@ComponentScan("com.example.security")
@ComponentScan("com.example.config")

public class UserapiApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(UserapiApplication.class, args);
	}
}
