package com.uzair.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uzair.entity.PasswordReset;
import com.uzair.entity.UserEntity;
import com.uzair.repository.PasswordResetRepository;
import com.uzair.repository.UserRepository;

@Service
public class PasswordResetService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordResetRepository resetRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void requestToken(String email) {
		
		UserEntity user = userRepo.findByEmail(email);
		
		if(user == null) {
			throw new IllegalArgumentException("No user found with email " + email );
		}
		
		String token = UUID.randomUUID().toString();
		
		PasswordReset reset = new PasswordReset();
		reset.setToken(token);
		reset.setUser(user);
		reset.setExpiryDate(new Date(System.currentTimeMillis() + 600000));
		resetRepo.save(reset);
		
		 String resetUrl = "http://localhost:8080/resetPassword?token=" + token;
		 
		 String text = "To reset your password, click the link given below:\n" + resetUrl;

		 emailService.sendMail(email, text);
	}
	
	
	public void resetPassword(String token, String newPassword) {
		
		PasswordReset pass = resetRepo.findByToken(token);
		
		if(pass == null || pass.getExpiryDate().before(new Date())) {
			throw new IllegalArgumentException("Invalid or expired token.");
		}
		
		UserEntity user = pass.getUser();
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepo.save(user);
		
		resetRepo.delete(pass);
	}
}
