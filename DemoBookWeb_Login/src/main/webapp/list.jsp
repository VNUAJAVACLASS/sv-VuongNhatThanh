<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Book"%>

<%
    List<Book> bookList = (List<Book>) request.getAttribute("bookList");
%>

<html>
<head>
<meta charset="UTF-8">
<title>Danh sách sách đang bán</title>
</head>
<body>
	<!-- Hiển thị username từ sessionScope + link Logout -->
	<p>
		Xin chào: ${sessionScope.username} | <a href="logout.jsp">Đăng
			xuất</a>
	</p>
	<hr>

	<h2>Danh sách sách đang bán</h2>
	<a href="book?action=create">Thêm sách mới</a>
	<br>
	<br>

	<table border="1" cellpadding="5">
		<tr>
			<th>ID</th>
			<th>Tiêu đề</th>
			<th>Tác giả</th>
			<th>Giá</th>
			<th>Ảnh</th>
			<th>Hành động</th>
		</tr>

		<%
            if (bookList != null && !bookList.isEmpty()) {
                for (Book book : bookList) {
        %>
		<tr>
			<td><%= book.getBookId() %></td>
			<td><a href="book?action=detail&id=<%= book.getBookId() %>">
					<%= book.getTitle() %>
			</a></td>
			<td><%= book.getAuthor() %></td>
			<td><%= book.getPrice() %></td>
			<td>
				<% if (book.getImagePath() != null && !book.getImagePath().isEmpty()) { %>
				<img src="<%= book.getImagePath() %>" alt="Ảnh sách" width="100"
				height="140"> <% } else { %> Không có ảnh <% } %>
			</td>
			<td><a href="book?action=edit&id=<%= book.getBookId() %>">Sửa</a>
				| <a href="book?action=delete&id=<%= book.getBookId() %>"
				onclick="return confirm('Bạn có chắc muốn xóa sách này?');">Xóa</a>
			</td>
		</tr>
		<%
                }
            } else {
        %>
		<tr>
			<td colspan="6">Không có sách nào trong danh sách.</td>
		</tr>
		<%
            }
        %>
	</table>
</body>
</html>
