package com.fita.vnua.credit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_usersubject")
public class UserSubject {
	@Id
	@Column(name = "user_subject_id")
	private int userSubjectId;
	@Column(name = "user_code")
	private String userCode;
	@Column(name = "subject_code")
	private String subjectCode;
	@Column(name = "attendanceexammark")
	private float attendanceExamMark;
	@Column(name = "midexammark1")
	private float midExamMark1;
	@Column(name = "midexammark2")
	private float midExamMark2;
	@Column(name = "midexammark3")
	private float midExamMark3;
	@Column(name = "finalexammark")
	private float finalExamMark;

	public UserSubject() {
	}

	public UserSubject(int userSubjectId, String userCode, String subjectCode, float attendanceExamMark,
			float midExamMark1, float midExamMark2, float midExamMark3, float finalExamMark) {
		this.userSubjectId = userSubjectId;
		this.userCode = userCode;
		this.subjectCode = subjectCode;
		this.attendanceExamMark = attendanceExamMark;
		this.midExamMark1 = midExamMark1;
		this.midExamMark2 = midExamMark2;
		this.midExamMark3 = midExamMark3;
		this.finalExamMark = finalExamMark;
	}

	public int getUserSubjectId() {
		return userSubjectId;
	}

	public void setUserSubjectId(int userSubjectId) {
		this.userSubjectId = userSubjectId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public float getAttendanceExamMark() {
		return attendanceExamMark;
	}

	public void setAttendanceExamMark(float attendanceExamMark) {
		this.attendanceExamMark = attendanceExamMark;
	}

	public float getMidExamMark1() {
		return midExamMark1;
	}

	public void setMidExamMark1(float midExamMark1) {
		this.midExamMark1 = midExamMark1;
	}

	public float getMidExamMark2() {
		return midExamMark2;
	}

	public void setMidExamMark2(float midExamMark2) {
		this.midExamMark2 = midExamMark2;
	}

	public float getMidExamMark3() {
		return midExamMark3;
	}

	public void setMidExamMark3(float midExamMark3) {
		this.midExamMark3 = midExamMark3;
	}

	public float getFinalExamMark() {
		return finalExamMark;
	}

	public void setFinalExamMark(float finalExamMark) {
		this.finalExamMark = finalExamMark;
	}

	@Override
	public String toString() {
		return userSubjectId + "-" + userCode + "-" + subjectCode + "-" + attendanceExamMark + "-" + midExamMark1 + "-"
				+ midExamMark2 + "-" + midExamMark3 + "-" + finalExamMark;
	}
}
