
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Tariff ${tariff.name}"/>
    </jsp:include>
</head>
<body class="bg-light">

    <div id="main">
    <c:choose>
        <c:when test="${tariff.valid == true}">
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
        <jsp:param name="contentType" value="tariff"/>
        <jsp:param name="tariffId" value="${tariff.id}"/>
        <jsp:param name="blockName" value="${activeBtn}"/>
    </jsp:include>



    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="${tariff.name}"/>
        <jsp:param name="message" value="<p>Welcome to the tariff's home page</p>"/>
        <jsp:param name="secondName" value="₽${tariff.price} <small class='text-muted'>/ mo</small>"/>
    </jsp:include>

    <div class="container">

        <br>

        <jsp:include page="parts/info.jsp">
            <jsp:param name="imgPath" value="images/contract.png"/>

            <jsp:param name="title" value="Tariff info"/>

            <jsp:param name="name1" value="Name"/>
            <jsp:param name="name2" value="Info"/>
            <jsp:param name="name3" value="Clients"/>
            <jsp:param name="name4" value="Status"/>

            <jsp:param name="value1" value="${tariff.name}"/>
            <jsp:param name="value2" value="${tariff.info}"/>
            <jsp:param name="value3" value="${clientsCount}"/>
            <jsp:param name="value4" value="${activeStatus}"/>
            <jsp:param name="value4Style" value="${activeStyle}"/>
        </jsp:include>

        <c:if test="${tariff.options.size() > 0}">
        <jsp:include page="parts/separator.jsp">
            <jsp:param name="name" value="Tariff options"/>
        </jsp:include>
        </c:if>

        <div class="card-columns mb-3 text-center">

            <c:forEach items="${tariff.options}" var="option">

                <div class="card mb-4 shadow-sm">
                    <div class="card-body">
                        <h4 class="card-subtitle">${option.name}</h4>
                        <h1 class="card-title pricing-card-title">₽${option.price} <small class="text-muted">/ mo</small></h1>
                        <p class='card-text'>${option.info}</p>
                        <p class='card-text'>Connection price <b>₽${option.connectionPrice}</b></p>


                        <%----%>
                        <c:if test="${(option.requiredOptions != null && option.requiredOptions.size() != 0) ||
                                      (option.incompatibleOptions != null && option.incompatibleOptions.size() != 0) ||
                                      (option.incompatibleOptionsOf != null && option.incompatibleOptionsOf.size() != 0)}">
                            <div class="row">
                                <div class="col-sm">
                                    <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#multiCollapseExample${option.id}"
                                            aria-expanded="false" aria-controls="multiCollapseExample${option.id}"><u>Require</u></button>
                                </div>

                                <div class="col-sm">
                                    <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#multiCollapseExample${option.id}"
                                            aria-expanded="false" aria-controls="multiCollapseExample${option.id}"><u>Incompatible</u></button>
                                </div>
                            </div>
                        </c:if>
                        <div class="collapse multi-collapse" id="multiCollapseExample${option.id}">
                            <div class="card card-body">
                                <div class="row">

                                    <div class="col-sm">
                                        <c:if test="${option.requiredOptions != null && option.requiredOptions.size() != 0}">
                                            <ul class="list-group list-group-flush" style="margin-bottom: 5px">
                                                <c:forEach items="${option.requiredOptions}" var="rOption">
                                                    <li class="list-group-item">${rOption.name}</li>
                                                </c:forEach>
                                            </ul>
                                        </c:if>
                                    </div>

                                    <div class="col-sm">
                                        <c:if test="${(option.incompatibleOptions != null && option.incompatibleOptions.size() != 0) ||
                                                      (option.incompatibleOptionsOf != null && option.incompatibleOptionsOf.size() != 0)}">
                                            <ul class="list-group list-group-flush" style="margin-bottom: 5px">
                                                <c:forEach items="${option.incompatibleOptions}" var="iOption">
                                                    <li class="list-group-item">${iOption.name}</li>
                                                </c:forEach>
                                                <c:forEach items="${option.incompatibleOptionsOf}" var="iOption">
                                                    <li class="list-group-item">${iOption.name}</li>
                                                </c:forEach>
                                            </ul>
                                        </c:if>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <button onclick="removeOption(${option.id})" class="btn btn-link">Remove</button>

                    </div>
                </div>
            </c:forEach>
        </div>

    </div>

    </div>

    <jsp:include page="parts/footer.jsp"/>


    <script src="js/tariff.js" type="text/javascript"></script>

</body>
</html>
