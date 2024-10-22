<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Удалить привычку</title>
</head>
<body>

Вы действительно хотите удалить привычку ${param.habit} пользователя ${param.id}?

<form action="habits" method="delete">
    <input type="hidden" name="id" value="${param.id}">
    <input type="hidden" name="habit" value="${param.habit}">
    <input type="submit" value="Удалить">
</form>

</body>
</html>