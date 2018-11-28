package com.example.springmall.sample.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.springmall.sample.service.SampleService;
import com.example.springmall.sample.vo.Sample;
import com.example.springmall.sample.vo.SampleAndSampleFile;
import com.example.springmall.sample.vo.SampleFile;
import com.example.springmall.sample.vo.SampleRequest;

@Controller
public class sampleController {
	@Autowired
	private SampleService sampleService;
	
	// 첨부파일 다운로드

/*	@RequestMapping(value="/sample/download", method=RequestMethod.GET)
	public ModelAndView callDownload(@RequestParam(value="sampleNo") int sampleNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(":::sampleController.downloadFile() START:::");
		System.out.println("sampleNo: " + sampleNo);
		
		List<SampleFile> sampleFileList = sampleService.getSampleFile(sampleNo);
		
		String realPath = null;
		String fileName = null;
		
		for(int i=0; i<sampleFileList.size(); i++) {
			SampleFile oneSampleFile = new SampleFile();
			oneSampleFile = sampleFileList.get(i);
			realPath = oneSampleFile.getSamplefilePath();
			System.out.println(realPath+"<---realPath");
			fileName = oneSampleFile.getSamplefileName();
			System.out.println(fileName+"<---fileName");
			
		}
		
		// 전체 경로를 인자로 넣어 파일 객체를 생성
		File downloadFile = new File(realPath + fileName);
		

		return new ModelAndView("FileDownloadView", "downloadFile", downloadFile);
	}*/

	/*
	 * ResponseEntity 타입이란?
	 * 		개발자가 결과 데이터와 HTTP상태코드를 직접 제어할 수 있는 클래스.
	 * 		주어진 바디, 헤더들, 상태코드로 새로운 HttpEntity를 생성한다.
	 * 		public ResponseEntity<Resource>(①Resource body, ②MultiValueMap<String, String> headers, ③HttpStatus status) {}
	 * 		① body the entity body : 결과 데이터, 바디에 삽입할 정보를 유지하는 오브젝트
	 *		② headers the entity headers : HTTP 응답
	 *		③ status the status code : 
	 */
	@RequestMapping(value="/sample/download", method=RequestMethod.GET, produces=org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody	// @ResponseBody 어노테이션이 적용된 경우, 리턴 객체를 HTTP 응답으로 전송한다. HttpMessageConverter를 이용해서 객체를 HTTP 응답 스트림으로 변환
	public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@RequestParam(value="sampleNo") int sampleNo) {
		System.out.println(":::sampleController.downloadFile() START:::");
		
		List<SampleFile> sampleFileList = sampleService.getSampleFile(sampleNo);
		
		String realPath = null;
		String fileName = null;
		String samplefileExt = null;
		
		for(int i=0; i<sampleFileList.size(); i++) {
			SampleFile oneSampleFile = new SampleFile();
			oneSampleFile = sampleFileList.get(i);
			realPath = oneSampleFile.getSamplefilePath();
			System.out.println(realPath+"<---realPath");
			fileName = oneSampleFile.getSamplefileName();
			System.out.println(fileName+"<---fileName");
			samplefileExt = oneSampleFile.getSamplefileExt();
			
		}
		/* 
		 * FileSystemResource :
		 * 		파일시스템의 특정 파일로 부터 정보를 읽어온다.
		 */
		org.springframework.core.io.Resource resource = new FileSystemResource(realPath+"\\"+fileName+"."+samplefileExt);
		System.out.println("resource: "+resource);
		
		String resourceName = resource.getFilename();
		System.out.println("resourceName: "+resourceName);
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			headers.add("Content-Disposition", "attachment; filename=" + new String(resourceName.getBytes("UTF-8"), "ISO-8859-1"));
			
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<org.springframework.core.io.Resource>(resource, headers, HttpStatus.OK);
	
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
		HashMap<String, Object> map = null;
		map = sampleService.getSample(sampleNo);
				
		model.addAttribute("sample", map.get("sample"));
		model.addAttribute("listForUpdate", map.get("listForUpdate"));
		
		System.out.println(":::sampleController.modifySample() END:::");
		
		return "/sample/modifySample";		// jquery필요, bootstrap필요, Sample command객체 사용할 것을 염두
	}

	// 4-2. 수정 액션
	@RequestMapping(value="/sample/modifySample", method=RequestMethod.POST)
	public String modifySample(SampleRequest sampleRequest, MultipartHttpServletRequest request) {
		System.out.println(":::sampleController.modifySample() START:::");
		int updateResult = sampleService.modeifySample(sampleRequest, request);
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
		/*
			Sample 친구들 존재할 수 있다. (베이스: Sample 테이블)
		 
			command객체 : 처리해야 할 파라미터가 늘어나면 늘어날 수록 작성해야 할 코드가 많아진다.
					         그래서 스프링은 커맨드 객체라는 것을 지원하고 있다.
					    http 요청 파라미터(request)의 이름(name)을 이용해 setter 메소드를 작성한 클래스를 만들고, 
					         이 클래스의 객체(커맨드 객체)를 메소드의 파라미터 값으로 넣어주면,
					         스프링은 요청 파라미터의 값을 커맨드 객체에 담아준다.
			command객체(sampleRequest)의 멤버변수  = input태그의 name속성  ---> 표준 setter를 호출			       
					       
		 */
		int totalRow = sampleService.addSample(sampleRequest, request);
		// view 없으니까 바로 리턴
		if(totalRow >= 1) {System.out.println("입력 성공 !");}
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
