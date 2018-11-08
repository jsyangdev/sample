package com.example.springmall.sample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springmall.mapper.SampleMapper;
import com.example.springmall.paging.Paging;
import com.example.springmall.sample.vo.Sample;

@Service
@Transactional
public class SampleService {	// Mapper를 주입 받을 거
	@Autowired
	private SampleMapper sampleMapper;
	
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
	public int addSample(Sample sample) {
		System.out.println(":::SampleService.getSampleTotalRowCount() START:::");
		int insertResult = 0;
		insertResult = sampleMapper.insertSample(sample);
		System.out.println(insertResult+"<---insertResult");
		System.out.println(":::SampleService.getSampleTotalRowCount() END:::");
		
		return insertResult;
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