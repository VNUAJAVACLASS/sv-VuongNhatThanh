package com.vnua.fita.thoikhoabieu;

import java.util.HashMap;
import java.util.Map;

public class Thu {
    private ThuTrongTuan thu;
    private Map<String, LichHoc> danhSachLichHoc = new HashMap<>();

    public enum ThuTrongTuan {
        HAI("2"), BA("3"), TU("4"), NAM("5"), SAU("6"), BAY("7"), CHU_NHAT("CN");

        private final String thuSo;

        ThuTrongTuan(String thuSo) {
            this.thuSo = thuSo;
        }

        public String getThuSo() {
            return thuSo;
        }

        public static ThuTrongTuan fromString(String s) {
            for (ThuTrongTuan thu : values()) {
                if (thu.thuSo.equalsIgnoreCase(s)) {
                    return thu;
                }
            }
            throw new IllegalArgumentException("Không có thứ: " + s);
        }
    }

    public Thu(ThuTrongTuan thu) {
        this.thu = thu;
    }

    public String getTenThu() {
        return thu.getThuSo();
    }

    // Dùng khóa là mã tiết hoặc mã lớp học duy nhất trong ngày
    public void themLichHoc(LichHoc lichHoc) {
        // Giả sử tiết học có mã tiết duy nhất trong ngày
        String key = String.valueOf(lichHoc.getSoTiet()); // hoặc lichHoc.getMaLichHoc() nếu có
        danhSachLichHoc.put(key, lichHoc);
    }

    public Map<String, LichHoc> getDanhSachLichHoc() {
        return danhSachLichHoc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Thứ " + getTenThu() + ":\n");
        for (Map.Entry<String, LichHoc> entry : danhSachLichHoc.entrySet()) {
            sb.append("\tTiết ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
