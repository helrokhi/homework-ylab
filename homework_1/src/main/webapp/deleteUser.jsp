<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Удалить пользователя</title>
</head>
<body>

Вы действительно хотите удалить пользователя ${param.id}?

<form action="profile" method="delete">
    <input type="hidden" name="id" value="${param.id}">
    <input type="submit" value="Удалить">
</form>

</body>
</html>