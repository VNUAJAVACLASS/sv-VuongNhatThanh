package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookDAO;
import model.Book;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;

	@Override
	public void init() {
		bookDAO = new BookDAO();
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
		case "create":
		case "edit":
		case "delete":
			// ==== bắt buộc login cho edit/delete ====
			HttpSession session = req.getSession(false);
			String user = (session == null) ? null : (String) session.getAttribute("username");
			if (user == null) {
				resp.sendRedirect("login");
				return;
			}
			// ========================================

			if ("edit".equals(action)) {
				int idEdit = Integer.parseInt(idStr);
				Book editBook = bookDAO.getBookById(idEdit);
				req.setAttribute("book", editBook);
				req.getRequestDispatcher("form.jsp").forward(req, resp);
			} else {
				int idDelete = Integer.parseInt(idStr);
				bookDAO.deleteBook(idDelete);
				resp.sendRedirect("book");
			}
			break;

		case "detail":
			int idDetail = Integer.parseInt(idStr);
			Book detailBook = bookDAO.getBookById(idDetail);
			req.setAttribute("book", detailBook);
			req.getRequestDispatcher("detail.jsp").forward(req, resp);
			break;

		default: // list
			List<Book> bookList = bookDAO.getAllBooks();
			req.setAttribute("bookList", bookList);
			req.getRequestDispatcher("list.jsp").forward(req, resp);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		// ==== bắt buộc login cho thêm/sửa ====
		HttpSession session = req.getSession(false);
		String user = (session == null) ? null : (String) session.getAttribute("username");
		if (user == null) {
			resp.sendRedirect("login");
			return;
		}
		// =====================================

		String idStr = req.getParameter("id");
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String priceStr = req.getParameter("price");
		String imagePath = req.getParameter("imagePath");

		double price = 0.0;
		if (priceStr != null && !priceStr.isEmpty()) {
			try {
				price = Double.parseDouble(priceStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		if (idStr == null || idStr.isEmpty()) {
			// Thêm mới
			Book newBook = new Book(0, title, author, price, imagePath);
			bookDAO.addBook(newBook);
		} else {
			// Cập nhật
			int id = Integer.parseInt(idStr);
			Book updateBook = new Book(id, title, author, price, imagePath);
			bookDAO.updateBook(updateBook);
		}

		resp.sendRedirect("book");
	}

	@Override
	public void destroy() {
		bookDAO.close();
		super.destroy();
	}
}
