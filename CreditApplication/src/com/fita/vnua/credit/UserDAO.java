package com.fita.vnua.credit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        try {
            String dbURL = "jdbc:ucanaccess://lib/CreditDB.accdb";
            connection = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM tbl_users";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String userCode = rs.getString("user_code");
                String fullname = rs.getString("fullname");
                String address = rs.getString("address");
                String class_ = rs.getString("class");
                String password = rs.getString("password");
                int userType = rs.getInt("user_type");

                User user = new User(userCode, fullname, address, class_, password, userType);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    // Lấy người dùng theo mã
    public User getUserByCode(String userCode) {
        User user = null;
        String query = "SELECT * FROM tbl_users WHERE user_code = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String fullname = rs.getString("fullname");
                String address = rs.getString("address");
                String class_ = rs.getString("class");
                String password = rs.getString("password");
                int userType = rs.getInt("user_type");

                user = new User(userCode, fullname, address, class_, password, userType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // Thêm người dùng
    public boolean addUser(User user) {
        String query = "INSERT INTO tbl_users (user_code, fullname, address, class, password, user_type) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUserCode());
            stmt.setString(2, user.getFullname());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getClass_());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getUserType());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
