<%@tag description="User Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="user" type="com.epam.anuar.gorkomtrans.entity.User"--%>

<t:genericpage>
    <jsp:attribute name="header">
        <div id="logo">
            <a href="${pageContext.request.contextPath}/do/"><img id="gktlogo" src="/images/gkt.png"></a>
        </div>
        <div id="logout">
            <c:choose>
                <c:when test="${user.roleByCode eq 2}">
                    Hello, ${user.fullName}! <a href="<c:url value="/do/admin-cabinet"/>">Admin cabinet</a> <a href="<c:url value="/do/logout"/>">log out</a>.
                </c:when>
                <c:when test="${user.roleByCode eq 1}">
                    Hello, ${user.fullName}! <a href="<c:url value="/do/admin-cabinet"/>">Admin cabinet</a> <a href="<c:url value="/do/logout"/>">log out</a>.
                </c:when>
                <c:when test="${user.roleByCode eq 0}">
                    Hello, ${user.fullName}! <a href="<c:url value="/do/personal-cabinet"/>">Personal cabinet</a> <a href="<c:url value="/do/logout"/>">log out</a>.
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/do/welcome"/>">Login</a>
                </c:otherwise>
            </c:choose>
        </div>
        <nav>
            <ul class="main-menu">
                <li><a href="${pageContext.request.contextPath}/do/">ABOUT COMPANY</a></li>
                <li><a href="${pageContext.request.contextPath}/do/services">SERVICES</a></li>
                <li><a href="${pageContext.request.contextPath}/do/employee">EMPLOYEE</a></li>
                <c:choose>
                    <c:when test="${user eq null}">
                        <li><a href="${pageContext.request.contextPath}/do/error-page">CONTRACTS</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/do/contracts">CONTRACTS</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </jsp:attribute>
    <jsp:attribute name="footer">
      <p id="copyright">Copyright 2016 by Omarov Anuar.</p>
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</t:genericpage>