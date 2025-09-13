package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookDAO;
import model.Book;

@WebServlet("/ClientHome")
public class ClientHomeServlet extends HttpServlet {
	private static List<Book> bookList = new ArrayList<>();
	private static int idCounter = 1;

	@Override
	public void init() throws ServletException {
		try {
			BookDAO bookDAO = new BookDAO();
			// Lấy danh sách book từ DB
			bookList = bookDAO.getAllBooks();
		} catch (Exception e) {
			throw new ServletException("Không thể khởi tạo dữ liệu sách từ DB", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		String idStr = req.getParameter("id");

		if (action == null)
			action = "list";

		switch (action) {
		case "detail": // trường hợp click "Xem chi tiết"
			int idDetail = Integer.parseInt(idStr);
			Book detailNews = findById(idDetail);
			req.setAttribute("news", detailNews);
			req.getRequestDispatcher("detail_client.jsp").forward(req, resp);
			break;

		default: // trường hợp mặc định tại trang chủ phía máy khách
			req.setAttribute("b", bookList);
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			break;
		}
	}

	private Book findById(int id) {
		for (Book n : bookList) {
			if (n.getBookId() == id)
				return n;
		}
		return null;
	}
}
