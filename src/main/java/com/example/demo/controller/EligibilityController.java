package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.EligibiltyDAO;
import com.example.demo.pojo.Applicant;
import com.example.demo.pojo.EligibityProcessRequest;

@RestController
public class EligibilityController {

	@Autowired
	private EligibiltyDAO eligibiltyDAO;

	public static Logger nceLogger = org.slf4j.LoggerFactory.getLogger("ncoeLog");
	public static Logger nceErrorLogger = org.slf4j.LoggerFactory.getLogger("ncoeErrorLogger");

	@GetMapping(path = "/processEligibility")
	public void processEligibility(@RequestBody EligibityProcessRequest eligibilityProcessRequest) {
		nceLogger.debug("My process Eligibity Method called " + eligibilityProcessRequest.getRequestedUser());
		eligibiltyDAO.processEligibility(eligibilityProcessRequest);
		nceLogger.debug("Prcoess Eligibility Method called sucessfully");
	}

//	@GetMapping(value = "/getAllCourses")
//	public String getAllCourses() throws SQLException {
//		return eligibiltyDAO.getAllcourses();
//	}
//	
//	@PostMapping(value = "/getAllApplicantsByCourseNo/{courseNo}")
//	public List<Applicant> getAllApplicantsByCourseNo(@PathVariable String courseNo){
//		try {
//			return eligibiltyDAO.getAllApplicantsByCourseNo(courseNo);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	

}