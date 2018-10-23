
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/meta.jsp"/>
    <title>N42 Account</title>
</head>
<body style="margin-left: auto;margin-right: auto;">
    <jsp:include page="parts/navbar.jsp"/>

    <div class="container">
        <div class="card-columns">

            <div class="card text-center text-white bg-dark border-light">
                <div class="card-header"><h4><b>Personal info</b></h4></div>
                <div class="card-body">
                    <p class="card-text">${user.firstName} ${user.lastName}</p>
                    <p class="card-text">Birth date: ${user.birthDate}</p>
                    <p class="card-text">Address: ${user.address}</p>
                    <p class="card-text">Email: ${user.email}</p>
                    <p class="card-text">Password: ********</p>
                    <a href="#" class="btn">Edit</a>
                </div>
            </div>



            <c:forEach items="${contracts}" var="contract">
                <div class="card text-center bg-light border-light mb-3">
                    <div class="card-header"><h4><b>${contract.phoneNumber}</b></h4></div>
                    <div class="card-body">
                        <p class="card-text">${contract.tariffId}</p>
                        <a href="#" class="btn">Manage</a>
                    </div>
                </div>
            </c:forEach>

        </div>


    </div>

    <jsp:include page="parts/footer.jsp"/>
    </body>
</html>
