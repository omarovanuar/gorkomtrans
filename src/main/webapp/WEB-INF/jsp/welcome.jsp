<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="loginError" type="java.lang.String"--%>

<t:welcomepage>
    <div id="login-center" align="center">
        <section class="welcomeform cf">
            <form action="<c:url value="/do/login"/>" method="post">
                <ul>
                    <p>Please, login:</p>
                    <li>Login:
                        <input id="login" type="text" name="login" placeholder="login"/>
                    </li>
                    <li>Password:
                        <input id="password" type="password" name="password" placeholder="password"/>
                    </li>
                    <li>
                        <input type="submit" value="Login">
                    </li>
                    <li>
                        <div style="color:red">${loginError}</div>
                    </li>
                    <p>or <a href="<c:url value="/do/register"/>">register</a> a new user.</p>
                </ul>
            </form>
        </section>
    </div>
</t:welcomepage>
