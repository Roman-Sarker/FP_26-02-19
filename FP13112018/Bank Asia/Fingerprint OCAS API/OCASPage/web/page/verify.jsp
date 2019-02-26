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
            String cust_no = request.getParameter("cust_no");
            String cust_type = request.getParameter("cust_type");
            out.println("cust_no is "+cust_no);   
            
            Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();
            cust_no = param_fixed_verify.getParameter(out,cust_no, false);
            cust_type = param_fixed_verify.getParameter(out,cust_type, false);
            
        %>

        <Form name="scan"  id="scan" method="Post" action="logonHome.jsp" >
            <Input type="Hidden" name="cust_no"  id="cust_no" value="<%=cust_no%>"> 
            <Input type="Hidden" name="cust_type"  id="cust_type" value="<%=cust_type%>">  
        </Form>

        <script type="text/javascript">
            document.getElementById("scan").submit();                 
        </script>

    </body>
</html>