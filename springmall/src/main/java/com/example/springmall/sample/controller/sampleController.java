package com.example.springmall.sample.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.springmall.sample.service.SampleService;
import com.example.springmall.sample.vo.Sample;
import com.example.springmall.sample.vo.SampleAndSampleFile;
import com.example.springmall.sample.vo.SampleRequest;

@Controller
public class sampleController {
	@Autowired
	private SampleService sampleService;
	
	// 5. 업로드 된 파일 삭제
	
	public String deleteUploadedFile() {
		
		
		
		return null;
	}
	
	
	
	
	// 5. 리스트에서 검색 액션
/*	@RequestMapping(value="/sample/sampleList", method=RequestMethod.POST)
	public String searchSample(Model model, @RequestParam(value="selectValue", required=false) String selectValue, @RequestParam(value="sampleId", required=false) String sampleId) {
		System.out.println(":::sampleController.searchSample() START:::");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectValue", selectValue);
		map.put("sampleId", sampleId);
		System.out.println(selectValue+"<---sampleId");
		System.out.println(sampleId+"<---sampleId");
		List<Sample> sampleList = sampleService.getSampleOfSearch(map);
		System.out.println(sampleList+"<--sampleList");
		model.addAttribute("sampleList", sampleList);
		System.out.println(":::sampleController.searchSample() END:::");
		
		return "/sample/sampleList";
		
	}*/

	
	// 4-1. 수정폼
	@RequestMapping(value="/sample/modifySample", method=RequestMethod.GET)
	public String modifySample(Model model, @RequestParam(value="sampleNo", required=false) int sampleNo) {
		System.out.println(":::sampleController.modifySample() START:::");
		Sample sample = sampleService.getSample(sampleNo);
		model.addAttribute("sample", sample);
		System.out.println(":::sampleController.modifySample() END:::");
		
		return "/sample/modifySample";		// jquery필요, bootstrap필요, Sample command객체 사용할 것을 염두
	}

	// 4-2. 수정 액션
	@RequestMapping(value="/sample/modifySample", method=RequestMethod.POST)
	public String modifySample(Sample sample) {
		System.out.println(":::sampleController.modifySample() START:::");
		int updateResult = sampleService.modeifySample(sample);
		if(updateResult == 1) {System.out.println("수정 성공 !");}
		else {System.out.println("수정 실패 !");}
		System.out.println(":::sampleController.modifySample() END:::");
		
		return "redirect:/sample/sampleList";
	}
	
	// 3-1. 입력 폼
	@RequestMapping(value="/sample/addSample", method=RequestMethod.GET)
	public String addSample() {
		System.out.println(":::sampleController.addSample() START:::");
		System.out.println(":::sampleController.addSample() END:::");
		
		return "/sample/addSample";
	}
	
	// 3-2. 입력 액션
	@RequestMapping(value="/sample/addSample", method=RequestMethod.POST)
	public String addSample(SampleRequest sampleRequest, MultipartHttpServletRequest request) {
		System.out.println(":::sampleController.addSample() START:::");
		// Sample 친구들 존재할 수 있다. (베이스: Sample 테이블)
		// command객체의 멤버변수  == input 태그의 name속성  ---> 표준 setter를 호출
		System.out.println("sampleRequest.multipartFile: "+sampleRequest.getMultipartFile());
		int row = sampleService.addSample(sampleRequest, request);
		// view 없으니까 바로 리턴
		if(row == 1) {System.out.println("입력 성공 !");}
		else {System.out.println("입력 실패 !");}
		System.out.println(":::sampleController.addSample() END:::");

		return "redirect:/sample/sampleList";
	}
	
	// 2. 삭제
	@RequestMapping(value="/sample/removeSample", method=RequestMethod.GET)
	public String removeSample(@RequestParam(value="sampleNo") int sampleNo) {	// sampleNo에 @RequestParam(value="sampleNo")

		sampleService.removeSample(sampleNo);
		
		return "redirect:/sample/sampleList";	//	view 없다. redirect가 있으면 뷰 네임이 아니다.(= response.sendRedirect())
	}
	
	// 1. 샘플목록  + 검색
	@RequestMapping(value="/sample/sampleList", method=RequestMethod.GET)
	public String sampleList(Model model, @RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage,
			@RequestParam(value="selectValue", required=false) String selectValue, @RequestParam(value="sampleId", required=false) String sampleId) {	// Model model = new Model();
		System.out.println(":::sampleController.sampleList() START:::");
		List<Sample> sampleList = null;
		int rowsPerPage = 10;
		int lastPage;	// 마지막 페이지
		// 넘어오는 currentPage
		System.out.println(currentPage+"<---currentPage");
		// 넘어오는 검색폼의 값들 출력
		System.out.println(selectValue+"<---selectValue");
		System.out.println(sampleId+"<---sampleId");
		// 총 행 수 구하는 메서드 호출
		int totalRowCount = sampleService.getSampleTotalRowCount();
		// 리스트 가지고 오는 메서드 호출
		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectValue", selectValue);
		map.put("sampleId", sampleId);
		map.put("currentPage", currentPage);
		map.put("rowsPerPage", rowsPerPage);
		System.out.println(selectValue+"<---sampleId");
		System.out.println(sampleId+"<---sampleId");

		sampleList = sampleService.getSampleAll(map);

		// 마지막 페이지 구하기
		lastPage = totalRowCount/rowsPerPage;
		if(totalRowCount%rowsPerPage != 0) {lastPage++;}
		// request객체 대신 model객체에 값 셋팅		( # model의 생명주기=>view까지 )
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalRowCount", totalRowCount);
		model.addAttribute("sampleList", sampleList);
		model.addAttribute("lastPage", lastPage);
		System.out.println(":::sampleController.sampleList() END:::");
		
		return "/sample/sampleList";
	}

}
