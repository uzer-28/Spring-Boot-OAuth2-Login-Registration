package com.uzair.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.uzair.dto.UserDTO;
import com.uzair.entity.UserEntity;
import com.uzair.repository.UserRepository;
import com.uzair.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService service;


	@GetMapping("/")
	public String home(Model model, Principal principal) {
		
		if(principal != null) {
			
			if(principal instanceof OAuth2AuthenticationToken) {
				OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) principal;
				OAuth2User authUser = auth.getPrincipal();
				authUser = authService.loadUser(authUser);
				String name = authUser.getAttribute("name");
				model.addAttribute("name", name);
			}
			else {			
			UserEntity user = service.findByEmail(principal.getName());
			model.addAttribute("name", user.getName());
		
			}
		}
		return "index";
	}
	
	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
}
