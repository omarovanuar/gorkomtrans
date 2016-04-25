<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="euro" type="java.lang.String"--%>
<%--@elvariable id="standard" type="java.lang.String"--%>
<%--@elvariable id="nonStandardNumber" type="java.lang.String"--%>
<%--@elvariable id="listError" type="java.util.List"--%>


<t:authorizedpage>

    <section class="tech-spec">
        <form action="<c:url value="/do/contract"/>" method="post">
            <p align="center">
                Please, fill all fields to create a new contract
            </p>
            <table>
                <tr>
                    <td>Address:</td>
                    <td><input id="tech-address" type="text" name="tech-address" placeholder="Address" value="${paramValues.get("tech-address")[0]}"></td>
                    <td><div>${listError.get(0)}</div></td>
                </tr>
                <c:forEach var="currentEuro" begin="1" end="${euro}">
                <tr>
                    <td>Euro container:</td>
                    <td><input  id="euro" name="euro" type="text" placeholder="Container quantity" value="${paramValues.get("euro")[0]}"></td>
                    <td><div>${listError.get(1)}</div></td>
                </tr>
                </c:forEach>
                <c:forEach var="currentStandard" begin="1" end="${standard}">
                <tr>
                    <td>Standard container:</td>
                    <td><input id="standard" name="standard" type="text" placeholder="Container quantity" value="${paramValues.get("standard")[0]}"></td>
                    <td><div>${listError.get(2)}</div></td>
                </tr>
                </c:forEach>
                <c:forEach var="currentNonStandard" begin="1" end="${nonStandardNumber}">
                <tr>
                    <td>Non-standard container №${currentNonStandard}:</td>
                    <td><input id="container-number-${currentNonStandard.toString()}" type="text"
                           name="container-number-${currentNonStandard.toString()}" placeholder="Container quantity" value="${paramValues.get("container-number-".concat(currentNonStandard))[0]}"></td>
                    <td><input id="container-capacity-${currentNonStandard.toString()}" type="text"
                           name="container-capacity-${currentNonStandard.toString()}" placeholder="Container capacity" value="${paramValues.get("container-capacity-".concat(currentNonStandard))[0]}"></td>
                    <td class="non-standard-error">
                        <p><div>${listError.get(currentNonStandard + 2)}</div></p>
                        <p><div>${listError.get(currentNonStandard + 5)}</div></p>
                    </td>
                </tr>
                </c:forEach>
                <tr>
                    <td class="per-month"><p>How often remove the garbage</p><p>(how many times per month?)</p></td>
                    <td><input id="perMonth" type="text" name="perMonth" value="${paramValues.get("perMonth")[0]}"></td>
                    <td><div>${listError.get(9)}</div></td>
                </tr>
                <tr>
                    <td>Providing month number</td>
                    <td><input id="providingMonthNumber" type="text" name="providingMonthNumber" value="${paramValues.get("providingMonthNumber")[0]}"></td>
                    <td><div>${listError.get(10)}</div></td>
                </tr>
                <tr>
                    <td><input id="submit" type="submit" value="Make a contract"></td>
                </tr>
            </table>
        </form>
    </section>
</t:authorizedpage>