package com.example.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.model.SecurityAuthenticationToken;
import com.example.model.SecurityUserDetails;
import com.example.model.User;

@Component
public class SecurityAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

	@Autowired
	private SecurityValidator validator;
	
	@Override
	public boolean supports(Class<?> aClass) {
		// TODO Auto-generated method stub
		return SecurityAuthenticationToken.class.isAssignableFrom(aClass);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails user, UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
			throws AuthenticationException {
		SecurityAuthenticationToken securityAuthenticationToken = (SecurityAuthenticationToken) usernamePasswordAuthenticationToken;
		String token = securityAuthenticationToken.getToken();
		//System.out.println(token +" security authentication provider");
		User user=null;
		SecurityUserDetails userdetails=null;
	    user=validator.validate(token);
	    //System.out.println(user+ " securityAuthentication provider");
		if(user==null) {
			throw new RuntimeException("JWT token is incorrect");
		}
		List<GrantedAuthority> grantedAuthorities= AuthorityUtils.commaSeparatedStringToAuthorityList(user.getDepartment());
		
		userdetails=new SecurityUserDetails(user.getUsername(),user.getPassword(),user.getId(),user.getName(),token,grantedAuthorities);
		
		return userdetails;
	}

}
