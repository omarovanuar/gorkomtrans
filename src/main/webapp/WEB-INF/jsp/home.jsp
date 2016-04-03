<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="user" type="tb.entity.User"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:authorizedpage>
    Hello, ${user.login}! You are logged in! Now you may <a href="<c:url value="/do/logout"/>">log out</a>.
</t:authorizedpage>
