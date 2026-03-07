package com.fita.vnua.credit;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Student extends Human{
	private String class_;
	private Map<String, Subject> subjectMap = new HashMap<>();
	
	public Student() {}
	
	public Student(String code) {
		this.code = code;
	}
	
	public Student(String code, String fullname) {
		super(code,fullname);
	}
	
	public Student(String code, String fullname, String class_) {
		super(code,fullname);
		this.class_ = class_;
	}
	
	public Student(String code, String fullname, String class_, String address) {
		this(code, fullname,class_);
		this.address = address;
	}
	

	public void enterInfo(Scanner sc)
	{
		System.out.println("Nhap ma sinh vien: ");
		code = sc.nextLine();
		System.out.println("Nhap ho ten: ");
		fullName = sc.nextLine();
		System.out.println("Nhap dia chi: ");
		address = sc.nextLine();
		System.out.println("Nhap lop: ");
		class_ = sc.nextLine();
	}
	
	public void addSubject(Subject sub) {
		subjectMap.put(sub.getSubjectCode(), sub);
	}
	
	
	
	public float calTermAverageMark() {
		float ts=0;
		int ms=0;
		for(Subject sub: subjectMap.values()) {
			ts += sub.getCredit() * sub.calSubjectMark();
			ms += sub.getCredit();
		}
		
		return ts/ms;
	}
	
	public void deleteByCode(String code){
		subjectMap.remove(code);
	}
	
	public void findSubjectByCode(String subjectCode) {
		subjectMap.get(subjectCode);
	}
	
	@Override
	public String toString() {
		return code + " - " + fullName + " - " + class_;
	}
	
	@Override
	public boolean equals(Object obj) {
		Student anotherStd = (Student)obj;
		float d = Math.abs(this.calTermAverageMark() - anotherStd.calTermAverageMark());
		return d<0.3;
	}
	
	public String getclass_() {
		return class_;
	}
	
	public void setClass_(String class_) {
		this.class_ = class_;
	}

}