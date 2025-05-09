package vnua.fita.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocHTML {
    private CtrinhChinh ctrinhChinh;

    public DocHTML(CtrinhChinh ctrinhChinh) {
        this.ctrinhChinh = ctrinhChinh;
    }

    public void docFileHTML(String tenFile) throws IOException {
        // Đọc file HTML
        File input = new File(tenFile);
        Document doc = Jsoup.parse(input, "UTF-8");

        // Chọn bảng đầu tiên trong HTML
        Element table = doc.select("table").first();
        if (table == null) {
            throw new IOException("Không tìm thấy bảng trong file HTML");
        }

        // Lấy tất cả các dòng của bảng
        Elements rows = table.select("tr");

        // Bỏ qua dòng tiêu đề 
        boolean isHeader = true;
        List<String> cachedData = new ArrayList<>(); // Lưu trữ dữ liệu từ các cột có rowspan
        int rowspanCount = 0; // Đếm số hàng còn lại của rowspan

        for (Element row : rows) {
            if (isHeader) {
                isHeader = false; // Bỏ qua dòng tiêu đề
                continue;
            }

            Elements cols = row.select("td");
            List<String> currentRowData = new ArrayList<>();

            // Nếu còn trong phạm vi rowspan, sử dụng dữ liệu đã lưu
            if (rowspanCount > 0) {
                currentRowData.addAll(cachedData.subList(0, 5)); // Thêm dữ liệu từ các cột rowspan
                for (int i = 0; i < cols.size(); i++) {
                    currentRowData.add(cols.get(i).text());
                }
                rowspanCount--;
            } else {
                // Xử lý dòng mới
                for (Element col : cols) {
                    currentRowData.add(col.text());
                }

                // Kiểm tra rowspan của cột đầu tiên (mã môn học)
                String rowspanAttr = cols.get(0).attr("rowspan");
                if (!rowspanAttr.isEmpty()) {
                    rowspanCount = Integer.parseInt(rowspanAttr) - 1;
                    cachedData = new ArrayList<>(currentRowData.subList(0, 5)); // Lưu 5 cột đầu
                }
            }

            // Đảm bảo dòng có đủ cột (11 cột)
            if (currentRowData.size() == 11) {
                // Tạo đối tượng LichHoc từ dữ liệu
                LichHoc lichHoc = taoLichHoc(currentRowData);
                if (lichHoc != null) {
                    ctrinhChinh.themLichHoc(lichHoc);
                }
            }
        }
    }

    private LichHoc taoLichHoc(List<String> cols) {
        try {
            String maMonHoc = cols.get(0);
            String tenMonHoc = cols.get(1);
            String nhomTo = cols.get(2);
            int soTinChi = Integer.parseInt(cols.get(3));
            String lop = cols.get(4);
            int thu = cols.get(5).equals("CN") ? 8 : Integer.parseInt(cols.get(5)); // Xử lý "CN" là Chủ Nhật
            int tietBatDau = Integer.parseInt(cols.get(6));
            int soTiet = Integer.parseInt(cols.get(7));
            String phong = cols.get(8);
            String giangVien = cols.get(9);
            String thoiGianHoc = cols.get(10);

            return new LichHoc(maMonHoc, tenMonHoc, nhomTo, soTinChi, lop, thu, tietBatDau, soTiet, phong, giangVien, thoiGianHoc);
        } catch (NumberFormatException e) {
            System.err.println("Lỗi định dạng số trong dữ liệu: " + cols);
            return null;
        }
    }
}