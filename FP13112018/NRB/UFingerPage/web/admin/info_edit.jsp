<%@page import="com.era.restApi.RestAPICheck"%>
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
        <!--  wrapper -->
        <div id="wrapper">
            <!-- navbar top -->
            <nav class="navbar navbar-default navbar-fixed-top" role="navigation" id="navbar">

                <!-- end navbar-header -->

                <!-- navbar-top-links -->
                <jsp:include page="topbar.jsp" />
                <!-- end navbar-top-links -->

            </nav>
            <!-- end navbar top -->

            <!-- navbar side -->
            <jsp:include page="sidebar.jsp" />
            <!-- end navbar side -->

            <!--  page-wrapper -->
            <div id="page-wrapper">

                <div class="row">
                    <!-- Page Header -->
                    <div class="col-lg-12">
                        <h1 class="page-header">Web Service Information</h1>
                    </div>
                    <!--End Page Header -->
                </div>  

                <%
                    if ((request.getParameter("btnForm") == null) ? false : true) {

                        String ip_address = request.getParameter("ip");
                        String portNo = request.getParameter("portNo");

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

                    out.println("root user is " + rootUser);
                    out.println("ip_address is " + ip_address);
                    out.println("portNo is " + portNo);

                    UpdateIPAdress updateIPAdress = new UpdateIPAdress();
                    String errorMessage = updateIPAdress.updateIPAddress(rootUser, ip_address, portNo);

                    if (errorMessage != null) {
                %>
                <div class="alert alert-danger">
                    <%=errorMessage%> 
                </div>    
                <%
                } else {
                %>
                <div class="alert alert-success">
                    <%="IP Address and port no are successfully changed."%> 
                </div> 
                <%
                                }
                            }
                        }
                    }

                    String ipAddressFromDb = null, PortNo = "";
                    UpdateIPAdress updateIPAdress = new UpdateIPAdress();
                    ipAddressFromDb = updateIPAdress.getIPAddressFromDb();
                    PortNo = updateIPAdress.getPORT();
                %>

                <table class="table table-responsive">
                    <thead>
                        <tr class="warning">
                            <th>Row No</th>
                            <th>Web Service IP Address</th>
                            <th>PORT</th>
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
                            <td>
                                <button type="button" name="btnEditIP" data-toggle="modal" data-target="#myModal" class="sultan_btn">Edit IP</button>
                            </td>
                        </tr> 
                    </tbody>
                </table>

                <div class="row">
                    <!-- Page Header -->
                    <div class="col-lg-12">
                        <h1 class="page-header">Web Service Response Testing</h1>
                    </div>
                    <!--End Page Header -->
                </div> 

                <table class="table table-responsive">
                    <thead>
                        <tr class="warning">
                            <th>Row No</th>
                            <th>Rest API Test Result</th>
                            <th>Check Response</th>
                        </tr>
                    </thead>
                    <%
                       boolean restApiCheckFlag = false,btnPressFlag = false;
                       if ((request.getParameter("checkRestAPIbtn") == null) ? false : true) {
                           restApiCheckFlag = RestAPICheck.checkRestAPI();
                           btnPressFlag = true;
                       }
                    %>   
                    
                    <tbody>
                        <tr class="info">
                            <td>1</td>
                            <td>
                                <%
                                   if(btnPressFlag && restApiCheckFlag)
                                       out.println("Rest API is enabled");
                                   else if(btnPressFlag && !restApiCheckFlag)
                                       out.println("No response comes from  Rest API");
                                   else
                                        out.println("Rest API testing is not started yet.");
                                %> 
                            </td>
                            <td>
                                <form action="info_edit.jsp" method="post">
                                    <button type="submit" name="checkRestAPIbtn" class="sultan_btn">Check Rest API</button>
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
                                    <input  class="form-control" id="portNo" placeholder="port" onkeypress="return isNumberKey(event)" type="text" name="portNo" maxlength="4"> 
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
                            
        <jsp:include page="footer.jsp" />
        
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
