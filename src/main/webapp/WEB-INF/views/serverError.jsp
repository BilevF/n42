
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Error</title>
</head>
<body class="bg-light">

    <jsp:include page="parts/navbar.jsp"/>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Internal server error"/>
        <jsp:param name="secondName" value=""/>
        <jsp:param name="massage" value="We will be back soon>"/>

    </jsp:include>

</body>
</html>
