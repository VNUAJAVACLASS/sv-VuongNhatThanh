package controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@WebServlet("/testmail")
public class TestMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Nhận email từ form
		String toEmail = req.getParameter("email");

		if (toEmail == null || toEmail.trim().isEmpty()) {
			resp.getWriter().println("❌ Vui lòng nhập email!");
			return;
		}

		final String fromEmail = "thanhchoimine1302@gmail.com";
		final String password = "mvtl mxyo lsxh ilqc"; // App password

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");

			// Gmail yêu cầu thêm TLS 1.2 + SSL trust
			props.put("mail.smtp.ssl.protocols", "TLSv1.2");
			props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail, "Bookstore"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("Bạn đã đăng ký nhận email!");
			message.setText("Chúc mừng!\nBạn đã đăng ký nhận thông báo từ Bookstore.");

			Transport.send(message);

			resp.getWriter().println("✅ Gửi email thành công tới: " + toEmail);

		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().println("❌ Lỗi gửi mail: " + e.getMessage());
		}
	}
}
