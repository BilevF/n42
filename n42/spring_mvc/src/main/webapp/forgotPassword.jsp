<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Forgot password"/>
    </jsp:include>
</head>
<body class="bg-light">

<jsp:include page="parts/navbar.jsp"/>

<jsp:include page="parts/welcom.jsp">
    <jsp:param name="name" value="Forgot password?"/>
    <jsp:param name="secondName" value=""/>
    <jsp:param name="message" value=""/>

</jsp:include>

<div class="container">

    <div id="exception"></div>

    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" class="form-control" id="email" placeholder="example@mail.com">
    </div>

    <button onclick="sendPasswordLink()" class="btn btn-primary">Request new password</button>

</div>

<jsp:include page="parts/footer.jsp"/>

<script src="js/sha1.js" type="text/javascript"></script>

<script src="js/editForms.js" type="text/javascript"></script>
<script src="js/newPassword.js" type="text/javascript"></script>

</body>
</html>