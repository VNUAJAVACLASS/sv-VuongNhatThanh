package vnua.fita.jsoup;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final LocalDate NGAY_BAT_DAU = LocalDate.of(2025, 1, 13);
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final int SO_TUAN_TOI_DA = 22;

	public static void main(String[] args) {
		try {
			CtrinhChinh ctrinhChinh = new CtrinhChinh();
			DocHTML docHTML = new DocHTML(ctrinhChinh);

			// Đường dẫn tới file HTML
			String tenFile = "src/main/resources/VuongNhatThanh.html";

			// Kiểm tra file tồn tại và quyền truy cập
			File file = new File(tenFile);
			if (!file.exists()) {
				System.err.println("File không tồn tại: " + file.getAbsolutePath());
				return;
			}
			if (!file.canRead()) {
				System.err.println("Không có quyền đọc file: " + file.getAbsolutePath());
				return;
			}

			// Đọc file HTML
			System.out.println("Debug: Đang đọc file HTML...");
			docHTML.docFileHTML(tenFile);
			System.out.println("Debug: Đọc file HTML thành công.");

			// Kiểm tra xem dữ liệu có được đọc thành công không
			if (ctrinhChinh.getDsTuanHoc().isEmpty()) {
				System.out.println("Không có dữ liệu lịch học. Vui lòng kiểm tra file HTML.");
				return;
			}

			// In danh sách tuần để debug
			System.out.println("Debug: Các tuần có lịch học: " + ctrinhChinh.getDsTuanHoc().keySet());

			// Giao diện người dùng
			Scanner scanner = new Scanner(System.in);
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
					xemThoiKhoaBieuNgayHienTai(ctrinhChinh);
					break;
				case 2:
					xemThoiKhoaBieuCaTuan(ctrinhChinh, scanner);
					break;
				case 3:
					xemThoiKhoaBieuTheoTuanThu(ctrinhChinh, scanner);
					break;
				case 4:
					xemThoiKhoaBieuTheoNgay(ctrinhChinh, scanner);
					break;
				default:
					System.out.println("Tính năng không hợp lệ! Vui lòng chọn từ 1 đến 5.");
				}

				// Tạm dừng để người dùng đọc kết quả
				System.out.print("Nhấn Enter để tiếp tục...");
				scanner.nextLine();
			}
			scanner.close();
		} catch (IOException e) {
			System.err.println("Lỗi khi đọc file HTML: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Lỗi không xác định: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Tính năng 1: Xem thời khóa biểu ngày hiện tại
	private static void xemThoiKhoaBieuNgayHienTai(CtrinhChinh ctrinhChinh) {
		try {
			LocalDate ngayHienTai = LocalDate.now();
			int tuan = tinhTuanTuNgay(ngayHienTai);
			System.out.println("Tuần: " + tuan);

			if (tuan < 1 || tuan > SO_TUAN_TOI_DA) {
				System.out.println("Không có lịch học trong tuần này.");
				return;
			}

			int thu = ngayHienTai.getDayOfWeek().getValue(); // 1=Thứ Hai, 7=Chủ Nhật
			if (thu == 7)
				thu = 8; // Chủ Nhật là 8
			String tenThu;
			switch (thu) {
			case 1:
				tenThu = "Thứ Hai";
				break;
			case 2:
				tenThu = "Thứ Ba";
				break;
			case 3:
				tenThu = "Thứ Tư";
				break;
			case 4:
				tenThu = "Thứ Năm";
				break;
			case 5:
				tenThu = "Thứ Sáu";
				break;
			case 6:
				tenThu = "Thứ Bảy";
				break;
			case 8:
				tenThu = "Chủ Nhật";
				break;
			default:
				tenThu = "Không xác định";
			}
			System.out.println("Thứ: " + tenThu);
			int thuHTML = thu == 8 ? 8 : thu + 1; // Điều chỉnh để khớp với quy ước HTML
			hienThiLichHocTheoTuanThu(ctrinhChinh, tuan, thuHTML);
		} catch (Exception e) {
			System.err.println("Lỗi trong xemThoiKhoaBieuNgayHienTai: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Tính năng 2: Xem thời khóa biểu cả tuần
	private static void xemThoiKhoaBieuCaTuan(CtrinhChinh ctrinhChinh, Scanner scanner) {
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
		List<LichHoc> lichHocList = ctrinhChinh.getDsTuanHoc().get(tuan);
		if (lichHocList == null || lichHocList.isEmpty()) {
			System.out.println("Không có lịch học trong tuần này.");
			return;
		}

		for (LichHoc lh : lichHocList) {
			hienThiLichHoc(lh);
		}
	}

	// Tính năng 3: Xem thời khóa biểu theo tuần, thứ
	private static void xemThoiKhoaBieuTheoTuanThu(CtrinhChinh ctrinhChinh, Scanner scanner) {
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
		hienThiLichHocTheoTuanThu(ctrinhChinh, tuan, thu);
	}

	// Tính năng 4: Xem thời khóa biểu theo ngày
	private static void xemThoiKhoaBieuTheoNgay(CtrinhChinh ctrinhChinh, Scanner scanner) {
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
		hienThiLichHocTheoTuanThu(ctrinhChinh, tuan, thuHTML);
	}

	// Tính tuần từ ngày
	private static int tinhTuanTuNgay(LocalDate ngay) {
		long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(NGAY_BAT_DAU, ngay);
		return (int) (daysBetween / 7) + 1;
	}

	// Hiển thị lịch học theo tuần và thứ
	private static void hienThiLichHocTheoTuanThu(CtrinhChinh ctrinhChinh, int tuan, int thu) {
		try {
			List<LichHoc> lichHocList = ctrinhChinh.getDsTuanHoc().get(tuan);
			if (lichHocList == null || lichHocList.isEmpty()) {
				System.out.println("Không có lịch học trong tuần " + tuan + ".");
				return;
			}

			boolean coLichHoc = false;
			for (LichHoc lh : lichHocList) {
				if (lh.getThu() == thu && tuan - 1 < lh.getThoiGianHoc().length()
						&& lh.getThoiGianHoc().charAt(tuan - 1) != '-') {
					hienThiLichHoc(lh);
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

	// Hiển thị thông tin lịch học
	private static void hienThiLichHoc(LichHoc lh) {
		System.out.printf("- %s (Thứ %s, Tiết %d-%d, Phòng: %s, Giảng viên: %s, %s)\n", lh.getTenMonHoc(),
				lh.getThu() == 8 ? "Chủ Nhật" : lh.getThu(), lh.getTietBatDau(),
				lh.getTietBatDau() + lh.getSoTiet() - 1, lh.getPhong(), lh.getGiangVien(), lh.getThoiGianHocHienThi());
	}
}