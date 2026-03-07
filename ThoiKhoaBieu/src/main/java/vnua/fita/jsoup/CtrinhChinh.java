package vnua.fita.jsoup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CtrinhChinh {
	private static final LocalDate NGAY_BAT_DAU = LocalDate.of(2025, 1, 13);
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final int SO_TUAN_TOI_DA = 22;
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

	// Hiển thị menu và xử lý lựa chọn
	public void hienThiMenuVaXuLyLuaChon(Scanner scanner) {
		while (true) {
			System.out.println("\n=== MENU THỜI KHÓA BIỂU ===");
			System.out.println("1. Xem thời khóa biểu ngày hiện tại");
			System.out.println("2. Xem thời khóa biểu cả tuần");
			System.out.println("3. Xem thời khóa biểu theo tuần, thứ");
			System.out.println("4. Xem thời khóa biểu theo ngày");
			System.out.println("5. Thoát");
			System.out.print("Chọn tính năng (1-5): ");

			int luaChon;
			try {
				luaChon = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Vui lòng nhập số từ 1 đến 5!");
				continue;
			}

			if (luaChon == 5) {
				System.out.println("Tạm biệt!");
				break;
			}

			switch (luaChon) {
			case 1:
				xemThoiKhoaBieuNgayHienTai();
				break;
			case 2:
				xemThoiKhoaBieuCaTuan(scanner);
				break;
			case 3:
				xemThoiKhoaBieuTheoTuanThu(scanner);
				break;
			case 4:
				xemThoiKhoaBieuTheoNgay(scanner);
				break;
			default:
				System.out.println("Tính năng không hợp lệ! Vui lòng chọn từ 1 đến 5.");
			}

			System.out.print("Nhấn Enter để tiếp tục...");
			scanner.nextLine();
		}
	}

	// Tính năng 1: Xem thời khóa biểu ngày hiện tại
	private void xemThoiKhoaBieuNgayHienTai() {
		try{LocalDate ngayHienTai=LocalDate.now();int tuan=tinhTuanTuNgay(ngayHienTai);System.out.println("Tuần: "+tuan);

		if(tuan<1||tuan>SO_TUAN_TOI_DA){System.out.println("Không có lịch học trong tuần này.");return;}

		int thu=ngayHienTai.getDayOfWeek().getValue(); // 1=Thứ Hai, 7=Chủ Nhật
		if(thu==7) thu=8; // Chủ Nhật là 8
		Map<Integer, String> tenThuMap = Map.of(
			    1, "Thứ Hai",
			    2, "Thứ Ba",
			    3, "Thứ Tư",
			    4, "Thứ Năm",
			    5, "Thứ Sáu",
			    6, "Thứ Bảy",
			    8, "Chủ Nhật"
			);

			String tenThu = tenThuMap.getOrDefault(thu, "Không xác định");


		System.out.println("Thứ: "+tenThu);int thuHTML=thu==8?8:thu+1; 
		hienThiLichHocTheoTuanThu(tuan,thuHTML);}catch(Exception e){System.err.println("Lỗi trong xemThoiKhoaBieuNgayHienTai: "+e.getMessage());e.printStackTrace();}
	}

	// Tính năng 2: Xem thời khóa biểu cả tuần
	private void xemThoiKhoaBieuCaTuan(Scanner scanner) {
		System.out.print("Nhập số tuần (1-22): ");
		int tuan;
		try {
			tuan = Integer.parseInt(scanner.nextLine());
			if (tuan < 1 || tuan > SO_TUAN_TOI_DA) {
				System.out.println("Tuần phải từ 1 đến " + SO_TUAN_TOI_DA + "!");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("Vui lòng nhập số hợp lệ!");
			return;
		}

		LocalDate ngayDauTuan = NGAY_BAT_DAU.plusWeeks(tuan - 1);
		System.out.println("Thời khóa biểu tuần " + tuan + " (từ " + ngayDauTuan.format(DATE_FORMATTER) + "):");
		List<LichHoc> lichHocList = dsTuanHoc.get(tuan);
		if (lichHocList == null || lichHocList.isEmpty()) {
			System.out.println("Không có lịch học trong tuần này.");
			return;
		}

		for (LichHoc lh : lichHocList) {
			lh.hienThiLichHoc();
		}
	}

	// Tính năng 3: Xem thời khóa biểu theo tuần, thứ
	private void xemThoiKhoaBieuTheoTuanThu(Scanner scanner) {
		System.out.print("Nhập số tuần (1-22): ");
		int tuan;
		try {
			tuan = Integer.parseInt(scanner.nextLine());
			if (tuan < 1 || tuan > SO_TUAN_TOI_DA) {
				System.out.println("Tuần phải từ 1 đến " + SO_TUAN_TOI_DA + "!");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("Vui lòng nhập số hợp lệ!");
			return;
		}

		System.out.print("Nhập thứ (2-8, 2=Thứ Hai, 8=Chủ Nhật): ");
		int thu;
		try {
			thu = Integer.parseInt(scanner.nextLine());
			if (thu < 2 || thu > 8) {
				System.out.println("Thứ phải từ 2 đến 8!");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("Vui lòng nhập số hợp lệ!");
			return;
		}

		LocalDate ngayDauTuan = NGAY_BAT_DAU.plusWeeks(tuan - 1);
		LocalDate ngayXem = ngayDauTuan.plusDays(thu == 8 ? 6 : thu - 2);
		System.out.println("Thời khóa biểu tuần " + tuan + ", thứ " + (thu == 8 ? "Chủ Nhật" : thu) + " ("
				+ ngayXem.format(DATE_FORMATTER) + "):");
		hienThiLichHocTheoTuanThu(tuan, thu);
	}

	// Tính năng 4: Xem thời khóa biểu theo ngày
	private void xemThoiKhoaBieuTheoNgay(Scanner scanner) {
		System.out.print("Nhập ngày (DD/MM/YYYY): ");
		String ngayStr = scanner.nextLine();
		LocalDate ngay;
		try {
			ngay = LocalDate.parse(ngayStr, DATE_FORMATTER);
		} catch (DateTimeParseException e) {
			System.out.println("Ngày không hợp lệ! Vui lòng nhập theo định dạng DD/MM/YYYY.");
			return;
		}

		int tuan = tinhTuanTuNgay(ngay);
		if (tuan < 1 || tuan > SO_TUAN_TOI_DA) {
			System.out.println(
					"Ngày " + ngay.format(DATE_FORMATTER) + " không thuộc khoảng thời gian có lịch học (13/01/2025 - "
							+ NGAY_BAT_DAU.plusWeeks(SO_TUAN_TOI_DA - 1).format(DATE_FORMATTER) + ").");
			return;
		}

		int thu = ngay.getDayOfWeek().getValue();
		if (thu == 7)
			thu = 8; // Chủ Nhật là 8
		int thuHTML = thu == 8 ? 8 : thu + 1; // Điều chỉnh để khớp với quy ước HTML
		System.out.println("Thời khóa biểu ngày " + ngay.format(DATE_FORMATTER) + " (Thứ "
				+ (thu == 8 ? "Chủ Nhật" : thu) + ", Tuần " + tuan + "):");
		hienThiLichHocTheoTuanThu(tuan, thuHTML);
	}

	// Tính tuần từ ngày
	private int tinhTuanTuNgay(LocalDate ngay) {
		long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(NGAY_BAT_DAU, ngay);
		return (int) (daysBetween / 7) + 1;
	}

	// Hiển thị lịch học theo tuần và thứ
	private void hienThiLichHocTheoTuanThu(int tuan, int thu) {
		try {
			List<LichHoc> lichHocList = dsTuanHoc.get(tuan);
			if (lichHocList == null || lichHocList.isEmpty()) {
				System.out.println("Không có lịch học trong tuần " + tuan + ".");
				return;
			}

			boolean coLichHoc = false;
			for (LichHoc lh : lichHocList) {
				if (lh.getThu() == thu && tuan - 1 < lh.getThoiGianHoc().length()
						&& lh.getThoiGianHoc().charAt(tuan - 1) != '-') {
					lh.hienThiLichHoc();
					coLichHoc = true;
				}
			}

			if (!coLichHoc) {
				System.out.println("Không có lịch học trong ngày này.");
			}
		} catch (Exception e) {
			System.err.println("Lỗi trong hienThiLichHocTheoTuanThu: " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	public Map<Integer, List<LichHoc>> getDsTuanHoc() {
		return dsTuanHoc;
	}

	public void setDsTuanHoc(Map<Integer, List<LichHoc>> dsTuanHoc) {
		this.dsTuanHoc = dsTuanHoc;
	}
}