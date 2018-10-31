
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Admin account</title>
</head>
<body>

    <jsp:include page="parts/navbar.jsp"/>

    <div class="container ">

        <jsp:include page="parts/welcom.jsp">
            <jsp:param name="name" value="Hello, ${admin.firstName} ${admin.lastName}!"/>
            <jsp:param name="massage" value="Birth date: ${admin.birthDate}. Address: ${admin.address}. Email: ${admin.email}"/>
        </jsp:include>



        <div class="card">
            <div class="row">

                <div class="col-sm-5">
                    <img src="/resources/images/user.png" class="img-fluid" alt="">
                </div>

                <div class="col-sm-7">
                    <div class="card-block px-2" style="padding-bottom: 5%; margin-right: 5%">
                        <h2 style="margin-bottom: 20px"><b>Admin workspace</b></h2>
                        <h4>Manage users</h4>
                        <form>
                            <div class="input-group">
                                <input type="text" class="form-control" name="user_info" placeholder="Phone / Email">
                                <span class="input-group-btn">
                                    <button class="btn btn-primary" type="button">Find client</button>
                                </span>
                            </div>
                        </form>
                        <a href="#" class="btn btn-primary btn-mg" style="margin-top: 12px">Create new</a>
                        <a href="#" class="btn btn-light btn-mg" style="margin-top: 12px">Show all</a>
                        <hr class="my-4">
                        <h4>Manage tariffs</h4>
                        <form>
                            <div class="input-group">
                                <input type="text" class="form-control" name="user_info" placeholder="Tariff name">
                                <span class="input-group-btn">
                            <button class="btn btn-primary" type="button">Find tariff</button>
                        </span>
                            </div>
                        </form>

                        <a href="/newTariff" class="btn btn-primary btn-mg" style="margin-top: 12px">Create new</a>
                        <a href="/tariffs" class="btn btn-light btn-mg" style="margin-top: 12px">Show all</a>
                    </div>
                </div>
            </div>
        </div>

        <%--<jsp:include page="parts/findUser.jsp"/>--%>

        <%--<br>--%>

        <%--<jsp:include page="parts/findTariff.jsp"/>--%>



    </div>

    <jsp:include page="parts/footer.jsp"/>

</body>
</html>
