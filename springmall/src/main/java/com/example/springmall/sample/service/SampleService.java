package com.example.springmall.sample.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.springmall.mapper.SampleFileMapper;
import com.example.springmall.mapper.SampleMapper;
import com.example.springmall.sample.vo.Sample;
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
	public List<Sample> getSampleOfSearch(HashMap<String, Object> map){
		System.out.println(":::SampleService.getSampleOfSearch() START:::");
		List<Sample> searchList = null;
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
	}
	
	
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
	public int addSample(SampleRequest sampleRequest) {
		/*
		 * SampleRequest  ---> Sample , SampleFile (분리시킨다)
		 * 1. multipartfile 파일데이터  -> 저장
		 * 2. multipartfile 정보 -> 새로운 정보 추가 -> SampleFile
		 */
		System.out.println(":::SampleService.addSample() START:::");
		// 1.
		Sample sample = new Sample();
		sample.setSampleId(sampleRequest.getSampleId());
		sample.setSamplePw(sampleRequest.getSamplePw());
		sampleMapper.insertSample(sample);	// auto increment에 의해서 sampleNo가 만들어 졌을 것이다. 이 메서드 실행 후에 sample에 아디와 비번이 채워져있음.
		// System.out.println(sample.getSampleNo()+"<----sample.getSampleNo()");	// sampleNo(기본키) getting
		
		// 2.
		SampleFile sampleFile = new SampleFile();
		MultipartFile multipartFile = sampleRequest.getMultipartFile();	// 화면에서 받아온 multifile을 MultipartFile인터페이스 데이터 타입으로 변수 선언하여 저장
		// (1) SampleFileNo : Auto Increment로 해결
		
		// (2) SampleNo
		sampleFile.setSampleNo(sample.getSampleNo());	// insertSample(sample) 후에 pk 값이 sample에 채워진다.
		System.out.println(sample.getSampleNo()+"<----sample.getSampleNo()");	// sampleNo(기본키) getting
		
		// (3) samplefilePath (홈디렉토리 쓰려면 알아서 고민혀) - 복잡한 루틴을 통해서 내가 원하는 !!! request.realPath ? 
		String path = "c:\\uploads";	// 근데 이렇게 적으면 클라우드 같은데로 갔을 때 c에 uploads가 없을 수도 있잖아
		sampleFile.setSamplefilePath(path);
		
		request.
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// (4) 확장자
		System.out.println(multipartFile.getOriginalFilename()+"<---multipartFile.getOriginalFilename() addSample");
		String originalFileName = multipartFile.getOriginalFilename();
		// originalFileName = 이름.확장자
		int pos = originalFileName.lastIndexOf(".");
		System.out.println(pos+"<---pos");
		String ext = originalFileName.substring(pos+1);	// 조작해서 확장자 만들면 된다.
		System.out.println(ext+"<---ext");
		sampleFile.setSamplefileExt(ext);
		
		/* (5) 이름
		 * UUID 클래스를 사용해서 유일한 식별자를 생성
		 * - java.util.UUID 클래스를 임포트
		 * - UUID 클래스의 randomUUID() 메소드를 사용해서 유일한 식별자를 생성
		 * - 반환 되는 객체가 UUID 객체이므로 문자열 표현을 얻기 위해 toString() 메소드로 출력
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
		File f = new File(path+"\\"+fileName+"."+ext);

		try {	// try, catch 필요 (예외가 날 수도 있으니까, 예를 들면 하드디스크 용량이 부족하다던가)
			multipartFile.transferTo(f);	// 2. 그리고 multipartFile파일을 빈 파일로 복사하자 !
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
	public void removeSample(int sampleNo) {
		System.out.println(":::SampleService.getSampleTotalRowCount() START:::");
		sampleMapper.deleteSample(sampleNo);
		System.out.println(":::SampleService.getSampleTotalRowCount() END:::");
		
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
	
	public List<Sample> getSampleAll(int currentPage, int rowsPerPage){
		System.out.println(":::SampleService.getSampleAll() START:::");
		System.out.println(currentPage+"<---currentPage");
		System.out.println(rowsPerPage+"<---rowsPerPage");
		// :::페이징 관련 코드:::
		int startRow;		// SELECT쿼리 LIMIT의 첫번째 ?
		startRow = (currentPage-1)*rowsPerPage;
		System.out.println(startRow+"<--startRow");
		// :::END:::
		List<Sample> sampleList = new ArrayList<Sample>();
		sampleList = sampleMapper.selectSampleAll(startRow, rowsPerPage);
		System.out.println(":::SampleService.getSampleAll() END:::");
		
		return sampleList;
	}

}