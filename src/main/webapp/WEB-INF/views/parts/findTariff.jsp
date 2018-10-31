<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: FedorBilev
  Date: 10/28/2018
  Time: 6:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="card">
    <div class="row">
        <div class="col-sm-8">
            <div style="padding-left: 10%">
                <h2 style="margin-bottom: 20px"><b>Manage tariffs</b></h2>
                <form>
                    <div class="input-group">
                        <input type="text" class="form-control" name="user_info" placeholder="Tariff name">
                        <span class="input-group-btn">
                            <button class="btn btn-primary" type="button">Find tariff</button>
                        </span>
                    </div>
                </form>

                <a href="/newTariff" class="btn btn-primary btn-mg" style="margin-top: 12px">Create new</a>
                <a href="/tariffs" class="btn btn-light btn-mg" style="margin-top: 12px">Show all</a>
            </div>
        </div>
        <div class="col-sm-4">
            <img src="/resources/images/tariff.png" class="img-fluid" style="padding-left: 15%; padding-right: 15%;" alt="">
        </div>
    </div>
</div>
