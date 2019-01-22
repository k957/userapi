package com.example.security;

import org.springframework.stereotype.Component;


import com.example.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class SecurityValidator {
	private String secret="youtube";
	
	
	public User validate(String token) {
		User user= new User();
		try {
		Claims body=Jwts.parser().
				setSigningKey(secret).
				parseClaimsJws(token).getBody();
		
		
		user.setUsername(body.getSubject());
		user.setId(Long.parseLong((String) body.get("id")));
		user.setName((String) body.get("name"));
		user.setPassword((String) body.get("password"));
		user.setDepartment((String) body.get("department"));
		}catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(user+" security validator ");
		return user;
		
	}
}
