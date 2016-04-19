<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="user" type="com.epam.anuar.gorkomtrans.entity.User"--%>

<t:authorizedpage>
    <c:if test="${user eq null}">
        <p>Please, login to view contracts</p>
    </c:if>
</t:authorizedpage>