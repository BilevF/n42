
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

    <div id="main">

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


    <jsp:include page="parts/navbar.jsp">
        <jsp:param name="contentType" value="contract"/>
        <jsp:param name="contractId" value="${contract.id}"/>
        <jsp:param name="blockName" value="${activeBtn}"/>
        <jsp:param name="showSelect" value="${contract.blockType == 'NON'}"/>
        <jsp:param name="showBlock" value="${showBtn}"/>
        <jsp:param name="balance" value="${contract.userBalance}"/>

    </jsp:include>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Phone number: ${contract.phoneNumber}"/>
        <jsp:param name="secondName" value="Balance: ₽${contract.userBalance}"/>
        <jsp:param name="message" value="<p>Welcome to the home page of contract management</p>"/>
    </jsp:include>

    <div class="container">

        <br>

        <jsp:include page="parts/info.jsp">
            <jsp:param name="imgPath" value="images/contract.png"/>


            <jsp:param name="title" value="Contract info"/>

            <jsp:param name="name1" value="Tariff"/>
            <jsp:param name="name2" value="Info"/>
            <jsp:param name="name3" value="Price"/>
            <jsp:param name="name4" value="Contract"/>

            <jsp:param name="value1" value="${contract.tariff.name}"/>
            <jsp:param name="value2" value="${contract.tariff.info}"/>
            <jsp:param name="value3" value="₽${contract.tariff.price} <small class='text-muted'>/ mo</small>"/>
            <jsp:param name="value4" value="${activeStatus}"/>
            <jsp:param name="value4Style" value="${activeStyle}"/>

        </jsp:include>

        <br>

        <c:if test="${contract.blockType == 'NON' && contract.options.size() > 0}">

        <jsp:include page="parts/separator.jsp">
            <jsp:param name="name" value="Contract options"/>
        </jsp:include>

        <div class="card-columns mb-3 text-center">

            <c:forEach items="${contract.options}" var="option">

                <div class="card mb-4 shadow-sm">
                    <div class="card-body">
                        <h4 class="card-subtitle">${option.name}</h4>
                        <h2 class="card-title pricing-card-title">₽${option.price} <small class="text-muted">/ mo</small></h2>
                        <p class='card-text'>${option.info}</p>
                        <c:if test="${option.availableForRemove}">
                            <button onclick="removeOption(${contract.id}, ${option.id})" class="btn btn-link">Remove</button>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>

        </c:if>

    </div>

    </div>

    <jsp:include page="parts/footer.jsp"/>

    <script src="js/contract.js" type="text/javascript"></script>


</body>
</html>
