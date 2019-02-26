<%-- 
    Document   : index
    Created on : Aug 28, 2018, 4:13:17 PM
    Author     : sultan
--%>
<!DOCTYPE html>
<html lang="en">

    <!-- Mirrored from brandio.io/envato/iofrm/html/login9.html by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 27 Aug 2018 10:22:02 GMT -->
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Finger Authentication</title> 
        <link rel="shortcut icon" href="LoginAsset/ico/favicon.jpg">
        <link rel="stylesheet" type="text/css" href="LoginAsset/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="LoginAsset/css/fontawesome-all.min.css">
        <link rel="stylesheet" type="text/css" href="LoginAsset/css/iofrm-style.css">
        <link rel="stylesheet" type="text/css" href="LoginAsset/css/iofrm-theme9.css">
        <script type="text/javascript" src="LoginAsset/js/jquery.min.js"></script>
        <script type="text/javascript" src="LoginAsset/js/popper.min.js"></script>
        <script type="text/javascript" src="LoginAsset/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="LoginAsset/js/main.js"></script>
         
    </head>
    <body style="margin: 0; height: 100%; overflow: hidden">
        <%
            String loginFlag = (String) session.getAttribute("LOGIN");
            if (loginFlag != null) {
                String redirectURL = "home.jsp";
                response.sendRedirect(redirectURL);
            }
            String contextPath = request.getContextPath();
        %>
        
        <div class="form-body" class="container-fluid">
            <div class="row">
                <div class="img-holder">
                    <div class="bg"></div>
                    <div class="info-holder">
                        <h3>Finger Authentication Server</h3> 
                        <img src="LoginAsset/images/finger.png" alt="">
                    </div>
                </div>
                <div class="form-holder">
                    <div class="form-content">
                        <div class="form-items">
                            <div style="margin-bottom: 40px;"> <!--class="website-logo-inside"> -->
                                 <a href="index.jsp">
                                    <div class="logo">
                                        <img class="logo-size" src="LoginAsset/images/agent.png" alt="">
                                    </div>
                                </a>
                            </div>
                            <%
                                String error_msg = (String) request.getParameter("eMessage");
                                if (error_msg != null) { %>
                            <div class="alert alert-danger">
                                <%=error_msg%> 
                            </div>
                            <%
                                }
                            %>

                            <form role="form" action="<%=contextPath%>/LoginServlet" method="post">	
                                <input class="form-control" type="text" name="username" placeholder="E-mail Address" style="color: #000000;" required>
                                <input class="form-control" type="password" name="password" placeholder="Password" style="color: #000000;" required>
                                <div class="form-button">
                                    <button id="submit" type="submit" class="ibtn">Login</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    <!-- Mirrored from brandio.io/envato/iofrm/html/login9.html by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 27 Aug 2018 10:22:03 GMT -->
</html>