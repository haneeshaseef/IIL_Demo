package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.Constants.NcoeContants;
import com.example.demo.pojo.Applicant;
import com.example.demo.pojo.Course;
import com.example.demo.pojo.EligibityProcessRequest;

@Repository
public class EligibiltyDAO {

	public static Logger ncelogger = org.slf4j.LoggerFactory.getLogger("ncoeLog");
	public static Logger nceErrorlogger = org.slf4j.LoggerFactory.getLogger("ncoeErrorLog");

	@Autowired
	private DataSource datasource;

	public String processEligibility(EligibityProcessRequest eligibilityProcessRequest) {

		String sql = "";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Course> allCourses = new ArrayList<Course>();

		try {
			ncelogger.debug("Our EligibilityDao processEligibility Method Called...");
			ncelogger.debug("Try to Create Database Connection");
			conn = datasource.getConnection();
			int processSquence = getSquenceVal(conn);
			ncelogger.debug("Sequence added Sucessfully");
			if (conn != null) {
				ncelogger.debug("Create Database Connection Successful");
			}

			sql = "select * from edu_m_all_courses";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Course course = new Course();
				course.setCourseId(rs.getString("code"));
				course.setCourseName(rs.getString("course_name"));
				allCourses.add(course);
			}
			stmt.close();
			rs.close();
			ncelogger.debug("Found " + allCourses.size() + " Courses");
			for (Course currentCourse : allCourses) {
				try {
					ncelogger.debug("Course " + currentCourse.getCourseName() + " Selected for Eligibility Check");
					getAllApplicantsByCourseNo(currentCourse.getCourseId(), conn, processSquence);
					ncelogger.debug("All Applicants loaed Sucessfully for" + currentCourse.getCourseName());
				} catch (Exception e) {
					nceErrorlogger.error("Unable to Process Applicant for course" + currentCourse.getCourseName());
				}

			}

		} catch (Exception e) {
			nceErrorlogger.error(e.getMessage());
			return "Error Occurred";
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return "Success";
	}

	public int getSquenceVal(Connection conn) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int sequence = 0;
		try {
			String sql = "select NEXT VALUE FOR eligibility_process_seq";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				sequence = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sequence;
	}

	public void processCourse_1(Course primaryEducationCourse, Connection conn) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from edu_t_application "
					+ "where course_pref_one=? or course_pref_two=? or course_pref_three=?";

			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String applicationNo = null;
				try {
					applicationNo = rs.getString("application_no");

				} catch (Exception e) {
					nceErrorlogger.error("Unable to Process Student With Application No " + applicationNo);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public List<Applicant> getAllApplicantsByCourseNo(String courseNo, Connection conn, int ProcessSequence)
			throws Exception {
		String sql = "";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Applicant> applicantsList = new ArrayList<Applicant>();
		try {
			ncelogger.debug("Our getAllApplicantsByCourseNo Method Called...");
			ncelogger.debug("Try to Create Database Connection");
			conn = datasource.getConnection();
			if (conn != null) {
				ncelogger.debug("Create Database Connection Successful");
			}

			sql = "select * from edu_t_application WHERE course_pref_one =? OR course_pref_two = ? OR course_pref_three = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, courseNo);
			stmt.setString(2, courseNo);
			stmt.setString(3, courseNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Applicant applicant = new Applicant();
				applicant.setAppliationNo(rs.getString("application_no"));
				applicant.setApplicantName(rs.getString("full_name"));
				applicant.setNicNo(rs.getString("nic_no"));
				applicant.setzScore(rs.getDouble("zscore"));
				applicant.setApplicant_seq(rs.getString("application_seq"));
				applicant.setCoursePrefOne("course_pref_one");
				applicant.setCoursePrefTwo("course_pref_two");
				applicant.setCoursePrefOne("course_pref_three");

				if (applicant.getCoursePrefOne().equalsIgnoreCase(NcoeContants.BUDDHISM_COURSE_ID)) {
					ncelogger.debug(applicant.getApplicantName() + " is applied for Buddhism as course preference one");
				} else {

				}

				if (applicant.getCoursePrefTwo().equalsIgnoreCase(NcoeContants.BUDDHISM_COURSE_ID)) {
					ncelogger.debug(applicant.getApplicantName() + " is applied for Buddhism as course preference Two");
				} else {

				}
				if (applicant.getCoursePrefThree().equalsIgnoreCase(NcoeContants.BUDDHISM_COURSE_ID)) {
					ncelogger.debug(
							applicant.getApplicantName() + " is applied for Buddhism as course preference Three");
				} else {

				}

				applicantsList.add(applicant);
				ncelogger.debug("Applicant No : " + applicant.getAppliationNo() + " Applicant Name: "
						+ applicant.getApplicantName() + " Applicant NIC No: " + applicant.getNicNo()
						+ "Applicant Sequence: " + applicant.getApplicant_seq() + " Course Pref One "
						+ applicant.getCoursePrefOne() + " Course Pref Two" + applicant.getCoursePrefTwo()
						+ " Course Pref Three " + applicant.getCoursePrefThree() + " Z score: "
						+ applicant.getzScore());

				sql = "INSERT INTO el_t_processed_applications (execution_instance_id, application_no,application_seq,course_code , z_score, calculated_score, ol_score) VALUES (?, ?,? ,?, ?, ?, ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, ProcessSequence);
				stmt.setString(2, applicant.getAppliationNo());
				stmt.setString(3, applicant.getApplicant_seq());
				stmt.setString(4, courseNo);
				stmt.setDouble(5, applicant.getzScore());
				stmt.setInt(6, 15);
				stmt.setInt(7, 19);
				int result = stmt.executeUpdate();
				if (result > 0) {
					ncelogger.debug("Applicanted added to database");
				} else {
					ncelogger.error("Failed to add applicant to database");
				}

			}
			stmt.close();
			rs.close();

		} catch (Exception e) {
			nceErrorlogger.error(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return applicantsList;
	}

	public String getAllcourses() {

		String sql = "";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Course> allCourses = new ArrayList<Course>();
		try {
			ncelogger.debug("Get all Courses Method Called...");
			ncelogger.debug("Try to Create Database Connection");
			conn = datasource.getConnection();
			if (conn != null) {
				ncelogger.debug("Create Database Connection Successful");
			}

			sql = "select * from edu_m_all_courses";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Course course = new Course();
				course.setCourseId(rs.getString("code"));
				course.setCourseName(rs.getString("course_name"));
				allCourses.add(course);
			}
			stmt.close();
			rs.close();
			ncelogger.debug("Found " + allCourses.size() + " Courses");
			for (Course currentCourse : allCourses) {
				try {
					ncelogger.debug("Course " + currentCourse.getCourseName() + " Selected for Eligibility Check");

					if (currentCourse.getCourseId().equalsIgnoreCase("1")) { // Primary Education
						processCourse_1(currentCourse, conn);
					}

				} catch (Exception e) {
					nceErrorlogger.error("Unable to Process Course " + currentCourse.getCourseName());
				}
			}

		} catch (Exception e) {
			nceErrorlogger.error(e.getMessage());
			return "Error Occurred";
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return "Success";
	}

}
