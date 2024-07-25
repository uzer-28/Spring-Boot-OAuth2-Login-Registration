package com.uzair.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.uzair.dto.UserDTO;
import com.uzair.service.UserService;

@Controller
public class AuthenticationController {
	
	@Autowired
	private UserService service;

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		return "SignUp";
	}
	
	@PostMapping("/registerUser")
	public String register(@ModelAttribute("userDTO") UserDTO user) {
		service.save(user);
		return "redirect:/login?success";
	}
}
