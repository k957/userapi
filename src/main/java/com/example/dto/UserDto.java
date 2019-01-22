package com.example.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



public class UserDto {
	@Id
	@NotNull(message="please provide id, not needed its auto generated")
	@Min(value=1, message="id must be greater than 1")
	private long id;
	@NotEmpty(message ="please enter name")
	@NotNull(message ="please enter name")
	private String name;
	
	@NotEmpty(message ="please enter department name , no blanks")
	private String department;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	private String token;
	
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	
}
