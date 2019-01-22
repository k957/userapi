package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.dto.UserDto;
import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserServiceImpl implements iUserService{
	
	UserDto u=new UserDto();
	Long id=u.getId();
	String name=u.getName();
	@Autowired
	private UserRepository userRepository;
	
	//private RedisTemplate<Long, User> redisTemplate;
	
	private HashOperations hashOperations;
	
	
		
	

	@Override
	public String findByIdAndName(Long id, String name) {
		
			List<User> user=userRepository.findByIdAndName(id, name);
			String department=(String)user.get(0).getDepartment();
		    return department;
	}

	@Override
	public User loginUser(String username, String password) {
		User user=userRepository.findByUsernameAndPassword(username, password);
		return user;
	}

	@Override
	public String findByToken(long id) {
		String usertoken=userRepository.findTokenById(id);
		return usertoken;
	}
	
	
	@Override
	public void save(User user) {
		
		hashOperations.put("User", user.getId(), user);
	}
	
	@Override
	public Map<Long, User> findall() {
	
		return hashOperations.entries("User");
	}

	@Override
	public User update(User user) {
		save(user);
		return user;
		
	}

	@Override
	public void delete(Long id) {
		hashOperations.delete("User", id);
		
	}

	
}
