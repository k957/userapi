package com.example.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	
	List<User> findByName(String name);
	
	
	List<User> findByDepartment(String department);
	
	public List<User> findByIdAndName(Long id,String name);

	
	@Query("select name from User where id = :id")
	String findNameById(@Param("id") Long id);
	
	@Query("select department from User where id = :id")
	String findDepartmentById(@Param("id")Long id);
	
	//@Query("select u from User where username = :username and password = :password")
	User findByUsernameAndPassword(String username, String password);
	
	@Query("select token from User where id = :id")
	String findTokenById(@Param("id")Long id);
}
