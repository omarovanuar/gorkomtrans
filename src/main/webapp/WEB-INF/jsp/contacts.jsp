<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="provider" type="com.epam.anuar.gorkomtrans.entity.Provider"--%>

<t:authorizedpage>
    <div id="contacts">
        <table id="contact-table">
            <tr>
                <td>Post index:</td>
                <td>${provider.postIndex}</td>
            </tr>
            <tr>
                <td>Address</td>
                <td>${provider.mainAddress}</td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>${provider.email}</td>
            </tr>
            <tr>
                <td>Tel.:</td>
                <td>${provider.phoneNumber}</td>
            </tr>
            <tr>
                <td>Fax:</td>
                <td>${provider.fax}</td>
            </tr>
        </table>
    </div>
</t:authorizedpage>
