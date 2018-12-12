
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Edit contract"/>
    </jsp:include>
</head>
<body class="bg-light">

<jsp:include page="parts/navbar.jsp"/>

<div class="container">

    <div id="exception"></div>

    <%--<input name="balance" class="form-control" type="hidden" value="${contract.balance}">--%>
    <input name="id" class="form-control" type="hidden" value="${contract.id}">
    <input name="userId" class="form-control" type="hidden" value="${contract.userId}">
    <%--<input name="blockType" class="form-control" type="hidden" value="${contract.blockType}">--%>

    <div class="form-group">
        <label for="phoneNumber">Phone number</label>
        <input type="text" name="phoneNumber" class="form-control" id="phoneNumber" value="${contract.phoneNumber}"/>
        <div class="invalid-feedback">
            <span id="phoneNumber_error"></span>
        </div>
    </div>

    <button onclick="updateContract(${contract.id})" class="btn btn-primary">Save</button>

</div>
<jsp:include page="parts/footer.jsp"/>

<script src="js/editForms.js" type="text/javascript"></script>
<script src="js/editContract.js" type="text/javascript"></script>


</body>
</html>
