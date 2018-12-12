
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Account"/>
    </jsp:include>
</head>
<body class="bg-light">

    <jsp:include page="parts/navbar.jsp">
        <jsp:param name="contentType" value="account"/>
        <jsp:param name="userId" value="${client.id}"/>
    </jsp:include>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Hello, ${client.firstName} ${client.lastName}!"/>
        <jsp:param name="message" value="<p>Welcome to the client's home page</p>"/>
        <jsp:param name="secondName" value="Balance: ₽${client.balance}"/>
    </jsp:include>

    <div class="container">

        <c:set var="title" value="Personal info"/>
        <sec:authorize access="hasRole('ROLE_CLIENT')">
            <c:set var="edit" value="<a href='user/update?userId=${client.id}' class='btn btn-link' role='button'><span class='glyphicon glyphicon-cog'></span></a>"/>
            <c:set var="title" value="Personal info ${edit}"/>
        </sec:authorize>

        <br>

        <jsp:include page="parts/info.jsp">
            <jsp:param name="imgPath" value="images/admin.png"/>

            <jsp:param name="title" value="${title}"/>

            <jsp:param name="name1" value="Email"/>
            <jsp:param name="name2" value="Birth date"/>
            <jsp:param name="name3" value="Passport"/>
            <jsp:param name="name4" value="Address"/>

            <jsp:param name="value1" value="${client.email}"/>
            <jsp:param name="value2" value="${client.birthDate}"/>
            <jsp:param name="value3" value="${client.passport}"/>
            <jsp:param name="value4" value="${client.address}"/>
            <jsp:param name="value4Style" value=""/>

        </jsp:include>

        <br>


        <c:if test="${client.contracts.size() > 0}">
        <jsp:include page="parts/separator.jsp">
            <jsp:param name="name" value="Contracts"/>
        </jsp:include>
        </c:if>

        <div class="card-columns mb-3 text-center" >

            <c:forEach items="${client.contracts}" var="contract">

                <c:set var="total" value="${0}"/>
                <c:forEach items="${contract.options}" var="option">
                    <c:set var="total" value="${total + option.price}" />
                </c:forEach>

                <jsp:include page="parts/priceCard.jsp">
                    <jsp:param name="title" value="${contract.phoneNumber}"/>
                    <jsp:param name="price" value="${contract.tariff.price + total}"/>
                    <jsp:param name="info" value="<p class='card-text' style='margin: 0px'>Tariff: ${contract.tariff.name}</p>
                                                  <p class='card-text'>${contract.tariff.info}</p>"/>
                    <jsp:param name="path" value="contract"/>
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
