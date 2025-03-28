package vnua.fita.credit;

import java.util.Scanner;

// Lớp Lecturer kế thừa từ lớp Human
public class Lecturer extends Human {
    private String password; // Thuộc tính lưu mật khẩu của giảng viên
    
    // hàm không tham số
    public Lecturer() {}
    
    // hàm tv tham số khởi tạo mã giảng viên và mật khẩu
    public Lecturer(String code, String password) {
        this.code = code;
        this.password = password;
    }
    
    // hàm tv có tham số khởi tạo mã giảng viên, họ tên và địa chỉ
    public Lecturer(String code, String fullname, String address) {
        super(code, fullname, address); // Gọi constructor của lớp cha (Human)
    }
    
    
    @Override
    public String toString() {
        return code + "-" + fullname + "-" + password;
    }
    
    // hàm tv nhập thông tin giảng viên từ bàn phím
    @Override
    public void enterInfo(Scanner sc) {
        super.enterInfo(sc); // Gọi phương thức nhập thông tin từ lớp cha
        System.out.println("Nhap mat khau: ");
        password = sc.nextLine(); // Nhập mật khẩu từ bàn phím
    }
}
