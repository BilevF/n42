
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Account</title>
</head>
<body>
    <jsp:include page="parts/navbar.jsp"/>

    <div class="container">

        <jsp:include page="parts/welcom.jsp">
            <jsp:param name="name" value="Hello, ${client.firstName} ${client.lastName}!"/>
            <jsp:param name="massage" value="Birth date: ${client.birthDate}. Address: ${client.address}. Email: ${client.email}"/>
        </jsp:include>

        <div class="card-columns">

            <c:forEach items="${client.contracts}" var="contract">
                <div class="card text-center bg-light border-light mb-3">
                    <div class="card-header"><h4><b>${contract.phoneNumber}</b></h4></div>
                    <div class="card-body">
                        <p class="card-text"><b>${contract.tariff.name}</b></p>
                        <p class="card-text">${contract.tariff.info}</p>
                        <a href="#" class="btn">Manage</a>
                    </div>
                </div>
            </c:forEach>

        </div>

    </div>

    <jsp:include page="parts/footer.jsp"/>
</body>
</html>
