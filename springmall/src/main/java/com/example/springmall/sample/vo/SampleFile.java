package com.example.springmall.sample.vo;

public class SampleFile {
	// auto increment
	private int samplefileNo;
	// insert sample로부터 채워짐
	private int sampleNo;
	// multipartfile로부터 채워짐
	private String samplefilePath;	// 내가 만드는 것
	private String samplefileName;
	private String samplefileExt;
	private String samplefileType;
	private long samplefileSize;
	private String samplefileDate;

	public int getSamplefileNo() {
		return samplefileNo;
	}

	public void setSamplefileNo(int samplefileNo) {
		System.out.println(samplefileNo+"<---samplefileNo setSamplefileNo SampleFile");
		this.samplefileNo = samplefileNo;
	}

	public int getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(int sampleNo) {
		System.out.println(sampleNo+"<---sampleNo setSampleNo SampleFile");
		this.sampleNo = sampleNo;
	}

	public String getSamplefilePath() {
		return samplefilePath;
	}

	public void setSamplefilePath(String samplefilePath) {
		System.out.println(samplefilePath+"<---samplefilePath setSamplefilePath SampleFile");
		this.samplefilePath = samplefilePath;
	}

	public String getSamplefileName() {
		return samplefileName;
	}

	public void setSamplefileName(String samplefileName) {
		System.out.println(samplefileName+"<---samplefileName setSamplefileName SampleFile");
		this.samplefileName = samplefileName;
	}

	public String getSamplefileExt() {
		return samplefileExt;
	}

	public void setSamplefileExt(String samplefileExt) {
		System.out.println(samplefileExt+"<---samplefileExt setSamplefileExt SampleFile");
		this.samplefileExt = samplefileExt;
	}

	public String getSamplefileType() {
		return samplefileType;
	}

	public void setSamplefileType(String samplefileType) {
		System.out.println(samplefileType+"<---samplefileType setSamplefileType SampleFile");
		this.samplefileType = samplefileType;
	}

	public long getSamplefileSize() {
		return samplefileSize;
	}

	public void setSamplefileSize(long samplefileSize) {
		System.out.println(samplefileSize+"<---samplefileSize setSamplefileSize SampleFile");
		this.samplefileSize = samplefileSize;
	}

	public String getSamplefileDate() {
		return samplefileDate;
	}

	public void setSamplefileDate(String samplefileDate) {
		System.out.println(samplefileDate+"<---samplefileDate setSamplefileDate SampleFile");
		this.samplefileDate = samplefileDate;
	}

	@Override
	public String toString() {
		return "SampleFile [samplefileNo=" + samplefileNo + ", sampleNo=" + sampleNo + ", samplefilePath="
				+ samplefilePath + ", samplefileName=" + samplefileName + ", samplefileExt=" + samplefileExt
				+ ", samplefileType=" + samplefileType + ", samplefileSize=" + samplefileSize + ", samplefileDate="
				+ samplefileDate + "]";
	}
	
}
