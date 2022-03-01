<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp" %>
<h1>YOUR ORDERS</h1>
<ul>
    <c:forEach var="order" items="${requestScope.orders}">
        <li>
            <a href="${pageContext.request.contextPath}/see-order?orderId=${order.id}">
                    ${order.description}
            </a>
        </li>
    </c:forEach>
</ul>
</body>
</html>

