package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.News;

public class NewsDAO {
	private Connection connection;

	// ket noi dtb
	public NewsDAO() {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        connection = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/news?useSSL=false&serverTimezone=UTC",
	            "thanh", "123456"
	        );

	        System.out.println("Kết nối MySQL thành công!");

	    } catch (ClassNotFoundException e) {
	        System.out.println("Không tìm thấy driver MySQL!");
	        e.printStackTrace();
	    } catch (SQLException e) {
	        System.out.println("Kết nối MySQL thất bại!");
	        e.printStackTrace();
	    }

	    if (connection == null) {
	        System.out.println("Warning: connection vẫn null sau khi khởi tạo!");
	    }
	}

	//them
	public boolean addNews(String title, String content) {
		String sql = "INSERT INTO news(title, content) VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, title);
			stmt.setString(2, content);
			int rows = stmt.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//hien thi
	public List<News> getAllNews() {
		List<News> newsList = new ArrayList<>();
		String sql = "SELECT * FROM news";

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				News news = new News(rs.getInt("id"), rs.getString("title"), rs.getString("content"));
				newsList.add(news);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return newsList;
	}

	//sua
	public boolean updateNews(int id, String title, String content) {
		String sql = "UPDATE news SET title = ?, content = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, title);
			stmt.setString(2, content);
			stmt.setInt(3, id);
			int rows = stmt.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	//xoa
	public boolean deleteNews(int id) {
		String sql = "DELETE FROM news WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			int rows = stmt.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public News getById(int id) {
		String sql = "SELECT * FROM news WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id); 
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					// tao doi tuong new
					News news = new News(rs.getInt("id"), rs.getString("title"), rs.getString("content"));
					return news;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; //tra ve null neu ko tim thay
	}

	//dong ket noi
	public void close() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}