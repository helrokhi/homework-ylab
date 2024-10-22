<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Авторизация пользователя</title>
</head>
<body>
<br>
<c: item="${person}" var = "person"
<br>
    <form action = "login" method="get">
        <input type="hidden" name="id" value="${person.getId()}">
        <input type="submit" value="Авторизация пользователя ${person.getId()} ${person.getName()}">
    </form>
</c>
</body>
</html>