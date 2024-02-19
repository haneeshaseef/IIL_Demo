package com.example.demo.pojo;

import java.io.Serializable;

public class Applicant implements Serializable {
	private String AppliationNo;
	private String ApplicantName;
	private String applicant_seq;
	private String nicNo;
	private String coursePrefOne;
	private String coursePrefTwo;
	private String coursePrefThree;
	private double zScore;
	
	public String getApplicant_seq() {
		return applicant_seq;
	}
	public void setApplicant_seq(String applicant_seq) {
		this.applicant_seq = applicant_seq;
	}
	
	
	public double  getzScore() {
		return zScore;
	}
	public void setzScore(double  zScore) {
		this.zScore = zScore;
	}
	public String getAppliationNo() {
		return AppliationNo;
	}
	public void setAppliationNo(String appliationNo) {
		AppliationNo = appliationNo;
	}
	public String getApplicantName() {
		return ApplicantName;
	}
	public void setApplicantName(String applicantName) {
		ApplicantName = applicantName;
	}
	public String getNicNo() {
		return nicNo;
	}
	public void setNicNo(String nicNo) {
		this.nicNo = nicNo;
	}
	public String getCoursePrefOne() {
		return coursePrefOne;
	}
	public void setCoursePrefOne(String coursePrefOne) {
		this.coursePrefOne = coursePrefOne;
	}
	public String getCoursePrefTwo() {
		return coursePrefTwo;
	}
	public void setCoursePrefTwo(String coursePrefTwo) {
		this.coursePrefTwo = coursePrefTwo;
	}
	public String getCoursePrefThree() {
		return coursePrefThree;
	}
	public void setCoursePrefThree(String coursePrefThree) {
		this.coursePrefThree = coursePrefThree;
	}
	
	
	

}
