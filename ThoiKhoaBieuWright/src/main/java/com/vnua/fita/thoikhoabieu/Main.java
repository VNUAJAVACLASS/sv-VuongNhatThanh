package com.vnua.fita.thoikhoabieu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy");

    public static void main(String[] args) {
        // Bước 1: Lấy HTML thời khóa biểu tự động
        getHTML downloader = new getHTML();
        System.out.println("Loading...");
        downloader.getTKBHtml(); // Tải và lưu file HTML vào src/main/resources/VuongNhatThanh.html

        // Bước 2: Đọc dữ liệu lịch học từ file HTML vừa tải
        readHTML reader = new readHTML();
        String filePath = "src/main/resources/VuongNhatThanh.html";

        List<LichHoc> lichHocList = reader.docThoiKhoaBieu(filePath);

        if (lichHocList.isEmpty()) {
            System.out.println("Không có dữ liệu lịch học, vui lòng kiểm tra lại file HTML.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n========= MENU =========");
            System.out.println("1. Xem thời khóa biểu ngày hiện tại");
            System.out.println("2. Xem thời khóa biểu cả tuần (nhập tuần 1-22)");
            System.out.println("3. Xem thời khóa biểu theo tuần và thứ");
            System.out.println("4. Xem thời khóa biểu theo ngày (ngày/tháng/năm)");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    xemTheoNgayHienTai(lichHocList);
                    quayVeMenu(sc);
                    break;
                case "2":
                    xemTheoTuan(lichHocList, sc);
                    quayVeMenu(sc);
                    break;
                case "3":
                    xemTheoTuanVaThu(lichHocList, sc);
                    break;
                case "4":
                    xemTheoNgay(lichHocList, sc);
                    quayVeMenu(sc);
                    break;
                case "0":
                    exit = true;
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng thử lại.");
            }
        }
        sc.close();

        // Bước 3: Xóa file HTML sau khi dùng xong để tránh đọc nhầm TK khác
        int retries = 3;
        while (retries > 0) {
            try {
                java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(filePath));
                System.out.println("Đã xóa file tạm thời: " + filePath);
                break;
            } catch (Exception e) {
                retries--;
                if (retries == 0) {
                    System.err.println("Lỗi khi xóa file tạm: " + e.getMessage());
                }
                try {
                    Thread.sleep(1000); // Wait before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static void quayVeMenu(Scanner sc) {
        System.out.println("\nNhấn Enter để quay về menu...");
        sc.nextLine();
    }

    private static String getThuString(LocalDate date) {
        int thu = date.getDayOfWeek().getValue();
        return thu == 7 ? "8" : String.valueOf(thu + 1);
    }

    private static void xemTheoNgayHienTai(List<LichHoc> lichHocList) {
        LocalDate today = LocalDate.now();
        String thuStr = getThuString(today);

        System.out.println("\n=== Thời khóa biểu ngày " + today.format(DATE_FORMATTER) + " (Thứ " + (thuStr.equals("8") ? "CN" : thuStr) + ") ===");
        boolean coLich = false;
        for (LichHoc lh : lichHocList) {
            if (String.valueOf(lh.getThu()).equals(thuStr) && checkTuanTheoNgay(lh, today)) {
                lh.hienThiLichHoc();
                coLich = true;
            }
        }
        if (!coLich) {
            System.out.println("Không có lịch học trong ngày hôm nay.");
        }
    }

    private static void xemTheoTuan(List<LichHoc> lichHocList, Scanner sc) {
        System.out.print("Nhập số tuần (1-22): ");
        String input = sc.nextLine().trim();
        int tuan;
        try {
            tuan = Integer.parseInt(input);
            if (tuan < 1 || tuan > 22) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Tuần không hợp lệ! Vui lòng nhập số từ 1 đến 22.");
            return;
        }

        System.out.println("\n=== Thời khóa biểu tuần " + tuan + " ===");
        boolean coLich = false;
        for (LichHoc lh : lichHocList) {
            int tuanDau = lh.getTuanDauTien();
            int tuanCuoi = lh.getTuanCuoiCung();
            if (tuan >= tuanDau && tuan <= tuanCuoi) {
                lh.hienThiLichHoc();
                coLich = true;
            }
        }
        if (!coLich) {
            System.out.println("Không có lịch học trong tuần " + tuan);
        }
    }

    private static void xemTheoTuanVaThu(List<LichHoc> lichHocList, Scanner sc) {
        System.out.print("Nhập số tuần (1-22): ");
        String inputTuan = sc.nextLine().trim();
        int tuan;
        try {
            tuan = Integer.parseInt(inputTuan);
            if (tuan < 1 || tuan > 22) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Tuần không hợp lệ! Vui lòng nhập số từ 1 đến 22.");
            return;
        }

        System.out.print("Nhập thứ (2-7 hoặc CN): ");
        String thuInput = sc.nextLine().trim().toUpperCase();
        String thu;
        if (thuInput.equals("CN")) {
            thu = "8";
        } else if (thuInput.matches("[2-7]")) {
            thu = thuInput;
        } else {
            System.out.println("Thứ không hợp lệ! Vui lòng nhập từ 2 đến 7 hoặc CN.");
            return;
        }

        System.out.println("\n=== Thời khóa biểu tuần " + tuan + ", Thứ " + (thu.equals("8") ? "CN" : thu) + " ===");
        boolean coLich = false;
        for (LichHoc lh : lichHocList) {
            int tuanDau = lh.getTuanDauTien();
            int tuanCuoi = lh.getTuanCuoiCung();
            if (tuan >= tuanDau && tuan <= tuanCuoi && String.valueOf(lh.getThu()).equals(thu)) {
                lh.hienThiLichHoc();
                coLich = true;
            }
        }
        if (!coLich) {
            System.out.println("Không có lịch học trong tuần " + tuan + ", Thứ " + (thu.equals("8") ? "CN" : thu));
        }
    }

    private static void xemTheoNgay(List<LichHoc> lichHocList, Scanner sc) {
        System.out.print("Nhập ngày (dd/MM/yyyy): ");
        String ngayStr = sc.nextLine().trim();
        LocalDate ngay;
        try {
            ngay = LocalDate.parse(ngayStr, DATE_FORMATTER);
        } catch (Exception e) {
            System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập theo dd/MM/yyyy.");
            return;
        }

        String thuStr = getThuString(ngay);
        System.out.println("\n=== Thời khóa biểu ngày " + ngay.format(DATE_FORMATTER) + " (Thứ " + (thuStr.equals("8") ? "CN" : thuStr) + ") ===");
        boolean coLich = false;
        for (LichHoc lh : lichHocList) {
            if (String.valueOf(lh.getThu()).equals(thuStr) && checkTuanTheoNgay(lh, ngay)) {
                lh.hienThiLichHoc();
                coLich = true;
            }
        }
        if (!coLich) {
            System.out.println("Không có lịch học trong ngày này.");
        }
    }

    private static boolean checkTuanTheoNgay(LichHoc lh, LocalDate date) {
        int tuanDau = lh.getTuanDauTien();
        int tuanCuoi = lh.getTuanCuoiCung();

        if (tuanDau == -1 || tuanCuoi == -1) return false;

        LocalDate ngayBatDau = LocalDate.of(2025, 1, 13);
        long weeksBetween = java.time.temporal.ChronoUnit.WEEKS.between(ngayBatDau, date) + 1;

        return weeksBetween >= tuanDau && weeksBetween <= tuanCuoi;
    }
}