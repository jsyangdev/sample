package com.example.springmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.springmall.sample.vo.Sample;

@Mapper
public interface SampleMapper {
	// 1. select all
	List<Sample> selectSampleAll(int startRow, int rowsPerPage);
	// 2. insert
	int insertSample(Sample sample);
	// 3. delete
	int deleteSample(int sampleNo);
	// 4. update
	int updateSample(Sample sample);
	// 5. select count
	int selectSampleCount();
}