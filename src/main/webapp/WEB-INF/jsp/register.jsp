<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=windows-1251" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="userParamList" type="java.util.List"--%>
<%--@elvariable id="violations" type="java.util.List"--%>
<%--@elvariable id="values" type="java.util.List"--%>
<%--@elvariable id="userParamName" type="java.util.List"--%>
<fmt:setBundle basename="login-register" var="rb"/>

<t:welcomepage>
    <div id="register-center" align="center">
        <section class="welcomeform cf">
            <form action="<c:url value="/do/register"/>" method="post">
                <ul>
                    <p><fmt:message key="register.main-text" bundle="${rb}"/>:</p>
                    <table>
                        <c:forEach items="${userParamList}" var="userParam" varStatus="i">
                            <tr>
                                <td>${userParamName.get(i.index)}</td>
                                <td>
                                    <div><input type="text" name="${userParam}" value="${values.get(i.index)}"
                                                placeholder="${userParamName.get(i.index)}"></div>
                                    <p id="register-error">${violations.get(i.index)}</p>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <li>
                        <input type="submit" value="<fmt:message key="register.button" bundle="${rb}"/>">
                    </li>
                    <p><a href="<c:url value="/do/welcome"/>"><fmt:message key="register.login-page" bundle="${rb}"/></a></p>
                </ul>
            </form>
        </section>
    </div>
</t:welcomepage>
