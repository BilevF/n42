
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div class="card mb-4 shadow-sm">
    <div class="card-body">
        <h4 class="card-subtitle">${param.title}</h4>
        <h1 class="card-title pricing-card-title">$${param.price} <small class="text-muted">/ mo</small></h1>
        ${param.info}
        <c:if test="${param.showBtn}">
            <form action="${param.path}" method="${param.method}">
                <input name="${param.hiddenName1}" type="hidden" value="${param.hiddenValue1}">
                <input name="${param.hiddenName2}" type="hidden" value="${param.hiddenValue2}">
                <button type="submit" class="btn ${param.btnStyle}">${param.btnName}</button>
            </form>
        </c:if>
        <c:if test="${!param.showBtn}">
            <br>
        </c:if>
    </div>
</div>
