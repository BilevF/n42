
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Tariffs</title>
</head>
<body>
    <jsp:include page="parts/navbar.jsp"/>
    <div class="container">

        <div class="card-columns">

            <c:forEach items="${tariffs}" var="tariff">
                <div class="card text-center bg-light border-light mb-3">
                    <div class="card-header"><h4><b>${tariff.name}</b></h4></div>
                    <div class="card-body">
                        <p class="card-text">Price: ${tariff.price}</p>
                        <p class="card-text">${tariff.info}</p>
                        <form action="/tariff">
                            <input name="tariffId" type="hidden" value="${tariff.id}">
                            <input type="submit" class="form-control btn btn-primary" value="Manage">
                        </form>
                    </div>
                </div>
            </c:forEach>

        </div>

    </div>
    <jsp:include page="parts/footer.jsp"/>
</body>
</html>