
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Option"/>
    </jsp:include>
</head>
<body class="bg-light">

    <jsp:include page="parts/navbar.jsp"/>

    <div class="container">

        <div id="exception"></div>

        <div id="main">

        <input type="hidden" name="id" class="form-control" id="id"/>

        <input name="tariffId" class="form-control" type="hidden" value="${option.tariffId}"/>

        <div class="form-group">
            <label for="name">Option name</label>
            <input type="text" name="name" class="form-control" id="name" placeholder="name"/>
            <div class="invalid-feedback">
                <span id="name_error"></span>
            </div>
        </div>


        <div class="form-group">
            <label for="price">Option price</label>
            <input type="number" min="0" name="price" class="form-control" id="price" placeholder="100.0"/>
            <div class="invalid-feedback">
                <span id="price_error"></span>
            </div>
        </div>


        <div class="form-group">
            <label for="connectionPrice">Option connection price</label>
            <input type="number" min="0" name="connectionPrice" class="form-control" id="connectionPrice" placeholder="100.0"/>
            <div class="invalid-feedback">
                <span id="connectionPrice_error"></span>
            </div>
        </div>


        <div class="form-group">
            <label for="info">Option info</label>
            <input type="text" name="info" class="form-control" id="info" placeholder="info"/>
            <div class="invalid-feedback">
                <span id="info_error"></span>
            </div>
        </div>

        <c:if test = "${option.relatedOptions.size() != 0}">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Connection price</th>
                    <th scope="col">Info</th>
                    <th scope="col">Option</th>
                </tr>
                </thead>
                <tbody id="relatedOptions">
                    <c:forEach items="${option.relatedOptions}" var="relatedOption" varStatus="i">
                        <tr>
                            <td>${relatedOption.name}</td>
                            <td>${relatedOption.price}</td>
                            <td>${relatedOption.connectionPrice}</td>
                            <td>${relatedOption.info}</td>

                            <td>
                                <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                    <label class="btn btn-secondary active">
                                        <input type="radio" name="${relatedOption.id}" value="NON" autocomplete="off" checked> Non
                                    </label>
                                    <label class="btn btn-secondary">
                                        <input type="radio" name="${relatedOption.id}" value="INCOMPATIBLE" autocomplete="off"> INCOMPATIBLE
                                    </label>
                                    <label class="btn btn-secondary">
                                        <input type="radio" name="${relatedOption.id}" value="REQUIRED" autocomplete="off"> REQUIRED
                                    </label>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <button onclick="editOption(${option.tariffId})" class="btn btn-primary">Save option</button>

        </div>

    </div>

    <jsp:include page="parts/footer.jsp"/>

    <script src="js/editForms.js" type="text/javascript"></script>
    <script src="js/editOption.js" type="text/javascript"></script>

</body>
</html>
