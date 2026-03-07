package com.vnua.fita.thoikhoabieu;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.vnua.fita.thoikhoabieu.Thu.ThuTrongTuan;

public class Tuan {
    private int soTuan;
    private Map<String, Thu> thuMap = new HashMap<>();

    public Tuan(int soTuan) {
        this.soTuan = soTuan;
        for (ThuTrongTuan thuEnum : ThuTrongTuan.values()) {
            thuMap.put(thuEnum.getThuSo(), new Thu(thuEnum));
        }
    }

    public void themLichHoc(LichHoc lichHoc) {
        String thuKey = lichHoc.getThu() == 8 ? "CN" : String.valueOf(lichHoc.getThu());
        Thu thu = thuMap.get(thuKey);
        if (thu != null) {
            thu.themLichHoc(lichHoc);
        } else {
            System.err.println("⚠️ Không tìm thấy thứ: " + thuKey);
        }
    }

    public int getSoTuan() {
        return soTuan;
    }

    public Collection<Thu> getDanhSachThu() {
        return thuMap.values();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("📅 Tuần " + soTuan + ":\n");
        for (Thu thu : thuMap.values()) {
            if (!thu.getDanhSachLichHoc().isEmpty()) {
                sb.append(thu).append("\n");
            }
        }
        return sb.toString();
    }
}