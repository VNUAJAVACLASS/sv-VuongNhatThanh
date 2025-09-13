<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table border="1" cellpadding="5">
    <tr>
        <th>ID</th>
        <th>Tiêu đề</th>
        <th>--</th>
    </tr>
    <c:forEach var="books" items="${bookList}">
        <tr>
            <td>${news.id}</td>
            <td><h4>${news.title}</h4></td>
            <td>
                <a href="clientHome?action=detail&id=${book.id}">Xem chi tiết</a>
            </td>
        </tr>
    </c:forEach>
</table>
