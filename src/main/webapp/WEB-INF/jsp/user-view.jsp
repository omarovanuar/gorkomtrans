<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="userParam" type="com.epam.anuar.gorkomtrans.entity.User"--%>
<%--@elvariable id="updateUserParamError" type="java.lang.String"--%>

<t:authorizedpage>
    <div id="personal-cabinet" align="left">
        <section class="user-data cf">
            <form action="<c:url value="/do/user-change"/>" method="post">
                <table class="user-data-table" id="user-data-table">
                    <tr>
                        <th align="left">User data:</th>
                        <th style="color:red">${updateUserParamError}</th>
                        <input type="hidden" name="id" value="${userParam.id}"/>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input id="password" type="text" name="password" value="${userParam.password}"/></td>
                    </tr>
                    <tr>
                        <td>Email:</td>
                        <td><input id="email" type="email" name="email" value="${userParam.email}"/></td>
                    </tr>
                    <tr>
                        <th align="left">Role:</th>
                        <td>
                            <select id="role-select" name="role-select">
                                <option <c:if test="${userParam.role.roleCode eq 0}">selected</c:if> value="0">REGISTERED USER</option>
                                <option <c:if test="${userParam.role.roleCode eq 1}">selected</c:if> value="1">MODERATOR</option>
                                <option <c:if test="${userParam.role.roleCode eq 2}">selected</c:if> value="2">ADMIN</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th align="left">Balance:</th>
                        <td><input id="balance" type="text" name="balance" value="${userParam.wallet.money.amount.toString()}"/></td>
                    </tr>
                </table>
                <input type="submit" value="Apply changes">
            </form>
        </section>
    </div>
</t:authorizedpage>
