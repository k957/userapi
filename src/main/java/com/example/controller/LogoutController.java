package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.example.exception.ResourceNotFoundException;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.SecurityAuthenticationTokenFilter;
import com.example.security.SecurityValidator;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/user/logout")
public class LogoutController {

	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private SecurityAuthenticationTokenFilter securityAuthenticationTokenFilter;
	
	@Autowired
	private SecurityValidator securityValidator;
	
	Jedis jedis = new Jedis("localhost");
	
	@DeleteMapping()
	public Map<String, Object> logout(HttpServletRequest request)
	{
		
		Map<String,Object> map=new HashMap<>(); 
		try {
			String token=securityAuthenticationTokenFilter.getToken(request);
			User user = securityValidator.validate(token);
			user.setToken(null);
			userRepository.save(user);
			jedis.del(token);
			map.put("status", HttpStatus.OK);
			map.put("Message","Successfully logout" );
			
			
		}catch (Exception e) {
			
		}
		return map;
	}
}
