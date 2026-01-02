package vnua.fita.jsoup;

import java.util.ArrayList;
import java.util.List;

public class Thu {
	private int thu;
	private List<LichHoc> dsLichHoc;

	public Thu(int thu) {
		this.thu = thu;
		this.dsLichHoc = new ArrayList<>();
	}

	public void themLichHoc(LichHoc lichHoc) {
		dsLichHoc.add(lichHoc);
	}

	public int getThu() {
		return thu;
	}

	public void setThu(int thu) {
		this.thu = thu;
	}

	public List<LichHoc> getDsLichHoc() {
		return dsLichHoc;
	}

	public void setDsLichHoc(List<LichHoc> dsLichHoc) {
		this.dsLichHoc = dsLichHoc;
	}
}