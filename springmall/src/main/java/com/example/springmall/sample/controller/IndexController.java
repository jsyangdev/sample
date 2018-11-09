package com.example.springmall.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	@RequestMapping(value="/sample/index", method=RequestMethod.GET)
	public String indexSample(HttpSession session) {
		System.out.println(":::IndexController.indexSample() START:::");
		session.getAttribute("sample");
		System.out.println(":::IndexController.indexSample() END:::");
		
		return "/sample/index";
	}
	

}
