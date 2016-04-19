<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="registerError" type="java.lang.String"--%>
<%--@elvariable id="userParamList" type="java.util.List<String>"--%>

<t:welcomepage>
    <div id="register-center" align="center">
        <section class="welcomeform cf">
            <form action="<c:url value="/do/register"/>" method="post">
                <ul>
                    <p>Registration form:</p>
                    <table>
                        <c:forEach items="${userParamList}" var="userParam">
                            <tr>
                                <td>${userParam}</td>
                                <td><input type="text" name="${userParam}" placeholder="${userParam}"></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <li>
                        <input type="submit" value="Register">
                    </li>
                    <li>
                        <div style="color:red">${registerError}</div>
                    </li>
                    <p><a href="<c:url value="/do/welcome"/>">login page</a></p>
                </ul>
            </form>
        </section>
    </div>
</t:welcomepage>
