package com.vnua.fita.thoikhoabieu;

import java.util.ArrayList;
import java.util.List;

public class Thu {
    private ThuTrongTuan thu;
    private List<LichHoc> danhSachLichHoc = new ArrayList<>();

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

    public void themLichHoc(LichHoc lichHoc) {
        danhSachLichHoc.add(lichHoc);
    }

    public List<LichHoc> getDanhSachLichHoc() {
        return danhSachLichHoc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Thứ " + getTenThu() + ":\n");
        for (LichHoc lich : danhSachLichHoc) {
            sb.append("\t").append(lich).append("\n");
        }
        return sb.toString();
    }
}