
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Contract ${contract.phoneNumber}"/>
    </jsp:include>
</head>
<body class="bg-light">

    <jsp:include page="parts/navbar.jsp"/>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Phone number: ${contract.phoneNumber}"/>
        <jsp:param name="secondName" value="Balance: $${contract.balance}"/>
        <jsp:param name="message" value="<p>Welcome to the home page of contract management</p>"/>

    </jsp:include>

    <div class="container" style="max-width: 960px;">

        <c:if test="${not empty exception}">
            <div class="alert alert-danger" role="alert">
                    ${exception}
            </div>
        </c:if>

        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="breadcrumb-item"><a href="/user?userId=${contract.userId}">Client</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_CLIENT')">
                    <li class="breadcrumb-item"><a href="/account">Account</a></li>
                </sec:authorize>
                <li class="breadcrumb-item active" aria-current="page">Contract</li>
            </ol>
        </nav>

        <jsp:include page="parts/separator.jsp">
            <jsp:param name="name" value="Contract info"/>
        </jsp:include>

        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <c:set var="showBtn" value="${true}"/>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_CLIENT')">
            <c:set var="showBtn" value="${contract.blockType != 'ADMIN_BLOCK'}"/>
        </sec:authorize>

        <c:choose>
            <c:when test="${contract.blockType == 'NON'}">
                <c:set var="activeStatus" value="Active"/>
                <c:set var="activeBtn" value="Block"/>
                <c:set var="activeStyle" value="color: green;"/>
            </c:when>
            <c:otherwise>
                <c:set var="activeStatus" value="Blocked"/>
                <c:set var="activeBtn" value="Activate"/>
                <c:set var="activeStyle" value="color: red;"/>
            </c:otherwise>
        </c:choose>

        <jsp:include page="parts/info.jsp">
            <jsp:param name="imgPath" value="/resources/images/user.png"/>

            <jsp:param name="name1" value="Tariff"/>
            <jsp:param name="name2" value="Info"/>
            <jsp:param name="name3" value="Price"/>
            <jsp:param name="name4" value="Contract"/>

            <jsp:param name="value1" value="${contract.tariff.name}"/>
            <jsp:param name="value2" value="${contract.tariff.info}"/>
            <jsp:param name="value3" value="$${contract.tariff.price} <small class='text-muted'>/ mo</small>"/>
            <jsp:param name="value4" value="${activeStatus}"/>
            <jsp:param name="value4Style" value="${activeStyle}"/>

            <jsp:param name="showBtn1" value="${contract.blockType == 'NON'}"/>
            <jsp:param name="btn1Path" value="/changeTariff"/>
            <jsp:param name="btn1HiddenName" value="contractId"/>
            <jsp:param name="btn1HiddenValue" value="${contract.id}"/>
            <jsp:param name="btn1Name" value="Change tariff"/>
            <jsp:param name="btn1Method" value="get"/>

            <jsp:param name="showBtn2" value="${contract.blockType == 'NON'}"/>
            <jsp:param name="btn2Path" value="/addMoney"/>
            <jsp:param name="btn2HiddenName" value="contractId"/>
            <jsp:param name="btn2HiddenValue" value="${contract.id}"/>
            <jsp:param name="btn2Name" value="Add money"/>
            <jsp:param name="btn2Method" value="get"/>

            <jsp:param name="showBtn3" value="${contract.histories.size() != 0}"/>
            <jsp:param name="btn3Path" value="/history"/>
            <jsp:param name="btn3HiddenName" value="contractId"/>
            <jsp:param name="btn3HiddenValue" value="${contract.id}"/>
            <jsp:param name="btn3Name" value="History"/>
            <jsp:param name="btn3Method" value="get"/>

            <jsp:param name="showBtn4" value="${showBtn}"/>
            <jsp:param name="btn4Path" value="/changeContractStatus"/>
            <jsp:param name="btn4HiddenName" value="contractId"/>
            <jsp:param name="btn4HiddenValue" value="${contract.id}"/>
            <jsp:param name="btn4Name" value="${activeBtn}"/>
            <jsp:param name="btn4Method" value="post"/>
        </jsp:include>

        <br>

        <c:if test="${contract.blockType == 'NON'}">

        <jsp:include page="parts/separator.jsp">
            <jsp:param name="name" value="Contract options"/>
        </jsp:include>

        <div class="card-columns mb-3 text-center">

            <jsp:include page="parts/basicCard.jsp">
                <jsp:param name="title" value="New option"/>
                <jsp:param name="body" value="<ul class='list-unstyled mt-3 mb-4'>
                        <li>Explore new options</li>
                        <li>Make the contract</li>
                        <li>more convenient for you!</li>
                    </ul>"/>
                <jsp:param name="path" value="/addNewOption"/>
                <jsp:param name="method" value="get"/>
                <jsp:param name="hiddenName1" value="contractId"/>
                <jsp:param name="hiddenValue1" value="${contract.id}"/>
                <jsp:param name="hiddenName2" value=""/>
                <jsp:param name="hiddenValue2" value=""/>
                <jsp:param name="btnName" value="Add"/>
            </jsp:include>

            <c:forEach items="${contract.options}" var="option">
                <jsp:include page="parts/priceCard.jsp">
                    <jsp:param name="title" value="${option.name}"/>
                    <jsp:param name="price" value="${option.price}"/>
                    <jsp:param name="info" value="<p class='card-text'>${option.info}</p>"/>
                    <jsp:param name="showBtn" value="${option.availableForRemove}"/>
                    <jsp:param name="path" value="/removeContractOption"/>
                    <jsp:param name="method" value="post"/>
                    <jsp:param name="hiddenName1" value="contractId"/>
                    <jsp:param name="hiddenValue1" value="${contract.id}"/>
                    <jsp:param name="hiddenName2" value="optionId"/>
                    <jsp:param name="hiddenValue2" value="${option.id}"/>
                    <jsp:param name="btnName" value="Remove"/>
                    <jsp:param name="btnStyle" value="btn-link"/>
                </jsp:include>
            </c:forEach>
        </div>

        </c:if>

    </div>
    <jsp:include page="parts/footer.jsp"/>

</body>
</html>
