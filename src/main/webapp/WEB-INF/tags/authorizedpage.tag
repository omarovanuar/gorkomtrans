<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="mainMenu" var="rb"/>
<%--@elvariable id="user" type="com.epam.anuar.gorkomtrans.entity.User"--%>
<%--@elvariable id="locale" type="java.lang.String"--%>


<t:genericpage>
    <jsp:attribute name="header">
        <div id="logo">
            <a href="${pageContext.request.contextPath}/do/"><img id="gktlogo" src="/images/gkt.png"></a>
        </div>
        <div class="functions">
            <c:choose>
                <c:when test="${user.roleByCode eq 2}">
                    <fmt:message key="function.hello" bundle="${rb}"/>, ${user.fullName}!
                    <a href="<c:url value="/do/admin-cabinet"/>"><fmt:message key="function.admin-cabinet"
                                                                              bundle="${rb}"/></a>
                    <a href="<c:url value="/do/contract-sanction"/>"><fmt:message key="function.sanction-contract"
                                                                                  bundle="${rb}"/></a>
                    <a href="<c:url value="/do/logout"/>"><fmt:message key="function.logout" bundle="${rb}"/></a>
                </c:when>
                <c:when test="${user.roleByCode eq 1}">
                    <fmt:message key="function.hello" bundle="${rb}"/>, ${user.fullName}!
                    <a href="<c:url value="/do/contract-sanction"/>"><fmt:message key="function.sanction-contract"
                                                                                  bundle="${rb}"/></a>
                    <a href="<c:url value="/do/logout"/>"><fmt:message key="function.logout" bundle="${rb}"/></a>
                </c:when>
                <c:when test="${user.roleByCode eq 0}">
                    <fmt:message key="function.hello" bundle="${rb}"/>, ${user.fullName}!
                    <a href="<c:url value="/do/personal-cabinet"/>"><fmt:message key="function.personal-cabinet"
                                                                                 bundle="${rb}"/></a>
                    <a href="<c:url value="/do/logout"/>"><fmt:message key="function.logout" bundle="${rb}"/></a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/do/welcome"/>"><fmt:message key="function.login" bundle="${rb}"/></a>
                </c:otherwise>
            </c:choose>
            <div class="language-change">
                <form action="<c:url value="/do/ru"/>" method="post">
                    <input type="submit" value="<fmt:message key="function.ru" bundle="${rb}"/>">
                </form>
                <form action="<c:url value="/do/en"/>" method="post">
                    <input type="submit" value="<fmt:message key="function.en" bundle="${rb}"/>">
                </form>
            </div>
        </div>
        <nav>
            <ul class="main-menu">
                <li><a href="${pageContext.request.contextPath}/do/"><fmt:message key="nav.aboutCompany"
                                                                                  bundle="${rb}"/></a></li>
                <li><a href="${pageContext.request.contextPath}/do/services"><fmt:message key="nav.services"
                                                                                          bundle="${rb}"/></a></li>
                <li><a href="${pageContext.request.contextPath}/do/employee"><fmt:message key="nav.employee"
                                                                                          bundle="${rb}"/></a></li>
                <li><a href="${pageContext.request.contextPath}/do/contracts"><fmt:message key="nav.contracts"
                                                                                           bundle="${rb}"/></a></li>
            </ul>
        </nav>
    </jsp:attribute>
    <jsp:attribute name="footer">
      <p id="copyright"><fmt:message key="footer.copyright" bundle="${rb}"/>.</p>
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</t:genericpage>