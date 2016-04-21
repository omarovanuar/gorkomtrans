<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="allUsers" type="java.util.List"--%>
<%--@elvariable id="item" type="com.epam.anuar.gorkomtrans.entity.User"--%>
<%--@elvariable id="noOfPages" type="java.lang.Integer"--%>
<%--@elvariable id="currentPage" type="java.lang.Integer"--%>

<t:authorizedpage>
    <div class="contract-list">
        <p align="center">Users:</p>
        <table>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Full name</th>
                <th>Email</th>
                <th>Role</th>
                <th>View</th>
                <th>Delete</th>
            </tr>
            <c:forEach var="item" items="${allUsers}">
                <tr>
                    <td>${item.id.toString()}</td>
                    <td>${item.login}</td>
                    <td>${item.fullName}</td>
                    <td>${item.email}</td>
                    <td>${item.role.toString()}</td>
                    <td>
                        <form action="<c:url value="/do/user-view"/>" method="post">
                            <p>
                                <input type="hidden" name="current-user" value="${item.id.toString()}">
                                <input type="submit" value="View">
                            </p>
                        </form>
                    </td>
                    <td>
                        <form action="<c:url value="/do/user-delete"/>" method="post">
                            <p>
                                <input type="hidden" name="current-user" value="${item.id.toString()}">
                                <input type="submit" value="Delete">
                            </p>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${currentPage != 1}">
            <div id="next"><a href="${pageContext.request.contextPath}/do/admin-cabinet?page=${currentPage - 1}">Previous</a></div>
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
                                <td><a href="${pageContext.request.contextPath}/do/admin-cabinet?page=${i}">${i}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </table>
        </div>
        <c:if test="${currentPage lt noOfPages}">
            <div id="prev"><a href="${pageContext.request.contextPath}/do/admin-cabinet?page=${currentPage + 1}">Next</a>
            </div>
        </c:if>
    </div>
</t:authorizedpage>