package vnua.fita.credit;

import java.util.Scanner;

/**
 * Lớp Subject đại diện cho một môn học với các thuộc tính là các loại điểm số và tín chỉ.
 */
public class Subject {
	private String subjectCode; // Mã môn học
	private String subjectName; // Tên môn học
	private int credit; // Số tín chỉ
	private float attendanceMark; // Điểm chuyên cần
	private float midExamMark; // Điểm thi giữa kỳ
	private float finalExamMark; // Điểm thi cuối kỳ

	/**
	 * Hàm mặc định
	 */
	public Subject() {
	}

	/**
	 * Hàm khởi tạo môn học với mã môn, tên môn và số tín chỉ
	 */
	public Subject(String subjectCode, String subjectName, int credit) {
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.credit = credit;
	}

	/**
	 * Tính điểm tổng kết môn học dựa trên trọng số các cột điểm
	 */
	public float calSubjectMark() {
		return (attendanceMark + 3 * midExamMark + 6 * finalExamMark) / 10;
	}

	/**
	 * Chuyển đổi điểm số sang hệ số 4
	 */
	public float calConversionMark() {
		float subjectMark = calSubjectMark();
		if ((subjectMark <= 3.9) && (subjectMark >= 0)) return 0;
		else if (subjectMark <= 4.9) return 1;
		else if (subjectMark <= 5.4) return 1.5f;
		else if (subjectMark <= 6.4) return 2;
		else if (subjectMark <= 6.9) return 2.5f;
		else if (subjectMark <= 7.9) return 3;
		else if (subjectMark <= 8.4) return 3.5f;
		else return 4;
	}

	/**
	 * Xếp loại môn học theo thang điểm chữ
	 */
	public String calGrade() {
		float subjectMark = calSubjectMark();
		if (subjectMark < 0) return "Error";
		else if (subjectMark <= 3.9) return "F";
		else if (subjectMark <= 4.9) return "D";
		else if (subjectMark <= 5.4) return "D+";
		else if (subjectMark <= 6.4) return "C";
		else if (subjectMark <= 6.9) return "C+";
		else if (subjectMark <= 7.9) return "B";
		else if (subjectMark <= 8.4) return "B+";
		else return "A";
	}

	/**
	 * Phương thức get và set
	 */
	public void setAttendanceMark(float attendaceMark) {
		this.attendanceMark = attendaceMark;
	}

	public void setMidExamMark(float midExamMark) {
		this.midExamMark = midExamMark;
	}

	public void setFinalExamMark(float finalExamMark) {
		this.finalExamMark = finalExamMark;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getCredit() {
		return credit;
	}

	/**
	 * Hiển thị thông tin môn học
	 */
	@Override
	public String toString() {
		return subjectName + "-" + subjectCode + "-" + credit + "-" + calSubjectMark() + "-" + calConversionMark() + "-" + calGrade();
	}

	
	public static void main(String[] args) {
		// Tạo đối tượng môn học và thiết lập điểm
		Subject sub = new Subject("TH03111", "Lap trinh Java", 3);
		sub.setAttendanceMark(9.5f);
		sub.setMidExamMark(8.5f);
		sub.setFinalExamMark(9);
		System.out.println(sub);

		Scanner sc = new Scanner(System.in);
		System.out.println("Nhap ma mon hoc: ");
		String subjectCode = sc.nextLine();
		System.out.println("Nhap ten mon hoc: ");
		String subjectName = sc.nextLine();
		System.out.println("Nhap so tin chi: ");
		int credit = sc.nextInt();
		sc.nextLine();
		sc.close();
		
		// Khởi tạo đối tượng môn học với thông tin nhập vào
		Subject sub2 = new Subject(subjectCode, subjectName, credit);
		System.out.println(sub2);
	}
}
