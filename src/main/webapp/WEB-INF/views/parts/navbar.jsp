
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm" style="padding-right: 15px;padding-left: 15px;margin-right: auto;margin-left: auto;max-width: 960px;">
    <div class="my-0 mr-md-auto font-weight-normal">
        <div class="navbar-header ">
            <a class="navbar-brand" href="/account" style="font-size: 30px; color: black"><b>N42</b></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
    </div>
    <nav class="my-2 my-md-0 mr-md-3">
        <a class="p-2 text-dark" href="#">Features</a>
        <a class="p-2 text-dark" href="#">Enterprise</a>
        <a class="p-2 text-dark" href="#">Support</a>
        <a class="p-2 text-dark" href="#">Pricing</a>
        <c:choose>
            <c:when test="${requestScope['javax.servlet.forward.request_uri'] == '/'}">
                <jsp:include page="login.jsp"/>
            </c:when>
            <c:otherwise>
                <a class="btn btn-primary" href="/logout">Logout</a>
            </c:otherwise>
        </c:choose>
    </nav>

</div>
