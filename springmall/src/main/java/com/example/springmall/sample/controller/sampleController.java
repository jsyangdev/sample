package com.example.springmall.sample.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springmall.paging.Paging;
import com.example.springmall.sample.service.SampleService;
import com.example.springmall.sample.vo.Sample;

@Controller
public class sampleController {
	@Autowired
	private SampleService sampleService;
	
	// 1. 샘플목록
	@RequestMapping(value="/sample/sampleList", method=RequestMethod.GET)
	public String sampleList(Model model, @RequestParam(value="currentPage", required=false) Integer currentPage) {	// Model model = new Model();
		System.out.println(":::SampleService.sampleList() START:::");
		int rowsPerPage = 10;
		int lastPage;	// 마지막 페이지
		// 넘어오는 currentPage
		if(currentPage != null) {currentPage = (int)RequestParam("currentPage");}
		currentPage = 1;
		System.out.println(currentPage+"<---currentPage");
		// 총 행 수 구하는 메서드 호출
		int totalRowCount = sampleService.getSampleTotalRowCount();
		// 리스트 가지고 오는 메서드 호출
		List<Sample> sampleList = sampleService.getSampleAll(currentPage, rowsPerPage);		
		// 마지막 페이지 구하기
		lastPage = totalRowCount/rowsPerPage;
		if(totalRowCount%rowsPerPage != 0) {lastPage++;}
		// request객체 대신 model객체에 값 셋팅		# model의 생명주기=>view까지
		model.addAttribute(totalRowCount);
		model.addAttribute("sampleList", sampleList);
		model.addAttribute(lastPage);
		System.out.println(":::SampleService.sampleList() END:::");
		
		return "/sample/sampleList";
	}
	
	private Object RequestParam(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	// 2. 삭제
	@RequestMapping(value="/sample/removeSample", method=RequestMethod.GET)
	public String removeSample(@RequestParam(value="sampleNo") int sampleNo) {	// sampleNo에 @RequestParam(value="sampleNo")
		sampleService.removeSample(sampleNo);
		
		return "redirect:/sample/sampleList";	// 	// view 없다. redirect가 있으면 뷰 네임이 아니다.(= response.sendRedirect())
	}

}
