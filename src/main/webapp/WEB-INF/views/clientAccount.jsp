
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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

            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <div class="card text-center bg-light border-light mb-3">
                    <div class="card-header"><h4><b>New contract</b></h4></div>
                    <div class="card-body">
                        <form action="/newContract">
                            <input name="userId" type="hidden" value="${client.id}">
                            <input type="submit" class="btn btn-primary" value="Add">
                        </form>
                    </div>
                </div>
            </sec:authorize>

            <c:forEach items="${client.contracts}" var="contract">
                <div class="card text-center bg-light border-light mb-3">
                    <div class="card-header"><h4><b>${contract.phoneNumber}</b></h4></div>
                    <div class="card-body">
                        <p class="card-text"><b>${contract.tariff.name}</b></p>
                        <p class="card-text">${contract.tariff.info}</p>
                        <p class="card-text">Balance: ${contract.balance}</p>
                        <form action="/contract">
                            <input name="contractId" type="hidden" value="${contract.id}">
                            <input type="submit" class="btn btn-primary" value="Manage">
                        </form>
                    </div>
                </div>
            </c:forEach>

        </div>

    </div>

    <jsp:include page="parts/footer.jsp"/>
</body>
</html>
