<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp" %>

<h1>
    <fmt:message key="page.registration.head.registration" />
</h1>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="firstNameId"><fmt:message key="page.registration.first.name" />:
        <input type="text" name="firstName" id="firstNameId" required>
    </label><br>
    <label for="lastNameId"><fmt:message key="page.registration.last.name" />:
        <input type="text" name="lastName" id="lastNameId" required>
    </label><br>
    <label for="emailId"><fmt:message key="page.registration.email" />:
        <input type="text" name="email" id="emailId" required>
    </label><br>
    <label for="passwordId"><fmt:message key="page.registration.password" />:
        <input type="password" name="password" id="passwordId" required>
    </label><br>
    <label for="roleId"><fmt:message key="page.registration.role" />:
        <select name="role" id="roleId">
            <c:forEach var="role" items="${requestScope.roles}">
                <option value="${role}">${role}</option>
            </c:forEach>
        </select><br>
    </label>
    <button type="submit"><fmt:message key="page.registration.button.send" /></button>
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
