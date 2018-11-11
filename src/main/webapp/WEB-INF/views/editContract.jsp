
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Edit contract</title>
</head>
<body class="bg-light">

    <jsp:include page="parts/navbar.jsp"/>
    <div class="container" style="max-width: 960px;">

        <c:if test="${not empty exception}">
            <div class="alert alert-danger" role="alert">
                    ${exception}
            </div>
        </c:if>

        <form:form action="/newContract" method="post" modelAttribute="contract">

            <input name="balance" type="hidden" value="${contract.balance}">
            <input name="userId" type="hidden" value="${contract.userId}">
            <input name="id" type="hidden" value="${contract.id}">
            <input name="blockType" type="hidden" value="${contract.blockType}">
            <spring:bind path="phoneNumber">
                <div class="form-group">
                    <form:label for="phoneNumber" path="phoneNumber">Phone number</form:label>
                    <form:input type="text" path="phoneNumber" class="form-control ${status.error ? 'is-invalid' : ''}" id="phoneNumber"/>
                    <div class="invalid-feedback">
                        <form:errors path="phoneNumber" cssClass="error"/>
                    </div>
                </div>
            </spring:bind>

            <div class="form-group">
                <label for="sel1">Select tariff:</label>
                <select class="form-control" id="sel1" name="tariff.id">
                    <c:forEach items="${tariffs}" var="tariff">
                        <option value="${tariff.id}">${tariff.name}</option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Save contract</button>
        </form:form>
    </div>
    <jsp:include page="parts/footer.jsp"/>

</body>
</html>
