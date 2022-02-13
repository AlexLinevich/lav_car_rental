<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>КАТЕГОРИЯ АВТОМОБИЛЯ</h1>
<ul>
    КАТЕГОРИЯ АВТОМОБИЛЯ: ${requestScope.carCategory.category}
    ЦЕНА АРЕНДЫ ЗА СУТКИ: ${requestScope.carCategory.dayPrice}
</ul>
</body>
</html>
