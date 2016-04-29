<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="loginError" type="java.lang.String"--%>
<%--@elvariable id="locale" type="java.lang.String"--%>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="login-register" var="rb"/>

<t:welcomepage>
    <div id="login-center" align="center">
        <section class="welcomelogin cf">
            <form action="<c:url value="/do/login"/>" method="post">
                <ul>
                    <p><fmt:message key="login.main-text" bundle="${rb}"/>:</p>
                    <li><fmt:message key="login.login" bundle="${rb}"/>:
                        <input id="login" type="text" name="login" placeholder="<fmt:message key="login.login" bundle="${rb}"/>"/>
                    </li>
                    <li><fmt:message key="login.password" bundle="${rb}"/>:
                        <input id="password" type="password" name="password" placeholder="<fmt:message key="login.password" bundle="${rb}"/>"/>
                    </li>
                    <li>
                        <input type="submit" value="<fmt:message key="login.button" bundle="${rb}"/>">
                    </li>
                    <li>
                        <div style="color:red">${loginError}</div>
                    </li>
                    <p><fmt:message key="login.or" bundle="${rb}"/> <a href="<c:url value="/do/register"/>"><fmt:message
                            key="login.register" bundle="${rb}"/></a> <fmt:message key="login.new-user" bundle="${rb}"/>.
                    </p>
                </ul>
            </form>
        </section>
    </div>
</t:welcomepage>
