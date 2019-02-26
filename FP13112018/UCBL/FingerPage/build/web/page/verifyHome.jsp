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
        <title>JSP Page</title>
        <script type="text/javascript">
            if (document.layers) {
                //Capture the MouseDown event.
                document.captureEvents(Event.MOUSEDOWN);

                //Disable the OnMouseDown event handler.
                document.onmousedown = function () {
                    return false;
                };
            } else {
                //Disable the OnMouseUp event handler.
                document.onmouseup = function (e) {
                    if (e != null && e.type == "mouseup") {
                        //Check the Mouse Button which is clicked.
                        if (e.which == 2 || e.which == 3) {
                            //If the Button is middle or right then disable.
                            return false;
                        }
                    }
                };
            }

            //Disable the Context Menu event.
            document.oncontextmenu = function () {
                return false;
            };
        </script>
    </head>
    <body>
        <%  //onLoad="GetSampleModel()"
            String alogid = request.getParameter("ailogid");
            String sessid = request.getParameter("sessid");
            String username = request.getParameter("name");
            String depamount = request.getParameter("amount");
            String custno = request.getParameter("custno");
            String appuser = request.getParameter("appuser");
            String logid = request.getParameter("ailogid");
            String opr_type = request.getParameter("opr_type");
            String cust_type = request.getParameter("cust_type");

         /*   out.println("<br><br><br><br><br><br>"
                    + "ailogid is " + alogid + "<br>sessid is " + sessid + "<br>username is " + username
                    + "<br>depamount is " + depamount + "<br>custno is " + custno + "<br>appuser is " + appuser
                    + "<br> logid is " + logid + "<br>cust_type is " + cust_type);  */
            
            if(alogid == null){
                out.println("alogid is not got by system");
                return;
            }
            if(sessid == null){
                out.println("sessid is not got by system");
                return;
            }
            if(username == null){
                out.println("name is not got by system");
                return;
            }
            if(appuser == null){
                out.println("appuser is not got by system");
                return;
            }
            if(logid == null){
                out.println("logid is not got by system");
                return;
            }if(opr_type == null){
                out.println("opr_type is not got by system");
                return;
            }if(cust_type == null){
                out.println("cust_type is not got by system");
                return;
            }
            Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();
            alogid = param_fixed_verify.getParameter(out,alogid, true);
            sessid = param_fixed_verify.getParameter(out,sessid, true);
            username = param_fixed_verify.getParameter(out,username, false);
            if(depamount != null)
                 depamount = param_fixed_verify.getParameter(out,depamount, false);
            custno = param_fixed_verify.getParameter(out,custno, false);
            appuser = param_fixed_verify.getParameter(out,appuser, true);
            logid = param_fixed_verify.getParameter(out,logid, true);
            opr_type = param_fixed_verify.getParameter(out,opr_type, true);
            cust_type = param_fixed_verify.getParameter(out,cust_type, true);
            
              out.println("<br><br><br><br><br><br>"
                    + "ailogid is " + alogid + "<br>sessid is " + sessid + "<br>username is " + username
                    + "<br>depamount is " + depamount + "<br>custno is " + custno + "<br>appuser is " + appuser
                    + "<br> logid is " + logid + "<br>cust_type is " + cust_type);   
            
        %>

        <Form name="scan"  id="scan" method="Post" action="logonHome.jsp" >
            <Input type="Hidden" name="name"  id="name" value="<%=username%>"> 
            <Input type="Hidden" name="ailogid"  id="ailogid" value="<%=logid%>"> 
            <Input type="Hidden" name="amount"  id="amount" value="<%=depamount%>"> 
            <Input type="Hidden" name="sessid"  id="sessid" value="<%=sessid%>"> 
            <Input type="Hidden" name="appuser"  id="appuser" value="<%=appuser%>"> 
            <Input type="Hidden" name="custno"  id="custno" value="<%=custno%>"> 
            <Input type="Hidden" name="opr_type"  id="opr_type" value="<%=opr_type%>"> 
            <Input type="Hidden" name="cust_type"  id="cust_type" value=<%=cust_type%>> 
        </Form>

        <script type="text/javascript">
            document.getElementById("scan").submit();                 
        </script>

    </body>
</html>