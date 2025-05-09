package vnua.fita.jsoup;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            // Khởi tạo và đọc file HTML
            CtrinhChinh ctrinhChinh = new CtrinhChinh();
            DocHTML docHTML = new DocHTML(ctrinhChinh);
            docHTML.khoiTaoVaDocFileHTML("src/main/resources/VuongNhatThanh.html");

            // Hiển thị menu và xử lý tương tác người dùng
            ctrinhChinh.hienThiMenuVaXuLyLuaChon(scanner);
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file HTML: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}