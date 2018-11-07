package com.example.springmall.paging;

import org.springframework.web.bind.annotation.RequestParam;

public class Paging {
	
	int currentPage;	// 현재 페이지
	int rowsPerPage;	// 한 페이지에 몇 개의 데이터(행)을 보여줄 것인가
	int totalRowCount;	// 총 행의 갯수
	int startRow;		// SELECT쿼리 LIMIT의 첫번째 물음표 부분
	int lastPage;		// 마지막 페이지
	
	public Paging(int currentPage, int rowsPerPage) {
		System.out.println(":::Paging.Paging() START:::");
		this.currentPage = currentPage;
		this.rowsPerPage = rowsPerPage;
		System.out.println(this.currentPage+"<---this.currentPage");
		System.out.println(this.rowsPerPage+"<---this.rowsPerPage");
		System.out.println(":::Paging.Paging() END:::");
	}
	
	
	
	
	
	
	
	
	
	

}
