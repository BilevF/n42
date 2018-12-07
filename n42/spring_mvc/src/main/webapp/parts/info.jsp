<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<div class="card mb-4 shadow-sm">
    <div class="row">

        <div class="col-sm-5">
            <img src="${param.imgPath}" class="img-fluid" alt="" style="display: block;margin-left: auto;margin-right: auto;position: absolute;bottom: 0px;">
        </div>

        <div class="col-sm-6 " style="margin: 8px; margin-left: 15px">

            <h3 >${param.title}</h3>

            <hr>

            <div class="row">
                <div class="col-xs-3">
                    <h5 class="text-muted">${param.name1}:</h5>
                </div>

                <div class="col-xs-9">
                    <h5 >nikalitvinova.g@gmail.com</h5>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-3">
                    <h5 class="text-muted">${param.name2}:</h5>
                </div>

                <div class="col-xs-9">
                    <h5 >${param.value2}</h5>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-3">
                    <h5 class="text-muted">${param.name3}:</h5>
                </div>

                <div class="col-xs-9">
                    <h5 >${param.value3} 159159</h5>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-3">
                    <h5 class="text-muted">${param.name4}:</h5>

                </div>

                <div class="col-xs-9">
                    <h5 >${param.value4}, Nevskiy prospect dom 45, kv 69</h5>
                </div>
            </div>


            <%----%>

            <%--<div class="row">--%>
                <%--<div class="col-xs-6">--%>
                    <%--<span  class="glyphicon glyphicon-gift" aria-hidden="true"></span>--%>
                    <%--<h5>${param.value2}</h5>--%>

                <%--</div>--%>

                <%--<div class="col-xs-6">--%>
                    <%--<span  class="glyphicon glyphicon-envelope" aria-hidden="true"></span>--%>
                    <%--<h5 >nikalitvinova.g@gmail.com</h5>--%>
                <%--</div>--%>
            <%--</div>--%>

            <%--<br>--%>

            <%--<div class="row">--%>
                <%--<div class="col-xs-6">--%>
                    <%--<span class="glyphicon glyphicon-user" aria-hidden="true"></span>--%>
                    <%--<h5 >${param.value3} 159159</h5>--%>

                <%--</div>--%>

                <%--<div class="col-xs-6">--%>
                    <%--<span  class="glyphicon glyphicon-bed" aria-hidden="true"></span>--%>
                    <%--<h5 >${param.value4}, Nevskiy prospect dom 45, kv 69</h5>--%>
                <%--</div>--%>
            <%--</div>--%>

            <%----%>

            <%--<div class="row">--%>
                <%--<div class="col-xs-6">--%>
                    <%--<h5 class="text-muted">${param.name2}</h5>--%>
                    <%--<h5><b>${param.value2}</b></h5>--%>

                <%--</div>--%>

                <%--<div class="col-xs-6">--%>
                    <%--<h5 class="text-muted">${param.name1}</h5>--%>
                    <%--<h5><b>nikalitvinova.g@gmail.com</b></h5>--%>
                <%--</div>--%>
            <%--</div>--%>

            <%--<br>--%>

            <%--<div class="row">--%>
                <%--<div class="col-xs-6">--%>
                    <%--<h5 class="text-muted">${param.name3}</h5>--%>
                    <%--<h5><b>${param.value3} 159159</b></h5>--%>

                <%--</div>--%>

                <%--<div class="col-xs-6">--%>
                    <%--<h5 class="text-muted">${param.name4}</h5>--%>
                    <%--<h5><b>${param.value4}, Nevskiy prospect dom 45,kv 69</b></h5>--%>
                <%--</div>--%>
            <%--</div>--%>



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