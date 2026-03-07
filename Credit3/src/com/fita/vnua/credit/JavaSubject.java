package com.fita.vnua.credit;
public abstract class JavaSubject extends Subject 
{
	private float attendanceMark;
	private float midExamMark;
	private float finalExamMark;
    public JavaSubject(String subjectCode, String subjectName, int credit, float attendanceMark, float midExamMark, float finalExamMark)
    {
        super(subjectCode, subjectName, credit);
        this.attendanceMark = attendanceMark;
        this.midExamMark = midExamMark;
        this.finalExamMark = finalExamMark;
    }

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
	
    public float calSubjectMark()
    {
        return (float) ( attendanceMark * 0.1 + midExamMark * 0.3 + finalExamMark * 0.6);
    }
    
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
}