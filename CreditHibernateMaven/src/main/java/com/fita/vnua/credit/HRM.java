package com.fita.vnua.credit;

import java.util.List;

public class HRM {

    // Thêm người dùng vào cơ sở dữ liệu
    public void addHR(User user) {
        if (UserDAO.addUser(user)) {
            System.out.println("Thêm thành công người dùng");
        } else {
            System.out.println("Thêm người dùng thất bại");
        }
    }

    // Hiển thị danh sách người dùng
    public void printHRList() {
        List<User> users = UserDAO.getAllUsers();
        if (users != null && !users.isEmpty()) {
            users.forEach(user -> System.out.println(user));
        } else {
            System.out.println("Không có người dùng nào");
        }
    }

    // Tìm kiếm người dùng theo mã
    public void searchHuman(String userID) {
        User user = UserDAO.getUserByCode(userID);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("Không tìm thấy người dùng với mã " + userID);
        }
    }

    // Thêm môn học cho người dùng
    public void addUserSubject(UserSubject userSubject) {
        if (UserSubjectDAO.insertUserSubject(userSubject)) {
            System.out.println("Thêm môn học thành công");
        } else {
            System.out.println("Thêm môn học thất bại");
        }
    }

    // Hiển thị danh sách môn học
    public void printSubjectList() {
        List<Subject> subjects = SubjectDAO.getAllSubjects();
        if (subjects != null && !subjects.isEmpty()) {
        	System.out.println("Danh sach môn học ");
            subjects.forEach(subject -> System.out.println(subject));
        } else {
            System.out.println("Không có môn học nào của người dùng");
        }
    }

    // Hiển thị danh sách môn học của user 
    public void printUserSubjectList(String userID) {
        List<UserSubject> userSubjects = UserSubjectDAO.getAllUserSubjects();

        if (userSubjects != null && !userSubjects.isEmpty()) {
            // Lọc userSubjects theo userID
           System.out.println("Danh sách môn học của người dùng"+ " " + userID + ":");
        	userSubjects.stream()
                        .filter(us -> us.getUserCode().equals(userID))
                        .forEach(us -> System.out.println(us));
        } else {
            System.out.println("Không tìm thấy môn học cho người dùng " + userID);
        }
    }

    public static void main(String[] args) {
        HRM hrm = new HRM();

        // Tạo user mới và thêm
        User user = new User("888888", "Vuong Nhat Thanh", "Ha Noi", "K68CNTTC", "123456", 1);
        hrm.addHR(user);

        // Hiển thị danh sách người dùng
        hrm.printHRList();

        // Tìm người dùng theo mã
        hrm.searchHuman("888888");

        // Thêm môn học cho user
        UserSubject userSubject = new UserSubject(2, "888888", "CS102", 8.5f, 7.5f, 8.0f, 7.0f, 8.0f);
        hrm.addUserSubject(userSubject);

        // Hiển thị danh sách môn học
        hrm.printSubjectList();

        // Hiển thị môn học của user
        hrm.printUserSubjectList("888888");
    }
}
