package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.Applicant;

@Repository
public class ApplicantsDAO {
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	public List<Applicant>  findAllAppliacts() {
		Connection connection = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		List<Applicant> data = new ArrayList<Applicant>(0);
		
		try {
			connection = datasource.getConnection();
			
			String mySql = "select  application_no ,applicant_name , nic_no ,course_name  from iit_application ia ,iit_courses ic where ia.course_code = ic.code ";
			stmt =connection.prepareStatement(mySql);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Applicant applicant = new Applicant();
				applicant.setAppliationNo(rs.getString("application_no"));
				applicant.setApplicantName(rs.getString("applicant_name"));
				applicant.setNicNo(rs.getString("nic_no"));
				
				
				data.add(applicant);
			}
		}catch(Exception e) {
			
		}finally {
			try {
				connection.close();			
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public Applicant findApplicantByNo(String applicationNo) {
		Connection connection = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		Applicant applicant = new Applicant();
	
		
		try {
			connection = datasource.getConnection();
			
			String mySql = "SELECT ia.application_no, ia.applicant_name, ia.nic_no, ic.course_name FROM iit_application ia JOIN iit_courses ic ON ia.course_code = ic.code WHERE ia.application_no = ?";
			stmt =connection.prepareStatement(mySql);
		    stmt.setString(1, applicationNo);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				applicant.setAppliationNo(rs.getString("application_no"));
				applicant.setApplicantName(rs.getString("applicant_name"));
				applicant.setNicNo(rs.getString("nic_no"));
			
				
				return applicant;
			}
		}catch(Exception e) {
			
		}finally {
			try {
				connection.close();			
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return applicant;
	}
	public List<Applicant> getAllApplicantsByCourse(String courseName) {
		Connection connection = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		List<Applicant> data = new ArrayList<Applicant>(0);
		try {
			connection = datasource.getConnection();
			
			String mySql = "SELECT * FROM iit_application ia JOIN iit_courses ic ON ia.course_code = ic.code WHERE ic.course_name = ?";
			stmt =connection.prepareStatement(mySql);
			
			stmt.setString(1, courseName);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Applicant applicant = new Applicant();
				applicant.setAppliationNo(rs.getString("application_no"));
				applicant.setApplicantName(rs.getString("applicant_name"));
				applicant.setNicNo(rs.getString("nic_no"));
		
				
				data.add(applicant);
			}
		}catch(Exception e) {
			
		}finally {
			try {
				connection.close();			
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return data;
		
	}

	
}
