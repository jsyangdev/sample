package com.example.springmall.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.springmall.sample.vo.Sample;
import com.example.springmall.sample.vo.SampleAndSampleFile;

@Mapper
public interface SampleMapper {		// 추상메서드, 추상클래스, 인터페이스(추상메서드 밖에 없으니까 public abstract 안 써도 됨)
	
	// 1. select all
	List<Sample> selectSampleAll(int startRow, int rowsPerPage);	// public abstract
	
	// 2. select count
	int selectSampleCount();
	
	// 3. delete
	int deleteSample(int sampleNo);
	
	// 4. insert
	int insertSample(Sample sample);
	
	// 5. select one for update
	Sample selectOne(int sampleNo);
	
	// 6. update
	int updateSample(Sample sample);
	
	// 7. select one for login
	Sample selectOneForLogin(Sample sample);

	// 8. select search list 
	List<Sample> selectSampleOfSearch(HashMap<String, Object> map);
	
	// 9. select one for deleting samplefile
	HashMap<String, Object> selectSampleAndSampleFile(int sampleNo);
	
	
}