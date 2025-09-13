package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Book;

public class BookDAO {
	private Connection connection;

	// Kết nối DB
	public BookDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/books?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
					"thanh", "123456");
			System.out.println("Kết nối MySQL thành công!");
		} catch (ClassNotFoundException e) {
			System.out.println("Không tìm thấy driver MySQL!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Kết nối MySQL thất bại!");
			e.printStackTrace();
		}
	}

	// Thêm sách
	public boolean addBook(Book book) {
		String sql = "INSERT INTO books (title, author, price, imagePath) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.setDouble(3, book.getPrice());
			stmt.setString(4, book.getImagePath());
			int rows = stmt.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Lấy tất cả sách
	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		String sql = "SELECT * FROM books";

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Book book = new Book(rs.getInt("bookId"), rs.getString("title"), rs.getString("author"),
						rs.getDouble("price"), rs.getString("imagePath"));
				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	// Sửa sách
	public boolean updateBook(Book book) {
		String sql = "UPDATE books SET title = ?, author = ?, price = ?, imagePath = ? WHERE bookId = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.setDouble(3, book.getPrice());
			stmt.setString(4, book.getImagePath());
			stmt.setInt(5, book.getBookId());
			int rows = stmt.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Xóa sách
	public boolean deleteBook(int bookId) {
		String sql = "DELETE FROM books WHERE bookId = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, bookId);
			int rows = stmt.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Lấy sách theo ID
	public Book getBookById(int bookId) {
		String sql = "SELECT * FROM books WHERE bookId = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, bookId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Book(rs.getInt("bookId"), rs.getString("title"), rs.getString("author"),
							rs.getDouble("price"), rs.getString("imagePath"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // Không tìm thấy
	}

	// Đóng kết nối
	public void close() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
