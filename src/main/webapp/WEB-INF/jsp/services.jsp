<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="other-text" var="rb"/>
<%--@elvariable id="violation" type="java.lang.String"--%>


<t:authorizedpage>
    <section class="service">
        <div align="center">
            <img id="gkt-service" src="/images/service.jpg" height="400" width="600" style="background: dimgrey">
        </div>
        <p><fmt:message key="services.text1" bundle="${rb}"/></p>
        <p><fmt:message key="services.text2" bundle="${rb}"/></p>
        <p><fmt:message key="services.text3" bundle="${rb}"/></p>

        <form action="<c:url value="/do/tech-spec"/>" method="post">
            <ul>
                <p>
                    <fmt:message key="services.tech-spec-text" bundle="${rb}"/>
                </p>
                <li>
                    <input id="euro-type" type="checkbox" name="euro-type" value="1"><fmt:message key="services.euro" bundle="${rb}"/>
                </li>
                <li>
                    <input id="standard-type" type="checkbox" name="standard-type" value="1"><fmt:message key="services.standard" bundle="${rb}"/>
                </li>
                <li>
                    <fmt:message key="services.non-standard" bundle="${rb}"/>
                    <select id="non-standard-type-number" name="non-standard-type-number">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select>
                </li>
                <li class="empty-tech-spec">
                    <div><input type="submit" value="<fmt:message key="services.button" bundle="${rb}"/>"></div>
                    <div id="empty-tech-error" style="color: red">${violation}</div>
                </li>
            </ul>
        </form>
    </section>
</t:authorizedpage>