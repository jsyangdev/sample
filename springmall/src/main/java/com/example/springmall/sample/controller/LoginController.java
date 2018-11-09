package com.example.springmall.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.springmall.sample.service.SampleService;
import com.example.springmall.sample.vo.Sample;

@Controller
public class LoginController {
	@Autowired
	private SampleService sampleService;
	
	// 1-1. 로그인폼
	@RequestMapping(value="/sample/loginSample", method=RequestMethod.GET)
	public String loginSample() {
		System.out.println(":::LoginController.loginSample() START:::");
		System.out.println(":::LoginController.loginSample() END:::");
		
		return "/sample/loginSample";
	}
	
	// 1-2. 로그인 액션
	@RequestMapping(value="/sample/loginSample", method=RequestMethod.POST)
	public String login(Sample sample, HttpSession session) {
		System.out.println(":::LoginController.login() START:::");
		Sample loginSample = sampleService.getSampleForLogin(sample);
		System.out.println(loginSample+"<---loginSample");
		// request.getHttpSession();
		session.setAttribute("sample",loginSample);
		// session.getAttribute("session");
		System.out.println(":::LoginController.login() END:::");
		
		return "redirect:/sample/index";
	}
	
	// 2. 로그아웃
	@RequestMapping(value="/sample/logoutSample", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
				
		return "redirect:/sample/loginSample";
	}
	
}
