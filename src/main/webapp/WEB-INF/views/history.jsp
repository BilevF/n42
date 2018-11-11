
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Contract history</title>
</head>
<body class="bg-light">
    <jsp:include page="parts/navbar.jsp"/>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Contract history"/>
        <jsp:param name="message" value=""/>
        <jsp:param name="secondName" value=""/>
    </jsp:include>

    <div class="container" style="max-width: 960px;">

        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/account">Account</a></li>
                <li>
                    <a href="/contract?contractId=${contractId}">Contract</a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">History</li>
            </ol>
        </nav>

        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Price</th>
                <th scope="col">Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${history}" var="item">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.price}</td>
                    <td>${item.date}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>

    <jsp:include page="parts/footer.jsp"/>
</body>
</html>
