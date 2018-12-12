
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css">
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Contract history"/>
    </jsp:include>
</head>
<body class="bg-light">
    <jsp:include page="parts/navbar.jsp">
        <jsp:param name="balance" value="${contract.userBalance}"/>
    </jsp:include>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="Contract history"/>
        <jsp:param name="message" value=""/>
        <jsp:param name="secondName" value=""/>
    </jsp:include>

    <div class="container" style="max-width: 960px;">

        <table id="table_history" class="ui table" >
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Price</th>
                <th scope="col">Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${contract.histories}" var="item">
                <tr>
                    <td>${item.name}</td>
                    <td>₽${item.price}</td>
                    <td>${item.date}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>

    <jsp:include page="parts/footer.jsp"/>


    <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>

    <%--<script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.3.1.js"></script>--%>
    <%--<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>--%>
    <%--<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>--%>

    <%--<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>--%>

    <script type="text/javascript" charset="utf8">

        $(document).ready(function() {
            $('#table_history').DataTable( {
                fnInitComplete: function(){
                    $("#table_history_filter").css("font-size", 14);
                }
            } );
        } );


    </script>

</body>
</html>
