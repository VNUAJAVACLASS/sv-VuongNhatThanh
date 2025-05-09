package vnua.fita.jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CtrinhChinh {
	private Map<Integer, List<LichHoc>> dsTuanHoc;

	public CtrinhChinh() {
		this.dsTuanHoc = new HashMap<>();
	}

	public void themLichHoc(LichHoc lichHoc) {
		String thoiGianHoc = lichHoc.getThoiGianHoc();
		// Tách chuỗi thời gian học để lấy các tuần
		List<Integer> tuanHocList = tachTuanHoc(thoiGianHoc);

		for (Integer tuan : tuanHocList) {
			// Thêm lịch học vào danh sách tuần tương ứng
			dsTuanHoc.computeIfAbsent(tuan, k -> new ArrayList<>()).add(lichHoc);
		}
	}

	private List<Integer> tachTuanHoc(String thoiGianHoc) {
		List<Integer> tuanHocList = new ArrayList<>();
		// Chuỗi thời gian học dạng "12----5678----------------"
		// Mỗi ký tự tương ứng với một tuần, ký tự không phải '-' là tuần học
		for (int i = 0; i < thoiGianHoc.length(); i++) {
			if (thoiGianHoc.charAt(i) != '-') {
				// Tuần bắt đầu từ 1, nên cộng thêm 1 vào chỉ số
				tuanHocList.add(i + 1);
			}
		}
		return tuanHocList;
	}

	// Getters and Setters
	public Map<Integer, List<LichHoc>> getDsTuanHoc() {
		return dsTuanHoc;
	}

	public void setDsTuanHoc(Map<Integer, List<LichHoc>> dsTuanHoc) {
		this.dsTuanHoc = dsTuanHoc;
	}
}