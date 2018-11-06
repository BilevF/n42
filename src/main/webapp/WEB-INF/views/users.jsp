
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp"/>
    <title>N42 Users</title>
</head>
<body>

    <jsp:include page="parts/navbar.jsp"/>
    <div class="container">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">First name</th>
                <th scope="col">Last name</th>
                <th scope="col">Birth date</th>
                <th scope="col">Email</th>
                <th scope="col">Option</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <input name="relatedOptions[${i.index}].id" type="hidden" value="${relatedOption.id}"/>
                <tr>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.birthDate}</td>
                    <td>${user.email}</td>
                    <td>
                        <form action="/user">
                            <input name="userId" type="hidden" value="${user.id}">
                            <input type="submit" class="form-control btn btn-primary" value="Manage">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
    <jsp:include page="parts/footer.jsp"/>

</body>
</html>
