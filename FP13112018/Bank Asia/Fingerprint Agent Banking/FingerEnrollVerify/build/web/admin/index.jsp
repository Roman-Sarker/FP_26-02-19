<%@page import="com.era.admin.AdminDetails"%>
<%@page import="com.era.sqlitedb.DbConnectivity"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Finger</title>
        <!-- Core CSS - Include with every page -->
        <link href="assets/plugins/bootstrap/bootstrap.css" rel="stylesheet" />
        <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
        <link href="assets/plugins/pace/pace-theme-big-counter.css" rel="stylesheet" />
        <link href="assets/css/style.css" rel="stylesheet" />
        <link href="assets/css/main-style.css" rel="stylesheet" />
        <script src="assets/plugins/jquery-1.10.2.js"></script>

        <style>
            body{
                background-image: url("login.jpg");
                background-repeat: repeat-x; 
            }
        </style> 
    </head>
    <body > 


        <%
            if (session.getAttribute("LOGIN") != null) {
                String redirectURL = "home.jsp";
                response.sendRedirect(redirectURL);
            }

            String error_msg = null;
            if ((request.getParameter("btnLogon") == null) ? false : true) {
                String username = request.getParameter("email");
                String password = request.getParameter("password");

                DbConnectivity dbConnectivity = new DbConnectivity();
                String dbMessage = dbConnectivity.processingAllTask();
                out.println("dbMessage is " + dbMessage);
                boolean checkFlag = false;
                if(checkFlag)
                    response.sendRedirect("/FingerEnrollVerify/SqliteDBInfo");
                else if (dbMessage == null) {
                    boolean loginStatus = dbConnectivity.checkLogin(username, password);
                    AdminDetails adminDetails = dbConnectivity.getAdminDetails(username, password);

                    if (loginStatus) {
                        session.setAttribute("LOGIN", "TRUE");
                        session.setAttribute("fName", adminDetails.getFirstName());
                        session.setAttribute("lName", adminDetails.getLastName());
                        session.setAttribute("EMAIL", username);

                        String redirectURL = "home.jsp";
                        response.sendRedirect(redirectURL);
                    } else {
                        error_msg = "username/password is wrong";
                    }
                } else {
                    error_msg = dbMessage;
                }

            }

        %>

        <div class="container">

            <div class="row">
                <div class="col-md-4 col-md-offset-4 text-center logo-margin ">
                    <img src="assets/img/logo.png" alt=""/>
                </div>

                <div class="col-md-4 col-md-offset-4">

                    <%if (error_msg != null) {%>
                    <div class="alert alert-danger">
                        <%=error_msg%> 
                    </div>
                    <%}%>


                    <div class="login-panel panel panel-default">                  
                        <div class="panel-heading">
                            <h3 class="panel-title">Please Sign In</h3>
                        </div>
                        <div class="panel-body">
                            <form role="form" action="index.jsp" method="POST">
                                <fieldset>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="E-mail" name="email" type="email" autofocus>
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                    </div>
                                    <div class="checkbox">
                                        <label>
                                            <input name="remember" type="checkbox" value="Remember Me">Remember Me
                                        </label>
                                    </div>
                                    <!-- Change this to a button or input when using this as a form 
                                    <a href="home.jsp" class="btn btn-lg btn-success btn-block">Login</a> -->
                                    <button class="btn btn-lg btn-success btn-block"  name="btnLogon">Login</button>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            $(document).ready(function () {
                $("input").keypress(function (evt) {
                    if (evt.keyCode == 13) {
                        iname = $(this).val();
                        if (iname !== 'Submit') {
                            var fields = $(this).parents('form:eq(0),body').find('button,input,textarea,select');
                            var index = fields.index(this);
                            if (index > -1 && (index + 1) <= fields.length) {
                                fields.eq(index + 1).focus();
                            }
                            return false;
                        }
                    }
                });
            });

        </script>

        <script src="assets/plugins/jquery-1.10.2.js"></script>
        <script src="assets/plugins/bootstrap/bootstrap.min.js"></script>
        <script src="assets/plugins/metisMenu/jquery.metisMenu.js"></script>

    </body>

</html>
