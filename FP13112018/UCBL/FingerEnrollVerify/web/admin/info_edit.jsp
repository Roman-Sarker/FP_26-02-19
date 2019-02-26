<%@page import="era.dbconnectivity.DBConnectionHandler"%>
<%@page import="com.era.IPAddress.UpdateIPAdress"%>
<%@page import="com.era.IPAddress.IPAddressValidator"%>
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
        <link href="assets/css/custom_css_sultan.css" rel="stylesheet" /> 
        <script src="assets/plugins/jquery-1.10.2.js"></script>
        <script src="assets/plugins/bootstrap/bootstrap.min.js"></script>
        <script src="assets/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="assets/plugins/pace/pace.js"></script>
        <script src="assets/scripts/siminta.js"></script> 
    </head>

    <body>
        <%
            if (session.getAttribute("LOGIN") == null) {
                String redirectURL = "index.jsp";
                response.sendRedirect(redirectURL);
            }
        %> 

        <div id="wrapper">
            <nav class="navbar navbar-default navbar-fixed-top" role="navigation" id="navbar">
                <jsp:include page="topbar.jsp" /> 
            </nav>

            <jsp:include page="sidebar.jsp" />

            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Database Connectivity Information</h1>
                    </div>
                </div>  

                <%
                    if ((request.getParameter("btnForm") == null) ? false : true) {

                        String ip_address = request.getParameter("ip");
                        String portNo = request.getParameter("portNo");
                        String serviceName = request.getParameter("serviceName");
                        String userName = request.getParameter("userName");
                        String password = request.getParameter("password");

                        if (ip_address == null) {
                %>
                <div class="alert alert-danger">
                    <%="IP Adddress can not be blank!"%> 
                </div>
                <%
                } else if (portNo == null) {
                %>
                <div class="alert alert-danger">
                    <%="Port No can not be blank!"%> 
                </div>
                <%
                } else {
                    boolean ipAddressValidatorFlag = IPAddressValidator.validate(ip_address);
                    if (!ipAddressValidatorFlag) {
                %>
                <div class="alert alert-danger">
                    <%="Entered IP Adddress is not valid!"%> 
                </div>
                <%
                } else {
                    String rootUser = (String) session.getAttribute("EMAIL");
                    //    out.println("root user "+rootUser);   
                    UpdateIPAdress updateIPAdress = new UpdateIPAdress();

                    String errorMessage = updateIPAdress.updateIPAddress(rootUser, ip_address, portNo, serviceName, userName, password);
                    if (errorMessage != null) {
                %>
                <div class="alert alert-danger">
                    <%=errorMessage%> 
                </div>    
                <%
                } else {
                %>
                <div class="alert alert-success">
                    <%="DB_Connectivity Informations are successfully changed."%> 
                </div> 
                <%
                                }
                            }
                        }
                    }

                    String ipAddressFromDb = null, PortNo = "", serviceName, userName, password;

                    UpdateIPAdress updateIPAdress = new UpdateIPAdress();
                    ipAddressFromDb = updateIPAdress.getIPAddressFromDb();
                    PortNo = updateIPAdress.getPORT();
                    serviceName = updateIPAdress.getServiceName();
                    userName = updateIPAdress.getUserName();
                    password = updateIPAdress.getPassword();
                %>

                <table class="table table-responsive">
                    <thead>
                        <tr class="warning">
                            <th>Row No</th>
                            <th>Database IP Address</th>
                            <th>PORT</th>
                            <th>Service Name</th>
                            <th>User Name</th>
                            <th>Password</th> 
                            <th>Edit Information</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="info">
                            <td>1</td>
                            <%
                                if (updateIPAdress.getErrorFlag()) {
                            %>
                            <td class="alert alert-danger"><%=ipAddressFromDb%></td>
                            <%
                            } else {
                            %>  
                            <td class="alert alert-info"><%=ipAddressFromDb%></td>      
                            <%
                                }
                            %>
                            <td><%=PortNo%></td>
                            <td><%=serviceName%></td>
                            <td><%=userName%></td>
                            <td><%=password%></td> 
                            <td>
                                <button type="button" name="btnEditIP" data-toggle="modal" data-target="#myModal" class="sultan_btn">Edit IP</button>
                            </td>
                        </tr> 
                    </tbody>
                </table>

                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Database Connectivity Status</h1>
                    </div>
                </div> 

                <table class="table table-responsive">
                    <thead>
                        <tr class="warning">
                            <th>Row No</th>
                            <th>Connectivity Status</th>
                            <th>Error Message</th>
                            <th>Check Connectivity</th> 
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="info">
                            <td>1</td>
                            <%
                                boolean btnCheckConnectionFlag = false;
                                String errorMessage = null;
                                if ((request.getParameter("btnCheckConnections") == null) ? false : true) {
                                    btnCheckConnectionFlag = true;
                                }
                            %>
                            <td>
                                <%
                                    if (btnCheckConnectionFlag) {
                                        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
                                        if (dbConnectionHandler.getConnectivityStatus()) {
                                            out.println("DB Connection is Successful");
                                        } else {
                                            out.println("DB Connection is Failed");
                                        }
                                        errorMessage = dbConnectionHandler.getErrorMessge();

                                    } else {
                                        out.println("Connection check is not done yet.");
                                    }
                                %>
                            </td>
                            <td>
                                <%
                                    if(btnCheckConnectionFlag){
                                        if (errorMessage == null) {
                                            out.println("null");
                                        } else {
                                            out.println(errorMessage);
                                        }
                                    }else {
                                        out.println("Connection check is not done yet.");
                                    }
                                %>
                            </td>
                            <td>
                                <form action="info_edit.jsp" method="post">
                                    <button type="submit" id="btnCheckConnections" name="btnCheckConnections" class="sultan_btn">Check Connectivity</button>
                                </form>
                            </td>
                        </tr> 
                    </tbody>
                </table>
            </div>

            <!-- Modal HTML -->
            <div id="myModal" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <!-- <form id="ipForm" name="ipForm" class="form-horizontal" role="form" method="post"  action="formProcessIPChange.jsp">  
                        -->
                        <form role="form" action="info_edit.jsp" method="POST">
                            <div class="modal-header modal-header-warning">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title ">Please Enter IP Address</h4>
                            </div>
                            <div class="modal-body"> 
                                <div class="form-group">
                                    <label for="inputIP">IP Address</label>
                                    <input type="text" class="form-control" name="ip"/>
                                    <input type="hidden" id="btnForm" name="btnForm" class="form-control" name="ip"/>
                                </div> 
                                <div class="form-group">
                                    <h4>Port No</h4>   
                                    <input id="portNo" placeholder="port" onkeypress="return isNumberKey(event)" type="text" name="portNo" maxlength="4"> 
                                </div>
                                <div class="form-group">
                                    <h4>Service Name</h4>   
                                    <input id="serviceName" placeholder="service name" type="text" name="serviceName" maxlength="15"> 
                                </div>
                                <div class="form-group">
                                    <h4>User Name</h4>   
                                    <input id="userName" placeholder="user name" type="text" name="userName" maxlength="20"> 
                                </div>
                                <div class="form-group">
                                    <h4>Password</h4>   
                                    <input id="password" placeholder="password"  type="text" name="password" maxlength="20"> 
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-info btn-lg" id="btnSave"  name="btnReg">Save</button> 
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode != 46 && charCode > 31
                        && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script> 
    </div>  
</body> 
</html>
