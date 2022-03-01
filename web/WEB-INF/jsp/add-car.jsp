<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp" %>
<h1>ADD NEW CAR</h1>
<form action="${pageContext.request.contextPath}/add-car" method="post" enctype="multipart/form-data">
    <label for="modelId">Model:
        <input type="text" name="model" id="modelId" required>
    </label><br>
    <label for="colourId">Colour:
        <input type="text" name="colour" id="colourId" required>
    </label><br>
    <label for="seatsQuantityId">Seats quantity:
        <input type="number" name="seatsQuantity" id="seatsQuantityId" required>
    </label><br>
    <label for="imageId">Image:
        <input type="file" name="image" id="imageId">
    </label><br>
    <label for="carCategoryId">Car category:
        <select name="carCategory" id="carCategoryId">
            <c:forEach var="carCategory" items="${requestScope.carCategory}">
                <option value="${carCategory}">${carCategory}</option>
            </c:forEach>
        </select><br>
    </label>
    <button type="submit">Send</button>
</form>
</body>
</html>
