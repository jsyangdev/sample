package com.example.springmall.sample.vo;

public class SampleAndSampleFile {
	
	private int sampleNo;
	private String sampleId;
	private String samplePw;
	private String sampleFile;
	public int getSampleNo() {
		return sampleNo;
	}

	
	public String getSampleId() {
		return sampleId;
	}


	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}


	public String getSamplePw() {
		return samplePw;
	}


	public void setSamplePw(String samplePw) {
		this.samplePw = samplePw;
	}


	public String getSampleFile() {
		return sampleFile;
	}


	public void setSampleFile(String sampleFile) {
		this.sampleFile = sampleFile;
	}


	public void setSampleNo(int sampleNo) {
		this.sampleNo = sampleNo;
	}


	@Override
	public String toString() {
		return "SampleAndSampleFile [sampleNo=" + sampleNo + ", sampleId=" + sampleId + ", samplePw=" + samplePw
				+ ", sampleFile=" + sampleFile + "]";
	}

}
