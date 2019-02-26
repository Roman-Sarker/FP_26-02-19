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
        <%   
            String user_id = request.getParameter("user_id");
            String ailogid = request.getParameter("ailogid");
            String pAcId = request.getParameter("pAcId");
            String sessid = request.getParameter("sessid"); 
            String amount = request.getParameter("amount");
            String create_by = request.getParameter("create_by"); 
            String opr_type = request.getParameter("opr_type");
            String user_type = request.getParameter("user_type");

     /*     out.println("<br><br><br><br><br><br>"
                    + "ailogid is " + ailogid + "<br>sessid is " + sessid + "<br>user_id is " + user_id
                    + "<br>amount is " + amount + "<br>create_by is " + create_by
                    + "<br>user_type is " + user_type);  */ 
            
            if(user_id == null){
                out.println("user_id is not got by system");
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
             
            if(create_by == null){
                out.println("create_by is not got by system");
                return;
            }
            if(opr_type == null){
                out.println("opr_type is not got by system");
                return;
            }
            if(user_type == null){
                out.println("user_type is not got by system");
                return;
            } 
            
            Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();
            ailogid = param_fixed_verify.getParameter(out,ailogid, false);
            sessid = param_fixed_verify.getParameter(out,sessid, false);
            user_id = param_fixed_verify.getParameter(out,user_id, false);
             if(pAcId != null)
                 pAcId  = param_fixed_verify.getParameter(out,pAcId, false);
            if(amount != null)
                 amount = param_fixed_verify.getParameter(out,amount, false);
            
            opr_type = param_fixed_verify.getParameter(out,opr_type, false).trim();
                  
             
            create_by = param_fixed_verify.getParameter(out,create_by, false); 
            user_type = param_fixed_verify.getParameter(out,user_type, false);
            
            /*  out.println("<br><br><br><br><br><br>"
                    + "ailogid is " + alogid + "<br>sessid is " + sessid + "<br>username is " + username
                    + "<br>depamount is " + depamount + "<br>custno is " + custno + "<br>appuser is " + appuser
                    + "<br> logid is " + logid + "<br>cust_type is " + cust_type);   */
            
        %>

        <Form name="scan"  id="scan" method="Post" action="verify1.jsp" >
            <Input type="Hidden" name="user_id"  id="user_id" value="<%=user_id%>">  
            <Input type="Hidden" name="amount"  id="amount" value="<%=amount%>"> 
            <Input type="Hidden" name="sessid"  id="sessid" value="<%=sessid%>"> 
            <Input type="Hidden" name="ailogid"  id="ailogid" value="<%=ailogid%>"> 
            <Input type="Hidden" name="create_by"  id="create_by" value="<%=create_by%>">  
            <Input type="Hidden" name="opr_type"  id="opr_type" value="<%=opr_type%>"> 
            <Input type="Hidden" name="user_type"  id="user_type" value=<%=user_type%>> 
            <Input type="Hidden" name="pAcId"  id="pAcId" value=<%=pAcId%>> 
        </Form>
        
        <script type="text/javascript">
           document.getElementById("scan").submit();                 
        </script>

    </body>
</html>