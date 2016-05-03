<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="error" type="java.lang.String"--%>
<%--@elvariable id="exception" type="java.lang.String"--%>
<%--@elvariable id="statusCode" type="java.lang.String"--%>
<%--@elvariable id="servletName" type="java.lang.String"--%>
<%--@elvariable id="requestUri" type="java.lang.String"--%>

<t:authorizedpage>
    <div align="center">
        <table class="exception-table">
            <tr>
                <td>Exception:</td>
                <td>${exception}</td>
            </tr>
            <tr>
                <td>Status code:</td>
                <td>${statusCode}</td>
            </tr>
            <tr>
                <td>Servlet name:</td>
                <td>${servletName}</td>
            </tr>
            <tr>
                <td>Request URI</td>
                <td>${requestUri}</td>
            </tr>
        </table>
    </div>
</t:authorizedpage>