
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Tariffs"/>
    </jsp:include>
</head>
<body class="bg-light">
    <jsp:include page="parts/navbar.jsp"/>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="${title}"/>
        <jsp:param name="message" value="<p>Welcome to the tariff's home page</p>"/>
        <jsp:param name="secondName" value=""/>
    </jsp:include>

    <div class="container">

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

            <c:if test="${tariffs.size() == 0}">
                <p align="left">No tariffs available</p>
            </c:if>
        </div>

    </div>
    <jsp:include page="parts/footer.jsp"/>
</body>
</html>