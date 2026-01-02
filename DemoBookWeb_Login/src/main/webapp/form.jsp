<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Book" %>

<%
    Book book = (Book) request.getAttribute("book");
    boolean isEdit = (book != null);
%>

<html>
<head>
    <meta charset="UTF-8">
    <title><%= isEdit ? "Sửa sách" : "Thêm sách" %></title>
</head>
<body>
    <!-- Thêm chào username và logout -->
    <p>Xin chào: ${sessionScope.username} | <a href="logout.jsp">Đăng xuất</a></p>
    <hr>

    <h2><%= isEdit ? "Sửa sách" : "Thêm sách" %></h2>

    <form action="book" method="post">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= book.getBookId() %>">
        <% } %>

        Tiêu đề: <br>
        <input type="text" name="title" value="<%= isEdit ? book.getTitle() : "" %>" required><br><br>

        Tác giả: <br>
        <input type="text" name="author" value="<%= isEdit ? book.getAuthor() : "" %>" required><br><br>

        Giá: <br>
        <input type="number" step="0.01" name="price" value="<%= isEdit ? book.getPrice() : "" %>" required><br><br>

        Đường dẫn ảnh: <br>
        <input type="text" name="imagePath" value="<%= isEdit ? book.getImagePath() : "" %>"><br><br>

        <input type="submit" value="<%= isEdit ? "Cập nhật" : "Thêm mới" %>">
    </form>

    <br>
    <a href="book">Quay lại danh sách</a>
</body>
</html>
