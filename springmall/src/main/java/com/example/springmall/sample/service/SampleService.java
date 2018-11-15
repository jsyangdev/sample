package com.example.springmall.sample.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.example.springmall.mapper.SampleFileMapper;
import com.example.springmall.mapper.SampleMapper;
import com.example.springmall.sample.vo.Sample;
import com.example.springmall.sample.vo.SampleAndSampleFile;
import com.example.springmall.sample.vo.SampleFile;
import com.example.springmall.sample.vo.SampleRequest;

@Service
@Transactional
public class SampleService {	// Mapper를 주입 받을 거
	@Autowired
	private SampleMapper sampleMapper;
	@Autowired
	private SampleFileMapper sampleFileMapper;
	
	// 6. 샘플 검색
/*	public List<SampleAndSampleFile> getSampleOfSearch(HashMap<String, Object> map){
		System.out.println(":::SampleService.getSampleOfSearch() START:::");
		List<SampleAndSampleFile> searchList = null;
		String selectValue = (String)map.get("selectValue");
		String sampleId = (String)map.get("sampleId");
		int currentPage = (Integer)map.get("currentPage");
		int rowsPerPage = (Integer)map.get("rowsPerPage");
		System.out.println(selectValue+"<---selectValue");
		System.out.println(sampleId+"<---sampleId");
		// :::페이징 관련 코드:::
		int startRow;		// SELECT쿼리 LIMIT의 첫번째 ?
		startRow = (currentPage-1)*rowsPerPage;
		System.out.println(startRow+"<--startRow");
		map.put("startRow", startRow);
		// :::END:::
		searchList = sampleMapper.selectSampleOfSearch(map);
		System.out.println(searchList+"<---searchList");
		System.out.println(":::SampleService.getSampleOfSearch() END:::");
		
		return searchList;
	}*/
	
	
	// 5. 로그인을 위해 샘플 조회
	public Sample getSampleForLogin(Sample sample) {
		System.out.println(":::SampleService.getSampleForLogin() START:::");
		Sample loginSample = sampleMapper.selectOneForLogin(sample);
		System.out.println(loginSample+"<---loginResult");
		System.out.println(":::SampleService.getSampleForLogin() END:::");
		
		return loginSample;
	}
	
	// 4-1. 샘플 수정을 위해 특정 샘플 조회
	public Sample getSample(int sampleNo) {
		System.out.println(":::SampleService.getSample() START:::");
		Sample sample = sampleMapper.selectOne(sampleNo);
		System.out.println(":::SampleService.getSample() END:::");
		
		return sample;
	}
	
	// 4-2. 샘플 수정
	public int modeifySample(Sample sample) {
		System.out.println(":::SampleService.modeifySample() START:::");
		int updateResult = 0;
		updateResult = sampleMapper.updateSample(sample);
		System.out.println(updateResult+"<---updateResult");
		System.out.println(":::SampleService.modeifySample() END:::");

		return updateResult;
		
	}
	
	// 3. 입력 액션
	public int addSample(SampleRequest sampleRequest, MultipartHttpServletRequest request) {
		/*
			파일 업로드가 포함된 입력 액션 설계
		 	SampleRequest  ---> Sample , SampleFile (분리시킨다)
		 	1. multipartfile 파일데이터  -> 저장
		 	2. multipartfile 정보 -> 새로운 정보 추가 -> SampleFile
		 */
		
		System.out.println(":::SampleService.addSample() START:::");
		// 1.
		Sample sample = new Sample();
		sample.setSampleId(sampleRequest.getSampleId());
		sample.setSamplePw(sampleRequest.getSamplePw());
		sampleMapper.insertSample(sample);	// auto increment에 의해서 sampleNo가 만들어졌을 것이다. 이 메서드 실행 후에 sample에 아디와 비번이 채워져있음.
		// System.out.println(sample.getSampleNo()+"<---sample.getSampleNo()");	sampleNo 겟팅
		
		// 2.
		SampleFile sampleFile = new SampleFile();
		MultipartFile multipartFile = sampleRequest.getMultipartFile();	// 화면에서 받아온 multipart를 MultipartFile인터페이스 데이터 타입으로 변수 선언하여 저장
		// (1) SampleFileNo : Auto Increment로 해결
		// (2) SampleNo
		sampleFile.setSampleNo(sample.getSampleNo());	// insertSample(sample) 후에 pk 값이 sample에 채워진다.
		System.out.println(sample.getSampleNo()+"<---sample.getSampleNo()");	// sampleNo(기본키) getting
		
		/* 
			(3) samplefilePath
			  - 홈디렉토리 : 웹서버가 기본적으로 찾는 디렉토리
			  - 홈디렉토리에 저장 - 복잡한 루틴을 통해서 내가 원하는 위치에 !
		
	 		ⓐ 경로를 절대경로로 지정했을 때
			- 문제점 : 근데 이렇게 적으면 클라우드로 가거나 다른 PC로 갔을 때 c드라이브에 uploads가 없을 수도 있다.
			String realPath = "c:\\uploads";	
			sampleFile.setSamplefilePath(realPath);

	 		ⓑ경로를 상대경로로 지정했을 때
		*/
		String realPath = request.getSession().getServletContext().getRealPath("/upload");
		System.out.println(realPath+"<---realPath");
		sampleFile.setSamplefilePath(realPath);
		
		/*
		  1. File 객체(java.io.File) : 하드디스크에 존재하는 파일에 대한 경로 또는, 참조를 추상화한 객체. 즉, 새 파일에 대한 경로나 만들고자 하는 디렉토리를 캡슐화 한 것.
		  2. File 객체의 용도 :
		  		(1) 물리적 파일시스템에 대해 캡슐화한 경로명을 확인하고, 실제 파일이나 디렉토리와 대응하는지 알아볼 때
		  		(2) 파일 스트림 객체를 생성하고자 할때
		  3. File Class의 인스턴스 생성 :
		  		(1) 디렉토리 생성 : File dir = new File("디렉토리의 경로");
		 		(2) 부모 디렉토리를 파라미터로 인스턴스 생성 : File newFile = new File(dir,"파일명");
		 		(3) 부모 디렉토리를 String 타입으로 전달 : File newFile = new File("디렉토리의 경로","파일명");
		  		(4) File 객체를 URI 객체로부터 생성하는것도 방법도 있다.
		 		- 출처 : http://javafactory.tistory.com/1370
		 */
		
		File dir = new File(realPath);	// File class의 인스턴스 생성 (new File(String pathname))
		if(dir.exists() == false) {	// exists(): File 객체가 참조하는 파일이나 디렉토리가 '실존'하면 true 리턴
			System.out.println("dir객체참조변수가 참조하는 경로로 디렉토리 생성 !");
			dir.mkdir();	// mkdir(): 현재 파일 객체가 참조하는 경로로 디렉토리를 생성. ( ※반드시 부모 디렉토리가 있어야 한다. )
		} else {
			System.out.println("실존하는 디렉토리가 없습니다.");
		}

		// (4) 확장자
		System.out.println(multipartFile.getOriginalFilename()+"<---multipartFile.getOriginalFilename() addSample");
		String originalFileName = multipartFile.getOriginalFilename();
		// originalFileName = 이름.확장자
		int pos = originalFileName.lastIndexOf(".");
		System.out.println(pos+"<---pos");
		String ext = originalFileName.substring(pos+1);	// 조작해서 확장자 만들면 된다.
		System.out.println(ext+"<---ext");
		sampleFile.setSamplefileExt(ext);
		
		/* 
		 (5) 이름
		 UUID 클래스를 사용해서 유일한 식별자를 생성
		  - java.util.UUID 클래스를 import
		  - UUID 클래스의 randomUUID() 메소드를 사용해서 유일한 식별자를 생성
		     - 반환 되는 객체가 UUID 객체이므로 문자열 표현을 얻기 위해 toString() 메소드를 출력
		*/
		String fileName = UUID.randomUUID().toString();
		sampleFile.setSamplefileName(fileName);

		// (6) 타입
		sampleFile.setSamplefileType(multipartFile.getContentType());
		
		// (7) 크기
		sampleFile.setSamplefileSize(multipartFile.getSize());
		
		// samplefile 테이블에 값들 insert 시키는 메서드 호출하기
		int row = sampleFileMapper.insertSampleFile(sampleFile);
		
		// ★ multipartFile파일을 하드디스크에 복사 ! ★
		// 1. 내가 원하는 이름의 빈 파일을 하나 만들자 !
		File f = new File(realPath+"\\"+fileName+"."+ext);
		System.out.println(f+"<---f");

		try {	// try, catch 필요 (예외가 날 수도 있으니까. 예를 들면 하드디스크 용량이 부족하다던가)
			multipartFile.transferTo(f);// 2. 그리고 multipartFile파일을 빈 파일로 복사하자 !
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 1+2 => @Transactional
		System.out.println(":::SampleService.addSample() END:::");
		
		return row;
	}	
	
	// 2. 삭제
	public int removeSample(int sampleNo) {
		System.out.println(":::SampleService.removeSample() START:::");
		// sampleNo가 업로드한 파일의 정보가 있는지 확인
		HashMap<String, Object> selectSampleAndSampleFileResult = sampleMapper.selectSampleAndSampleFile(sampleNo);
		System.out.println(selectSampleAndSampleFileResult+"<---selectSampleAndSampleFileResult");
		
		if(selectSampleAndSampleFileResult != null) {	// 업로드한 파일이 있음
			System.out.println("업로드한 파일이 존재 !");
			String samplefilePath = (String) selectSampleAndSampleFileResult.get("samplefile_path");
			String samplefileName = (String) selectSampleAndSampleFileResult.get("samplefile_name");
			String samplefileExt = (String) selectSampleAndSampleFileResult.get("samplefile_ext");
			System.out.println(samplefilePath+"<---samplefilePath");
			System.out.println(samplefileName+"<---samplefileName");
			System.out.println(samplefileExt+"<---samplefileExt");
			
			File file = new File(samplefilePath+"\\"+samplefileName+"."+samplefileExt);
			
			sampleFileMapper.deleteSampleFile(sampleNo);	// db에서 samplefile T에 남아있는 데이터 삭제, transaction-1
			file.delete();
			
			sampleMapper.deleteSample(sampleNo);	// 회원 삭제, transaction-2			
			
			
		} else { // 업로드한 파일이 없는 경우 바로 회원정보만 삭제하면 됨.
			System.out.println("업로드한 파일이 존재 X ---> 바로 회원 정보 삭제");
			sampleMapper.deleteSample(sampleNo);
		}
		
		System.out.println(":::SampleService.removeSample() END:::");
		
		return 0;

	} 
		

	// 1. 샘플목록
	public int getSampleTotalRowCount() {
		System.out.println(":::SampleService.getSampleTotalRowCount() START:::");
		int totalRowCount;	// 총 행의 갯수
		totalRowCount = sampleMapper.selectSampleCount();
		System.out.println(totalRowCount+"<---totalRowCount");
		System.out.println(":::SampleService.getSampleTotalRowCount() END:::");
		
		return totalRowCount;
	}
	
	public List<Sample> getSampleAll(HashMap<String, Object> map){
		System.out.println(":::SampleService.getSampleAll() START:::");
		List<Sample> selectList = null;
		int currentPage = (int) map.get("currentPage");
		int rowsPerPage = (int) map.get("rowsPerPage");
		String selectvalue = (String) map.get("selectValue");
		String sampleId = (String) map.get("sampleId");
		System.out.println(currentPage+"<---currentPage");
		System.out.println(rowsPerPage+"<---rowsPerPage");
		System.out.println(selectvalue+"<---selectvalue");
		System.out.println(sampleId+"<---sampleId");
		// ::: 페이징 관련 코드 :::
		int startRow;		// SELECT쿼리 LIMIT의 첫번째 ?
		startRow = (currentPage-1)*rowsPerPage;
		map.put("startRow", startRow);
		System.out.println(startRow+"<--startRow");
		// :::END:::		
		if(selectvalue == null) {
			System.out.println("전체 리스트 출력 !");
			selectList = sampleMapper.selectSampleAll(startRow, rowsPerPage);
		} else if(sampleId != null) {
			System.out.println("검색된 리스트 출력 !");
			selectList = sampleMapper.selectSampleOfSearch(map);
		}
		System.out.println(selectList+"<---selectList");
		System.out.println(":::SampleService.getSampleAll() END:::");
		
		return selectList;
	}

}