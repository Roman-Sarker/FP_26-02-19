<%@page import="com.era.admin.RestAPIInfo"%>
<%@page import="com.era.admin.GetRestAPIInfo"%>
<%@page import="com.era.restApi.RestAPICheck"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Finger Authentication</title> 
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <link rel="stylesheet" href="assets/css/AdminLTE.min.css">  <!-- Theme style -->
        <link rel="stylesheet" href="assets/css/_all-skins.min.css">
        <link rel="stylesheet" href="assets/css/blue.css">   <!-- iCheck -->
        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/app.min.js"></script>
        <script src="assets/js/demo.js"></script>
        <link rel="shortcut icon" href="assets/ico/favicon.jpg">
        <style>
            .modal-dialog-center {
                margin-top: 10%;
            }
        </style>
    </head>

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">
            <%
                String loginFlag = (String) session.getAttribute("LOGIN");
                if (loginFlag == null) {
                    String redirectURL = "index.jsp";
                    response.sendRedirect(redirectURL);
                }

                RestAPIInfo restAPIInfo = GetRestAPIInfo.getRestAPIInfo();
                String ip = "", port = "", serviceName = "", uName = "", password = "";
                if (restAPIInfo != null) {
                    ip = restAPIInfo.ip;
                    port = restAPIInfo.portNo;
                } else {
                    ip = "error";
                    port = "error";
                }

                String error_msg = request.getParameter("eMessage");
                String success_msg = request.getParameter("sMessage");
                String dbeMessage = request.getParameter("dbeMessage");
                String dbsMessage = request.getParameter("dbsMessage");

                String dbConnStatus = "", dbConnStatusReason = "";
                if (dbeMessage != null) {
                    dbConnStatus = "Rest API checking is Failed";
                    dbConnStatusReason = dbeMessage;
                } else if (dbsMessage != null) {
                    dbConnStatus = "Rest API connection checking is Successfull";
                    dbConnStatusReason = null;
                } else {
                    dbConnStatus = "Rest API connection is not check yet";
                    dbConnStatusReason = "Rest API connection is not check yet";
                }
                String contextPath = request.getContextPath();
            %>
            <jsp:include page="topbar.jsp" />
            <jsp:include page="sidebar.jsp" />

            <div class="content-wrapper">
                <section class="content-header">
                </section>

                <section class="content">
                    <div class="row">
                        <%
                            if (error_msg != null) {%>
                        <div class="alert alert-danger">
                            <%=error_msg%> 
                        </div>
                        <%
                            }
                        %>
                        <%
                            if (success_msg != null) {%>
                        <div class="alert alert-success"> 
                            <%=success_msg%> 
                        </div>
                        <%
                            }
                        %>
                    </div>
                    <div class="row">
                        <div class="col-md-10">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Matching Server Connectivity Information</h3>
                                </div>
                                <div class="box-body">
                                    <table class="table table-bordered">
                                        <tr>
                                            <th>#</th>
                                            <th>Matching Server Ip Address</th>
                                            <th>Port</th>
                                            <th>Edit Information</th>
                                        </tr>
                                        <tr>       
                                            <td>1.</td>
                                            <td><%=ip%></td>
                                            <td><%=port%></td>
                                            <td><button type="button" name="btnEditIP"  data-toggle="modal" data-target="#myModal"  class="btn btn-block btn-info">Update Information</button></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section class="content">
                    <div class="row">
                        <div class="col-md-10">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Matching Server Connectivity Status</h3>
                                </div>
                                <div class="box-body">
                                    <table class="table table-bordered">
                                        <tr>
                                            <th>Row No</th>
                                            <th>Rest API Test Result</th>
                                            <th>Check Response</th>
                                        </tr>

                                        <%
                                            boolean restApiCheckFlag = false, btnPressFlag = false;
                                            if ((request.getParameter("checkRestAPIbtn") == null) ? false : true) {
                                                restApiCheckFlag = RestAPICheck.checkRestAPI();
                                                btnPressFlag = true;
                                            }
                                        %>   

                                        <tr>  
                                            <td>1.</td>
                                            <td>
                                                <%
                                                    if (btnPressFlag && restApiCheckFlag) {
                                                        out.println("Rest API is enabled");
                                                    } else if (btnPressFlag && !restApiCheckFlag) {
                                                        out.println("No response comes from  Rest API");
                                                    } else {
                                                        out.println("Rest API testing is not started yet.");
                                                    }
                                                %> 
                                            </td>

                                            <td>
                                                <form id="myForm" role="form" action="info_edit.jsp" method="POST">
                                                    <button name="checkRestAPIbtn" class="btn btn-block btn-info">Check Connectivity</button>
                                                </form>    
                                            </td>
                                        </tr>

                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <jsp:include page="footer.jsp" />
            <div class="control-sidebar-bg"></div>
        </div>


        <div id="myModal"  class="modal">
            <div class="modal-dialog modal-dialog-center">
                <div class="modal-content">
                    <form id="myForm" role="form" action="<%=contextPath%>/IP_Info_Insert" method="POST" data-toggle="validator">
                        <div class="modal-header alert-info">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Please Enter Matching Server Information</h4>
                        </div>
                        <div class="modal-body" style="margin-left:2%;margin-right:2%">
                            <div class="row"> 
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="row"> 
                                        <h4>IP Address</h4>  
                                        <input type="text"  placeholder="ip address" class="form-control" onkeypress="return isNumberKey(event)" required pattern="((^|\.)((25[0-5])|(2[0-4]\d)|(1\d\d)|([1-9]?\d))){4}$" maxlength="15" name="ip" id="ip" required/>
                                    </div>
                                </div>
                                <div class="col-md-2">

                                </div>
                                <div class="col-md-4">
                                    <div class="row"> 
                                        <h4>Port No</h4>   
                                        <input id="portNo" placeholder="port" class="form-control" onkeypress="return isNumberKey(event)" type="text" name="portNo" maxlength="4" required> 
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                else
                    return true;
            }
        </script> 


    </body>
</html>
