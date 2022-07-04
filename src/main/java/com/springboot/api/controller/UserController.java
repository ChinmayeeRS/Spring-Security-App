package com.springboot.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.api.model.Role;
import com.springboot.api.model.UserInfo;
import com.springboot.api.repository.RoleRepository;
import com.springboot.api.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder; 
	
	@Autowired
	private RoleRepository roleRepository; 
	
	@PostMapping("/user") 
	public UserInfo postUser(@RequestBody UserInfo user, @RequestParam("idString") String ids) {
		
		List<Role> roles = new ArrayList<>();
		
		String[] idArry = ids.split(","); //["22","23"]
		for( String id : idArry) {
			 Role role = roleRepository.getById(Long.parseLong(id));
			 roles.add(role);
		}
		String passEncoded = encoder.encode(user.getPassword()); 
		user.setPassword(passEncoded);
		user.setRoles(roles);
		return userRepository.save(user); 
	}
	
	@PostMapping("/role")
	public Role postRole(@RequestBody Role role) {
		role.setRoleName(role.getRoleName().toUpperCase());
		return roleRepository.save(role); 
	}
	
	@GetMapping("/role")
	public List<Role> getAllRoles(){
		return roleRepository.findAll();
	}
}
