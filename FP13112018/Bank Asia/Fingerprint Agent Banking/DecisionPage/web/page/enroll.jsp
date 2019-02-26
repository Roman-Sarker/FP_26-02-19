<%-- 
    Document   : enroll.jsp
    Created on : May 16, 2018, 11:42:55 AM
    Author     : root
--%>

<%@page import="com.era.param.Parameter_Fixed_Verify"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enroll Page</title> 
        <link rel="shortcut icon" href="assets/ico/favicon.jpg">
        <script src="assets/js/device_check.js"></script> 
        <script src="assets/js/codeSeen.js"></script> 
        <script type="text/javascript">
            code_seen_off();
        </script>
    </head>
    <body>
        <%
            String name = request.getParameter("name");
            String app_user = request.getParameter("app_user");
            String ai_logid = request.getParameter("ai_logid");
            String cust_type = request.getParameter("cust_type");

            Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();
            if (name == null || name.equals("null") || name.equals("'")
                    || name.trim().equals("") || name.equals("''")) {
                out.println("name is not got by system");
                return;
            } else if (!name.matches("[0-9]+")) {
                out.println("name length "+name.length());
                out.println("name is not got by system<br>");
                out.println("name must be numberic");
                return;

            }
            if (app_user == null) {
                out.println("app_user is not got by system");
                return;
            }
            if (ai_logid == null) {
                out.println("ai_logid is not got by system");
                return;
            }

            if (cust_type == null) {
                out.println("cust_type is not got by system");
                return;
            } else {
                cust_type = param_fixed_verify.getParameter(cust_type, false).trim();
                if (!cust_type.equals("AGN") && !cust_type.equals("cust")
                        && !cust_type.equals("agn") && !cust_type.equals("CUS")) {
                    out.println("appropriate user type is not got by system");
                    return;
                }
            }

            name = param_fixed_verify.getParameter(name, false);
            app_user = param_fixed_verify.getParameter(app_user, false);
            ai_logid = param_fixed_verify.getParameter(ai_logid, true);
            cust_type = param_fixed_verify.getParameter(cust_type, false);
        %>
        
        <Form name="fingerForm"  id="fingerForm" method="Post" action="../EnrollCheck" >
            <Input type="Hidden" name="name"  id="name" value="<%=name%>"> 
            <Input type="Hidden" name="ai_logid"  id="ai_logid" value="<%=ai_logid%>"> 
            <Input type="Hidden" name="app_user"  id="app_user" value="<%=app_user%>">  
            <Input type="Hidden" name="cust_type"  id="user_type" value="<%=cust_type%>"> 
            <Input type="Hidden" name="dName"  id="dName" value="">   
        </Form>

        <object style='height: 0px; width: 0px;' id='objReaderCollection' name='objReaderCollection' classid='clsid:CAC5592F-EBA5-487C-AF8A-F35A70FAA33B'></object>
        <p id="message" style="color:black;font-size:20px" ></p>

        <script type="text/javascript">

            function setActionAndDeviceName(deviceName) {
                document.getElementById("dName").value = deviceName;
                document.getElementById("fingerForm").submit();
            }

            function checkDevice() { 
                if ( checkSecugenDevice()  === true) 
                    setActionAndDeviceName("SG");
                else if (checkDigitalPersonaDevice() === true)
                    setActionAndDeviceName("DP");
                else if (checkFutronicDevice() === true)
                    setActionAndDeviceName("FN");
                else
                    document.getElementById("message").innerHTML= "No Authorised Device Is Connected";
            }
            checkDevice();
        </script>  

        

    </body>
</html>
