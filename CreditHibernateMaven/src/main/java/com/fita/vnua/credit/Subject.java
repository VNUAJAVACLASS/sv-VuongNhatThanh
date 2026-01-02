package com.fita.vnua.credit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tbl_subject")
public class Subject {
	@Id
	@Column(name = "subject_code")
    private String subjectCode;
	
	@Column(name = "subject_name")
    private String subjectName;

	@Column(name = "credit")
    private int credit;
	
	@Transient 
    private float attendanceMark; // Diem CC
	@Transient 
    private float midExamMark;    // Diem GK
	@Transient 
    private float finalExamMark;  // Diem CK
   
    @Column(name = "hesodiem1")
    private Float hesoDiem1;      // Hệ số điểm 1
    @Column(name = "hesodiem2")
    private Float hesoDiem2;      // Hệ số điểm 2
    @Column(name = "hesodiem3")
    private Float hesoDiem3;      // Hệ số điểm 3
    @Column(name = "hesodiem4")
    private Float hesoDiem4;      // Hệ số điểm 4
    @Column(name = "hesodiem5")
    private Float hesoDiem5;      // Hệ số điểm 5

    // Constructor mặc định
    public Subject() {}

    // Constructor với tham số
    public Subject(String subjectCode, String subjectName, int credit) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
    }

    // Tính điểm học phần
    public float calSubjectMark() {
        return attendanceMark * 0.1f + midExamMark * 0.4f + finalExamMark * 0.5f;
    }

    // Tính điểm quy đổi
    public float calConversionMark() {
        float subjectMark = calSubjectMark();
        if (subjectMark <= 3.9) return 0;
        else if (subjectMark <= 4.9) return 1;
        else if (subjectMark <= 5.4) return 1.5f;
        else if (subjectMark <= 6.4) return 2;
        else if (subjectMark <= 6.9) return 2.5f;
        else if (subjectMark <= 7.4) return 3;
        else if (subjectMark <= 8.4) return 3.5f;
        else return 4;
    }

    // Tính điểm chữ
    public String calGrade() {
        float subjectMark = calSubjectMark();
        if (subjectMark < 0) return "Error";
        if (subjectMark <= 3.9) return "F";
        if (subjectMark <= 4.9) return "D";
        if (subjectMark <= 5.4) return "D+";
        if (subjectMark <= 6.4) return "C";
        if (subjectMark <= 6.9) return "C+";
        if (subjectMark <= 7.4) return "B";
        if (subjectMark <= 8.4) return "B+";
        return "A";
    }

    // Getter và Setter cho subjectCode, subjectName và credit
    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    // Các getter và setter cho hệ số điểm
    public Float getHesoDiem1() { return hesoDiem1; }
    public Float getHesoDiem2() { return hesoDiem2; }
    public Float getHesoDiem3() { return hesoDiem3; }
    public Float getHesoDiem4() { return hesoDiem4; }
    public Float getHesoDiem5() { return hesoDiem5; }

    public void setHesoDiem1(Float hesoDiem1) { this.hesoDiem1 = hesoDiem1; }
    public void setHesoDiem2(Float hesoDiem2) { this.hesoDiem2 = hesoDiem2; }
    public void setHesoDiem3(Float hesoDiem3) { this.hesoDiem3 = hesoDiem3; }
    public void setHesoDiem4(Float hesoDiem4) { this.hesoDiem4 = hesoDiem4; }
    public void setHesoDiem5(Float hesoDiem5) { this.hesoDiem5 = hesoDiem5; }

    @Override
    public String toString() {
        return subjectName + "-" + subjectCode + "-" + credit + "-" + calSubjectMark() + "-" + calConversionMark() + "-" + calGrade();
    }
}
