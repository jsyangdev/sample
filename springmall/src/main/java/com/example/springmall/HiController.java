package com.example.springmall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HiController {
	// 서블릿 역할할 것임
	// 요청처리
	@RequestMapping("/hi")
	public String hi() {
		System.out.println("Hi Spring Boot !");
		return "hi";	// forward : 어디로? WEB-INF/jsp/hi.jsp
	}
}