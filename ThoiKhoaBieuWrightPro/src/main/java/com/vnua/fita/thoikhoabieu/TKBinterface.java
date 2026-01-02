package com.vnua.fita.thoikhoabieu;

import java.util.List;

public interface TKBinterface {
    /**
     * Lấy nội dung HTML của thời khóa biểu từ trang web
     * username Tên đăng nhập
     * password Mật khẩu
     * semester Học kỳ cần lấy (ví dụ: "Học kỳ 2 - Năm học 2024 - 2025")
     * outputPath Đường dẫn lưu file HTML
     * true nếu lấy và lưu HTML thành công, false nếu thất bại
     */
    boolean fetchTKBHtml(String username, String password, String semester, String outputPath);

    /**
     * Đọc và phân tích file HTML để trả về danh sách lịch học
     * filePath Đường dẫn tới file HTML
     * Danh sách các đối tượng LichHoc
     */
    List<LichHoc> parseTKBFromHtml(String filePath);

    /**
     * Xóa file HTML tạm thời sau khi sử dụng
     * filePath Đường dẫn tới file HTML
     * true nếu xóa thành công, false nếu thất bại
     */
    boolean deleteTempHtmlFile(String filePath);
}