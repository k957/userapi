package com.example.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.model.User;
import com.example.security.SecurityAuthenticationEntryPoint;
import com.example.security.SecurityAuthenticationProvider;
import com.example.security.SecurityAuthenticationTokenFilter;
import com.example.security.SecuritySuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class JwtSecurityUserConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SecurityAuthenticationProvider authenticationProvider;
	
	@Autowired
	private SecurityAuthenticationEntryPoint entryPoint;
	
	@Bean
	public SecurityAuthenticationTokenFilter  authenticationTokenFilter()
	{
		SecurityAuthenticationTokenFilter filter =new SecurityAuthenticationTokenFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(new SecuritySuccessHandler());
		return filter;
	}
	
	@Bean
	public AuthenticationManager authenticationManager()
	{
		return new ProviderManager(Collections.singletonList(authenticationProvider));
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable() 
		.authorizeRequests().antMatchers("**/api/**").authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(entryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		
		http.headers().cacheControl();
	}
	
/*	@Bean	
 * JedisConnectionFactory jedisConnectionFactory(){
		return new JedisConnectionFactory();//custom hostname and port number here
	}
	@Bean
	RedisTemplate<Long, User> redisTemplate()
	{
		RedisTemplate<Long, User> redisTemplate=new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}*/
}
