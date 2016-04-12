<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:authorizedpage>
    <section class="service">
        <form action="<c:url value="/do/tech-spec"/>" method="post">
            <ul>
                <p align="center">
                    Choose types of containers(if you choose non-standard type, please specify it's quantity)
                </p>
                <li>
                    <input id="euro-type" type="checkbox" name="euro-type" value="1">Euro container
                </li>
                <li>
                    <input id="standard-type" type="checkbox" name="standard-type" value="1">Standard container
                </li>
                <li>
                    Non-standard container
                    <select id="non-standard-type-number" name="non-standard-type-number">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select>
                </li>
                <li>
                    <input type="submit" value="Start">
                </li>
            </ul>
        </form>
    </section>
</t:authorizedpage>