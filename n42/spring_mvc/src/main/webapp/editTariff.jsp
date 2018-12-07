
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Create tariff"/>
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
            <label for="name">Tariff name</label>
            <input type="text" name="name" class="form-control" id="name" placeholder="name"/>
            <div class="invalid-feedback">
                <span id="name_error"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="price">Tariff price</label>
            <input type="number" min="0" name="price" class="form-control" id="price" placeholder="100.0"/>
            <div class="invalid-feedback">
                <span id="price_error"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="info">Tariff info</label>
            <input type="text" name="info" class="form-control" id="info" placeholder="info"/>
            <div class="invalid-feedback">
                <span id="info_error"></span>
            </div>
        </div>

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

        <button onclick="editTariff()" class="btn btn-primary">Save tariff</button>

    </div>
    <jsp:include page="parts/footer.jsp"/>

    <script src="js/editForms.js" type="text/javascript"></script>
    <script src="js/editTariff.js" type="text/javascript"></script>
</body>
</html>
