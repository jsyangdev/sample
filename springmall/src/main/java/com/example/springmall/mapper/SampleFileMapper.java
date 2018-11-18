package com.example.springmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.springmall.sample.vo.SampleFile;

@Mapper
public interface SampleFileMapper {
	
	// 4. 첨부파일 정보 수정
	int updateSampleFile(SampleFile sampleFile);
	
	// 3. 수정폼에 특정 sampleNo가 업로드한 파일명, 크기 select
	List<SampleFile> selectOneFileForUpdate(int sampleNo);
	
	// 2. delete samplefile
	int deleteSampleFile(int sampleNo);
	
	// 1. insert samplefile
	int insertSampleFile(SampleFile sampleFile);
	
}
