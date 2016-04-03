<%@tag description="User Page template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
    <jsp:attribute name="header">
      <h1>GorKomTrans</h1>
    </jsp:attribute>
    <jsp:attribute name="footer">
      <p id="copyright">Created by Omarov Anuar. Karaganda. 2016.</p>
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</t:genericpage>