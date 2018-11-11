<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div class="card mb-4 shadow-sm">
    <div class="row">

        <div class="col-sm-5">
            <img src="${param.imgPath}" class="img-fluid" alt="" style="display: block;margin-left: auto;margin-right: auto;">
        </div>

        <div class="col-sm-6" style="margin: auto;">
            <h4 class="card-title" style="text-align:left;">
                ${param.name1}
                <span style="float:right;">${param.value1}</span>
            </h4><hr>

            <h4 class="card-title" style="text-align:left;">
                ${param.name2}
                <span style="float:right;">${param.value2}</span>
            </h4><hr>

            <h4 class="card-title" style="text-align:left;">
                ${param.name3}
                <span style="float:right;">${param.value3}</span>
            </h4><hr>

            <h4 class="card-title" style="text-align:left;">
                ${param.name4}  
                <span style="float:right; ${param.value4Style}">${param.value4}</span>
            </h4>

            <div class="btn-group" role="group" aria-label="basket btn" style="float:right; padding-top: 8px">
                <c:if test="${param.showBtn1}">
                <form action="${param.btn1Path}" method="${param.btn1Method}" style="display: inline;">
                    <input name="${param.btn1HiddenName}" type="hidden" value="${param.btn1HiddenValue}">
                    <input type="submit" class="btn btn-primary" value="${param.btn1Name}">
                </form>
                </c:if>

                <c:if test="${param.showBtn2}">
                    <form action="${param.btn2Path}" method="${param.btn2Method}" style="display: inline;">
                        <input name="${param.btn2HiddenName}" type="hidden" value="${param.btn2HiddenValue}">
                        <input type="submit" class="btn btn-primary" value="${param.btn2Name}">
                    </form>
                </c:if>

                <c:if test="${param.showBtn3}">
                    <form action="${param.btn3Path}"  method="${param.btn3Method}" style="display: inline;">
                        <input name="${param.btn3HiddenName}" type="hidden" value="${param.btn3HiddenValue}">
                        <input type="submit" class="btn btn-primary" value="${param.btn3Name}">
                    </form>
                </c:if>

                <c:if test="${param.showBtn4}">
                    <form action="${param.btn4Path}"  method="${param.btn4Method}" style="display: inline;">
                        <input name="${param.btn4HiddenName}" type="hidden" value="${param.btn4HiddenValue}">
                        <input type="submit" class="btn btn-primary" value="${param.btn4Name}">
                    </form>
                </c:if>
            </div>

        </div>
    </div>
</div>