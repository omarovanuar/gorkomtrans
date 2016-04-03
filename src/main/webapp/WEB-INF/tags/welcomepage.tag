<%@tag description="User Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
    <jsp:attribute name="header">
        <div id="welcome-header" width="">
            <h1>GorKomTrans</h1>
        </div>
    </jsp:attribute>
    <jsp:attribute name="footer">
        <div id="welcome-footer">
            <p id="copyright">Created by Omarov Anuar. Karaganda. 2016.</p>
        </div>
    </jsp:attribute>
    <jsp:body>
        <div id="welcome-body">
            <jsp:doBody/>
        </div>
    </jsp:body>
</t:genericpage>