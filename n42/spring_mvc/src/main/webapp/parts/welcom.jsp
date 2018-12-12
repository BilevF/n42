
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="welcome" class="jumbotron bg-white">
    <div class="container" style="max-width: 960px;">
        <h2 class="card-title" style="text-align:left; font-size: 26px;">
            <b>${param.name}</b>
            <span style="float:right;">${param.secondName}</span>
        </h2>

        <div>
            <small class="text-muted" style="font-size: 18px;">${param.message}</small>
        </div>
    </div>
</div>
