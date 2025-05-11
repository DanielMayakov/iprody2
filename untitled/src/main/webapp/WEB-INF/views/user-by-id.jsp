<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Details</title>
</head>
<body>
    <h1>User Details</h1>

    <c:choose>
        <c:when test="${user != null}">
            <p><strong>ID:</strong> ${user.id}</p>
            <p><strong>Name:</strong> ${user.name}</p>
            <p><strong>Email:</strong> ${user.email}</p>

            <h2>Address</h2>
            <c:if test="${user.address != null}">
                <p><strong>City:</strong> ${user.address.city}</p>
                <p><strong>Street:</strong> ${user.address.street}</p>
                <p><strong>Postal Code:</strong> ${user.address.postalCode}</p>
            </c:if>
        </c:when>
        <c:otherwise>
            <p>User not found.</p>
        </c:otherwise>
    </c:choose>

    <a href="/users">Back to Users List</a>
</body>
</html>
