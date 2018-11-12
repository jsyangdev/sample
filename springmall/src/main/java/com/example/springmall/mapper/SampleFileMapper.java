package com.example.springmall.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.springmall.sample.vo.SampleFile;

@Mapper
public interface SampleFileMapper {
	
	
	int insertSampleFile(SampleFile sampleFile);
	
	

}
