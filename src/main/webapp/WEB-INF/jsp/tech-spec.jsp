<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="other-text" var="rb"/>
<%--@elvariable id="euro" type="java.lang.String"--%>
<%--@elvariable id="standard" type="java.lang.String"--%>
<%--@elvariable id="nonStandardNumber" type="java.lang.String"--%>
<%--@elvariable id="listError" type="java.util.List"--%>


<t:authorizedpage>

    <section class="tech-spec">
        <form action="<c:url value="/do/contract"/>" method="post">
            <p align="center">
                <fmt:message key="tech-spec.main-text" bundle="${rb}"/>
            </p>
            <table>
                <tr>
                    <td><fmt:message key="tech-spec.address" bundle="${rb}"/>:</td>
                    <td><input id="tech-address" type="text" name="tech-address"
                               placeholder="<fmt:message key="tech-spec.address" bundle="${rb}"/>"
                               value="${paramValues.get("tech-address")[0]}"></td>
                    <td>
                        <div>${listError.get(0)}</div>
                    </td>
                </tr>
                <c:forEach var="currentEuro" begin="1" end="${euro}">
                    <tr>
                        <td><fmt:message key="tech-spec.euro-container" bundle="${rb}"/>:</td>
                        <td><input id="euro" name="euro" type="text"
                                   placeholder="<fmt:message key="tech-spec.quantity" bundle="${rb}"/>"
                                   value="${paramValues.get("euro")[0]}"></td>
                        <td>
                            <div>${listError.get(1)}</div>
                        </td>
                    </tr>
                </c:forEach>
                <c:forEach var="currentStandard" begin="1" end="${standard}">
                    <tr>
                        <td><fmt:message key="tech-spec.standard-container" bundle="${rb}"/>:</td>
                        <td><input id="standard" name="standard" type="text"
                                   placeholder="<fmt:message key="tech-spec.quantity" bundle="${rb}"/>"
                                   value="${paramValues.get("standard")[0]}"></td>
                        <td>
                            <div>${listError.get(2)}</div>
                        </td>
                    </tr>
                </c:forEach>
                <c:forEach var="currentNonStandard" begin="1" end="${nonStandardNumber}">
                    <tr>
                        <td><fmt:message key="tech-spec.non-standard-container" bundle="${rb}"/>
                            ¹${currentNonStandard}:
                        </td>
                        <td><input id="container-number-${currentNonStandard.toString()}" type="text"
                                   name="container-number-${currentNonStandard.toString()}"
                                   placeholder="<fmt:message key="tech-spec.quantity" bundle="${rb}"/>"
                                   value="${paramValues.get("container-number-".concat(currentNonStandard))[0]}"></td>
                        <td><input id="container-capacity-${currentNonStandard.toString()}" type="text"
                                   name="container-capacity-${currentNonStandard.toString()}"
                                   placeholder="<fmt:message key="tech-spec.capacity" bundle="${rb}"/>"
                                   value="${paramValues.get("container-capacity-".concat(currentNonStandard))[0]}"></td>
                        <td class="non-standard-error">
                            <p>
                            <div>${listError.get(currentNonStandard + 2)}</div>
                            </p>
                            <p>
                            <div>${listError.get(currentNonStandard + 5)}</div>
                            </p>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td class="per-month"><p><fmt:message key="tech-spec.per-month1" bundle="${rb}"/>:</p></td>
                    <td><input id="perMonth" type="text" name="perMonth"
                               placeholder="<fmt:message key="tech-spec.per-month2" bundle="${rb}"/>"
                               value="${paramValues.get("perMonth")[0]}"></td>
                    <td>
                        <div>${listError.get(9)}</div>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="tech-spec.providing-period" bundle="${rb}"/>:</td>
                    <td><input id="providingMonthNumber" type="text" name="providingMonthNumber"
                               placeholder="<fmt:message key="tech-spec.month-number" bundle="${rb}"/>"
                               value="${paramValues.get("providingMonthNumber")[0]}"></td>
                    <td>
                        <div>${listError.get(10)}</div>
                    </td>
                </tr>
                <tr>
                    <td><input id="submit" type="submit" value="<fmt:message key="tech-spec.button" bundle="${rb}"/>">
                    </td>
                </tr>
            </table>
        </form>
    </section>
</t:authorizedpage>