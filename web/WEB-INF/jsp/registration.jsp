<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="firstNameId">First name:
        <input type="text" name="firstName" id="firstNameId">
    </label><br>
    <label for="lastNameId">Last name:
        <input type="text" name="lastName" id="lastNameId">
    </label><br>
    <label for="emailId">Email:
        <input type="text" name="email" id="emailId">
    </label><br>
    <label for="passwordId">Password:
        <input type="password" name="password" id="passwordId">
    </label><br>
    <label for="roleId">Role:
        <select name="role" id="roleId">
            <c:forEach var="role" items="${requestScope.roles}">
                <option value="${role}">${role}</option>
            </c:forEach>
        </select><br>
    </label>
    <%--    <label for="dlExpirationDayId">Driver licence expiration day:--%>
    <%--        <input type="date" name="dlExpirationDay" id="dlExpirationDayId">--%>
    <%--    </label><br>--%>
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
