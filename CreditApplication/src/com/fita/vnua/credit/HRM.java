package com.fita.vnua.credit;

import java.util.List;
import java.util.Scanner;

public class HRM {
	private UserDAO userDAO;

	// Hàm khởi tạo
	public HRM() {
		userDAO = new UserDAO();
	}

	// Thêm người dùng vào cơ sở dữ liệu
	public void addHR(User user) {
		if (userDAO.addUser(user)) {
			System.out.println("Them thanh cong user");
		} else {
			System.out.println("Them that bai");
		}
	}

	// Hiển thị danh sách người dùng
	public void printHRList() {
		List<User> users = userDAO.getAllUsers();
		for (User user : users) {
			System.out.println(user.toString());
		}
	}

	// Tìm kiếm người dùng theo mã
	public void searchHuman(String userID) {
		User user = userDAO.getUserByCode(userID);
		if (user != null) {
			System.out.println(user.toString());
		} else {
			System.out.println("Khong tim thay user");
		}
	}

	// Main để chạy chương trình
	public static void main(String[] args) {
		HRM hrm = new HRM();
		Scanner sc = new Scanner(System.in);

		// Nhập và thêm người dùng từ người dùng
		System.out.println("Nhap thong tin nguoi dung: ");
		User user = new User("999999", "Vuong Nhat Thanh", "Ha Noi", "K68CNTTC", "123456", 1);
		hrm.addHR(user);

		// Hiển thị danh sách người dùng
		hrm.printHRList();

		// Tìm kiếm người dùng theo mã
		hrm.searchHuman("680377");
	}
}
