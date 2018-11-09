package com.example.springmall.sample.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springmall.sample.service.SampleService;
import com.example.springmall.sample.vo.Sample;

@Controller
public class sampleController {
	@Autowired
	private SampleService sampleService;
	// 4-1. 수정폼
	@RequestMapping(value="/sample/modifySample", method=RequestMethod.GET)
	public String modifySample(Model model, @RequestParam(value="sampleNo", required=false) int sampleNo) {
		System.out.println(":::SampleService.modifySample() START:::");
		Sample sample = sampleService.getSample(sampleNo);
		model.addAttribute("sample", sample);
		System.out.println(":::SampleService.modifySample() END:::");
		
		return "/sample/modifySample";		// jquery필요, bootstrap필요, Sample command객체 사용할 것을 염두
	}

	// 4-2. 수정 액션
	@RequestMapping(value="/sample/modifySample", method=RequestMethod.POST)
	public String modifySample(Sample sample) {
		System.out.println(":::SampleService.modifySample() START:::");
		int updateResult = sampleService.modeifySample(sample);
		if(updateResult == 1) {System.out.println("수정 성공 !");}
		else {System.out.println("수정 실패 !");}
		System.out.println(":::SampleService.modifySample() END:::");
		
		return "redirect:/sample/sampleList";
	}
	
	// 3-1. 입력 폼
	@RequestMapping(value="/sample/addSample", method=RequestMethod.GET)
	public String addSample() {
		System.out.println(":::SampleService.addSample() START:::");
		System.out.println(":::SampleService.addSample() END:::");
		
		return "/sample/addSample";
	}
	
	// 3-2. 입력 액션
	@RequestMapping(value="/sample/addSample", method=RequestMethod.POST)
	public String addSample(Sample sample) {
		// Sample 친구들 존재할 수 있다. base: Sample 테이블
		// command객체의 멤버변수  == input 태그의 name속성  --> 표준 setter를 호출
		int row = sampleService.addSample(sample);
		// view 없으니까 바로 리턴
		if(row == 1) {System.out.println("입력 성공 !");}
		else {System.out.println("입력 실패 !");}

		return "redirect:/sample/sampleList";
	}
	
	// 2. 삭제
	@RequestMapping(value="/sample/removeSample", method=RequestMethod.GET)
	public String removeSample(@RequestParam(value="sampleNo") int sampleNo) {	// sampleNo에 @RequestParam(value="sampleNo")
		sampleService.removeSample(sampleNo);
		
		return "redirect:/sample/sampleList";	//	view 없다. redirect가 있으면 뷰 네임이 아니다.(= response.sendRedirect())
	}
	
	// 1. 샘플목록
	@RequestMapping(value="/sample/sampleList", method=RequestMethod.GET)
	public String sampleList(Model model, @RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage) {	// Model model = new Model();
		System.out.println(":::SampleService.sampleList() START:::");
		int rowsPerPage = 10;
		int lastPage;	// 마지막 페이지
		// 넘어오는 currentPage
		System.out.println(currentPage+"<---currentPage");
		// 총 행 수 구하는 메서드 호출
		int totalRowCount = sampleService.getSampleTotalRowCount();
		// 리스트 가지고 오는 메서드 호출
		List<Sample> sampleList = sampleService.getSampleAll(currentPage, rowsPerPage);	
		// 마지막 페이지 구하기
		lastPage = totalRowCount/rowsPerPage;
		if(totalRowCount%rowsPerPage != 0) {lastPage++;}
		// request객체 대신 model객체에 값 셋팅		( # model의 생명주기=>view까지 )
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalRowCount", totalRowCount);
		model.addAttribute("sampleList", sampleList);
		model.addAttribute("lastPage", lastPage);
		System.out.println(":::SampleService.sampleList() END:::");
		
		return "/sample/sampleList";
	}

}
