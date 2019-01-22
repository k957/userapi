package com.example.assembler;

import com.example.dto.UserDto;
import com.example.model.User;

public class UserAssembly {
	public static User createUserEntity(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setDepartment(userDto.getDepartment());
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		user.setToken(userDto.getToken());
		return user;
	}
}
