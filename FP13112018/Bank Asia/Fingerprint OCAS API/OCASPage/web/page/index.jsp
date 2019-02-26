<%-- 
    Document   : index
    Created on : Dec 12, 2017, 6:12:23 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body> 
        <%
            String redirectURL = "../admin/index.jsp";
            response.sendRedirect(redirectURL);
        %>
    </body>
</html>
