<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменить данные пользователя</title>
</head>
<body>

Редактировать пользователя ${param.id}

<form action="profile" method="put">
    <input type="text" name="name" value="${param.name}" placeholder=${param.name}>
    <input type="text" name="email" value="${param.email}" placeholder=${param.email}>
    <input type="text" name="password" value="${param.password}" placeholder=${param.password}>
    <input type="submit" value="Обновить">
</form>

</body>
</html>