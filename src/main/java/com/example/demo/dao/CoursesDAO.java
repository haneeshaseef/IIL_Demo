package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.Course;

@Repository
public class CoursesDAO {
	
	@Autowired
	private DataSource datasource;
	
	@Autowired

	public List<Course>  findaAllCourses() {
		Connection connection = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		List<Course> data = new ArrayList<Course>(0);
		
		try {
			connection = datasource.getConnection();
			
			String mySql = "select * from edu_m_all_courses";
			stmt =connection.prepareStatement(mySql);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Course course = new Course();
				course.setCourseId(rs.getString("code"));
				course.setCourseName(rs.getString("course_name"));
				
				data.add(course);
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
	
	public Course findCourseById(String courseId) {
		Connection connection = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		Course course = new Course();
	
		
		try {
			connection = datasource.getConnection();
			
			String mySql = "select * from edu_m_all_courses where code=?";
			stmt =connection.prepareStatement(mySql);
		    stmt.setString(1, courseId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				course.setCourseId(rs.getString("code"));
				course.setCourseName(rs.getString("course_name"));
				
				return course;
			}
		}catch(Exception e) {
			
		}finally {
			try {
				connection.close();			
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return course;
	}
	
	
}
