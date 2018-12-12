<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="New password"/>
    </jsp:include>
</head>
<body class="bg-light">

<jsp:include page="parts/navbar.jsp"/>

<jsp:include page="parts/welcom.jsp">
    <jsp:param name="name" value="Select new password"/>
    <jsp:param name="secondName" value=""/>
    <jsp:param name="message" value=""/>

</jsp:include>

    <div class="container">

        <div id="exception"></div>

        <div class="form-group">
            <label for="newPassword">Password:</label>
            <input type="password" class="form-control" minlength="3" id="newPassword">
        </div>

        <input type="hidden" class="form-control" id="userId" value="${userId}">
        <input type="hidden" class="form-control" id="token" value="${token}">

        <button onclick="setPassword()" class="btn btn-primary">Submit</button>

    </div>

    <jsp:include page="parts/footer.jsp"/>

    <script src="js/sha1.js" type="text/javascript"></script>

    <script src="js/editForms.js" type="text/javascript"></script>
    <script src="js/newPassword.js" type="text/javascript"></script>

</body>
</html>