<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить новую привычку</title>
</head>
<body>

<br>
Добавить новую привычку пользователю ${param.id}<br>
<br>

<form action = "habits" method="post">
    <input type="hidden" name="id" value="${param.id}">
    <input required type="text" name="title" placeholder="Название">
    <input required type="text" name="text" placeholder="Описание">
    <input required type="text" name="frequency" placeholder="Частота">
    <input type="submit" value="Сохранить">
</form>
</body>
</html>