
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Add Money</title>
</head>
<body class="bg-light">
    <jsp:include page="parts/navbar.jsp"/>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Phone number: ${contract.phoneNumber}"/>
        <jsp:param name="secondName" value="Balance: $${contract.balance}"/>
        <jsp:param name="message" value="<p>On this page you can add funds to your account</p>"/>
    </jsp:include>

    <div class="container" style="max-width: 960px;">

        <c:if test="${not empty exception}">
        <div class="alert alert-danger" role="alert">
                ${exception}
        </div>
        </c:if>

        <form action="/addMoney" method="post">
            <div class="input-group">
                <input type="number" min="0" class="form-control" name="moneyValue" placeholder="Phone">
                <input name="contractId" type="hidden" value="${contract.id}">
                <span class="input-group-btn">
                    <button class="btn btn-primary" type="submit">Add</button>
                </span>
            </div>
        </form>

    </div>

    <jsp:include page="parts/footer.jsp"/>

</body>
</html>
