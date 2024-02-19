package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dao.ApplicantsDAO;
import com.example.demo.dao.CoursesDAO;
import com.example.demo.pojo.Applicant;
import com.example.demo.pojo.Course;
import com.example.demo.pojo.Person;


@RestController
public class WebServerController {
	
	@Autowired
	private CoursesDAO coursesDAO;
	
	@Autowired
	private ApplicantsDAO applicantsDAO;
	
	
	@GetMapping(value ="/sayHello")
	public String sayHello() {
		return "Hello Web Service Called..";
	}
	
	
	
	@GetMapping(value = "/getVegetableInformation/{vegetableName}/{color}")
	public String getVegetableInformation(@PathVariable String vegetableName, @PathVariable String color) {
		return "Today I have 20Kg " + color + " " + vegetableName;
	}
	
	@PostMapping(path ="/savePersonInformation")
	public void savePersonInformation(@RequestBody Person personObj) {
		System.out.println("Person's first Name is " + personObj.getFirstName());
		System.out.println("Person's last Name is " + personObj.getLastName());
		System.out.println("Person's Age is " + personObj.getAge());
		System.out.println("Person's Address is " + personObj.getAddress());
	}
	
	@GetMapping(value = "/findAllCourses")
	public List<Course> findAllCourse() {
		List<Course> data = coursesDAO.findaAllCourses();
		return data;
	}
	
	@GetMapping (value = "/findCourseById/{courseId}")
	public Course findCourseById(@PathVariable String courseId) {
		return coursesDAO.findCourseById(courseId);
		
	}
	
	@GetMapping(value = "/findAllApplicants")
	public List<Applicant> findAllApplicant() {
		return applicantsDAO.findAllAppliacts();	
	}
	
	@GetMapping (value = "/findApplicantByNo/{applicationNo}")
	public Applicant findApplicantByNo(@PathVariable String applicationNo) {
		return applicantsDAO.findApplicantByNo(applicationNo);
		
	}
	
	@GetMapping(value = "/getAllApplicantsByCourse/{courseName}")
	public List<Applicant> getAllApplicantsByCourse(@PathVariable String courseName) {
		List<Applicant> data = applicantsDAO.getAllApplicantsByCourse(courseName);
		return data;	
	}
	
	
	
	
	
}
