<%--
  Created by IntelliJ IDEA.
  User: FedorBilev
  Date: 10/23/2018
  Time: 3:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li class=" nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        Login
    </a>
    <ul class="dropdown-menu" role="menu" style="width: 300px;">
        <div class="col-lg-12">
            <div class="text-center"><h3><b>Log In</b></h3></div>
            <form id="ajax-login-form" action="/account" method="post" role="form">
                <div class="form-group">
                    <label for="username">Email/Phone</label>
                    <input type="text" name="username" id="username" class="form-control" placeholder="Username" value="" autocomplete="off">
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" class="form-control" placeholder="Password" autocomplete="off">
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="submit" name="login-submit" id="login-submit"class="form-control btn btn-success" value="Log In">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="text-center">
                                <a href="#" class="forgot-password">Forgot Password?</a>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </ul>
</li>
