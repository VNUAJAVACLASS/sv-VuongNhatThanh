package com.fita.vnua.credit;

public abstract class Subject {
	protected String subjectCode;
	protected String subjectName;
	protected int credit;
	protected float attendanceMark;	//Diem CC
	protected float midExamMark;	//Diem GK
	protected float finalExamMark;	//Diem CK
	
	//Tao constructor mac dinh
	public Subject() {}
	
	//Tao constructor co tham so
	public Subject(String subjectCode, String subjectName, int credit) {
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.credit = credit;
	}
	
	//Phuong thuc tinh diem hoc phan
	public abstract float calSubjectMark();
	
	//Tinh thang diem 4
	public float calConversionMark() {
		float subjectMark = calSubjectMark();
		float conversionMark = -1;
		if(subjectMark <= 3.9) {
			conversionMark = 0;
		}else if(subjectMark <=4.9) {
			conversionMark = 1;
		}else if(subjectMark <=5.4) {
			conversionMark = (float) 1.5;
		}else if(subjectMark <= 6.4) {
			conversionMark = 2;
		}else if(subjectMark <= 6.9) {
			conversionMark = (float) 2.5;
		}else if(subjectMark <= 7.4) {
			conversionMark = 3;
		}else if(subjectMark <= 8.4) {
			conversionMark = (float) 3.5;
		}else if(subjectMark <= 10) {
			conversionMark = 4;
		}
		
		return conversionMark;
	}
	
	//Phuong thuc tinh diem theo thang chu
	public String calGrade() {
		float subjectMark = calSubjectMark();
		String grade = null;
		if(subjectMark < 0) {
			grade = "Error";
		}if(subjectMark <= 3.9) {
			grade = "F";
		}else if(subjectMark <=4.9) {
			grade = "D";
		}else if(subjectMark <=5.4) {
			grade = "D+";
		}else if(subjectMark <= 6.4) {
			grade = "C";
		}else if(subjectMark <= 6.9) {
			grade =  "C+";
		}else if(subjectMark <= 7.4) {
			grade = "B";
		}else if(subjectMark <= 8.4) {
			grade =  "B+";
		}else if(subjectMark <= 10) {
			grade = "A";
		}
		
		return grade;
	}
	
	//Chuyen tu thang chu sang thang 4
	public float calConversionMark(String grade) {
		float conversionMark = -1;
		switch (grade) {
			case "F" : 
				conversionMark = 0;
				break;
			
			case "D" : 
				conversionMark = (float) 4.9;
				break;
				
			case "D+" : 
				conversionMark = (float) 5.4;
				break;
				
			case "C" : 
				conversionMark = (float) 6.4;
				break;
				
			case "C+" : 
				conversionMark = (float) 6.9;
				break;
				
			case "B" : 
				conversionMark = (float) 7.4;
				break;
				
			case "B+" : 
				conversionMark = (float) 8.4;
				break;
				
			case "A" : 
				conversionMark = 10;
				break;
		}
	
		return conversionMark;
	}
	
	//Phuong thuc get tra ve so credit
	public int getCredit() {
		return credit;
	}
	
	public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }
	
	//Phuong thuc set dat gia tri cho attendanceMark
	public void setAttendanceMark(float attendanceMark) {
		this.attendanceMark = attendanceMark;
	}
	
	//Phuong thuc set dat gia tri cho midExamMark
	public void setMidExamMark(float midExamMark) {
		this.midExamMark = midExamMark;
	}
	
	//Phuong thuc set dat gia tri cho finalExamMark
	public void setFinalExamMark(float finalExamMark) {
		this.finalExamMark = finalExamMark;
	}
		
	@Override
	public String toString() {
		return subjectName + "-" + subjectCode + "-" + credit + "-" + calSubjectMark() + "-" + calConversionMark() + "-" + calGrade();
	}
	
}

	