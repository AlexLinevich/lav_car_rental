<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp" %>
<h1>ORDER</h1>
<ul>
    ${requestScope.order.description}
</ul>
<form action="${pageContext.request.contextPath}/check"  method="get">
    <button type="submit" name="orderId" value="${order.id}">Check</button>
</form>
</body>
</html>
