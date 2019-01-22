package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.dto.UserDto;
import com.example.model.User;

public interface iUserService {
	
	
	 String findByIdAndName(Long id,String name);
	 User loginUser(String username,String password);
	 String findByToken(long id);
	 
	 void save(User user);
	 Map<Long,User> findall();
	 User update(User user);
	 void delete(Long id);
	 
	
}
