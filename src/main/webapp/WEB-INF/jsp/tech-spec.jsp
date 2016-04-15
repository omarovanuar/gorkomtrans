<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="euro" type="java.lang.String"--%>
<%--@elvariable id="standard" type="java.lang.String"--%>
<%--@elvariable id="nonStandardNumber" type="java.lang.String"--%>


<t:authorizedpage>

    <section class="tech-spec">
        <form action="<c:url value="/do/contract"/>" method="post">
            <ul>
                <p align="center">
                    Please, fill all fields to create a new contract
                </p>
                <li>Address:
                    <input id="tech-address" type="text" name="tech-address" placeholder="Address">
                </li>
                <c:forEach var="currentEuro" begin="1" end="${euro}">
                <li>Euro container:
                    <input  id="euro" name="euro" type="number" placeholder="Container quantity">
                </li>
                </c:forEach>
                <c:forEach var="currentStandard" begin="1" end="${standard}">
                <li>Standard container:
                    <input id="standard" name="standard" type="number" placeholder="Container quantity">
                </li>
                </c:forEach>
                <c:forEach var="currentNonStandard" begin="1" end="${nonStandardNumber}">
                <li>Non-standard container №${currentNonStandard}:
                    <input id="container-number-${currentNonStandard.toString()}" type="number"
                           name="container-number-${currentNonStandard.toString()}" placeholder="Container quantity">
                    <input id="container-capacity-${currentNonStandard.toString()}" type="text"
                           name="container-capacity-${currentNonStandard.toString()}" placeholder="Container capacity">
                </li>
                </c:forEach>
                <li>How often remove the garbage(how many times per month)?
                    <input id="providingMonthNumber" type="number" name="providingMonthNumber">
                </li>
                <li>Providing month number
                    <input id="perMonth" type="number" name="perMonth">
                </li>
                <li>
                    <input id="submit" type="submit" value="Make a contract">
                </li>
            </ul>
        </form>
    </section>
</t:authorizedpage>