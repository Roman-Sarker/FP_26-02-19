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
            String name = request.getParameter("name");
            String cust_no = name;
            String app_user = request.getParameter("app_user");
            String ai_logid = request.getParameter("ai_logid");
            String cust_type = request.getParameter("cust_type");
            String finger = request.getParameter("finger");

           /* out.println("<br><br><br><br><br><br>"
                    + "ailogid is " + alogid + "<br>sessid is " + sessid + "<br>username is " + username
                    + "<br>depamount is " + depamount + "<br>custno is " + custno + "<br>appuser is " + appuser
                    + "<br> logid is " + logid + "<br>cust_type is " + cust_type);
             */
           
            Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();
            if(name== null){
                out.println("cust_no is not got by system");
                return;
            }
           if(app_user== null){
                out.println("app_user is not got by system");
                return;
            }
            if(ai_logid == null){
                out.println("ai_logid is not got by system");
                return;
            }
            if(cust_type == null){
                out.println("cust_type is not got by system");
                return;
            }
            
            if(finger == null){
                out.println("fingerNo is not got by system");
                return;
            }
            
            name = param_fixed_verify.getParameter(out,name, false);
            app_user = param_fixed_verify.getParameter(out,app_user, true);
            ai_logid = param_fixed_verify.getParameter(out,ai_logid, true);
            cust_type = param_fixed_verify.getParameter(out,cust_type, true);
            finger = param_fixed_verify.getParameter(out,finger, false);
            
            /* out.println("<br><br><br><br><br><br>"
                    + "ailogid is " + alogid + "<br>sessid is " + sessid + "<br>username is " + username
                    + "<br>depamount is " + depamount + "<br>custno is " + custno + "<br>appuser is " + appuser
                    + "<br> logid is " + logid + "<br>cust_type is " + cust_type);
             */
        %>

        <Form name="scan"  id="scan" method="Post" action="test.jsp" >
            <Input type="Hidden" name="name"  id="name" value="<%=name%>"> 
            <Input type="Hidden" name="ai_logid"  id="ai_logid" value="<%=ai_logid%>"> 
            <Input type="Hidden" name="app_user"  id="app_user" value="<%=app_user%>">  
            <Input type="Hidden" name="cust_type"  id="cust_type" value="<%=cust_type%>">  
            <Input type="Hidden" name="finger"  id="finger" value=<%=finger%>> 
        </Form>

        <script type="text/javascript">
            document.getElementById("scan").submit();                 
        </script>

    </body>
</html>
