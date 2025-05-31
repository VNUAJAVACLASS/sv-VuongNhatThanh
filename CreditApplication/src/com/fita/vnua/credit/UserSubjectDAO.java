package com.fita.vnua.credit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSubjectDAO {
    private Connection connection;

    // Khởi tạo DAO với một Connection đã có sẵn
    public UserSubjectDAO() {
        try {
            String dbURL = "jdbc:ucanaccess://lib/CreditDB.accdb";
            connection = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy tất cả user subjects
    public List<UserSubject> getAllUserSubjects() {
        List<UserSubject> list = new ArrayList<>();
        String query = "SELECT * FROM tbl_usersubject";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UserSubject us = mapResultSetToUserSubject(rs);
                list.add(us);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm user subject
    public boolean insertUserSubject(UserSubject us) {
        String query = "INSERT INTO tbl_usersubject (user_code, subject_code, attendanceexammark, midexammark1, midexammark2, midexammark3, finalexammark) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, us.getUserCode());
            stmt.setString(2, us.getSubjectCode());
            stmt.setFloat(3, us.getAttendanceExamMark());
            stmt.setFloat(4, us.getMidExamMark1());
            stmt.setFloat(5, us.getMidExamMark2());
            stmt.setFloat(6, us.getMidExamMark3());
            stmt.setFloat(7, us.getFinalExamMark());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Cập nhật user subject
    public boolean updateUserSubject(UserSubject us) {
        String query = "UPDATE tbl_usersubject SET user_code = ?, subject_code = ?, attendanceexammark = ?, midexammark1 = ?, midexammark2 = ?, midexammark3 = ?, finalexammark = ? " +
                       "WHERE user_subject_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, us.getUserCode());
            stmt.setString(2, us.getSubjectCode());
            stmt.setFloat(3, us.getAttendanceExamMark());
            stmt.setFloat(4, us.getMidExamMark1());
            stmt.setFloat(5, us.getMidExamMark2());
            stmt.setFloat(6, us.getMidExamMark3());
            stmt.setFloat(7, us.getFinalExamMark());
            stmt.setInt(8, us.getUserSubjectId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Xóa user subject theo ID
    public boolean deleteUserSubject(int userSubjectId) {
        String query = "DELETE FROM tbl_usersubject WHERE user_subject_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userSubjectId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private UserSubject mapResultSetToUserSubject(ResultSet rs) throws SQLException {
        int userSubjectId = rs.getInt("user_subject_id");
        String userCode = rs.getString("user_code");
        String subjectCode = rs.getString("subject_code");
        float attendanceExamMark = rs.getFloat("attendanceexammark");
        float midExamMark1 = rs.getFloat("midexammark1");
        float midExamMark2 = rs.getFloat("midexammark2");
        float midExamMark3 = rs.getFloat("midexammark3");
        float finalExamMark = rs.getFloat("finalexammark");

        return new UserSubject(userSubjectId, userCode, subjectCode, attendanceExamMark, midExamMark1, midExamMark2, midExamMark3, finalExamMark);
    }
}
