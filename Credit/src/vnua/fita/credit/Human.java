package vnua.fita.credit;

import java.util.Scanner;

/**
 * Lớp Human đại diện cho một con người với các thuộc tính cơ bản như mã, họ tên và địa chỉ.
 */
public class Human {
    // Thuộc tính lưu trữ địa chỉ, mã và họ tên
    protected String address;
    protected String code;
    protected String fullname;
    
    /**
     * Hàm tv không tham số
     */
    public Human() {}
    
    
    public Human(String code) {
        this.code = code;
    }
    
    
    public Human(String code, String fullname) {
        this.code = code;
        this.fullname = fullname;
    }
    
       public Human(String code, String fullname, String address) {
        this(code, fullname); 
        this.address = address;
    }
    
    /**
     * hàm tv nhập thông tin từ bàn phím
     * 
     */
    public void enterInfo(Scanner sc) {
        System.out.println("Nhap ma: ");
        code = sc.nextLine();
        
        System.out.println("Nhap ho va ten: ");
        fullname = sc.nextLine();
        
        System.out.println("Nhap dia chi: ");
        address = sc.nextLine();
    }
    
    /**
     * hàm tv chuyển đối tượng thành chuỗi
     * 
     */
    @Override
    public String toString() {
        return code + "-" + fullname + "-" + address;
    }
    
    /**
     * hàm so sánh hai đối tượng Human dựa trên mã
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; 
        if (obj == null || getClass() != obj.getClass()) return false; 
        
        Human anotherHuman = (Human) obj; 
        return this.code.equals(anotherHuman.code);
    }
}
