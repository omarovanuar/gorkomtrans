<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="other-text" var="rb"/>


<t:authorizedpage>
    <section class="main-page">
        <div id="pic1">
            <img id="gkt-main1" src="/images/main1.jpg" style="background: dimgrey">
        </div>
        <p align="justify"><fmt:message key="main.text1" bundle="${rb}"/></p>
        <p><fmt:message key="main.text2" bundle="${rb}"/></p>
        <div>
            <p><fmt:message key="main.text3" bundle="${rb}"/></p>
            <p align="justify"><fmt:message key="main.text4" bundle="${rb}"/></p>
        </div>
        <div id="pic2">
            <img id="gkt-main2" src="/images/main2.jpg" style="background: dimgrey">
        </div>
        <p><fmt:message key="main.text5" bundle="${rb}"/></p>
        <section class="contacts">
            <p><fmt:message key="main.text6" bundle="${rb}"/></p>
            <p><fmt:message key="contract.tel" bundle="${rb}"/>: (7212) 56-51-08</p>
            <p><fmt:message key="main.text7" bundle="${rb}"/>: (7212) 56-51-08</p>
            <p><fmt:message key="main.text8" bundle="${rb}"/></p>
        </section>

    </section>
</t:authorizedpage>
