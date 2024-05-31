package com.products.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.entities.UserEntity;
import com.products.repository.UserRespository;
import com.products.requests.UserRequest;
import com.products.services.UserServiceImpl;


@Controller
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping("/login")
	public String getLogin(Model model) {
		
		model.addAttribute("user", new UserEntity());
		return "login";
	}
	
	
	@GetMapping("/signup")
	public String getSignup(Model model) {
		
		model.addAttribute("user", new UserEntity());
		
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute("user") UserEntity user) {
		
		userService.save(user);
		
		return "redirect:/auth/login";

		
	}
	
	
	

}
