package com.springboot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api.model.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long>{
	UserInfo findByUsername(String username); 
}
