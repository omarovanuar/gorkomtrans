<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="user" type="com.epam.anuar.gorkomtrans.entity.User"--%>
<%--@elvariable id="updateUserError" type="java.lang.String"--%>
<%--@elvariable id="upsertCustomerError" type="java.lang.String"--%>

<t:authorizedpage>
    <div id="personal-cabinet" align="left">

        <section class="user-data cf">
            <form action="<c:url value="/do/personal-cabinet"/>" method="post">
                <table class="user-data-table" id="user-data-table">
                    <tr>
                        <th align="left">User data:</th>
                        <th style="color:red">${updateUserError}</th>
                        <input type="hidden" name="id" value="${user.id}"/>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input id="password" type="text" name="password" value="${user.password}"/></td>
                    </tr>
                    <tr>
                        <td>Email:</td>
                        <td><input id="email" type="email" name="email" value="${user.email}"/></td>
                    </tr>
                    <tr>
                        <th align="left">Customer data:</th>
                        <th style="color:red">${upsertCustomerError}</th>
                    </tr>
                    <tr>
                        <td>First name:</td>
                        <td><input id="first-name" type="text" name="first-name" value="${user.firstName}"/></td>
                    </tr>
                    <tr>
                        <td>Last name:</td>
                        <td><input id="last-name" type="text" name="last-name" value="${user.lastName}"/></td>
                    </tr>
                    <tr>
                        <td>Phone number:</td>
                        <td><input id="phone-number" type="text" name="phone-number" value="${user.phoneNumber}"/></td>
                    </tr>
                    <tr>
                        <td>Main address:</td>
                        <td><input id="main-address" type="text" name="main-address" value="${user.mainAddress}"/></td>
                    </tr>
                    <tr>
                        <td>Bank:</td>
                        <td><input id="bank" type="text" name="bank" value="${user.bankName}"/></td>
                    </tr>
                    <tr>
                        <td>Bank account:</td>
                        <td><input id="bank-account" type="text" name="bank-account" value="${user.bankAccount}"/></td>
                    </tr>
                    <tr>
                        <th align="left">Balance:</th>
                    </tr>
                    <tr>
                        <td><input disabled id="balance" type="text" name="balance" value="${user.wallet.money.toString()}"/></td>
                    </tr>
                </table>
                <input type="submit" value="Apply changes">
            </form>
        </section>
    </div>
</t:authorizedpage>