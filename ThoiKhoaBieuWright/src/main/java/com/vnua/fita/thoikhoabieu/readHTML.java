package com.vnua.fita.thoikhoabieu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class readHTML {
    public List<LichHoc> docThoiKhoaBieu(String filePath) {
        List<LichHoc> danhSachLichHoc = new ArrayList<>();
        try {
            // Đọc file HTML
            File file = new File(filePath);
            Document doc = Jsoup.parse(file, "UTF-8");

            // Lấy bảng thời khóa biểu
            Element table = doc.selectFirst("table.table");
            if (table == null) {
                System.out.println("❌ Không tìm thấy bảng thời khóa biểu trong file HTML.");
                return danhSachLichHoc;
            }

            Elements rows = table.select("tbody tr");

            // Thông tin môn học có thể kéo dài nhiều hàng
            String maMon = "", tenMon = "", nhomTo = "", lop = "";
            int soTinChi = 0, rowspanCount = 0;

            for (Element row : rows) {
                Elements cells = row.select("td");
                int baseIndex = 0;

                // Nếu là hàng mới với rowspan
                if (cells.size() >= 9 && cells.get(0).hasAttr("rowspan")) {
                    rowspanCount = Integer.parseInt(cells.get(0).attr("rowspan"));
                    maMon = cells.get(0).text().trim();
                    tenMon = cells.get(1).text().trim();
                    nhomTo = cells.get(2).text().trim();
                    soTinChi = safeParseInt(cells.get(3).text().trim());
                    lop = cells.get(6).text().trim();
                    baseIndex = 8; // bắt đầu từ cột thứ 8 cho phần thông tin lịch học
                } else {
                    baseIndex = 0;
                    rowspanCount--;
                }

                if (cells.size() < baseIndex + 6)
                    continue; // đảm bảo đủ cột

                // Lấy thông tin lịch học
                String thuText = cells.get(baseIndex).text().trim();
                int thu = thuText.equalsIgnoreCase("CN") ? 8 : safeParseInt(thuText);
                int tietBatDau = safeParseInt(cells.get(baseIndex + 1).text().trim());
                int soTiet = safeParseInt(cells.get(baseIndex + 2).text().trim());
                String phong = cells.get(baseIndex + 3).text().trim();
                String giangVien = cells.get(baseIndex + 4).text().trim();
                String thoiGianHoc = cells.get(baseIndex + 5).select("span.text-left").text().trim();

                // Tạo đối tượng lịch học
                LichHoc lichHoc = new LichHoc(maMon, tenMon, nhomTo, soTinChi, lop, thu,
                        tietBatDau, soTiet, phong, giangVien, thoiGianHoc);
                danhSachLichHoc.add(lichHoc);
            }

        } catch (IOException e) {
            System.err.println("❌ Lỗi đọc file HTML: " + e.getMessage());
        }

        return danhSachLichHoc;
    }

    // Hàm an toàn chuyển số, tránh crash khi dữ liệu không đúng định dạng
    private int safeParseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.err.println("⚠️ Lỗi định dạng số: '" + input + "'");
            return 0;
        }
    }
}