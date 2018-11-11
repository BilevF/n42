
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Tariffs</title>
</head>
<body class="bg-light">
    <jsp:include page="parts/navbar.jsp"/>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="${title}"/>
        <jsp:param name="message" value="<p>Welcome to the tariff's home page</p>"/>
        <jsp:param name="secondName" value=""/>
    </jsp:include>

    <div class="container" style="max-width: 960px;">

        <c:if test="${not empty exception}">
            <div class="alert alert-danger" role="alert">
                    ${exception}
            </div>
        </c:if>

        <div class="card-columns mb-3 text-center">

            <c:forEach items="${tariffs}" var="tariff">
                <jsp:include page="parts/priceCard.jsp">
                    <jsp:param name="title" value="${tariff.name}"/>
                    <jsp:param name="price" value="${tariff.price}"/>
                    <jsp:param name="info" value="<p class='card-text'>Info: ${tariff.info}</p>"/>
                    <jsp:param name="path" value="${path}"/>
                    <jsp:param name="method" value="${method}"/>
                    <jsp:param name="showBtn" value="${showBtn}"/>
                    <jsp:param name="hiddenName1" value="tariffId"/>
                    <jsp:param name="hiddenValue1" value="${tariff.id}"/>
                    <jsp:param name="hiddenName2" value="${hiddenName}"/>
                    <jsp:param name="hiddenValue2" value="${hiddenValue}"/>
                    <jsp:param name="btnName" value="${btnName}"/>
                    <jsp:param name="btnStyle" value="btn-primary"/>
                </jsp:include>
            </c:forEach>

        </div>

    </div>
    <jsp:include page="parts/footer.jsp"/>
</body>
</html>