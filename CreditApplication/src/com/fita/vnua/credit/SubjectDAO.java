package com.fita.vnua.credit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private Connection connection;

    // Khởi tạo DAO với một Connection đã có sẵn
    public SubjectDAO() {
        try {
            String dbURL = "jdbc:ucanaccess://lib/CreditDB.accdb";
            connection = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy tất cả môn học
    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        String query = "SELECT * FROM tbl_subject";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Subject subject = mapResultSetToSubject(rs);
                list.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm môn học
    public boolean insertSubject(Subject subject) {
        String query = "INSERT INTO tbl_subject (subject_code, subject_name, credit) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subject.getSubjectCode());
            stmt.setString(2, subject.getSubjectName());
            stmt.setInt(3, subject.getCredit());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Cập nhật môn học
    public boolean updateSubject(Subject subject) {
        String query = "UPDATE tbl_subject SET subject_name = ?, credit = ? WHERE subject_code = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subject.getSubjectName());
            stmt.setInt(2, subject.getCredit());
            stmt.setString(3, subject.getSubjectCode());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Phương thức hỗ trợ: chuyển ResultSet thành đối tượng Subject
    private Subject mapResultSetToSubject(ResultSet rs) throws SQLException {
        String subjectCode = rs.getString("subject_code");
        String subjectName = rs.getString("subject_name");
        int credit = rs.getInt("credit");

        return new Subject(subjectCode, subjectName, credit);
    }
}
