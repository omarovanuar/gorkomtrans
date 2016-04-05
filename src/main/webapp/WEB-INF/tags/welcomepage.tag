<%@tag description="Welcome Page template" pageEncoding="UTF-8" %>

<html>
<title>Authorization</title>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div id="welcome-header" align="center">
    <img src="/images/gkt.png">
</div>
<div id="welcome-body">
    <jsp:doBody/>
</div>
<div id="welcome-footer">
</div>
</body>
</html>