
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Available options"/>
    </jsp:include>
</head>
<body class="bg-light">

    <jsp:include page="parts/navbar.jsp">
        <jsp:param name="balance" value="${contract.userBalance}"/>
    </jsp:include>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Phone number: ${contract.phoneNumber}"/>
        <jsp:param name="secondName" value="Balance: ₽${contract.userBalance}"/>
        <jsp:param name="message" value="<p>Welcome to the home page of available options</p>"/>

    </jsp:include>

    <div class="container">

        <div id="exception"></div>

        <div id="main">

        <c:if test="${contract.blockType == 'NON'}">

            <div class="row">

                <div class="col-xs-12 col-sm-8 col-lg-8">

                    <h2 class="mb-3">Available options</h2>

                    <div class="card-columns mb-3 text-center">
                        <c:forEach items="${availableOptions}" var="option">

                            <div class="card mb-4 shadow-sm">
                                <div class="card-body">
                                    <h4 class="card-subtitle">${option.name}</h4>
                                    <h1 class="card-title pricing-card-title">₽${option.price} <small class="text-muted">/ mo</small></h1>
                                    <p class='card-text'>${option.info}</p>
                                    <p class='card-text'>Connection price: ₽${option.connectionPrice}</p>
                                    <button onclick="addToBasket(${contract.id}, ${option.id})" class="btn btn-primary">Add</button>
                                </div>
                            </div>
                        </c:forEach>

                        <c:if test="${availableOptions.size() == 0}">
                            <p align="left">No options available</p>
                        </c:if>
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

                    <c:choose>
                    <c:when test = "${contract.basket != null && contract.basket.size() > 0}">
                        <c:set var="total" value="${0}"/>
                        <ul class="list-group mb-3">
                            <c:forEach items="${contract.basket}" var="option">
                                <li class="list-group-item d-flex justify-content-between lh-condensed">
                                    <div>
                                        <h4 class="my-0">${option.name}</h4>
                                        <small class="text-muted">${option.info}</small>
                                    </div>
                                    <span class="text-muted">₽${option.connectionPrice}
                                        <small class="text-muted">
                                            <button onclick="removeFromBasket(${contract.id}, ${option.id})" class="btn btn-link btn-xs">Remove</button>
                                        </small>
                                    </span>
                                    <c:set var="total" value="${total + option.connectionPrice}" />
                                </li>
                            </c:forEach>

                            <li class="list-group-item d-flex justify-content-between">
                            <span>Total (RUB)</span>
                            <strong>₽${total}</strong>
                            </li>
                        </ul>

                        <div class="btn-group" role="group" aria-label="basket btn">
                            <button onclick="submitBasket(${contract.id})" class="btn btn-primary">Submit</button>
                            <button onclick="clearBasket(${contract.id})" class="btn btn btn-secondary">Clear</button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <span class="text-muted">Your cart is empty</span>
                    </c:otherwise>
                    </c:choose>

                </div>

            </div>
        </c:if>

        </div>

    </div>
    <jsp:include page="parts/footer.jsp"/>

    <script src="js/basket.js" type="text/javascript"></script>

</body>
</html>
