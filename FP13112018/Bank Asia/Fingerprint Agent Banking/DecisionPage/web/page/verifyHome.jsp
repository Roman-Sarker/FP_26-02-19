<%-- 
    Document   : verifyHome
    Created on : Aug 16, 2018, 4:24:33 PM
    Author     : root
--%>
<%-- 
    Document   : verifyHome
    Created on : Sep 19, 2017, 6:03:40 PM
    Author     : Sultan Ahmed
--%>

<%@page import="com.era.param.Parameter_Fixed_Verify"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="assets/js/codeSeen.js"></script> 
        <title>Verify Page</title> 
        <link rel="shortcut icon" href="assets/ico/favicon.jpg">
        <script type="text/javascript">
            codeSeenOff();
        </script>
    </head>
    <body>
        <%  
            String ailogid = request.getParameter("ailogid");
            String sessid = request.getParameter("sessid");
            String name = request.getParameter("name");
            String amount = request.getParameter("amount");
            String custno = request.getParameter("custno");
            String appuser = request.getParameter("appuser"); 
            String opr_type = request.getParameter("opr_type");
            String cust_type = request.getParameter("cust_type");

     /*     out.println("<br><br><br><br><br><br>"
                    + "ailogid is " + ailogid + "<br>sessid is " + sessid + "<br>user_id is " + user_id
                    + "<br>amount is " + amount + "<br>create_by is " + create_by
                    + "<br>user_type is " + user_type);  */ 
            
            if(name == null){
                out.println("name is not got by system");
                return;
            }
            if(ailogid == null){
                out.println("alogid is not got by system");
                return;
            }
            if(sessid == null){
                out.println("sessid is not got by system");
                return;
            }
             
            if(appuser == null){
                out.println("appuser is not got by system");
                return;
            }
            if(opr_type == null){
                out.println("opr_type is not got by system");
                return;
            }
            if(cust_type == null){
                out.println("user_type is not got by system");
                return;
            } 
            
            Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();
            ailogid = param_fixed_verify.getParameter(ailogid, false);
            sessid = param_fixed_verify.getParameter(sessid, false);
            name = param_fixed_verify.getParameter(name, false);
             
             if(custno != null)
                 custno  = param_fixed_verify.getParameter(custno, false);
            if(amount != null)
                 amount = param_fixed_verify.getParameter(amount, false);
            
            opr_type = param_fixed_verify.getParameter(opr_type, false).trim();
            appuser = param_fixed_verify.getParameter(appuser, false); 
            cust_type = param_fixed_verify.getParameter(cust_type, false);
            
            /*  out.println("<br><br><br><br><br><br>"
                    + "ailogid is " + alogid + "<br>sessid is " + sessid + "<br>username is " + username
                    + "<br>depamount is " + depamount + "<br>custno is " + custno + "<br>appuser is " + appuser
                    + "<br> logid is " + logid + "<br>cust_type is " + cust_type);   */
            
        %>

        <Form name="verifyFingerForm"  id="verifyFingerForm" method="Post" action="../verifyHome" >
            <Input type="Hidden" name="name"  id="name" value="<%=name%>"> 
            <Input type="Hidden" name="ailogid"  id="ailogid" value="<%=ailogid%>"> 
            <Input type="Hidden" name="amount"  id="amount" value="<%=amount%>"> 
            <Input type="Hidden" name="sessid"  id="sessid" value="<%=sessid%>"> 
            <Input type="Hidden" name="appuser"  id="appuser" value="<%=appuser%>"> 
            <Input type="Hidden" name="custno"  id="custno" value="<%=custno%>"> 
            <Input type="Hidden" name="opr_type"  id="opr_type" value="<%=opr_type%>"> 
            <Input type="Hidden" name="cust_type"  id="cust_type" value=<%=cust_type%>> 
        </Form>
        
        <script type="text/javascript">
           document.getElementById("verifyFingerForm").submit();                 
        </script>

    </body>
</html>