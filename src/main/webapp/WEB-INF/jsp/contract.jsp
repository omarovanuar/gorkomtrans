<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="other-text" var="rb"/>
<%--@elvariable id="contract" type="com.epam.anuar.gorkomtrans.entity.Contract"--%>
<%--@elvariable id="provider" type="com.epam.anuar.gorkomtrans.entity.Provider"--%>
<%--@elvariable id="status" type="java.lang.Integer"--%>
<%--@elvariable id="user" type="com.epam.anuar.gorkomtrans.entity.User"--%>
<%--@elvariable id="locale" type="java.lang.String"--%>

<t:authorizedpage>
    <h2 align="center"><fmt:message key="contract.contract" bundle="${rb}"/> №${contract.id}</h2>
    <div class="main-text">
        <p>${contract.user.fullName}, <fmt:message key="contract.text1" bundle="${rb}"/>
                ${provider.organizationName}, <fmt:message key="contract.text2" bundle="${rb}"/>.</p>
        <p><fmt:message key="contract.text3" bundle="${rb}"/></p>
        <p> 1. <fmt:message key="contract.text4" bundle="${rb}"/></p>
        <p> 2. <fmt:message key="contract.text5" bundle="${rb}"/></p>
        <p> 3. <fmt:message key="contract.text6" bundle="${rb}"/></p>
        <p> 4. <fmt:message key="contract.text7" bundle="${rb}"/></p>
        <p><fmt:message key="contract.text8" bundle="${rb}"/></p>
        <p> - <fmt:message key="contract.text9" bundle="${rb}"/></p>
        <p><fmt:message key="contract.text10" bundle="${rb}"/></p>
        <p> - <fmt:message key="contract.text11" bundle="${rb}"/></p>
        <p><fmt:message key="contract.text12" bundle="${rb}"/></p>
        <p><fmt:message key="contract.text13" bundle="${rb}"/></p>
    </div>
    <div class="contract-details">
        <div class="customer-req">
            <table>
                <tr>
                    <th colspan="2"><fmt:message key="contract.customer-req" bundle="${rb}"/></th>
                </tr>
                <tr>
                    <td><fmt:message key="contract.customer-name" bundle="${rb}"/>:</td>
                    <td>${contract.user.fullName}</td>
                </tr>
                <tr>
                    <td><fmt:message key="contract.account" bundle="${rb}"/>:</td>
                    <td>${contract.user.bankName} ${contract.user.bankAccount}</td>
                </tr>
                <tr>
                    <td><fmt:message key="contract.address" bundle="${rb}"/>:</td>
                    <td>${contract.user.mainAddress}</td>
                </tr>
                <tr>
                    <td><fmt:message key="contract.tel" bundle="${rb}"/>:</td>
                    <td>${contract.user.phoneNumber}</td>
                </tr>
            </table>
        </div>
        <div class="provider-req">
            <table>
                <tr>
                    <th colspan="2"><fmt:message key="contract.provider-req" bundle="${rb}"/></th>
                </tr>
                <tr>
                    <td><fmt:message key="contract.organization" bundle="${rb}"/>:</td>
                    <td>${provider.organizationName}</td>
                </tr>
                <tr>
                    <td><fmt:message key="contract.account" bundle="${rb}"/>:</td>
                    <td>${provider.bankDetails}</td>
                </tr>
                <tr>
                    <td><fmt:message key="contract.address" bundle="${rb}"/>:</td>
                    <td>${provider.mainAddress}</td>
                </tr>
                <tr>
                    <td><fmt:message key="contract.tel" bundle="${rb}"/>:</td>
                    <td>${provider.phoneNumber}</td>
                </tr>
            </table>
        </div>
        <div class="tech-spec-details">
            <table>
                <th colspan="2"><fmt:message key="contract.tech-spec" bundle="${rb}"/></th>
                <tr>
                    <td><fmt:message key="contract.service-place" bundle="${rb}"/>:</td>
                    <td>${contract.garbageTechSpecification.address}</td>
                </tr>
                <tr>
                    <td><fmt:message key="contract.capacity-per-month" bundle="${rb}"/>:</td>
                    <td>${contract.garbageTechSpecification.capacityPerMonthString}</td>
                </tr>
                <tr>
                    <td><fmt:message key="contract.providing-month-number" bundle="${rb}"/>:</td>
                    <td>${contract.providingMonthNumber} <fmt:message key="contract.month" bundle="${rb}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="contract.total-capacity" bundle="${rb}"/></td>
                    <td>${contract.contractTotalCapacityString}</td>
                </tr>
            </table>
        </div>
        <div class="sanction-contract">
            <span><fmt:message key="contract.price" bundle="${rb}"/>:</span>
            <input disabled id="contract-cost" type="text" name="contract-cost"
                   value="${contract.contractAmount.toString()}">
            <span id="status"><fmt:message key="contract.status" bundle="${rb}"/>:
            <c:choose>
                <c:when test="${locale eq \"ru\"}">
                    ${contract.status.ru}
                </c:when>
                <c:otherwise>
                    ${contract.status.toString()}
                </c:otherwise>
            </c:choose></span>
            <c:if test="${status == 0 && user.roleByCode != 1}">
                <div id="submit-contract">
                    <form action="<c:url value="/do/submitted-contract"/>" method="post">
                        <input id="submit" type="submit" value="<fmt:message key="contract.button1" bundle="${rb}"/>">
                    </form>
                </div>
            </c:if>
            <c:if test="${status == 1 && user.roleByCode >= 1}">
                <div id="agree-contract">
                    <form action="<c:url value="/do/agree-contract"/>" method="post">
                        <input id="agree" type="submit" value="<fmt:message key="contract.button2" bundle="${rb}"/>">
                    </form>
                </div>
                <div id="deny-contract">
                    <form action="<c:url value="/do/deny-contract"/>" method="post">
                        <input id="deny" type="submit" value="<fmt:message key="contract.button3" bundle="${rb}"/>">
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</t:authorizedpage>