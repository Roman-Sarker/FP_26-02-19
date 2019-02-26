<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login</title> 
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="assets/css/awesome-bootstrap-checkbox.css">
        <link rel="stylesheet" href="assets/css/style.css"> 
        <link rel="shortcut icon" href="assets/ico/favicon.png">
        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/wow.min.js"></script>
        <script src="assets/js/retina-1.1.0.min.js"></script>
        <script src="assets/js/scripts.js"></script> 
    </head>

    <body>
        <%
                String loginFlag = (String) session.getAttribute("LOGIN");
                if (loginFlag != null) {
                    String redirectURL = "home.jsp";
                    response.sendRedirect(redirectURL);
                } 
                String contextPath = request.getContextPath();
            %>
        <nav class="navbar navbar-inverse navbar-fixed-top navbar-no-bg" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#top-navbar-1">
                        <span class="sr-only">Toggle navigation</span> 
                    </button>
                    <a class="navbar-brand" href="index.jsp">Login</a>
                </div> 
                <div class="collapse navbar-collapse" id="top-navbar-1">
                    <ul class="nav navbar-nav navbar-right"><li><a href=../index.jsp>Home</a></li></ul>
                </div>
            </div>
        </nav>
 
        <div class="l-form-9-container section-container">
            <div class="container">
                <div class="row">
                    <div class="col-sm-6 col-sm-offset-3 l-form-9-left section-description wow fadeIn">
                        <h2>Matching Server</h2> 
                        <hr style="color:black;margin-bottom:5px !important; margin-top:5px !important; " />
                        <%
                        String error_msg = (String)request.getParameter("eMessage"); 
                        if (error_msg != null) {%>
                        <div class="alert alert-danger">
                            <%=error_msg%> 
                        </div>
                    <%   
                        }
                    %>
                    </div>  
                     
                </div>
                <div class="row">
                    <div class="col-sm-6 col-sm-offset-3 l-form-9-box wow fadeInLeft"> 
                        <div class="l-form-9-bottom">
                            <form role="form" action="<%=contextPath%>/LoginServlet" method="post">		                        
                                <div class="form-group">
                                    <label for="l-form-9-username">Username:</label>
                                    <input type="text" name="username" placeholder="Your username..." class="l-form-9-username form-control" id="username" autofocus>
                                </div>
                                <div class="form-group">
                                    <label for="l-form-9-password">Password:</label>
                                    <input type="password" name="password" placeholder="Your password..." class="l-form-9-password form-control" id="password">
                                </div>
                                <button type="submit" class="btn">Sign in!</button>
                                <div class="checkbox">
                                    <input type="checkbox" id="remember-me" class="styled">
                                    <label for="remember-me">Remember me</label>
                                </div>
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


    </body>
</html>