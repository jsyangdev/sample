package com.example.springmall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
	// 서블릿 역할할 것임
	// 요청처리
	@RequestMapping("/hi")
	public void hi() {
		System.out.println("Hi Spring Boot !");
	}
}