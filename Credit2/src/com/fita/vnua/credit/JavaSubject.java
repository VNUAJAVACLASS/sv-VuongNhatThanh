package com.fita.vnua.credit;
public class JavaSubject extends Subject 
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

    @Override
    public float calSubjectMark()
    {
        return (float) ( attendanceMark * 0.1 + midExamMark * 0.3 + finalExamMark * 0.6);
    }
}