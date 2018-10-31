<%--
  Created by IntelliJ IDEA.
  User: FedorBilev
  Date: 10/30/2018
  Time: 8:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Option</title>
</head>
<body>
    <jsp:include page="parts/navbar.jsp"/>
    <div class="container">
        <form:form action="/addOption" method="post" modelAttribute="option">

            <div class="form-group">
                <form:label for="option_name" path="name">option name</form:label>
                <form:input type="text" path="name" class="form-control" id="option_name" placeholder="name"/>
            </div>

            <div class="form-group">
                <form:label for="option_price" path="price">option price</form:label>
                <form:input type="number" min="0" path="price" class="form-control" id="option_price" placeholder="100.0"/>
            </div>

            <div class="form-group">
                <form:label for="option_connection_price" path="connectionPrice">option connection price</form:label>
                <form:input type="number" min="0" path="connectionPrice" class="form-control" id="option_connection_price" placeholder="100.0"/>
            </div>

            <div class="form-group">
                <form:label for="option_info" path="info">option info</form:label>
                <form:input type="text" path="info" class="form-control" id="option_info" placeholder="info"/>
            </div>

            <form:input path="tariff.id" type="hidden" value="${option.tariff.id}"/>
            <form:input path="tariff.info" type="hidden" value="${option.tariff.id}"/>
            <form:input path="tariff.price" type="hidden" value="${option.tariff.id}"/>
            <form:input path="tariff.name" type="hidden" value="${option.tariff.id}"/>

            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Connection price</th>
                    <th scope="col">Info</th>
                    <th scope="col">Option</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${option.tariff.options}" var="option" varStatus="i">
                    <input name="tariff.options[${i.index}].id" type="hidden" value="${option.id}"/>
                    <tr>
                        <td>${option.name}</td>
                        <td>${option.price}</td>
                        <td>${option.connectionPrice}</td>
                        <td>${option.info}</td>
                        <td>
                            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-secondary active">
                                    <input type="radio" name="tariff.options[${i.index}].selectedType" value="NON" autocomplete="off" checked> Non
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="tariff.options[${i.index}].selectedType" value="INCOMPATIBLE" autocomplete="off"> INCOMPATIBLE
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="tariff.options[${i.index}].selectedType" value="REQUIRED" autocomplete="off"> REQUIRED
                                </label>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <button type="submit" class="btn btn-primary">Save option</button>
        </form:form>
    </div>
    <jsp:include page="parts/footer.jsp"/>
</body>
</html>
