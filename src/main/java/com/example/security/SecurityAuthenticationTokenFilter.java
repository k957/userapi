package com.example.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.model.SecurityAuthenticationToken;

public class SecurityAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter{
	
	
	public SecurityAuthenticationTokenFilter()
	{
		super("/api/**");
	}
	//decode token here
	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws AuthenticationException, IOException, ServletException {
		
		String authenticationToken=getToken(httpServletRequest);
		SecurityAuthenticationToken token = new SecurityAuthenticationToken(authenticationToken);
		return getAuthenticationManager().authenticate(token);
	}

	public String getToken(HttpServletRequest httpServletRequest) {
		String header = httpServletRequest.getHeader("Authorization");


        if (header == null || !header.startsWith("Token ")) {
            throw new RuntimeException("JWT Token is missing");
        }

        String authenticationToken = header.substring(6);
        return authenticationToken;
	}
	 @Override
	    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
	        super.successfulAuthentication(request, response, chain, authResult);
	        chain.doFilter(request, response);
	    }
}
