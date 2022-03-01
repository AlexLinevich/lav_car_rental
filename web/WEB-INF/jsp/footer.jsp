<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <c:if test="${sessionScope.user.role.name() == 'ADMIN'}">
        <form action="${pageContext.request.contextPath}/add-car" method="get">
            <button type="submit">Add car</button>
        </form>
        <form action="${pageContext.request.contextPath}/orders" method="get">
            <button type="submit">See orders</button>
        </form>
        <form action="${pageContext.request.contextPath}/save" method="get">
            <button type="submit">Save report</button>
        </form>
        <form action="${pageContext.request.contextPath}/download" method="get">
            <button type="submit">Download report</button>
        </form>
    </c:if>
    <c:if test="${sessionScope.user.role.name() == 'CLIENT'}">
        <form action="${pageContext.request.contextPath}/order" method="get">
            <button type="submit">Make order</button>
        </form>
        <form action="${pageContext.request.contextPath}/client-orders" method="get">
            <button type="submit">See your orders</button>
        </form>
    </c:if>
</div>
