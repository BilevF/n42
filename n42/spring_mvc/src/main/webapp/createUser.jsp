
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

        <input type="hidden" name="id" class="form-control" id="id"/>

        <div class="form-group">
            <label for="firstName">First name</label>
            <input type="text" name="firstName" class="form-control" id="firstName"/>
            <div class="invalid-feedback">
                <span id="firstName_error"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="lastName">Last name</label>
            <input type="text" min="0" name="lastName" class="form-control" id="lastName"/>
            <div class="invalid-feedback">
                <span id="lastName_error"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="birthDate">Birth date</label>
            <input type="date" name="birthDate" class="form-control" id="birthDate"/>
            <div class="invalid-feedback">
                <span id="birthDate_error"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="address">Address</label>
            <input type="text" name="address" class="form-control" id="address" placeholder=""/>
            <div class="invalid-feedback">
                <span id="address_error"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="passport">Passport</label>
            <input type="text" name="passport" class="form-control" id="passport" placeholder=""/>
            <div class="invalid-feedback">
                <span id="passport_error"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="text" name="email" autocomplete="false" class="form-control" id="email" placeholder=""/>
            <div class="invalid-feedback">
                <span id="email_error"></span>
            </div>
        </div>

        <%--<div class="form-group">--%>
            <%--<label for="password">Password</label>--%>
            <%--<input type="password" name="password" autocomplete="false" class="form-control" id="password" placeholder=""/>--%>
            <%--<div class="invalid-feedback">--%>
                <%--<span id="password_error"></span>--%>
            <%--</div>--%>
        <%--</div>--%>

        <input type="hidden" name="password" autocomplete="false" class="form-control" id="password" placeholder=""/>

        <button onclick="editUser()" class="btn btn-primary">Save</button>
    </div>

    <jsp:include page="parts/footer.jsp"/>

    <script src="js/editForms.js" type="text/javascript"></script>
    <script src="js/editUser.js" type="text/javascript"></script>
</body>
</html>
