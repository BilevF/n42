

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Access denied"/>
    </jsp:include>
</head>
<body class="bg-light">

<jsp:include page="parts/navbar.jsp"/>

<jsp:include page="parts/welcom.jsp">
    <jsp:param name="name" value="Access denied"/>
    <jsp:param name="secondName" value=""/>
    <jsp:param name="message" value=""/>

</jsp:include>

</body>
</html>