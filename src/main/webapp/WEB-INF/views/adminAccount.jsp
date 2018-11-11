
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Admin account</title>
</head>
<body class="bg-light">

    <jsp:include page="parts/navbar.jsp"/>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Hello, ${admin.firstName} ${admin.lastName}!"/>
        <jsp:param name="massage" value="<p>Welcome to the admin's home page</p>"/>
        <jsp:param name="secondName" value=""/>
    </jsp:include>

    <div class="container" style="max-width: 960px;">

        <c:if test="${not empty exception}">
            <div class="alert alert-danger" role="alert">
                ${exception}
            </div>
        </c:if>

        <jsp:include page="parts/separator.jsp">
            <jsp:param name="name" value="Admin info"/>
        </jsp:include>

        <jsp:include page="parts/info.jsp">
            <jsp:param name="imgPath" value="/resources/images/admin.png"/>

            <jsp:param name="name1" value="Email"/>
            <jsp:param name="name2" value="Birth date"/>
            <jsp:param name="name3" value="Passport"/>
            <jsp:param name="name4" value="Address"/>

            <jsp:param name="value1" value="${admin.email}"/>
            <jsp:param name="value2" value="${admin.birthDate}"/>
            <jsp:param name="value3" value="${admin.passport}"/>
            <jsp:param name="value4" value="${admin.address}"/>
            <jsp:param name="value4Style" value=""/>

            <jsp:param name="showBtn1" value="${false}"/>
            <jsp:param name="btn1Path" value="#"/>
            <jsp:param name="btn1HiddenName" value="userId"/>
            <jsp:param name="btn1HiddenValue" value="${admin.id}"/>
            <jsp:param name="btn1Name" value="Edit"/>
            <jsp:param name="btn1Method" value="get"/>

            <jsp:param name="showBtn3" value="${false}"/>
            <jsp:param name="showBtn2" value="${false}"/>
            <jsp:param name="showBtn4" value="${false}"/>
        </jsp:include>

        <jsp:include page="parts/separator.jsp">
            <jsp:param name="name" value="Admin workspace"/>
        </jsp:include>

        <div class="card-deck text-center">

            <div class="card mb-4 shadow-sm card-body">
                <h3 class="card-title">Tariffs</h3>

                <p><small class='text-muted'>Create new tariffs or manage existing ones</small></p>

                <h3><a href="/newTariff" class="btn btn-primary btn-mg" style="margin-top: 12px">Create new</a>
                <a href="/tariffs" class="btn btn-primary btn-mg" style="margin-top: 12px">Show all</a></h3>
            </div>

            <div class="card mb-4 shadow-sm card-body">
                <h3 class="card-title">Find client</h3>

                <br>

                <form action="/findUser" method="post">
                    <div class="input-group">
                        <input type="text" class="form-control" name="phone" placeholder="Phone">
                        <span class="input-group-btn">
                                <button class="btn btn-primary" type="submit">Find client</button>
                            </span>
                    </div>
                </form>
            </div>

            <div class="card mb-4 shadow-sm card-body">
                <h3 class="card-title">Users</h3>
                <p><small class='text-muted'>Create new users or manage existing ones</small></p>
                <h3><a href="/newUser" class="btn btn-primary btn-mg " style="margin-top: 12px">Create new</a>
                <a href="/allUser" class="btn btn-primary btn-mg" style="margin-top: 12px">Show all</a></h3>
            </div>

        </div>


    </div>

    <jsp:include page="parts/footer.jsp"/>

</body>
</html>
