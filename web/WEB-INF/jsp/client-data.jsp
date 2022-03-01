<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp" %>
<h1>
    ADD YOUR DATA
</h1>
<form action="${pageContext.request.contextPath}/client-data" method="post">
    <label for="driverLicenceNoId">Driver licence number:
        <input type="text" name="driverLicenceNo" id="driverLicenceNoId" required>
    </label><br>
    <label for="dlExpirationDayId">Driver licence expiration day:
        <input type="date" name="dlExpirationDay" id="dlExpirationDayId" required>
    </label><br>
    <label for="creditAmountId">Credit amount:
        <input type="number" name="creditAmount" id="creditAmountId" required>
    </label>
    <button type="submit">Send</button>
    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
            </c:forEach>
        </div>
    </c:if>
</form>
</body>
</html>
