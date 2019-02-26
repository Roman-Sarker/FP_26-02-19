<%@page import="com.era.sqlitedb.AdminRegistration"%>
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

    </head>

    <body>
        <%
            if (session.getAttribute("LOGIN") == null) {
                String redirectURL = "index.jsp";
                response.sendRedirect(redirectURL);
            }
        %>
        <!--  wrapper -->
        <div id="wrapper">
            <nav class="navbar navbar-default navbar-fixed-top" role="navigation" id="navbar">
                <jsp:include page="topbar.jsp" />
            </nav>

            <jsp:include page="sidebar.jsp" />

            <div id="page-wrapper">

                <div class="row">
                    <!-- Page Header -->
                    <div class="col-lg-12">
                        <h1 class="page-header">New Admin Registration</h1>
                    </div>                     
                    <!--End Page Header -->
                </div>

                <div class="row">
                    <%
                        if ((request.getParameter("btnReg") == null) ? false : true) {

                            String firstName = request.getParameter("first_name");
                            String lastName = request.getParameter("last_name");
                            String userName = request.getParameter("email");
                            String password = request.getParameter("password");
                            String confirm_password = request.getParameter("password_confirmation");

                            if (firstName == null) {
                    %>
                    <div class="alert alert-danger">
                        <%="First Name can not be blank!"%> 
                    </div>        
                    <%      } else if (lastName == null) {
                    %>
                    <div class="alert alert-danger">
                        <%="Last Name can not be blank!"%> 
                    </div>        
                    <%      } else if (userName == null) {
                    %>
                    <div class="alert alert-danger">
                        <%="Email can not be blank!"%> 
                    </div>        
                    <%      } else if (password == null) {
                    %>
                    <div class="alert alert-danger">
                        <%="password can not be blank!"%> 
                    </div>        
                    <%      } else if (!password.equals(confirm_password)) {
                    %>
                    <div class="alert alert-danger">
                        <%="Passwords does not match"%> 
                    </div>
                    <%      } else {
                        AdminRegistration adminRegistration = new AdminRegistration();
                        String rootUser = (String) session.getAttribute("EMAIL");
                        // out.println(rootUser);
                        adminRegistration.setRootUser(rootUser);
                        adminRegistration.setInformation(firstName, lastName, userName, password);
                        String errorMessage = adminRegistration.createAdmin();

                        if (errorMessage != null) {
                    %>
                    <div class="alert alert-danger">
                        <%=errorMessage%> 
                    </div>
                    <%
                    } else {
                    %>
                    <div class="alert alert-success">
                        <%="Successfully new admin created"%> 
                    </div>   
                    <%
                                }
                            }
                        }
                    %>

                </div>

                <div class="row centered-form">
                    <div class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Registration Form</h3>
                            </div>
                            <div class="panel-body">
                                <form role="form" action="registration.jsp" method="POST">
                                    <div class="row">
                                        <div class="col-xs-6 col-sm-6 col-md-6">
                                            <div class="form-group">
                                                <input type="text" name="first_name" id="first_name" class="form-control input-sm" placeholder="First Name">
                                            </div>
                                        </div>
                                        <div class="col-xs-6 col-sm-6 col-md-6">
                                            <div class="form-group">
                                                <input type="text" name="last_name" id="last_name" class="form-control input-sm" placeholder="Last Name">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <input type="email" name="email" id="email" class="form-control input-sm" placeholder="Email Address">
                                    </div>

                                    <div class="row">
                                        <div class="col-xs-6 col-sm-6 col-md-6">
                                            <div class="form-group">
                                                <input type="password" name="password" id="password" class="form-control input-sm" placeholder="Password">
                                            </div>
                                        </div>
                                        <div class="col-xs-6 col-sm-6 col-md-6">
                                            <div class="form-group">
                                                <input type="password" name="password_confirmation" id="password_confirmation" class="form-control input-sm" placeholder="Confirm Password">
                                            </div>
                                        </div>
                                    </div>

                                    <!--   <input type="submit" value="Register" class="btn btn-info btn-block">
                                    --> <button class="btn btn-info btn-block"  name="btnReg">Register</button>

                                </form>
                            </div>
                        </div>
                    </div>
                </div> 
            </div>
        </div>

        <%
            String error_msg = null;

        %>
        
        <jsp:include page="footer.jsp" />

        <!-- end page-wrapper -->

        <!-- end wrapper -->

        <!-- Core Scripts - Include with every page -->
        <script src="assets/plugins/jquery-1.10.2.js"></script>
        <script src="assets/plugins/bootstrap/bootstrap.min.js"></script>
        <script src="assets/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="assets/plugins/pace/pace.js"></script>
        <script src="assets/scripts/siminta.js"></script>

    </body>

</html>
