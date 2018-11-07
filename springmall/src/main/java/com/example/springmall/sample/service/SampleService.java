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
		
		int startRow;		// SELECT쿼리 LIMIT의 첫번째 물음표 부분
		//int lastPage;		// 마지막 페이지
		
		
		startRow = (currentPage-1)*rowsPerPage;
		
		//lastPage = totalRowCount/rowsPerPage;
		//if(totalRowCount%rowsPerPage != 0) {lastPage++;}		
		
		
		// :::END:::
		List<Sample> sampleList = new ArrayList<Sample>();
		sampleList = sampleMapper.selectSampleAll(startRow, rowsPerPage);
		System.out.println(sampleList+"<---sampleList");
		System.out.println(":::SampleService.getSampleAll() END:::");
		
		return sampleList;
	}
	// 2. 삭제
	public void removeSample(int sampleNo) {
		sampleMapper.deleteSample(sampleNo);
	}
}