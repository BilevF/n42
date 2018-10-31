<%--
  Created by IntelliJ IDEA.
  User: FedorBilev
  Date: 10/30/2018
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>Tariff </title>
</head>
<body>

    <jsp:include page="parts/navbar.jsp"/>
    <div class="container">

        <jsp:include page="parts/welcom.jsp">
            <jsp:param name="name" value="${tariff.name}"/>
            <jsp:param name="massage" value="Price: ${tariff.price}. Info: ${tariff.info}"/>
        </jsp:include>

        <div class="card-columns">

            <div class="card text-center bg-light border-light mb-3">
                <div class="card-header"><h4><b>New option</b></h4></div>
                <div class="card-body">
                    <form action="/newOption">
                        <input name="tariffId" type="hidden" value="${tariff.id}">
                        <input type="submit" class="form-control btn btn-success" value="Manage">
                    </form>
                </div>
            </div>

            <c:forEach items="${tariff.options}" var="option">
                <div class="card text-center bg-light border-light mb-3">
                    <div class="card-header"><h4><b>${option.name}</b></h4></div>
                    <div class="card-body">
                        <p class="card-text">Price: ${option.price}</p>
                        <p class="card-text">Info: ${option.info}</p>
                        <form action="/removeOption" method="post">
                            <input name="optionId" type="hidden" value="${option.id}">
                            <input type="submit" class="form-control btn btn-success" value="Remove">
                        </form>
                        <a href="#" class="btn">Manage</a>
                    </div>
                </div>
            </c:forEach>

        </div>

    </div>
    <jsp:include page="parts/footer.jsp"/>

</body>
</html>
