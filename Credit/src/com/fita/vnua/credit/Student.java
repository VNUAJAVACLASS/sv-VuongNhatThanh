package com.fita.vnua.credit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student extends Human
{
	private String class_;
	List <Subject> subjectList = new ArrayList<>();
	
	//Tao constructor 
	public Student()
	{
		
	}
	
	//Tao constructor chua tham so code
	public Student(String code)
	{
		super(code);
	}
	
	//Tao constructor chua tham so code,fullName
	public Student(String code, String fullName)
	{
		super(code,fullName);
	}
	
	//Tao constructor chua tham so code,fullName, class_
	public Student(String code, String fullName, String class_)
	{
		super(code,fullName);
		this.class_ = class_;
	}
	
	//Tao constructor chua tham so code,fullName,class_,address
	public Student(String code, String fullName, String class_, String address)
	{
		super(code,fullName,address);
		this.class_ = class_;
	}
	
	//Tao phuong thuc them mon hoc
	public void addSubject(Subject sub)
	{
		subjectList.add(sub);
	}
	
	//Tao phuong thuc tinh diem trung binh hoc ky
	public float calTermAverageMark() {
		float ts = 0;
		int ms = 0;
		for(Subject sub: subjectList) {
			ts += sub.getCredit()*sub.calConversionMark();
			ms += sub.getCredit();
		}
		return ts/ms;
	}
	
	@Override
	public String toString() {
		return code + "-" + fullName + "-" + class_;
	}
	
	@Override 
	public boolean equals(Object obj) {
		Student anotherStd = (Student)obj;
		float d = Math.abs(this.calTermAverageMark()-anotherStd.calTermAverageMark());
		return d < 0.3;
	}
	//Tao phuong thuc get class
	public String getClass_()
	{
		return class_;
	}
	public void setClass_(String class_)
	{
		this.class_ = class_;
	}
}