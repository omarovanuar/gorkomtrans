<%@tag description="Welcome Page template" pageEncoding="UTF-8" %>

<html>
<title>Registration</title>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div id="welcome-header" align="center">
    <p>GorKomTrans garbage removal company</p>
</div>
<div id="welcome-body">
    <div id="welcome-center" align="center">
        <jsp:doBody/>
    </div>
</div>
<div id="welcome-footer">
</div>
</body>
</html>