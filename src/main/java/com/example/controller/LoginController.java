package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.JwtGenerator;
import com.example.service.iUserService;

@RestController
@RequestMapping("/login") 
public class LoginController {
	@Autowired
	private iUserService iuserService;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@Autowired
	private UserRepository userRepository;
	
	Jedis jedis = new Jedis("localhost");
	
	public LoginController(JwtGenerator jwtGenerator) {
		super();
		this.jwtGenerator = jwtGenerator;
	}
	
	@PostMapping
	public Map<String,String> login(@RequestBody final User jwtUser,HttpSession session)
	{
		Map<String, String> map = new HashMap<>();
		
		try {
			if(jedis.get(String.valueOf(jwtUser.getId()))==null) {
				User user = iuserService.loginUser(jwtUser.getUsername(), jwtUser.getPassword());
				if(user != null) {
					String token = jwtGenerator.generate(user);
					user.setToken(token);
					userRepository.save(user);
					jedis.set(token, jwtUser.getUsername());
					map.put("Token", token);
					map.put("Status", HttpStatus.CREATED.toString());
				}
		}else {
					map.put("Status", HttpStatus.UNAUTHORIZED.toString());
					map.put("Error Message", "Please check your credentials");
				}
			}catch (Exception e) {
				map.put("Status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
			}
		return map;
	}
	
}
