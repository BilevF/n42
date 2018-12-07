
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="card mb-4 shadow-sm">
    <div class="card-body">
        <h3 class="card-title">${param.title}</h3>
        ${param.body}

        <form action="${param.path}" method="${param.method}">
            <input name="${param.hiddenName1}" type="hidden" value="${param.hiddenValue1}">
            <input name="${param.hiddenName2}" type="hidden" value="${param.hiddenValue2}">
            <button type="submit" class="btn btn-primary">${param.btnName}</button>
        </form>
    </div>
</div>
