
<%-- 
    Document   : verify
    Created on : May 21, 2018, 1:07:31 PM
    Author     : Sultan
--%>

<%@page import="com.era.param.Parameter_Fixed_Verify"%> 
<%@page import="org.apache.http.message.BasicNameValuePair"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.http.NameValuePair"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.era.json.JSONParserPost"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="assets/js/device_check.js"></script> 
        <title>Verify Page</title> 
        <link rel="shortcut icon" href="assets/ico/favicon.jpg">
        <script src="assets/js/codeSeen.js"></script> 
        <script type="text/javascript">
            code_seen_off();
        </script>
    </head>
    <body>
        <object style='height: 0px; width: 0px;' id='objReaderCollection' name='objReaderCollection' classid='clsid:CAC5592F-EBA5-487C-AF8A-F35A70FAA33B'></object>
        <p id="message" style="color:red;font-size:20px" ></p>

        <%
            String ailogid = request.getParameter("ailogid");
            String sessid = request.getParameter("sessid");
            String name = request.getParameter("name");
            String amount = request.getParameter("amount");
            String custno = request.getParameter("custno");
            String appuser = request.getParameter("appuser");
            String opr_type = request.getParameter("opr_type");
            String cust_type = request.getParameter("cust_type"); 
            String standard = request.getParameter("standard");

            /* out.println("name is "+name+"<br>custno is "+custno+"<br>appuser is "+appuser+"<br>opr_type is "+opr_type+
            "cust_type is "+cust_type+"<br>ailogid is "+ailogid+"<br>amount is "+amount+"<br>sessid is "+sessid+"<br>standard is "+standard);  
             */
            
        %>
        
        <Form name="verifyForm"  id="verifyForm" method="Post" action="" >
            <Input type="Hidden" name="name"  id="name" value="<%=name%>"> 
            <Input type="Hidden" name="ailogid"  id="ailogid" value="<%=ailogid%>"> 
            <Input type="Hidden" name="amount"  id="amount" value="<%=amount%>"> 
            <Input type="Hidden" name="sessid"  id="sessid" value="<%=sessid%>"> 
            <Input type="Hidden" name="appuser"  id="appuser" value="<%=appuser%>"> 
            <Input type="Hidden" name="custno"  id="custno" value="<%=custno%>"> 
            <Input type="Hidden" name="opr_type"  id="opr_type" value="<%=opr_type%>"> 
            <Input type="Hidden" name="cust_type"  id="cust_type" value=<%=cust_type%>> 
            <Input type="Hidden" name="standard"  id="standard" value=<%=standard%>> 
        </Form>
        
        <script type="text/javascript">
            function setFormAction(formAction){
                document.getElementById('verifyForm').action = formAction;//"../../enroll2.jsp";
                document.getElementById("verifyForm").submit();
            } 
            
            function checkDevice() {  
               if ( checkSecugenDevice()  === true) 
                    setFormAction("device/SG/verify1.jsp");
                else if (checkDigitalPersonaDevice() === true)
                    setFormAction("device/DP/verify1.jsp");
                else if (checkFutronicDevice() === true)
                    setFormAction("device/FN/verify1.jsp");
                else
                    document.getElementById("message").innerHTML= "No ISO device is connected";
            }
            checkDevice(); 
        </script>
    </body>
</html>
