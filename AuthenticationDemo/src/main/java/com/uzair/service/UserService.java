package com.uzair.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uzair.dto.UserDTO;
import com.uzair.entity.UserEntity;
import com.uzair.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void save(UserDTO userDTO) {                            //save the user details to database

		UserEntity user = new UserEntity(userDTO.getName(), userDTO.getEmail(), 
				passwordEncoder.encode(userDTO.getPassword()));
		repo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		                                                            //verify user from database during login
		UserEntity user = repo.findByEmail(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	public UserEntity findByEmail(String email) {
		return repo.findByEmail(email);
	}
}
