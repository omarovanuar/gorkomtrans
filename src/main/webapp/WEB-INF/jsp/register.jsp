<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<div>
    <form action=""<c:url value="/do/register"/>" method="post">
        <p>Login: <input type="text" name="login"/>
        <p>Password: <input type="password" name="password"/>
        <p>E-mail: <input type="email" name="email">
        <p><button type="submit">Register</button>
        <div style="color:red">${registerError}</div>
    </form>
</div>
</body>
</html>
