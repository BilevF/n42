
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Create tariff</title>
</head>
<body>
    <jsp:include page="parts/navbar.jsp"/>
    <div class="container">
        <form:form action="/addTariff" method="post" modelAttribute="tariff">
            <div class="form-group">
                <form:label for="tariff_name" path="name">Tariff name</form:label>
                <form:input type="text" path="name" class="form-control" id="tariff_name" placeholder="name"/>
            </div>

            <div class="form-group">
                <form:label for="tariff_price" path="name">Tariff price</form:label>
                <form:input type="number" min="0" path="price" class="form-control" id="tariff_price" placeholder="100.0"/>
            </div>

            <div class="form-group">
                <form:label for="tariff_info" path="name">Tariff info</form:label>
                <form:input type="text" path="info" class="form-control" id="tariff_info" placeholder="info"/>
            </div>

            <button type="submit" class="btn btn-primary">Save tariff</button>
        </form:form>
    </div>
    <jsp:include page="parts/footer.jsp"/>
</body>
</html>
