package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assembler.UserAssembly;
import com.example.dto.UserDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.iUserService;

import redis.clients.jedis.Jedis;


@RestController
@RequestMapping("/api")
public class UserController {
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	public iUserService iuserservice;
	
	public String err="JWT token is missing";
	
	Jedis jedis = new Jedis("localhost");
	
	
	@GetMapping("/user")
	public ResponseEntity<?> getAllusers(HttpServletRequest httpServletRequest)
	{
	    String token= httpServletRequest.getHeader("Authorization");
	    String subtoken=token.substring(6);
	    String username=jedis.get(subtoken);
	    if(username!=null)
	    {
	    	List<User> user=userRepository.findAll();
	    	HttpHeaders responseHeader = new HttpHeaders();
	    	return new ResponseEntity<>(user, responseHeader, HttpStatus.OK);
	    }else {
	    	return new ResponseEntity<>(err,HttpStatus.UNAUTHORIZED);
	    }
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto,HttpServletRequest httpServletRequest){
	   
		String token= httpServletRequest.getHeader("Authorization");
	    String subtoken=token.substring(6);
	    String username=jedis.get(subtoken);
	   
	    //System.out.println("========================"+token);
	    if(username!=null) {
	
		User user = UserAssembly.createUserEntity(userDto);
		//System.out.println("========================"+user.getDepartment());
		userRepository.save(user);
		HttpHeaders responseHeader = new HttpHeaders();
		return new ResponseEntity<>(user, responseHeader, HttpStatus.CREATED);
		
	    }
	    else
	    {
	    	return new ResponseEntity<>(err,HttpStatus.UNAUTHORIZED);
	    }
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable(value="id") Long id, @Valid @RequestBody UserDto userDto,HttpServletRequest httpServletRequest)
	{
		
		String token= httpServletRequest.getHeader("Authorization");
	    String subtoken=token.substring(6);
	    String username=jedis.get(subtoken);
	    if(username!=null) {
	    	User existingUser=userRepository.findById(userDto.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userDto.getId()));
			
			
			existingUser.setDepartment(userDto.getDepartment());
			existingUser.setName(userDto.getName());
			
			User updatedUser= userRepository.save(existingUser);
			//System.out.println(updatedUser);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(updatedUser, responseHeader, HttpStatus.CREATED);
			
	    }
	    else
	    	return new ResponseEntity<>(err,HttpStatus.UNAUTHORIZED);
	}
	
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value="id")Long id,HttpServletRequest httpServletRequest){
		String token= httpServletRequest.getHeader("Authorization");
	    String subtoken=token.substring(6);
	    String username=jedis.get(subtoken);
	  
	    if(username!=null) {
	    	User user=userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	    	userRepository.delete(user);
		
	    	return ResponseEntity.ok().build();
	    }else {
	    	return new ResponseEntity<>(err,HttpStatus.UNAUTHORIZED);
	    }

	}
	
	@GetMapping("/users/name/{name}")
	public ResponseEntity<?> findUserByName(@PathVariable(value="name")String name,HttpServletRequest httpServletRequest)
	{
		String token= httpServletRequest.getHeader("Authorization");
	    String subtoken=token.substring(6);
	    String username=jedis.get(subtoken);
	   
	    if(username!=null) {
	    	
			List<User> userByName=userRepository.findByName(name);
	    	HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(userByName, responseHeader, HttpStatus.OK);
	    	
	    }else {
	    	return new ResponseEntity<>(err,HttpStatus.UNAUTHORIZED);
	    }	
	}
	
	@GetMapping("/users/department/{department}")
	public ResponseEntity<?> findUserByDepartment(@PathVariable(value="department")String department,HttpServletRequest httpServletRequest)
	{
		String token= httpServletRequest.getHeader("Authorization");
	    String subtoken=token.substring(6);
	    String username=jedis.get(subtoken);
	    
	    if(username!=null) {
	    	List<User> userByDepartment= userRepository.findByDepartment(department);
	    	HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<>(userByDepartment, responseHeader, HttpStatus.OK);
	    }else {
	    	return new ResponseEntity<>(err,HttpStatus.UNAUTHORIZED);
	    }
	}
	
	@GetMapping("/user/name/{id}")
	public String findNameById(@PathVariable(value="id")Long id,HttpServletRequest httpServletRequest)
	{
		String token= httpServletRequest.getHeader("Authorization");
	    String subtoken=token.substring(6);
	    String username=jedis.get(subtoken);
	    
	    if(username!=null) {
	    	String name=userRepository.findNameById(id);
	    	return name;
	    }else {
	    	return err;
	    }
	}
	
	@GetMapping("/user/dept/{id}")
	public String findDepartmentById(@PathVariable(value="id")Long id,HttpServletRequest httpServletRequest)
	{
		String token= httpServletRequest.getHeader("Authorization");
	    String subtoken=token.substring(6);
	    String username=jedis.get(subtoken);
	    
	    if(username!=null) {
	    	try {
	    		String department=userRepository.findDepartmentById(id);
		
	    		return department;
		}catch (NullPointerException e) {
			
				 String hey=new ResourceNotFoundException("User", "id", id).toString();
				 return (String)hey;
				 
		}
	   }else {
		   return err;
	   }
	}
	
	@GetMapping("/user/{id}/{name}")
	public String idAndName(@PathVariable(value="id")Long id, @PathVariable(value="name")String name,HttpServletRequest httpServletRequest)
	{
		String token= httpServletRequest.getHeader("Authorization");
	    String subtoken=token.substring(6);
	    String username=jedis.get(subtoken);
	    
	    if(username!=null) {
	    	String department=iuserservice.findByIdAndName(id, name);
	    	return department;
	    }else {
	    	return err;
	    }
	}
}
