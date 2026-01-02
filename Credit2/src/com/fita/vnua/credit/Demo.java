 package com.fita.vnua.credit;

public class Demo {
	public static void main(String[] args) {
		
    Student sv = new Student("SV01", "Thanhvjppro", "CNTTC", "Ha Noi");

    Subject java = new JavaSubject("JV01", "Java Programming", 3, 4.5f, 5.5f, 6.5f);
    Subject python = new PythonSubject("PY01", "Python Programming",4, 5.5f, 6.5f, 7.5f);

    sv.addSubject(java);
    sv.addSubject(python);

    System.out.println("Diem TB hoc phan Java: "+ java.calSubjectMark()+"-"+java.calConversionMark()+"-"+java.calGrade());
    System.out.println("Diem TB hoc phan Python: "+ python.calSubjectMark()+"-"+java.calConversionMark()+"-"+python.calGrade());
    System.out.println("Điểm TB học kỳ: " + sv.calTermAverageMark());
}

}