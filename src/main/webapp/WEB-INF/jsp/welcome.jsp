<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="loginError" type="java.lang.String"--%>

<t:welcomepage>
    <section class="loginform cf">
        <div>
            Please, login:
        </div>
        <form action="<c:url value="/do/login"/>" method="post">
            <ul>
                <li>Login:
                    <input id="login" type="text" name="login"/>
                </li>
                <li>Password:
                    <input id="password" type="password" name="password"/>
                </li>
                <li>
                    <input type="submit" value="Login">
                </li>
                <li>
                    <div style="color:red">${loginError}</div>
                </li>
            </ul>
        </form>
        <div>
            or <a href="<c:url value="/do/register"/>">register</a> a new user.
        </div>
    </section>
</t:welcomepage>
