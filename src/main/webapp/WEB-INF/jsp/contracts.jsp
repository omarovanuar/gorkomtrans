<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="contracts" type="java.util.List"--%>
<%--@elvariable id="item" type="com.epam.anuar.gorkomtrans.entity.Contract"--%>
<%--@elvariable id="noOfPages" type="java.lang.Integer"--%>
<%--@elvariable id="currentPage" type="java.lang.Integer"--%>

<t:authorizedpage>
    <div class="contract-list">
        <table>
            <tr>
                <th>Id</th>
                <th>Address</th>
                <th>Capacity</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Contract</th>
            </tr>
            <c:forEach var="item" items="${contracts}">
                <tr>
                    <td>${item.id.toString()}</td>
                    <td>${item.garbageTechSpecification.address}</td>
                    <td>${item.contractTotalCapacityString}</td>
                    <td>${item.contractAmount}</td>
                    <td>${item.status.toString()}</td>
                    <td>
                        <form action="<c:url value="/do/contract-view"/>" method="post">
                            <p>
                                <input type="hidden" name="current-contract" value="${item.id.toString()}">
                                <input type="submit" value="View">
                            </p>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${currentPage != 1}">
            <div id="next"><a href="${pageContext.request.contextPath}/do/contracts?page=${currentPage - 1}">Previous</a></div>
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
            <div id="prev"><a href="${pageContext.request.contextPath}/do/contracts?page=${currentPage + 1}">Next</a></div>
        </c:if>
    </div>
</t:authorizedpage>
