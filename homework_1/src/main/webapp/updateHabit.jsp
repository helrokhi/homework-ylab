<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменить данные привычки</title>
</head>
<body>
<br>
Редактировать привычку ${param.habit} пользователя ${param.id}
<br>
<form action="habits" method="put">
    <input type="hidden" name ="id" value="${param.id}">
    <input type="hidden" name="habit" value="${param.habit}">
    <input type="text" name="title" value="${param.title}" placeholder=${param.title}>
    <input type="text" name="text" value="${param.text}" placeholder=${param.text}>
    <input type="text" name="frequency" value="${param.frequency}" placeholder=${param.frequency}>
    <input type="submit" value="Обновить">
</form>

</body>
</html>