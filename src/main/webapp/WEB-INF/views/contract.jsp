
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Contract</title>
</head>
<body>

    <jsp:include page="parts/navbar.jsp"/>
    <div class="container">

        <jsp:include page="parts/welcom.jsp">
            <jsp:param name="name" value="Phone number: ${contract.phoneNumber}"/>
            <jsp:param name="massage" value="Tariff: ${contract.tariff.name}. Balance: ${contract.balance}."/>
        </jsp:include>

        <div class="card-columns">

            <div class="card text-center bg-light border-light mb-3">
                <div class="card-header"><h4><b>New contract</b></h4></div>
                <div class="card-body">
                    <form action="/newContract">
                        <input name="userId" type="hidden" value="${client.id}">
                        <input type="submit" class="btn btn-primary" value="Add">
                    </form>
                </div>
            </div>

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

        <c:if test = "${contract.basket != null && contract.basket.size() > 0}">

            <div class="alert alert-dark" role="alert">
                Basket with ${contract.basket.size()} elements
            </div>

            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Info</th>
                    <th scope="col">Price</th>
                    <th scope="col">Connection price</th>
                    <th scope="col"> </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${contract.basket}" var="option">
                    <tr>
                        <td>${option.name}</td>
                        <td>${option.info}</td>
                        <td>${option.price}</td>
                        <td>${option.connectionPrice}</td>
                        <td>
                            <form action="/removeFromBasket">
                                <input name="contractId" type="hidden" value="${contract.id}">
                                <input name="optionId" type="hidden" value="${option.id}">
                                <input type="submit" class="form-control btn btn-primary" value="Remove">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </c:if>

        <div class="alert alert-dark" role="alert">
            Available Options
        </div>

        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Info</th>
                <th scope="col">Price</th>
                <th scope="col">Connection price</th>
                <th scope="col"> </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${availableOptions}" var="option">
                <tr>
                    <td>${option.name}</td>
                    <td>${option.info}</td>
                    <td>${option.price}</td>
                    <td>${option.connectionPrice}</td>
                    <td>
                        <form action="/removeFromBasket">
                            <input name="contractId" type="hidden" value="${contract.id}">
                            <input name="optionId" type="hidden" value="${option.id}">
                            <input type="submit" class="form-control btn btn-primary" value="Add">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
    <jsp:include page="parts/footer.jsp"/>

</body>
</html>
