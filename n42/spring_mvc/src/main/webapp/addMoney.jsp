
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Add Money"/>
    </jsp:include>
</head>
<body class="bg-light">
    <jsp:include page="parts/navbar.jsp"/>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="${user.firstName} ${user.lastName}"/>
        <jsp:param name="secondName" value="Balance: $${user.balance}"/>
        <jsp:param name="message" value="<p>On this page you can add funds to your account</p>"/>
    </jsp:include>

    <div class="container">

        <form method="POST" action="https://money.yandex.ru/quickpay/confirm.xml">
            <input type="hidden" name="receiver" value="410018229800312">
            <input type="hidden" name="formcomment" value="N42: top up ${user.firstName}'s account">
            <input type="hidden" name="short-dest" value="N42: Top up ${user.firstName}'s account">
            <input type="hidden" name="quickpay-form" value="shop">
            <input type="hidden" name="targets" value="Top up account">
            <input type="hidden" name="label" value="${user.id}">
            <input type="hidden" name="successURL" value="http://localhost:8080/n42/account">


            <div class="row">

                <div class="col">
                    <input type="number" name="sum" min="2" class="form-control" placeholder="10 ₽" data-type="number">
                </div>

                <div class="col-md-auto">
                    <div class="btn-group btn-group-toggle" data-toggle="buttons">
                        <label class="btn btn-secondary active">
                            <input type="radio" name="paymentType" value="PC">Yandex.Money
                        </label>
                        <label class="btn btn-secondary">
                            <input type="radio" name="paymentType" value="AC">Credit card
                        </label>
                    </div>
                </div>

            </div>

            <br>

            <button type="submit" class="btn btn-primary">Proceed to payment</button>

        </form>

    </div>

    <jsp:include page="parts/footer.jsp"/>

</body>
</html>
