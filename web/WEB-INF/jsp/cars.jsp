<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>СПИСОК ДОСТУПНЫХ АВТОМОБИЛЕЙ</h1>
<ul>
    <c:forEach var="car" items="${requestScope.cars}">
        <li>
            <a href="${pageContext.request.contextPath}/car-category?carCategoryId=${car.carCategoryId}">
                    ${car.description}
            </a>
        </li>
    </c:forEach>
</ul>
</body>
</html>
