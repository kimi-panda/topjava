<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form action="users" method="post">
    <p><strong>Выбери пользователя:</strong></p>
    <p><select name="userId">
        <option value="1">1</option>
        <option value="2">2</option>
    </select>
        <input type="submit" value="Выбрать"></p>
</form>
</body>
</html>