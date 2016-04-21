<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="statusMessage" type="java.lang.String"--%>

<t:authorizedpage>
    <p>${statusMessage}</p>
</t:authorizedpage>