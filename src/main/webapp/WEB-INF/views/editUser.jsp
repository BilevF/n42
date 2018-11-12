
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 User</title>
</head>
<body class="bg-light">
    <jsp:include page="parts/navbar.jsp"/>
    <div class="container" style="max-width: 960px;">
        <c:if test="${not empty exception}">
            <div class="alert alert-danger" role="alert">
                ${exception}
            </div>
        </c:if>

        <form:form action="/newUser" method="post" modelAttribute="user">
            <spring:bind path="firstName">
                <div class="form-group">
                    <form:label for="firstName" path="firstName">First name</form:label>
                    <form:input type="text" path="firstName" class="form-control ${status.error ? 'is-invalid' : ''}" id="firstName"/>
                    <div class="invalid-feedback">
                        <form:errors path="firstName" cssClass="error"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="lastName">
                <div class="form-group">
                    <form:label for="lastName" path="lastName">Last name</form:label>
                    <form:input type="text" min="0" path="lastName" class="form-control ${status.error ? 'is-invalid' : ''}" id="lastName"/>
                    <div class="invalid-feedback">
                        <form:errors path="lastName" cssClass="error"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="birthDate">
                <div class="form-group">
                    <form:label for="birthDate" path="birthDate">Birth date</form:label>
                    <form:input type="date" path="birthDate" class="form-control ${status.error ? 'is-invalid' : ''}" id="birthDate"/>
                    <div class="invalid-feedback">
                        <form:errors path="birthDate" cssClass="error"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="address">
                <div class="form-group">
                    <form:label for="address" path="address">Address</form:label>
                    <form:input type="text" path="address" class="form-control ${status.error ? 'is-invalid' : ''}" id="address" placeholder=""/>
                    <div class="invalid-feedback">
                        <form:errors path="address" cssClass="error"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="passport">
                <div class="form-group">
                    <form:label for="passport" path="passport">Passport</form:label>
                    <form:input type="text" path="passport" class="form-control ${status.error ? 'is-invalid' : ''}" id="passport" placeholder=""/>
                    <div class="invalid-feedback">
                        <form:errors path="passport" cssClass="error"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="email">
                <div class="form-group">
                    <form:label for="email" path="email">Email</form:label>
                    <form:input type="text" path="email" autocomplete="false" class="form-control ${status.error ? 'is-invalid' : ''}" id="email" placeholder=""/>
                    <div class="invalid-feedback">
                        <form:errors path="email" cssClass="error"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="password">
                <div class="form-group">
                    <form:label for="password" path="password">Password</form:label>
                    <form:input type="password" path="password" autocomplete="false" class="form-control ${status.error ? 'is-invalid' : ''}" id="password" placeholder=""/>
                    <div class="invalid-feedback">
                        <form:errors path="password" cssClass="error"/>
                    </div>
                </div>
            </spring:bind>

            <button type="submit" class="btn btn-primary">Save user</button>
        </form:form>

    </div>
    <jsp:include page="parts/footer.jsp"/>
</body>
</html>
