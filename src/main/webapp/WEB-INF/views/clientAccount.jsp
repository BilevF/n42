
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Account</title>
</head>
<body class="bg-light">
    <jsp:include page="parts/navbar.jsp"/>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Hello, ${client.firstName} ${client.lastName}!"/>
        <jsp:param name="message" value="<p>Welcome to the client's home page</p>"/>
        <jsp:param name="secondName" value=""/>
    </jsp:include>

    <div class="container" style="max-width: 960px;">

        <c:if test="${not empty exception}">
            <div class="alert alert-danger" role="alert">
                ${exception}
            </div>
        </c:if>

        <jsp:include page="parts/separator.jsp">
            <jsp:param name="name" value="Client info"/>
        </jsp:include>

        <jsp:include page="parts/info.jsp">
            <jsp:param name="imgPath" value="/resources/images/client2.png"/>

            <jsp:param name="name1" value="Email"/>
            <jsp:param name="name2" value="Birth date"/>
            <jsp:param name="name3" value="Passport"/>
            <jsp:param name="name4" value="Address"/>

            <jsp:param name="value1" value="${client.email}"/>
            <jsp:param name="value2" value="${client.birthDate}"/>
            <jsp:param name="value3" value="${client.passport}"/>
            <jsp:param name="value4" value="${client.address}"/>
            <jsp:param name="value4Style" value=""/>

            <jsp:param name="showBtn1" value="${false}"/>
            <jsp:param name="btn1Path" value="#"/>
            <jsp:param name="btn1HiddenName" value="userId"/>
            <jsp:param name="btn1HiddenValue" value="${client.id}"/>
            <jsp:param name="btn1Name" value="Edit"/>
            <jsp:param name="btn1Method" value="get"/>

            <jsp:param name="showBtn3" value="${false}"/>
            <jsp:param name="showBtn2" value="${false}"/>
            <jsp:param name="showBtn4" value="${false}"/>
        </jsp:include>

        <br>


        <jsp:include page="parts/separator.jsp">
            <jsp:param name="name" value="Contracts"/>
        </jsp:include>

        <div class="card-columns mb-3 text-center">

            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <jsp:include page="parts/basicCard.jsp">
                    <jsp:param name="title" value="New contract"/>
                    <jsp:param name="body" value=""/>
                    <jsp:param name="path" value="/newContract"/>
                    <jsp:param name="method" value="get"/>
                    <jsp:param name="hiddenName1" value="userId"/>
                    <jsp:param name="hiddenValue1" value="${client.id}"/>
                    <jsp:param name="hiddenName2" value=""/>
                    <jsp:param name="hiddenValue2" value=""/>
                    <jsp:param name="btnName" value="Add"/>
                </jsp:include>
            </sec:authorize>

            <c:forEach items="${client.contracts}" var="contract">
                <jsp:include page="parts/priceCard.jsp">
                    <jsp:param name="title" value="${contract.phoneNumber}"/>
                    <jsp:param name="price" value="${contract.tariff.price}"/>
                    <jsp:param name="info" value="<p class='card-text'>Tariff: ${contract.tariff.name}</p>
                                                  <p class='card-text'>Balance: ${contract.balance}</p>"/>
                    <jsp:param name="path" value="/contract"/>
                    <jsp:param name="method" value="get"/>
                    <jsp:param name="showBtn" value="${true}"/>
                    <jsp:param name="hiddenName1" value="contractId"/>
                    <jsp:param name="hiddenValue1" value="${contract.id}"/>
                    <jsp:param name="hiddenName2" value=""/>
                    <jsp:param name="hiddenValue2" value=""/>
                    <jsp:param name="btnName" value="Manage"/>
                    <jsp:param name="btnStyle" value="btn-primary"/>
                </jsp:include>
            </c:forEach>

        </div>

    </div>

    <jsp:include page="parts/footer.jsp"/>
</body>
</html>
