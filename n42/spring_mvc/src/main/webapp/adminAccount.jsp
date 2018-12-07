
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

        <jsp:include page="parts/info.jsp">
            <jsp:param name="imgPath" value="images/admin.png"/>

            <jsp:param name="title" value="Personal info"/>

            <jsp:param name="name1" value="Email"/>
            <jsp:param name="name2" value="Birth date"/>
            <jsp:param name="name3" value="Passport"/>
            <jsp:param name="name4" value="Address"/>

            <jsp:param name="value1" value="${admin.email}"/>
            <jsp:param name="value2" value="${admin.birthDate}"/>
            <jsp:param name="value3" value="${admin.passport}"/>
            <jsp:param name="value4" value="${admin.address}"/>
            <jsp:param name="value4Style" value=""/>

            <jsp:param name="showBtn1" value="${false}"/>
            <jsp:param name="btn1Path" value="#"/>
            <jsp:param name="btn1HiddenName" value="userId"/>
            <jsp:param name="btn1HiddenValue" value="${admin.id}"/>
            <jsp:param name="btn1Name" value="Edit"/>
            <jsp:param name="btn1Method" value="get"/>

            <jsp:param name="showBtn3" value="${false}"/>
            <jsp:param name="showBtn2" value="${false}"/>
            <jsp:param name="showBtn4" value="${false}"/>
        </jsp:include>


    <jsp:include page="parts/footer.jsp"/>

    <script src="js/findUser.js" type="text/javascript"></script>

</body>
</html>
