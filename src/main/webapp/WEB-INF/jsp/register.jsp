<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="registerError" type="java.lang.String"--%>

<t:welcomepage>
    <div id="register-center" align="center">
        <section class="welcomeform cf">
            <form action="<c:url value="/do/register"/>" method="post">
                <ul>
                    <p>Registration form:</p>
                    <li>Login:
                        <input id="login" type="text" name="login" placeholder="login"/>
                    </li>
                    <li>Password:
                        <input id="password" type="password" name="password" placeholder="password"/>
                    </li>
                </ul>
                <ul>
                    <li>Email:
                        <input id="email" type="email" name="email" placeholder="email"/>
                    </li>
                </ul>
                <ul>
                    <li>
                        <input type="submit" value="Login">
                    </li>
                    <li>
                        <div style="color:red">${registerError}</div>
                    </li>
                    <p><a href="<c:url value="/do/"/>">login page</a></p>
                </ul>
            </form>
        </section>
    </div>
</t:welcomepage>
