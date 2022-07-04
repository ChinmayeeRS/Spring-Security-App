package com.springboot.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.api.model.Role;
import com.springboot.api.model.UserInfo;
import com.springboot.api.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//GO TO repo and check weather given username is present in the users table or not 
		UserInfo myUser = userRepository.findByUsername(username); //my DB user 
		System.out.println(myUser.getName());
		System.out.println(myUser.getUsername());
		System.out.println(myUser.getPassword());
		List<Role> roles = myUser.getRoles();
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for(Role r: roles) {
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(r.getRoleName());
			authorities.add(sga);
		}
		//Spring also has 1 user 
	    
		return new User(myUser.getUsername(), myUser.getPassword(), authorities);
		 
		 
	}

	
}
