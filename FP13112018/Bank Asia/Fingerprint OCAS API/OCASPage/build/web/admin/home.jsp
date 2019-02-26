<%@page import="era.system.information.FileDescriptor"%>
<%@page import="era.system.information.PhysicalMemoryInfo"%>
<%@page import="era.system.information.PhysicalMemoryAndFileDescriptor"%>
<%@page import="era.system.information.FileInfo"%>
<%@page import="era.system.information.FileInfoRetrieve"%>
<%@page import="era.system.information.MemoryInformation"%>
<%@page import="era.system.information.SystemInfoRetrieve"%>
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
        <!-- Page-Level CSS -->
        <link href="assets/plugins/morris/morris-0.4.3.min.css" rel="stylesheet" />
    </head>
    <body>

        <%
            if (session.getAttribute("LOGIN") == null) {
                String redirectURL = "index.jsp";
                response.sendRedirect(redirectURL);
            }

            String fName = (String) session.getAttribute("fName");
            String lName = (String) session.getAttribute("lName");
        %>

        <!--  wrapper -->
        <div id="wrapper">
            <!-- navbar top -->
            <nav class="navbar navbar-default navbar-fixed-top" role="navigation" id="navbar">


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
                        <h1 class="page-header">Dashboard</h1>
                    </div>
                    <!--End Page Header -->
                </div>

                <div class="row">
                    <!-- Welcome -->
                    <div class="col-lg-12">
                        <div class="alert alert-info">
                            <i class="fa fa-folder-open"></i><b>&nbsp;Hello ! </b>Welcome Back <b><%=fName + " " + lName%></b>
                            <i class="fa  fa-pencil"></i><b>&nbsp;A lot of </b>Support Tickets Pending to Answere. 
                        </div>
                    </div>
                    <!--end  Welcome -->
                </div>


                <div class="row">

                    <%
                        MemoryInformation memoryInformation = SystemInfoRetrieve.getMemoryInfoRetrieve();
                    %>

                    <!--quick info section -->
                    <div class="col-lg-3">
                        <div class="alert alert-danger text-center">
                            <i class="fa fa-calendar fa-3x"></i>&nbsp;
                            <b><%out.println(memoryInformation.getAvailableProcessors());%></b> 
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="alert alert-success text-center">
                            <i class="fa  fa-beer fa-3x"></i>&nbsp;
                            <b><%out.println(SystemInfoRetrieve.getCpuUsage() + "% ");%></b> CPU usage in this server  
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="alert alert-info text-center">
                            <i class="fa fa-rss fa-3x"></i>&nbsp;<b>1,900</b> New Subscribers This Year

                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="alert alert-warning text-center">
                            <i class="fa  fa-pencil fa-3x"></i>&nbsp;<b>2,000 $ </b>Payment Dues For Rejected Items
                        </div>
                    </div>
                    <!--end quick info section -->
                </div>

                <div class="row">
                    <div class="col-lg-3">
                        <div class="panel panel-primary text-center no-boder">
                            <div class="panel-body yellow">
                                <i class="fa fa-bar-chart-o fa-3x"></i>
                                <h4><% out.println(memoryInformation.getFreeMemory());%></h4>
                                <h4><% out.println(memoryInformation.getMaxMemory());%></h4>
                                <h4><% out.println(memoryInformation.getTotalMemory());%></h4>
                                <h4><br></h4>
                            </div>
                            <div class="panel-footer">
                                <span class="panel-eyecandy-title">RAM Information</span>
                            </div>
                        </div>
                    </div>   

                    <%
                        FileInfo fileInfo = FileInfoRetrieve.getFileInfo();
                    %>        
                    <div class="col-lg-3">
                        <div class="panel panel-primary text-center no-boder">
                            <div class="panel-body blue">
                                <i class="fa fa-pencil-square-o fa-3x"></i>
                                <h4><%out.println(fileInfo.getRootFileSystem());%></h4>
                                <h4><%out.println(fileInfo.getFreeSpace());%></h4>
                                <h4><%out.println(fileInfo.getTotalSpace());%></h4>
                                <h4><%out.println(fileInfo.getUsableSpace());%></h4>
                            </div>
                            <div class="panel-footer">
                                <span class="panel-eyecandy-title">File System Information
                                </span>
                            </div>
                        </div>
                    </div> 
                    <%
                        PhysicalMemoryAndFileDescriptor physicalMemoryAndFileDescriptor = new PhysicalMemoryAndFileDescriptor();
                        physicalMemoryAndFileDescriptor.getMemoryAndFileInfo();
                        FileDescriptor fileDescriptor = physicalMemoryAndFileDescriptor.getFileDescriptor();
                        PhysicalMemoryInfo physicalMemoryInfo = physicalMemoryAndFileDescriptor.getPhysicalMemoryInfo();
                    %>        
                    <div class="col-lg-3"> 
                        <div class="panel panel-primary text-center no-boder">
                            <div class="panel-body green">
                                <i class="fa fa fa-floppy-o fa-3x"></i>
                                <h4><%out.println(fileDescriptor.getOpenFileDescriptorCount());%>></h4>
                                <h4><%out.println(fileDescriptor.getMaxFileDescriptorCount());%></h4>
                                <h4><br></h4> 
                            </div>
                            <div class="panel-footer">
                                <span class="panel-eyecandy-title">File Descriptor Information
                                </span>
                            </div>
                        </div>
                    </div> 
                    <div class="col-lg-3"> 
                        <div class="panel panel-primary text-center no-boder">
                            <div class="panel-body red">
                                <i class="fa fa-thumbs-up fa-3x"></i>
                                <h4><%out.println(physicalMemoryInfo.getCommittedVirtualMemorySize());%></h4>
                                <h4><%out.println(physicalMemoryInfo.getFreePhysicalMemorySize());%></h4>
                                <h4><%out.println(physicalMemoryInfo.getFreeSwapSpaceSize());%></h4>
                                <h4><%out.println();%></h4>
                            </div>
                            <div class="panel-footer">
                                <span class="panel-eyecandy-title">Swap Memory Information
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
        </div> 

        <!-- Core Scripts - Include with every page -->
        <script src="assets/plugins/jquery-1.10.2.js"></script>
        <script src="assets/plugins/bootstrap/bootstrap.min.js"></script>
        <script src="assets/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="assets/plugins/pace/pace.js"></script>
        <script src="assets/scripts/siminta.js"></script>
        <!-- Page-Level Plugin Scripts-->
        <script src="assets/plugins/morris/raphael-2.1.0.min.js"></script>
        <script src="assets/plugins/morris/morris.js"></script>
        <script src="assets/scripts/dashboard-demo.js"></script>

    </body>

</html>
