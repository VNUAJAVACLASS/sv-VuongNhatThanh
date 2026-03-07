<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Dùng cách thức nhúng code Java trong JSP -->
<%
    session.invalidate();          // Hủy Session của user bên phía webserver
    response.sendRedirect("login");// Chuyển lại trang login
%>

