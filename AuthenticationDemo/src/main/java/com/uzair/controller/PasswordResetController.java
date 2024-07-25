package com.uzair.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uzair.service.PasswordResetService;

@Controller
public class PasswordResetController {
	
	@Autowired
	private PasswordResetService service;

	@GetMapping("/passwordResetRequest")
	public String passwordResetRequest() {
		return "passwordResetRequest";
	}
	
	@PostMapping("/passwordResetRequest")
	public String requestToken(@RequestParam("email") String email, Model model){
		
		try {
			service.requestToken(email);
			model.addAttribute("message", "Reset token successfully sent to your mail.");
		}
		catch(IllegalArgumentException e){
			model.addAttribute("error", e.getMessage());
		}
		catch(Exception e) {
			model.addAttribute("error", "Error occurred while sending mail.");
		}
		
		return "passwordResetRequest";
	}
	
	@GetMapping("/resetPassword")
	public String resetPasswordForm(@RequestParam("token") String token, Model model) {
		model.addAttribute("token", token);
		return "resetPassword";
	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam("token") String token,
			@RequestParam("newPassword") String newPassword, 
			@RequestParam("confirmNewPassword") String confirmNewPassword,
			Model model) {
		
		if(!newPassword.equals(confirmNewPassword)) {
			model.addAttribute("error", "Password doesn't match.");
			return "resetPassword";
		}
		
		try {
			service.resetPassword(token, newPassword);
			model.addAttribute("message", "Password reset successfully.");
		}
		catch(IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
		}
		
		return "resetPassword";
	}
}
