package com.fita.vnua.credit;

import java.util.List;
import java.util.Scanner;

public class HRM {
    private UserDAO userDAO;
    private UserSubjectDAO userSubjectDAO;
    private SubjectDAO subjectDAO;

    // Hàm khởi tạo
    public HRM() {
        userDAO = new UserDAO();
        userSubjectDAO = new UserSubjectDAO();
        subjectDAO = new SubjectDAO();
    }

    // Thêm người dùng vào cơ sở dữ liệu
    public void addHR(User user) {
        if (userDAO.addUser(user)) {
            System.out.println("Thêm thành công người dùng");
        } else {
            System.out.println("Thêm người dùng thất bại");
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
            System.out.println("Không tìm thấy người dùng");
        }
    }

    // Thêm môn học cho người dùng
    public void addUserSubject(UserSubject userSubject) {
        if (userSubjectDAO.insertUserSubject(userSubject)) {
            System.out.println("Thêm môn học thành công");
        } else {
            System.out.println("Thêm môn học thất bại");
        }
    }

    // Hiển thị danh sách môn học
    public void printSubjectList() {
        List<Subject> subjects = subjectDAO.getAllSubjects();
        for (Subject subject : subjects) {
            System.out.println(subject.toString());
        }
    }
    
    public void printUserSubjectList(String userID) {
        List<UserSubject> userSubjects = userSubjectDAO.getAllUserSubjects();
        
        if (userSubjects != null && !userSubjects.isEmpty()) {
            for (UserSubject userSubject : userSubjects) {
                System.out.println(userSubject.toString());
            }
        } else {
            System.out.println("Không tìm thấy môn học cho người dùng " + userID);
        }
    }
    
    
    // Main để chạy chương trình
    public static void main(String[] args) {
        HRM hrm = new HRM();
        Scanner sc = new Scanner(System.in);

        // Nhập và thêm người dùng từ người dùng
        System.out.println("Nhập thông tin người dùng: ");
        User user = new User("888888", "Vuong Nhat Thanh", "Ha Noi", "K68CNTTC", "123456", 1);
        hrm.addHR(user);

        // Hiển thị danh sách người dùng
        hrm.printHRList();

        // Tìm kiếm người dùng theo mã
        hrm.searchHuman("999999");

        // Thêm môn học cho người dùng
        UserSubject userSubject = new UserSubject(1, "988888", "CS102", 8.5f, 7.5f, 8.0f, 7.0f, 8.0f);
        hrm.addUserSubject(userSubject);

        // Hiển thị danh sách môn học
        hrm.printSubjectList();
        hrm.printUserSubjectList("680373");
    }
}
