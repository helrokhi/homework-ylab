<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список привычек</title>
</head>
<body>

<br>
Список привычек пользователя ${param.id}<br>
<br>
<table border="2">
    <tr>
        <td>ID</td>
        <td>title</td>
        <td>text</td>
        <td>time</td>
        <td>frequency</td>
        <td>Действия</td>
    </tr>
    <c:forEach items="${habits}" var = "habit">
        <tr>
            <td>${habit.getId()}</td>
            <td>${habit.getTitle()}</td>
            <td>${habit.getText()}</td>
            <td>${habit.getTime()}</td>
            <td>${habit.getFrequency()}</td>
            <td>
                <form action = "updateHabit.jsp" method = "PUT">
                    <input type="hidden" name="id" value="${param.id}">
                    <input type="hidden" name="habit" value="${habit.getId()}">
                    <input type="hidden" name="title" value="${habit.getTitle()}">
                    <input type="hidden" name="text" value="${habit.getText()}">
                    <input type="hidden" name="frequency" value="${habit.getFrequency()}">
                    <input type="submit" value="Изменить" style="float:left">
                </form>
                <br>
                <form action="deleteHabit.jsp" method = "delete">
                    <input type="hidden" name="id" value="${param.id}">
                    <input type="hidden" name="habit" value="${habit.getId()}">
                    <input type="submit" value="Удалить" style="float:left">
                </form></td>
        </tr>
    </c:forEach>
</table>
<br>
<form action = "addHabit.jsp">
    <input type="hidden" name="id" value="${param.id}">
    <input type="submit" value="Добавить новую привычку">
</form>
<br>
<form action = "login">
    <input type="hidden" name="id" value="${param.id}">
    <input type="submit" value="Вернуться в личный кабинет">
</form>
<br>
<br>
</body>
</html>