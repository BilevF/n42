
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 User</title>
</head>
<body>
    <jsp:include page="parts/navbar.jsp"/>
    <div class="container">
        <form:form action="/addUser" method="post" modelAttribute="user">
            <div class="form-group">
                <form:label for="firstName" path="firstName">First name</form:label>
                <form:input type="text" path="firstName" class="form-control" id="firstName"/>
            </div>

            <div class="form-group">
                <form:label for="lastName" path="lastName">Last name</form:label>
                <form:input type="text" min="0" path="lastName" class="form-control" id="lastName"/>
            </div>

            <div class="form-group">
                <form:label for="birthDate" path="birthDate">Birth date</form:label>
                <form:input type="date" path="birthDate" class="form-control" id="birthDate"/>
            </div>

            <div class="form-group">
                <form:label for="address" path="address">Address</form:label>
                <form:input type="text" path="address" class="form-control" id="address" placeholder="info"/>
            </div>

            <div class="form-group">
                <form:label for="passport" path="passport">Passport</form:label>
                <form:input type="text" path="passport" class="form-control" id="passport" placeholder="info"/>
            </div>

            <div class="form-group">
                <form:label for="email" path="email">Email</form:label>
                <form:input type="text" path="email" class="form-control" id="email" placeholder="info"/>
            </div>

            <div class="form-group">
                <form:label for="password" path="password">Email</form:label>
                <form:input type="password" path="password" class="form-control" id="password" placeholder="info"/>
            </div>

            <button type="submit" class="btn btn-primary">Save user</button>
        </form:form>

    </div>
    <jsp:include page="parts/footer.jsp"/>
</body>
</html>
