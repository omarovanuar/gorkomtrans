<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:userpage>
    <div align="center">
        <form action="<c:url value="/do/register"/>" method="post">
            <p>Login: <input type="text" name="login"/>
                Password: <input type="password" name="password"/>
                E-mail: <input type="email" name="email">
            <p>
                <button type="submit">Register</button>
            <div style="color:red">${registerError}</div>
        </form>
    </div>
</t:userpage>
