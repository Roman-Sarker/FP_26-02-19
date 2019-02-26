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
            String user_id = request.getParameter("user_id"); 
            String create_by = request.getParameter("create_by");
            String ai_logid = request.getParameter("ai_logid");
            String user_type = request.getParameter("user_type");  

           /* out.println("<br><br><br><br><br><br>"
                    + "ailogid is " + alogid + "<br>sessid is " + sessid + "<br>username is " + username
                    + "<br>depamount is " + depamount + "<br>custno is " + custno + "<br>appuser is " + appuser
                    + "<br> logid is " + logid + "<br>cust_type is " + cust_type);
             */
           
            Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();
            if(user_id== null){
                out.println("user_id is not got by system");
                return;
            }
           if(create_by== null){
                out.println("create_by is not got by system");
                return;
            }
            if(ai_logid == null){
                out.println("ai_logid is not got by system");
                return;
            }
            
            if(user_type == null){
                out.println("user_type is not got by system");
                return;
            }
            else{ 
                user_type = param_fixed_verify.getParameter(out,user_type,false).trim(); 
                if(!user_type.equals("agent") && !user_type.equals("cust")
                        && !user_type.equals("agn") && !user_type.equals("CUS")){
                    out.println("appropriate user type is not got by system");
                    return;
                }
            } 
            
            user_id = param_fixed_verify.getParameter(out,user_id, false);
            create_by = param_fixed_verify.getParameter(out,create_by, false); 
            ai_logid = param_fixed_verify.getParameter(out,ai_logid, true); 
            user_type = param_fixed_verify.getParameter(out,user_type, false);  
            
            /* out.println("<br><br><br><br><br><br>"
                    + "ailogid is " + alogid + "<br>sessid is " + sessid + "<br>username is " + username
                    + "<br>depamount is " + depamount + "<br>custno is " + custno + "<br>appuser is " + appuser
                    + "<br> logid is " + logid + "<br>cust_type is " + cust_type);
             */
        %>

        <Form name="scan"  id="scan" method="Post" action="enroll1.jsp" >
            <Input type="Hidden" name="user_id"  id="user_id" value="<%=user_id%>"> 
            <Input type="Hidden" name="ai_logid"  id="ai_logid" value="<%=ai_logid%>"> 
            <Input type="Hidden" name="create_by"  id="create_by" value="<%=create_by%>">  
            <Input type="Hidden" name="user_type"  id="user_type" value="<%=user_type%>">   
        </Form>

        <script type="text/javascript">
            document.getElementById("scan").submit();                 
        </script>

    </body>
</html>