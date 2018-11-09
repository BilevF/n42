
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Available options</title>
</head>
<body class="bg-light">

    <jsp:include page="parts/navbar.jsp"/>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Phone number: ${contract.phoneNumber}"/>
        <jsp:param name="secondName" value="Balance: ${contract.balance}"/>
        <jsp:param name="massage" value="<p>Welcome to the home page of available options</p>"/>

    </jsp:include>

    <div class="container" style="max-width: 960px;">

        <c:if test="${not empty exception}">
            <div class="alert alert-danger" role="alert">
                    ${exception}
            </div>
        </c:if>

        <c:if test="${contract.blockType == 'NON'}">

            <div class="row">

                <div class="col-xs-12 col-sm-8 col-lg-8">

                    <h2 class="mb-3">Available options</h2>

                    <div class="card-columns mb-3 text-center">
                        <c:forEach items="${availableOptions}" var="option">
                            <jsp:include page="parts/priceCard.jsp">
                                <jsp:param name="title" value="${option.name}"/>
                                <jsp:param name="price" value="${option.price}"/>
                                <jsp:param name="info" value="<p class='card-text'>${option.info}</p>
                                    <p class='card-text'>Connection price: ${option.connectionPrice}</p>"/>
                                <jsp:param name="path" value="/addToBasket"/>
                                <jsp:param name="method" value="post"/>
                                <jsp:param name="showBtn" value="${true}"/>
                                <jsp:param name="hiddenName1" value="contractId"/>
                                <jsp:param name="hiddenValue1" value="${contract.id}"/>
                                <jsp:param name="hiddenName2" value="optionId"/>
                                <jsp:param name="hiddenValue2" value="${option.id}"/>
                                <jsp:param name="btnName" value="Add"/>
                                <jsp:param name="btnStyle" value="btn-primary"/>
                            </jsp:include>
                        </c:forEach>
                    </div>

                </div>

                <div class="col-xs-12 col-sm-4">
                        <%----%>
                    <h2 class="d-flex justify-content-between align-items-center mb-3">
                        <span class="text-muted">Your cart</span>
                        <c:if test = "${contract.basket != null && contract.basket.size() > 0}">
                            <span class="badge badge-secondary badge-pill">${contract.basket.size()}</span>
                        </c:if>
                    </h2>
                    <c:if test = "${contract.basket != null && contract.basket.size() > 0}">
                        <ul class="list-group mb-3">
                            <c:forEach items="${contract.basket}" var="option">
                                <li class="list-group-item d-flex justify-content-between lh-condensed">
                                    <div>
                                        <h4 class="my-0">${option.name}</h4>
                                        <small class="text-muted">${option.info}</small>
                                    </div>
                                    <span class="text-muted">$${option.connectionPrice}
                                        <small class="text-muted">
                                            <form action="/removeFromBasket" method="post" style="margin-bottom: 0em">
                                                <input name="contractId" type="hidden" value="${contract.id}">
                                                <input name="optionId" type="hidden" value="${option.id}">
                                                <button type="submit" class="btn btn-link btn-xs">Remove</button>
                                            </form>
                                        </small>
                                    </span>
                                </li>
                            </c:forEach>

                                <%--<li class="list-group-item d-flex justify-content-between">--%>
                                <%--<span>Total (USD)</span>--%>
                                <%--<strong>$20</strong>--%>
                                <%--</li>--%>
                        </ul>

                        <div class="btn-group" role="group" aria-label="basket btn">
                            <form action="/submitBasket" method="post" style="display: inline;">
                                <input name="contractId" type="hidden" value="${contract.id}">
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </form>
                            <form action="/clearBasket" method="post" style="display: inline;">
                                <input name="contractId" type="hidden" value="${contract.id}">
                                <button type="submit" class="btn btn btn-danger">Clear</button>
                            </form>
                        </div>
                    </c:if>

                </div>

            </div>
        </c:if>

    </div>
    <jsp:include page="parts/footer.jsp"/>

</body>
</html>
