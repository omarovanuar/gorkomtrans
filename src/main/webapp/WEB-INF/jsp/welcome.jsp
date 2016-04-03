<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:userpage>
    Please, <a href="<c:url value="/do/login"/>">log in</a> or <a href="<c:url value="/do/register"/>">register new user</a>
</t:userpage>
