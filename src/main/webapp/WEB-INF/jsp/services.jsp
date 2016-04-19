<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:authorizedpage>
    <section class="service">
        <div align="center">
            <img id="gkt-service" src="/images/service.jpg" height="400" width="600">
        </div>
        <p>Need It Removed? You've come to the right place...</p>
        <p>Removing junk can be a burdon. Why not let us do all the dirty work? Our junk removal crews are ready and able to dive in and explore the unknown. That's right, we will remove junk from under your home, in the attic, buried in the shed or anywhere else you would rather not go.</p>
        <p>You name it and we haul it. ----- You say, "junk." We say, "how high?". . . OK, you get the idea!</p>

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