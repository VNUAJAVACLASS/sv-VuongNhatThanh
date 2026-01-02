package com.fita.vnua.credit;

public class PythonSubject extends Subject {
	private float teamMark; 

    public PythonSubject(String subjectCode, String subjectName, int credit, float attendanceMark, float midExamMark, float finalExamMark)
    {
        super(subjectCode, subjectName, credit);
        this.attendanceMark = attendanceMark;
        this.midExamMark = midExamMark;
        this.finalExamMark = finalExamMark;
    }

    @Override
    public float calSubjectMark()
    {
        return attendanceMark * 0.1f + midExamMark * 0.2f + finalExamMark * 0.5f + teamMark * 0.2f;
    }

 
    public void setteamtMark(float teamMark)
    {
        this.teamMark = teamMark;
    }
}
