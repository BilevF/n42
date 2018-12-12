
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Admin account"/>
    </jsp:include>

</head>
<body class="bg-light">

    <jsp:include page="parts/navbar.jsp">
        <jsp:param name="contentType" value=""/>
    </jsp:include>


    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Hello, ${admin.firstName} ${admin.lastName}!"/>
        <jsp:param name="message" value="<p>Welcome to the administration homepage</p>"/>
        <jsp:param name="secondName" value=""/>
    </jsp:include>

    <div class="container">

        <br>

        <c:set var="edit" value="<a href='user/update?userId=${admin.id}' class='btn btn-link' role='button'><span class='glyphicon glyphicon-cog'></span></a>"/>

        <jsp:include page="parts/info.jsp">
            <jsp:param name="imgPath" value="images/admin.png"/>

            <jsp:param name="title" value="Personal info ${edit}"/>

            <jsp:param name="name1" value="Email"/>
            <jsp:param name="name2" value="Birth date"/>
            <jsp:param name="name3" value="Passport"/>
            <jsp:param name="name4" value="Address"/>

            <jsp:param name="value1" value="${admin.email}"/>
            <jsp:param name="value2" value="${admin.birthDate}"/>
            <jsp:param name="value3" value="${admin.passport}"/>
            <jsp:param name="value4" value="${admin.address}"/>
            <jsp:param name="value4Style" value=""/>

        </jsp:include>


    <jsp:include page="parts/footer.jsp"/>

    <script src="js/findUser.js" type="text/javascript"></script>

</body>
</html>
