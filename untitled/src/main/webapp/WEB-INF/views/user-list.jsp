<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html>
<head>
    <title>Список пользователей</title>
</head>
<body>
    <h1>Список пользователей</h1>
    <table border="1">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th>Город</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>
                <c:forEach var="address" items="${user.addresses}">
                    ${address}<br>
                </c:forEach>
                </td>
            </tr>
        </c:forEach>
    </table>

    <a href="add">Добавить</a><br>
    <c:out value="${param}"/>
</body>
</html>