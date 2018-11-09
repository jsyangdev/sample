package com.example.springmall.sample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springmall.sample.vo.Sample;

@RestController
public class RestSampleController {	// REST API : 내가 가지고 있는 DB의 결과물을 누구나 가지고 갈 수 있게
	@RequestMapping(value="/sample/getRestSample")
	public Sample getRestSample() {
		
		
		
		return new Sample(1,"guest","1234");	// {"sampleNo":1,"sampleId":"guest","samplePw":"1234"} ---> ajax 할 때 활용
	}
	
}