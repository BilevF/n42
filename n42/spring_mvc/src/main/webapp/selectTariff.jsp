
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Tariffs"/>
    </jsp:include>
</head>
<body class="bg-light">

<jsp:include page="parts/navbar.jsp">
    <jsp:param name="balance" value="${contract.phoneNumber}"/>
</jsp:include>

<jsp:include page="parts/welcom.jsp">
    <jsp:param name="name" value="Select new tariff"/>
    <jsp:param name="message" value=""/>
    <jsp:param name="secondName" value=""/>

</jsp:include>

<div class="container">

    <div id="main">

    <div class="card-columns mb-3 text-center">

        <c:forEach items="${tariffs}" var="tariff">
            <div class="card mb-4 shadow-sm">
                <div class="card-body">
                    <h4 class="card-subtitle">${tariff.name}</h4>
                    <h1 class="card-title pricing-card-title">$${tariff.price} <small class="text-muted">/ mo</small></h1>
                    <p class='card-text'>Info: ${tariff.info}</p>
                    <button onclick="replaceTariff(${tariff.id}, ${contract.id})" class="btn btn-primary">Select</button>
                </div>
            </div>
        </c:forEach>

        <c:if test="${tariffs.size() == 0}">
            <p align="left">No tariffs available</p>
        </c:if>
    </div>
    </div>

</div>

<jsp:include page="parts/footer.jsp"/>

<script src="js/selectTariff.js" type="text/javascript"></script>

</body>
</html>