<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет пользователя</title>
</head>
<body>

Добро пожаловать в личный кабинет пользователь ${param.id}<br>
<br>

<form action = "profile">
    <input type="hidden" name="id" value="${param.id}">
    <input type="submit" value="Управление пользователем">
</form>

<form action = "habits">
    <input type="hidden" name="id" value="${param.id}">
    <input type="submit" value="Управление привычками">
</form>

<form action = "/api">
    <input type="submit" value="Выйти из личного кабинета">
</form>
</body>

</html>