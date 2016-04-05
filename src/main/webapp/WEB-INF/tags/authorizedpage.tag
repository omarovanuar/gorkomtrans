<%@tag description="User Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
        <div id="logo">
            <a href="${pageContext.request.contextPath}/do/home"><img id="gktlogo" src="/images/gkt.png"></a>
        </div>
        <div id="logout">
            Hello, ${user.login}! You are logged in! Now you may <a href="<c:url value="/do/logout"/>">log out</a>.
        </div>
        <nav>
            <ul class="main-menu">
                <li><a href="${pageContext.request.contextPath}/do/home">ABOUT COMPANY</a></li>
                <li><a href="${pageContext.request.contextPath}/do/services">SERVICES</a></li>
                <li><a href="${pageContext.request.contextPath}/do/employee">EMPLOYEE</a></li>
                <li><a href="${pageContext.request.contextPath}/do/contacts">CONTACTS</a></li>
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