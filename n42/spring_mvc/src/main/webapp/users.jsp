
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css">
    <jsp:include page="parts/header.jsp">
        <jsp:param name="title" value="Users"/>
    </jsp:include>

    <%--<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">--%>


</head>
<body class="bg-light">

    <jsp:include page="parts/navbar.jsp"/>

    <jsp:include page="parts/welcom.jsp">
        <jsp:param name="name" value="N42 Clients"/>
        <jsp:param name="message" value=""/>
        <jsp:param name="secondName" value=""/>
    </jsp:include>

    <div class="container">

        <%--<div class="card mb-4 shadow-sm">--%>


            <%--<div style="padding: 12px;">--%>

                <table id="table_users" class="ui table">
                    <thead>
                    <tr>
                        <th scope="col">First name</th>
                        <th scope="col">Last name</th>
                        <th scope="col">Birth date</th>
                        <th scope="col">Email</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Option</th>
                    </tr>
                    </thead>
                    <tbody >
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.birthDate}</td>
                            <td>${user.email}</td>
                            <td>
                                <c:forEach items="${user.contracts}" var="contract">
                                    ${contract.phoneNumber}
                                </c:forEach>
                            </td>
                            <td><a href="user?userId=${user.id}" class="btn btn-primary btn-mg">Manage</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            <%--</div>--%>
        <%--</div>--%>

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
            $('#table_users').DataTable( {
                "columnDefs": [
                    {
                        "targets": [ 4 ],
                        "visible": false
                    }
                ],
                fnInitComplete: function(){
                    $("#table_users_filter").css("font-size", 14);
                }
            } );
        } );


    </script>

</body>
</html>
