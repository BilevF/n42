
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="User"/>
    </jsp:include>
</head>
<body class="bg-light">

<jsp:include page="parts/navbar.jsp">
    <jsp:param name="contentType" value=""/>
</jsp:include>

<div class="container">

    <div id="exception"></div>

    <input type="hidden" name="id" class="form-control" id="id" value="${user.id}"/>

    <div class="form-group">
        <label for="firstName">First name</label>
        <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}" readonly>
    </div>

    <div class="form-group">
        <label for="lastName">Last name</label>
        <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}" readonly>
    </div>

    <div class="form-group">
        <label for="birthDate">Birth date</label>
        <input type="date" class="form-control" id="birthDate" name="birthDate" value="${user.birthDate}" readonly>
    </div>

    <div class="form-group">
        <label for="passport">Passport</label>
        <input type="text" class="form-control" id="passport" name="passport" value="${user.passport}" readonly>
    </div>

    <div class="form-group">
        <label for="address">Address</label>
        <input type="text" name="address" class="form-control" id="address" value="${user.address}"/>
        <div class="invalid-feedback">
            <span id="address_error"></span>
        </div>
    </div>

    <div class="form-group">
        <label for="email">Email</label>
        <input type="text" name="email" autocomplete="false" class="form-control" id="email" value="${user.email}"/>
        <div class="invalid-feedback">
            <span id="email_error"></span>
        </div>
    </div>

    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" name="password" autocomplete="false" class="form-control" id="password" value=""/>
        <div class="invalid-feedback">
            <span id="password_error"></span>
        </div>
    </div>

    <button onclick="updateUser()" class="btn btn-primary">Save</button>

</div>

<jsp:include page="parts/footer.jsp"/>

<script src="js/sha1.js" type="text/javascript"></script>

<script src="js/editForms.js" type="text/javascript"></script>
<script src="js/editUser.js" type="text/javascript"></script>

</body>
</html>