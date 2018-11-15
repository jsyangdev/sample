package com.example.springmall.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.springmall.sample.vo.SampleFile;

@Mapper
public interface SampleFileMapper {
	
	// 2. delete samplefile
	int deleteSampleFile(int sampleNo);
	
	// 1. insert samplefile
	int insertSampleFile(SampleFile sampleFile);
	
}
