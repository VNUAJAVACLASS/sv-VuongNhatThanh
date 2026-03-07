<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Book"%>

<%
    Book book = (Book) request.getAttribute("book");
%>

<html>
<head>
<meta charset="UTF-8">
<title>Chi tiết sách (Client)</title>
</head>
<body>
	<% if (book != null) { %>
	<h2><%= book.getTitle() %></h2>
	<p>
		<b>Tác giả:</b>
		<%= book.getAuthor() %></p>
	<p>
		<b>Giá:</b>
		<%= book.getPrice() %>
		VND
	</p>
	<p>
		<b>Ảnh:</b><br>
		<% if (book.getImagePath() != null && !book.getImagePath().isEmpty()) { %>
		<img src="<%= request.getContextPath() %>/<%= book.getImagePath() %>"
			alt="Ảnh sách" width="150">
		<% } else { %>
		Không có ảnh
		<% } %>
	</p>
	<% } else { %>
	<p>Không tìm thấy sách!</p>
	<% } %>

	<br>
	<!-- Đường link quay lại ClientHome -->
	<a href="<%= request.getContextPath() %>/clientHome">Quay lại danh
		sách</a>
</body>
</html>
