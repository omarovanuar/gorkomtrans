<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="userParamList" type="java.util.List"--%>
<%--@elvariable id="values" type="java.util.List"--%>
<%--@elvariable id="violations" type="java.util.List"--%>
<%--@elvariable id="user" type="com.epam.anuar.gorkomtrans.entity.User"--%>

<t:authorizedpage>
    <div id="personal-cabinet" align="left">

        <section class="user-data cf">
            <form action="<c:url value="/do/personal-cabinet"/>" method="post">
                <table class="user-data-table" id="user-data-table">
                    <tr>
                        <th align="left">User data:</th>
                        <input type="hidden" name="id" value="${user.id}"/>
                    </tr>
                    <c:forEach items="${userParamList}" var="userParam" varStatus="i">
                        <tr>
                            <td>${userParam}</td>
                                <td><input id="${userParam}" type="text" name="${userParam}" value="${values.get(i.index)}"/></td>
                            <td>${violations.get(i.index)}</td>
                        </tr>
                    </c:forEach>
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