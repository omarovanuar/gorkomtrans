<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="other-text" var="rb"/>
<%--@elvariable id="violations" type="java.util.List"--%>
<%--@elvariable id="userParam" type="com.epam.anuar.gorkomtrans.entity.User"--%>

<t:authorizedpage>
    <div id="personal-cabinet" align="left">
        <section class="user-data cf">
            <form action="<c:url value="/do/user-change"/>" method="post">
                <table class="user-data-table" id="user-data-table">
                    <tr>
                        <th align="left"><fmt:message key="personal.user-data" bundle="${rb}"/>:</th>
                        <input type="hidden" name="id" value="${userParam.id}"/>
                    </tr>
                    <tr>
                        <td><fmt:message key="user.password" bundle="${rb}"/>:</td>
                        <td><input id="password" type="text" name="Password" value="${userParam.password}"/></td>
                        <td><div style="color: red">${violations.get(0)}</div></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="user.email" bundle="${rb}"/>:</td>
                        <td><input id="email" type="email" name="Email" value="${userParam.email}"/></td>
                        <td><div style="color: red">${violations.get(1)}</div></td>
                    </tr>
                    <tr>
                        <th align="left"><fmt:message key="user.role" bundle="${rb}"/>:</th>
                        <td>
                            <select id="role-select" name="role-select">
                                <option
                                        <c:if test="${userParam.role.roleCode eq 0}">selected</c:if> value="0">
                                    REGISTERED USER
                                </option>
                                <option
                                        <c:if test="${userParam.role.roleCode eq 1}">selected</c:if> value="1">MODERATOR
                                </option>
                                <option
                                        <c:if test="${userParam.role.roleCode eq 2}">selected</c:if> value="2">ADMIN
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th align="left"><fmt:message key="personal.balance" bundle="${rb}"/>:</th>
                        <td><input id="balance" type="text" name="balance"
                                   value="${userParam.wallet.money.amount.toString()}"/></td>
                        <td><div style="color: red">${violations.get(2)}</div></td>
                    </tr>
                </table>
                <input type="submit" value="<fmt:message key="personal.button" bundle="${rb}"/>">
            </form>
        </section>
    </div>
</t:authorizedpage>
