
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<nav class="navbar navbar-expand-lg fixed-top navbar-light bg-light" style="font-size: 15px;">
    <div class="container">

        <div class="navbar-header">
            <a class="navbar-brand" href="#" style="font-size: 30px;"><b>N42</b></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav navbar-right">
                <li class="nav-item active">
                    <a class="nav-link active" href="#">Sims</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Tariffs</a>
                </li>

                <c:choose>
                    <c:when test="${requestScope['javax.servlet.forward.request_uri'] == '/'}">
                        <jsp:include page="login.jsp"/>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="btn btn-primary btn-md" href="/logout">Logout</a>
                        </li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div>
    </div>
</nav>