
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Create tariff</title>
</head>
<body class="bg-light">
    <jsp:include page="parts/navbar.jsp"/>
    <div class="container" style="max-width: 960px;">

        <c:if test="${not empty exception}">
            <div class="alert alert-danger" role="alert">
                    ${exception}
            </div>
        </c:if>

        <form:form action="/newTariff" method="post" modelAttribute="tariff">
            <spring:bind path="name">
                <div class="form-group">
                    <form:label for="tariff_name" path="name">Tariff name</form:label>
                    <form:input type="text" path="name" class="form-control ${status.error ? 'is-invalid' : ''}" id="tariff_name" placeholder="name"/>
                    <div class="invalid-feedback">
                        <form:errors path="name" cssClass="error"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="price">
                <div class="form-group">
                    <form:label for="tariff_price" path="price">Tariff price</form:label>
                    <form:input type="number" min="0" path="price" class="form-control ${status.error ? 'is-invalid' : ''}" id="tariff_price" placeholder="100.0"/>
                    <div class="invalid-feedback">
                        <form:errors path="price" cssClass="error"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="info">
                <div class="form-group">
                    <form:label for="tariff_info" path="info">Tariff info</form:label>
                    <form:input type="text" path="info" class="form-control ${status.error ? 'is-invalid' : ''}" id="tariff_info" placeholder="info"/>
                    <div class="invalid-feedback">
                        <form:errors path="info" cssClass="error"/>
                    </div>
                </div>
            </spring:bind>


            <div class="form-group">
                <div class="btn-group btn-group-toggle" data-toggle="buttons">
                    <label class="btn btn-secondary active">
                        <input type="radio" name="valid" value="true" autocomplete="off" checked> ACTIVE
                    </label>
                    <label class="btn btn-secondary">
                        <input type="radio" name="valid" value="false" autocomplete="off"> BLOCKED
                    </label>
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Save tariff</button>
        </form:form>
    </div>
    <jsp:include page="parts/footer.jsp"/>
</body>
</html>
