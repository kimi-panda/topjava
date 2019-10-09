<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<hr>
<table border="1" cellpadding="8" cellspacing="0">
    <c:forEach items="${listMealTo}" var="meal">
        <tr style="background-color:${meal.excess ? 'red' : 'green'}">
            <td>${meal.dateTime.format( DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>