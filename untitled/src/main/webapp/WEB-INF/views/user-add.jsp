<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html>
<head>
    <title>Добавить пользователя</title>
</head>
<body>
    <h1>Добавить</h1>
    <form action="addUser" method="POST" modelAttribute="user">
           Имя<br>
           <input type="text" name="name"/><br>
           Email<br>
           <input type="text" name="email"/><br>
           <input type="submit" value="Добавить"/>
    </form>
</body>
</html>