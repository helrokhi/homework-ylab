<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Управление пользователем</title>
</head>
<body>
<br>
Управление пользователем ${param.id}<br>
<br>
<form action = "updateUser.jsp" method = "put">
    <input type="hidden" name="id" value="${param.id}">
    <input type="hidden" name="name" value="${param.name}">
    <input type="hidden" name="email" value="${param.email}">
    <input type="hidden" name="password" value="${param.password}">
    <input type="submit" value="Изменить" style="float:left">
</form>
<br>
<form action="deleteUser.jsp" method = "delete">
    <input type="hidden" name="id" value="${param.id}">
    <input type="submit" value="Удалить" style="float:left">
</form>
<br>
<form action = "login">
    <input type="hidden" name="id" value="${param.id}">
    <input type="submit" value="Вернуться в личный кабинет">
</form>
</body>

</html>