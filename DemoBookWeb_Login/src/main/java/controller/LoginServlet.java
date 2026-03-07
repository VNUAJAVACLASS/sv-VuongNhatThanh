package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	// Giả định tài khoản đăng nhập
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "123456";

	// doGet được gọi khi LoginServlet được gọi trực tiếp từ một servlet khác (vd
	// adminHome) hoặc từ URL
	// vd: http://localhost:8080/DemoNewsWeb_Login/login
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Kiểm tra cookie “remember me” có tồn tại từ lần đăng nhập trước không
		// Cookie được trình duyệt gửi tới WebServer kèm theo mỗi request
		Cookie[] cookies = req.getCookies();
		String remembereduser = null;

		if (cookies != null) {
			for (Cookie c : cookies) {
				if ("remembereduser".equals(c.getName())) {
					remembereduser = c.getValue(); // Nếu có lấy giá trị ra
					break;
				}
			}
		}

		// Ghi giá trị cookie ra request scope để sử dụng trong trang login.jsp điền vào
		// ô username
		req.setAttribute("remembereduser", remembereduser);
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	// doPost được gọi đến khi submit form đăng nhập trên trang login.jsp với phương
	// thức POST
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember"); // “on” nếu có check

		if (USERNAME.equals(username) && PASSWORD.equals(password)) {
			// Đăng nhập thành công
			HttpSession session = req.getSession();
			session.setAttribute("username", username);

			if ("on".equals(remember)) { // có check nhớ tài khoản
				Cookie cookie = new Cookie("remembereduser", username);
				cookie.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
				resp.addCookie(cookie);
			} else {
				Cookie cookie = new Cookie("remembereduser", "");
				cookie.setMaxAge(0); // xóa cookie
				resp.addCookie(cookie);
			}

			resp.sendRedirect("/adminHome");
		} else {
			req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}
}
