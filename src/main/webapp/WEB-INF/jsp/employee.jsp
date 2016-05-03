<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="other-text" var="rb"/>

<t:authorizedpage>
    <div class="team-row" align="center">
        <figure>
            <img src="/images/1.jpg" width="96" height="96" alt="">
            <figcaption><fmt:message key="employee.director" bundle="${rb}"/><br><span><fmt:message key="employee.director-name" bundle="${rb}"/></span></figcaption>
        </figure>
        <figure>
            <img src="/images/2.jpg" width="96" height="96" alt="">
            <figcaption><fmt:message key="employee.financial-director" bundle="${rb}"/><br><span><fmt:message key="employee.financial-director-name" bundle="${rb}"/></span></figcaption>
        </figure>
        <figure>
            <img src="/images/3.jpg" width="96" height="96" alt="">
            <figcaption><fmt:message key="employee.head-econom" bundle="${rb}"/><br><span><fmt:message key="employee.head-econom-name" bundle="${rb}"/></span></figcaption>
        </figure>
        <figure>
            <img src="/images/4.jpg" width="96" height="96" alt="">
            <figcaption><fmt:message key="employee.head-legal" bundle="${rb}"/><br><span><fmt:message key="employee.head-legal-name" bundle="${rb}"/></span></figcaption>
        </figure>
    </div>
    <div class="team-row" align="center">
        <figure>
            <img src="/images/5.jpg" width="96" height="96" alt="">
            <figcaption><fmt:message key="employee.head-abonent" bundle="${rb}"/><br><span><fmt:message key="employee.head-abonent-name" bundle="${rb}"/></span></figcaption>
        </figure>
        <figure>
            <img src="/images/6.jpg" width="96" height="96" alt="">
            <figcaption><fmt:message key="employee.ecologist" bundle="${rb}"/><br><span><fmt:message key="employee.ecologist-name" bundle="${rb}"/></span></figcaption>
        </figure>
        <figure>
            <img src="/images/7.jpg" width="96" height="96" alt="">
            <figcaption><fmt:message key="employee.head-column" bundle="${rb}"/><br><span><fmt:message key="employee.head-column-name" bundle="${rb}"/></span></figcaption>
        </figure>
        <figure>
            <img src="/images/8.jpg" width="96" height="96" alt="">
            <figcaption><fmt:message key="employee.engineer" bundle="${rb}"/><br><span><fmt:message key="employee.engineer-name" bundle="${rb}"/></span></figcaption>
        </figure>
    </div>
</t:authorizedpage>