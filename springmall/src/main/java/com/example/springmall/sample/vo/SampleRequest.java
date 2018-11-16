package com.example.springmall.sample.vo;

import org.springframework.web.multipart.MultipartFile;

public class SampleRequest {
	private int sampleNo;
	private String sampleId;
	private String samplePw;
	private MultipartFile multipartFile ;
	
	public int getsampleNo() {
		return sampleNo;
	}
	public void setsampleNo(int sampleNo) {
		System.out.println(sampleNo+"<---sampleNo setsampleNo SampleRequest");
		this.sampleNo = sampleNo;
	}
	public String getSampleId() {
		return sampleId;
	}
	public void setSampleId(String sampleId) {
		System.out.println(sampleId+"<---sampleId setSampleId SampleRequest");
		this.sampleId = sampleId;
	}
	public String getSamplePw() {
		return samplePw;
	}
	public void setSamplePw(String samplePw) {
		System.out.println(samplePw+"<---samplePw setSamplePw SampleRequest");
		this.samplePw = samplePw;
	}
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		System.out.println(multipartFile+"<---multipartFile setMultipartFile SampleRequest");
		this.multipartFile = multipartFile;
	}
	
	@Override
	public String toString() {
		return "SampleRequest [sampleId=" + sampleId + ", samplePw=" + samplePw + ", multipartFile=" + multipartFile
				+ "]";
	}
	
}
