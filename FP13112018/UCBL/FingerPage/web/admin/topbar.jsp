<div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
    </button>
    <a  href="home.jsp">
        <img src="assets/img/logo.png" alt="" />
    </a>
</div>
<%
    if ((request.getParameter("logout") == null) ? false : true) {
        session.setAttribute("LOGIN", null);
        session.invalidate();
        response.sendRedirect("index.jsp");
    }
%>

<ul class="nav navbar-top-links navbar-right">
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
            <i class="fa fa-user fa-3x"></i>
        </a> 
        <ul class="dropdown-menu dropdown-user">
            <li><a href="#"><i class="fa fa-user fa-fw"></i>User Profile</a>
            </li>
            <li><a href="#"><i class="fa fa-gear fa-fw"></i>Settings</a>
            </li>
            <li class="divider"></li>
            <li>
                <form role="form" action="topbar.jsp" method="POST">
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-2">
                        <button  name="logout" class="btn  btn-primary btn-lg">Logout</button>
                    </div>
                </form>
            </li>
        </ul>
        <!-- end dropdown-user -->
    </li>
    <!-- end main dropdown -->
</ul>