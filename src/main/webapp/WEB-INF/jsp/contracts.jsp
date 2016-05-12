<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="other-text" var="rb"/>

<t:authorizedpage>
    <div>
        <form action="<c:url value="/do/address-search"/>" method="post">
            <fmt:message key="contract.address" bundle="${rb}"/>:
            <input id="address-search" type="text" name="address-search"
                   placeholder="<fmt:message key="contract.address" bundle="${rb}"/>" value="${searchValue}">
            <input type="submit" value="<fmt:message key="action.search" bundle="${rb}"/>">
        </form>
    </div>
    <div class="contract-list">
        <table>
            <tr>
                <th>Id</th>
                <th><fmt:message key="contract.address" bundle="${rb}"/></th>
                <th><fmt:message key="contract.capacity" bundle="${rb}"/></th>
                <th><fmt:message key="contract.amount" bundle="${rb}"/></th>
                <th><fmt:message key="contract.status" bundle="${rb}"/></th>
                <th><fmt:message key="contract.sign-date" bundle="${rb}"/></th>
                <th><fmt:message key="contract.contract" bundle="${rb}"/></th>
                <th><fmt:message key="action.delete" bundle="${rb}"/></th>
            </tr>
            <c:forEach var="item" items="${contracts}">
                <tr>
                    <td>${item.id.toString()}</td>
                    <td>${item.garbageTechSpecification.address}</td>
                    <td>${item.contractTotalCapacityString}</td>
                    <td>${item.contractAmount}</td>
                    <c:choose>
                        <c:when test="${locale eq \"ru\"}">
                            <td>${item.status.ru}</td>
                        </c:when>
                        <c:otherwise>
                            <td>${item.status.toString()}</td>
                        </c:otherwise>
                    </c:choose>
                    <td>${item.signDateString}</td>
                    <td>
                        <form action="<c:url value="/do/contract-view"/>" method="post">
                            <p>
                                <input type="hidden" name="current-contract" value="${item.id.toString()}">
                                <input type="submit" value="<fmt:message key="action.view" bundle="${rb}"/>">
                            </p>
                        </form>
                    </td>
                    <td>
                        <form action="<c:url value="/do/contract-delete"/>" method="post">
                            <p>
                                <input type="hidden" name="current-contract" value="${item.id.toString()}">
                                <input
                                        <c:if test="${item.status.toString() eq \"AGREED\"}">disabled</c:if>
                                        type="submit" value="<fmt:message key="action.delete" bundle="${rb}"/>">
                            </p>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${currentPage != 1}">
            <div id="next"><a
                    href="${pageContext.request.contextPath}/do/contracts?page=${currentPage - 1}">Previous</a></div>
        </c:if>

        <div>
            <table id="contract-pagination" border="1" cellpadding="5" cellspacing="5">
                <tr>

                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="${pageContext.request.contextPath}/do/contracts?page=${i}">${i}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </table>
        </div>
        <c:if test="${currentPage lt noOfPages}">
            <div id="prev"><a href="${pageContext.request.contextPath}/do/contracts?page=${currentPage + 1}">Next</a>
            </div>
        </c:if>
    </div>
</t:authorizedpage>
