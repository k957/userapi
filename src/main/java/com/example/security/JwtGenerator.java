package com.example.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtGenerator {

	public String generate(User user) {
		
		
		 Date date = new Date();
		    long t = date.getTime();
		    Date expirationTime = new Date(t + 5000l); 
		Claims claims=Jwts.claims().
				setSubject(user.getUsername());
		claims.put("id",String.valueOf(user.getId()));
		claims.put("department", user.getDepartment());
		claims.put("password", user.getPassword());
		claims.put("name", user.getName());
		
		//System.out.println(claims+" Jwt generator");
		
		
		return Jwts.builder().setExpiration(expirationTime)
		.setClaims(claims)
		.signWith(SignatureAlgorithm.HS512, "youtube")
		.compact();
		 
		
		
	}

}
