package vnua.fita.credit;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp Student kế thừa từ Human, đại diện cho một sinh viên.
 * Chứa thông tin về lớp học và danh sách các môn học.
 */
public class Student extends Human {
    private String class_; // Lớp học của sinh viên
    private List<Subject> subjectList = new ArrayList<Subject>(); // Danh sách môn học

    /**
     * hàm mặc định
     */
    public Student() {}

    /**
     * hàm khởi tạo với mã sinh viên
     */
    public Student(String code) {
        super(code);
    }

    /**
     * hàm khởi tạo với mã sinh viên và họ tên
     */
    public Student(String code, String fullname) {
        super(code, fullname);
    }

    /**
     * hàm khởi tạo với mã sinh viên, họ tên và lớp học
     */
    public Student(String code, String fullname, String class_) {
        super(code, fullname);
        this.class_ = class_;
    }

    /**
     * hàm khởi tạo với mã sinh viên, họ tên, lớp học và địa chỉ
     */
    public Student(String code, String fullname, String class_, String address) {
        this(code, fullname, class_);
        this.address = address;
    }

    /**
     * Thêm một môn học vào danh sách môn học của sinh viên
     */
    public void addSubject(Subject sub) {
        subjectList.add(sub);
    }

    /**
     * Tính điểm trung bình học kỳ của sinh viên dựa trên điểm quy đổi của từng môn học
     */
    public float calTermAverageMark() {
        float ts = 0; // Tổng điểm tín chỉ * điểm quy đổi
        int ms = 0; // Tổng số tín chỉ
        for (Subject sub : subjectList) {
            ts += sub.getCredit() * sub.calConversionMark();
            ms += sub.getCredit();
        }
        return ts / ms;
    }

    /**
     * Chuyển đổi thông tin sinh viên thành chuỗi để hiển thị
     */
    @Override
    public String toString() {
        return code + "-" + fullname + "-" + class_;
    }

    /**
     * So sánh hai sinh viên dựa trên điểm trung bình học kỳ
     * Hai sinh viên được coi là bằng nhau nếu điểm trung bình chênh lệch dưới 0.3
     */
    @Override
    public boolean equals(Object obj) {
        Student anotherStd = (Student) obj;
        float d = Math.abs(this.calTermAverageMark() - anotherStd.calTermAverageMark());
        return d < 0.3;
    }

    /**
     * Getter cho class_
     */
    public String getClass_() {
        return class_;
    }

    /**
     * Setter cho class_
     */
    public void setClass_(String class_) {
        this.class_ = class_;
    }

    /**
     * Phương thức main để kiểm thử lớp Student
     */
    public static void main(String[] args) {
        // Tạo sinh viên mới
        Student std = new Student("680373", "Vuong Nhat Thanh", "K68CNTTC");

        // Tạo môn học Lap trinh Java và thiết lập điểm
        Subject sub1 = new Subject("TH03111", "Lap trinh Java", 3);
        sub1.setAttendanceMark(9.5f);
        sub1.setMidExamMark(8.5f);
        sub1.setFinalExamMark(9);

        // Tạo môn học CTDLGT và thiết lập điểm
        Subject sub2 = new Subject("TH04222", "Cau truc du lieu va giai thuat", 4);
        sub2.setAttendanceMark(8);
        sub2.setMidExamMark(7);
        sub2.setFinalExamMark(6);

        // Thêm các môn học vào danh sách môn học của sinh viên
        std.addSubject(sub1);
        std.addSubject(sub2);

        // Hiển thị điểm trung bình học kỳ của sinh viên
        System.out.println("TBHK: " + std.calTermAverageMark());
    }
}
