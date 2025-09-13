<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Book" %>

<%
    Book book = (Book) request.getAttribute("book");
%>

<html>
<head>
<meta charset="UTF-8">
<title>Chi tiết sách</title>
</head>
<body>
    <!-- Thêm chào username và logout -->
    <p>Xin chào: ${sessionScope.username} | <a href="logout.jsp">Đăng xuất</a></p>
    <hr>

    <% if (book != null) { %>
        <h2><%= book.getTitle() %></h2>
        <p><b>Tác giả:</b> <%= book.getAuthor() %></p>
        <p><b>Giá:</b> <%= book.getPrice() %> VND</p>
        <p>
            <b>Ảnh:</b><br>
            <% if (book.getImagePath() != null && !book.getImagePath().isEmpty()) { %>
                <img src="<%= book.getImagePath() %>" alt="Ảnh sách" width="150">
            <% } else { %>
                Không có ảnh
            <% } %>
        </p>
    <% } else { %>
        <p>Không tìm thấy sách!</p>
    <% } %>

    <br>
    <a href="book">Quay lại danh sách</a>
</body>
</html>
