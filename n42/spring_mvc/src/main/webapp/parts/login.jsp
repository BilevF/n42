
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<a class="btn btn-primary" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Login
</a>
<ul class="dropdown-menu" role="menu" style="width: 300px;z-index: 100;">
    <div class="col-lg-12">
        <form id="ajax-login-form" action="account" method="post">

            <div class="form-group">
                <label for="username">Email/Phone</label>
                <input type="text" name="username" id="username" class="form-control" placeholder="Username" value="">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" name="password" id="password" class="form-control" placeholder="Password">
            </div>

            <div class="form-group">
                <div class="row">
                    <div class="col-lg-12">
                        <input type="submit" name="login-submit" id="login-submit" class="form-control btn btn-primary" value="Login">
                    </div>
                </div>
            </div>

            <%--<div class="form-group">--%>
                <%--<div class="row">--%>
                    <%--<div class="col-lg-12">--%>
                        <%--<div class="text-center">--%>
                            <%--<a href="#" class="forgot-password">Forgot Password?</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        </form>
    </div>
</ul>
