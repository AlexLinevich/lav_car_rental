<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp" %>
<h1>LIST OF AVAILABLE CARS</h1>
<ul>
    <c:forEach var="car" items="${requestScope.cars}">
        <li>
            <img width="150" height="100" src="${pageContext.request.contextPath}/images/${car.image}"
                 alt="No image"><br>
            <a href="${pageContext.request.contextPath}/car-category?carCategoryId=${car.carCategoryId}">
                    ${car.description}
            </a>
        </li>
    </c:forEach>
</ul>
<%@ include file="footer.jsp" %>
</body>
</html>

