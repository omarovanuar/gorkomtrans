<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="containerTypeNumber" type="java.lang.Integer"--%>
<%--@elvariable id="techspec" type="com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification"--%>
<%--@elvariable id="containerType" type="com.epam.anuar.gorkomtrans.entity.GarbageContainerType"--%>

<t:authorizedpage>
    <section class="tech-spec">
        <form action="<c:url value="/do/contract"/>" method="post">
            <ul>
                <p align="center">
                    Please, fill all fields of technical specification to create a new contract:
                </p>
                <li>Address:
                    <input id="tech-address" type="text" name="tech-address" value="${techspec.address}">
                </li>

                <c:forEach items="${containerTypeNumber}" var="containerTypeNumber">
                    <li>
                        <select id="tech-container">
                            <option disabled>Choose container type</option>
                            <option value="${containerType.containerCapacity}">Euro container</option>
                            <option value="${containerType.containerCapacity}">Standart container</option>
                            <option value="${containerType.containerCapacity}">Non-standart container</option>
                        </select>
                    </li>
                </c:forEach>
                <li>
                    <%--<a>${techspec.add()}</a>--%>
                </li>
            </ul>
        </form>
    </section>
</t:authorizedpage>