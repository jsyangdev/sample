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
	
	public List<SampleFile> getSampleFile(int sampleNo) {
		System.out.println(":::SampleService.getSampleFile() START:::");
		System.out.println("sampleNo: "+sampleNo);
		List<SampleFile> sampleFileList = sampleFileMapper.selectOneFileForUpdate(sampleNo);
		
		System.out.println(":::SampleService.getSampleFile() END:::");
		return sampleFileList;
		
	}
	
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
	public HashMap<String, Object> getSample(int sampleNo) {
		System.out.println(":::SampleService.getSample() START:::");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<SampleFile> listForUpdate = null;
		Sample sample = null;

		sample = sampleMapper.selectOne(sampleNo);
		listForUpdate = sampleFileMapper.selectOneFileForUpdate(sampleNo);
		
		map.put("sample", sample);
		map.put("listForUpdate", listForUpdate);

		System.out.println(":::SampleService.getSample() END:::");
		
		return map;
	}
	
	// 4-2. 샘플 수정(Transaction처리)
	
	/*
	 * 만약 화면에서 회원 id, pw만 고치면 샘플맵퍼의 메서드만 호출되도록 해야되고,
	 * 파일 업로드만 수정하면 샘플파일맵퍼에 메서드만 호출
	 * 둘 다 고치면 샘플맵퍼, 샘플파일맵퍼 모두 호출 되도록	
	 */
	
	public int modeifySample(SampleRequest sampleRequest, MultipartHttpServletRequest request) {
		System.out.println(":::SampleService.modeifySample() START:::");
		int updateResult = 0;
		
	// 1. sample vo 만들어 update쿼리 호출
		Sample sample = new Sample();
		sample.setSampleNo(sampleRequest.getsampleNo());
		sample.setSampleId(sampleRequest.getSampleId());
		sample.setSamplePw(sampleRequest.getSamplePw());				
		sampleMapper.updateSample(sample);
		
	// 업로드된 파일을 수정했다면 기존에 있었던 파일을 실제 디렉토리 내에서 삭제 (->그 후, 다시 수정된 파일 업로드. DB내 파일 정보는 업데이트)
		int sampleNo = sample.getSampleNo();
		List<SampleFile> listForUpdate = sampleFileMapper.selectOneFileForUpdate(sampleNo);
		
		// sampleFile vo에 셋팅된 값들을 겟팅하여 특정 sampleNo가 기존에 올렸던 첨부파일을 서버에서 삭제
		for(int i=0; i<listForUpdate.size(); i++) {
			SampleFile dd = listForUpdate.get(i);
			String samplefilePath = dd.getSamplefilePath();
			String samplefileName = dd.getSamplefileName();
			String samplefileExt = dd.getSamplefileExt();
			File file = new File(samplefilePath+"\\"+samplefileName+"."+samplefileExt);
			sampleFileMapper.deleteSampleFile(sampleNo);
			file.delete();
		}
		

	// 2. SampleFile 클래스 통해 생성된 객체 내에 업로드 파일 정보 저장
		SampleFile sampleFile = new SampleFile();
		
		// 2-1 sampleNo: sample vo에서 get
		sampleFile.setSampleNo(sample.getSampleNo());
		System.out.println(sample.getSampleNo()+"<---sample.getSampleNo()");
		
		// 2-2 samplefilePath: request객체 내에서 realPath메서드 호출하여 상대경로 저장
		String realPath = request.getSession().getServletContext().getRealPath("/upload/");
		System.out.println(realPath+"<---realPath");
		sampleFile.setSamplefilePath(realPath);
		
		// 이 상대경로에 디렉토리가 없다면 디렉토리 생성
		File dir = new File(realPath);
		if(dir.exists() == false) {
			System.out.println("디렉토리 생성 !");
			dir.mkdir();
		} else {
			System.out.println("디렉토리 존재 !");
		}
		
		// 2-3 samplefileName:
		String fileName = UUID.randomUUID().toString();
		sampleFile.setSamplefileName(fileName);
		
		// 2-4 samplefileExt:
		MultipartFile multipartFile = sampleRequest.getMultipartFile();
		System.out.println(multipartFile.getOriginalFilename()+"<---multipartFile.getOriginalFilename() addSample");
		String originalFileName = multipartFile.getOriginalFilename();
		// originalFileName = 이름.확장자
		int pos = originalFileName.lastIndexOf(".");
		System.out.println(pos+"<---pos");
		String ext = originalFileName.substring(pos+1);	// 조작해서 확장자 만들면 된다.
		System.out.println(ext+"<---ext");
		sampleFile.setSamplefileExt(ext);
		
		// 2-5 samplefileType:
		sampleFile.setSamplefileType(multipartFile.getContentType());
		
		// 2-6 samplefileSize:
		sampleFile.setSamplefileSize(multipartFile.getSize());
		
		// 2-7 samplefileDate: mapper에서 now() 함수 쓸 것
		
		// 2-8 sql Mapper 내 update 호출
		updateResult = sampleFileMapper.updateSampleFile(sampleFile);
		System.out.println(updateResult+"<---updateResult");
		
	// 첨부파일을 하드디스크에 복사
		// 1. 빈 파일을 하나 만든다.
		File f = new File(realPath+"\\"+fileName+"."+ext);
		System.out.println(f+"<---f");
		
		// 2.
		try {
			multipartFile.transferTo(f);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	
		
		
		System.out.println(":::SampleService.modeifySample() END:::");

		return updateResult;
		
	}
	
	// 3. 입력 액션(Transaction처리)
	public int addSample(SampleRequest sampleRequest, MultipartHttpServletRequest request) {
		/*
			파일 업로드가 포함된 '입력 액션' 설계
		 	SampleRequest  ---> Sample , SampleFile (분리시킨다)
		 	1. multipartfile 파일데이터  -> 저장
		 	2. multipartfile 정보 -> 새로운 정보 추가 -> SampleFile에 셋팅
		 */		
		System.out.println(":::SampleService.addSample() START:::");
		int row = 0;
		int totalRow = 0;	// samplefile 테이블에 첨부파일의 데이터가 몇 개나 성공적으로 입력 되었는지 알아보기위한 변수
		
	// 1. sample vo 만들어서 화면에서 입력 받은 기본 정보들 셋팅 후, 맵퍼 호출
		Sample sample = new Sample();
		sample.setSampleId(sampleRequest.getSampleId());
		sample.setSamplePw(sampleRequest.getSamplePw());
		sampleMapper.insertSample(sample);	// Auto Increment에 의해서 sampleNo가 만들어진다. insertSample(sample) 메서드 실행 후, sampleNo(기본키) 값이 sample vo의 멤버필드에 채워진다.
		System.out.println(sample.getSampleNo()+"<---sample.getSampleNo()");	// sampleNo 겟팅 후, 출력
		
	// 2. sampleFile vo 만들어서 첨부파일들에 대한 정보를 셋팅 후, 맵퍼 호출		
		MultipartFile[] uploadFiles = sampleRequest.getMultipartFile();	// 화면에서 첨부한 파일들의 정보를 배열 타입으로 받는다.
		for(MultipartFile multipartFile : uploadFiles) {	// 첨부파일들을 반복문을 통해 각각 SampleFile vo에 정보들을 셋팅 시킨 후, 맵퍼 호출			
			SampleFile sampleFile = new SampleFile();
			
			// 1) samplefileNo : Auto Increment로 해결			
			// 2) sampleNo
			sampleFile.setSampleNo(sample.getSampleNo());
			
			/* 
				3) samplefilePath
				  - 홈디렉토리 : 웹서버가 기본적으로 찾는 디렉토리
			
			 		a. 경로를 절대경로로 지정했을 때
					String appointedPath = "c:\\uploads";	
					sampleFile.setSamplefilePath(appointedPath);
					- 문제점 : 이렇게 저장하면 클라우드로 가거나 다른 PC로 갔을 때 c드라이브에 uploads 디렉토리가 없을 수도 있다.
					
			 		b. 경로를 상대경로로 지정했을 때
			*/
			String realPath = request.getSession().getServletContext().getRealPath("/upload");
			System.out.println(realPath+"<---realPath");
			sampleFile.setSamplefilePath(realPath);
			
			/*
			  1. File 객체(java.io.File) : 하드디스크에 존재하는 파일에 대한 경로 또는 참조를 추상화한 객체. 즉, 새 파일에 대한 경로나 만들고자 하는 디렉토리를 캡슐화 한 것.
			  2. File 객체의 용도 :
			  		① 물리적 파일시스템에 대해 캡슐화한 경로명을 확인하고, 실제 파일이나 디렉토리와 대응하는지 알아볼 때
			  		② 파일 스트림 객체를 생성하고자 할 때
			  3. File 클래스의 인스턴스 생성 :
			  		① 디렉토리 생성 : File dir = new File("디렉토리의 경로");
			 		② 부모 디렉토리를 파라미터로 인스턴스 생성 : File newFile = new File(dir,"파일명");
			 		③ 부모 디렉토리를 String 타입으로 전달 : File newFile = new File("디렉토리의 경로","파일명");
			  		④ File 객체를 URI 객체로부터 생성하는 방법도 있다.
			 		- 출처 : http://javafactory.tistory.com/1370
			 */			
			File dir = new File(realPath);	// File 클래스의 인스턴스 생성
			if(dir.exists() == false) {	// exists() : File 객체가 참조하는 파일이나 디렉토리가 존재하면 true 리턴
				System.out.println("dir객체참조변수가 참조하는 경로로 디렉토리 생성 !");
				dir.mkdir();	// mkdir(): 현재 파일 객체가 참조하는 경로로 디렉토리를 생성. ( ※반드시 부모 디렉토리가 있어야 함)
			} else {
				System.out.println("디렉토리가 존재합니다 !");
			}

			// 4) 확장자
			String originalFileName = multipartFile.getOriginalFilename();	// originalFileName = 이름.확장자
			System.out.println(originalFileName+"<---originalFileName");
			int pos = originalFileName.lastIndexOf(".");	// lastIndexOf : 오른쪽부터 문자열을 센다.
			System.out.println(pos+"<---pos");	// 4
			String ext = originalFileName.substring(pos+1);
			System.out.println(ext+"<---ext");
			sampleFile.setSamplefileExt(ext);
			
			/* 
			 5) 이름
			 	UUID 클래스를 사용해서 유일한 식별자를 생성
				  ① java.util.UUID 클래스를 import
				  ② UUID 클래스의 randomUUID() 메소드를 사용해서 유일한 식별자를 생성
				  ③ 반환 되는 객체가 UUID 객체이므로 문자열 표현을 얻기 위해 toString() 메소드를 사용하여 출력
			*/
			String fileName = UUID.randomUUID().toString();
			sampleFile.setSamplefileName(fileName);

			// 6) 타입
			sampleFile.setSamplefileType(multipartFile.getContentType());
			
			// 7) 크기
			sampleFile.setSamplefileSize(multipartFile.getSize());
			
			// 8) samplefile 테이블에 값들 insert 시키는 메서드 호출하기
			row = sampleFileMapper.insertSampleFile(sampleFile);
			System.out.println(row+"<---row");
			totalRow = totalRow + row;
			System.out.println(totalRow+"<---totalRow");
			
			// 9) multipartFile 파일을 하드디스크에 복사
			// 9-1. 원하는 이름의 File 인스턴스를 만든다.
			File f = new File(realPath+"\\"+fileName+"."+ext);
			System.out.println(f+"<---f");
			try {	// try, catch 필요 (예외가 발생할 수도 있다. 예를 들면, 하드디스크 용량이 부족)
				multipartFile.transferTo(f);	// 9-2. 첨부된 multipartFile 파일을 지정한 파일로 트랜스퍼 시킨다.
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}	// end for

		// 1+2 => @Transactional
		System.out.println(":::SampleService.addSample() END:::");
		
		return totalRow;
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