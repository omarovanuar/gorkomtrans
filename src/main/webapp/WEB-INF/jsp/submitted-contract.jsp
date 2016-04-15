<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="isSubmitted" type="java.lang.String"--%>

<t:authorizedpage>
    <p>${isSubmitted}</p>
</t:authorizedpage>