
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="jumbotron bg-white">
    <div class="container" style="max-width: 960px;">
        <h2 class="card-title" style="text-align:left;">
            <b>${param.name}</b>
            <span style="float:right;">${param.secondName}</span>
        </h2>
        <%--<hr class="my-4">--%>
        <div>
            <small class="text-muted">${param.message}</small>
        </div>
    </div>
</div>
