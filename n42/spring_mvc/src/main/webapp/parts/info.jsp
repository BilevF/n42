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
                    <h5>${param.value1}</h5>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-3">
                    <h5 class="text-muted">${param.name2}:</h5>
                </div>

                <div class="col-xs-9">
                    <h5>${param.value2}</h5>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-3">
                    <h5 class="text-muted">${param.name3}:</h5>
                </div>

                <div class="col-xs-9">
                    <h5 >${param.value3}</h5>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-3">
                    <h5 class="text-muted">${param.name4}:</h5>
                </div>

                <div class="col-xs-9">
                    <h5 >${param.value4}</h5>
                </div>
            </div>

        </div>
    </div>
</div>