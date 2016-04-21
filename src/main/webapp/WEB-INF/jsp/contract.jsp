<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="contract" type="com.epam.anuar.gorkomtrans.entity.Contract"--%>
<%--@elvariable id="provider" type="com.epam.anuar.gorkomtrans.entity.Provider"--%>
<%--@elvariable id="status" type="java.lang.Integer"--%>
<%--@elvariable id="user" type="com.epam.anuar.gorkomtrans.entity.User"--%>

<t:authorizedpage>
    <h2 align="center">Contract №${contract.id}</h2>
    <div class="main-text">
        <p>${contract.user.fullName} , known as "First Party," agrees to enter into this contract
            with ${provider.organizationName}, known as "Second Party".</p>
        <p> This agreement is based on the following provisions:</p>
        <p> 1. First Party pays immediately the full cost of the contract.</p>
        <p> 2. If the Second Party doesn't sanction with the contract, the First Party takes full of his money
            back. </p>
        <p> 3. When the Second Party sanctions with the contract, status of the contract becomes "Sanctioned" and the
            Second Party starts executing of the contract in accordance with the technical specification.</p>
        <p> 4. After expiration of the contract, the Second Part provides to the First Party acts of rendered
            services.</p>
        <p> Furthermore, the First Party agrees:</p>
        <p> - to pay full cost of the contract to the Second Party</p>
        <p> and the Second Party agrees:</p>
        <p> - to fulfill its obligations to the First Party.</p>
        <p> Invalidity or unenforceability of one or more provisions of this agreement shall not affect any other
            provision of this agreement.</p>
        <p> This agreement is subject to the laws and regulations of the Republic of Kazakhstan.</p>
        </p>
    </div>
    <div class="contract-details">
        <div class="customer-req">
            <table>
                <tr>
                    <th colspan="2">Customer requisites</th>
                </tr>
                <tr>
                    <td>Customer name:</td>
                    <td>${contract.user.fullName}</td>
                </tr>
                <tr>
                    <td>Bank account:</td>
                    <td>${contract.user.bankName} ${contract.user.bankAccount}</td>
                </tr>
                <tr>
                    <td>Address:</td>
                    <td>${contract.user.mainAddress}</td>
                </tr>
                <tr>
                    <td>Tel:</td>
                    <td>${contract.user.phoneNumber}</td>
                </tr>
            </table>
        </div>
        <div class="provider-req">
            <table>
                <tr>
                    <th colspan="2">Provider requisites</th>
                </tr>
                <tr>
                    <td>Organization:</td>
                    <td>${provider.organizationName}</td>
                </tr>
                <tr>
                    <td>Bank account:</td>
                    <td>${provider.bankDetails}</td>
                </tr>
                <tr>
                    <td>Address:</td>
                    <td>${provider.mainAddress}</td>
                </tr>
                <tr>
                    <td>Tel:</td>
                    <td>${provider.phoneNumber}</td>
                </tr>
            </table>
        </div>
        <div class="tech-spec-details">
            <table>
                <th colspan="2">Technical specification</th>
                <tr>
                    <td>Service place</td>
                    <td>${contract.garbageTechSpecification.address}</td>
                </tr>
                <tr>
                    <td>Capacity per month</td>
                    <td>${contract.garbageTechSpecification.capacityPerMonthString}</td>
                </tr>
                <tr>
                    <td>Providing month number:</td>
                    <td>${contract.providingMonthNumber}</td>
                </tr>
                <tr>
                    <td>Total capacity</td>
                    <td>${contract.contractTotalCapacityString}</td>
                </tr>
            </table>
        </div>
        <div class="sanction-contract">
            <span>Service price:</span>
            <input disabled id="contract-cost" type="text" name="contract-cost"
                   value="${contract.contractAmount.toString()}">
            <span id="status">Status: ${contract.status.toString()}</span>
            <c:if test="${status == 0}">
                <form action="<c:url value="/do/submitted-contract"/>" method="post">
                    <input id="submit" type="submit" value="Submit contract">
                </form>
            </c:if>
            <c:if test="${status == 1 && user.roleByCode >= 1}">
                <div id="ag-de-contract">
                    <form action="<c:url value="/do/agree-contract"/>" method="post">
                        <input id="agree" type="submit" value="Agree">
                    </form>
                    <form action="<c:url value="/do/deny-contract"/>" method="post">
                        <input id="deny" type="submit" value="Deny">
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</t:authorizedpage>