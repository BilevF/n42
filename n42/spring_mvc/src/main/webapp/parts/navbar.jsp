
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div id="navbar">
    <div id="navitem" class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white shadow-sm">
        <div class="my-0 mr-md-3 font-weight-normal">
            <div class="navbar-header ">
                <a class="navbar-brand" href="account" style="font-size: 30px; color: black"><b>N42</b></a>
            </div>
        </div>

        <div id="menu" class="p-2" style="display: inline;">
        <sec:authorize access="hasRole('ROLE_ADMIN')">

            <div id="menu-tabs" style="display: inline;">
                <button id="menu-button-create" name="create" onclick="onCreate()" type="button" class="menu-button">Create</button>
                <button id="menu-button-show" name="show" onclick="onShow()" type="button" class="menu-button">Show</button>
                <button id="menu-button-find" name="find" onclick="onFind()" type="button" class="menu-button">Find</button>
            </div>
            <span id="menuSeparator" class="glyphicon glyphicon-menu-right hidden" aria-hidden="true"></span>

            <div id="createContent" class="hidden" style="display: inline;">
                <c:choose>

                    <c:when test = "${param.contentType == 'Contract'}">
                        <form name="contract" action="contract/new"  method="get" style="display: inline;">
                            <input  name="userId" type="hidden" value="${param.userId}">
                            <button type="submit" class="menu-button">Contract</button>
                        </form>
                    </c:when>

                    <c:when test = "${param.contentType == 'Option'}">
                        <form name="option" action="tariff/option/new" method="get" style="display: inline;">
                            <input id="tariffId" name="tariffId" type="hidden" value="${param.tariffId}">
                            <button type="submit" class="menu-button">Option</button>
                        </form>
                    </c:when>

                </c:choose>

                <form name="user" action="user/new" method="get" style="display: inline;">
                    <button type="submit" class="menu-button">User</button>
                </form>

                <form name="tariff" action="tariff/new" method="get" style="display: inline;">
                    <button type="submit" class="menu-button">Tariff</button>
                </form>
            </div>


            <div id="showContent" class="hidden" style="display: inline;">
                <form name="users" action="user/list" method="get" style="display: inline;">
                    <button type="submit" class="menu-button">Users</button>
                </form>

                <form name="tariffs" action="tariff/list" method="get" style="display: inline;">
                    <button type="submit" class="menu-button">Tariffs</button>
                </form>
                <%--<a href="tariff/list" type="button" class="btn btn-light">Contract</a>--%>
            </div>


            <div id="findContent" class="hidden" style="display: inline;">

                <form name="user" action="user/list" method="get" style="display: inline;">
                    <button type="submit" class="menu-button">User</button>
                </form>

                <form name="tariff" action="tariff/list" method="get" style="display: inline;">
                    <button type="submit" class="menu-button">Tariff</button>
                </form>
                <%--<a href="tariff/list" type="button" class="btn btn-light">Contract</a>--%>
            </div>



        </sec:authorize>
        </div>

        <div id="YourElementId">
        </div>

        <div id="YourElementId1">
        </div>

        <div class="ml-auto p-2"  style="display: inline;">
            <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')">
                <a class="btn btn-primary" href="logout">Logout</a>
            </sec:authorize>
            <sec:authorize access="!(hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT'))">
                <jsp:include page="login.jsp"/>
            </sec:authorize>
        </div>

        <%--</nav>--%>

    </div>
</div>

