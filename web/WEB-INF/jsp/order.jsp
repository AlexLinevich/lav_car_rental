<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp" %>
<h1>MAKE ORDER</h1>
<form action="${pageContext.request.contextPath}/order" method="post">
    <label for="car">Car:
        <select name="car" id="car">
            <c:forEach var="car" items="${requestScope.cars}">
                <option value="${car.description}">${car.description}</option>
            </c:forEach>
        </select><br>
    </label><br>
    <label for="beginTimeId">Start time:
        <input type="datetime-local" name="beginTime" id="beginTimeId" required>
    </label><br>
    <label for="endTimeId">End time:
        <input type="datetime-local" name="endTime" id="endTimeId" required>
    </label><br>
    <button type="submit">Send</button>
</form>
</body>
</html>
