package com.example.springmall.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
	
	public String login(HttpSession session) {
		// request.getHttpSession();
		session.setAttribute("", "");
		session.getAttribute("");
		
		return "redirect:/";
	}
	
	public String logout(HttpSession session) {
		session.invalidate();
				
		return "redirect:/";
	}
	
}
