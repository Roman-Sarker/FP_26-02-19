<%-- 
    Document   : famverifyHome
    Created on : Sep 17, 2017, 3:30:25 PM
    Author     : Sultan Ahmed
--%>

<%@page import="com.era.IPAddress.UpdateIPAdress"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.io.IOException"%> 
<%@page import="org.json.JSONArray"%>
<%@page import="com.era.json.JSONParserPost"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.era.fingerprint.verify.VerifyData"%>
<%@page import="com.era.Global.Global"%>
<%@ page errorPage="error.jsp" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<HTML>
    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">
        <style TYPE="text/css">
            <!-- BODY				{ font-family:arial,helvetica; margin-left:5; margin-top:0}
            A 					{ color:#FF5500; text-decoration:underline}
            A:hover,A:active	{ color:#0055FF; text-decoration:underline}
            -->
        </style>

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
        <title>Logon</title>
    </HEAD>
    <BODY>
        <%
 
            String cust_no = request.getParameter("cust_no"); 
            String sampleModel = request.getParameter("SampleModel");
            String cust_type = request.getParameter("cust_type"); 
            
            Global global = new Global();
            byte[] fingerData = global.getFingerData(sampleModel);
            //  out.println(data.length);

            VerifyData verifyData = new VerifyData();
            verifyData.setCust_type(cust_type);
            verifyData.setCust_no(cust_no);
            verifyData.setFingerData(fingerData);
            
            
            String jsonString = global.objectMappingToJsonString(verifyData, response);
            if (jsonString == null) {
                out.println("error : json string sent to webservice is null");
                return;
            }
            out.println(jsonString);
            
            UpdateIPAdress updateIPAdress = new UpdateIPAdress();            
            String IPAdress = updateIPAdress.getIPAddressFromDb() ;

            if(updateIPAdress.getErrorFlag()){
                out.println("Error in famVerifyHome.jsp : "+IPAdress);
                return;
            }
            
            String PORT = updateIPAdress.getPORT() ;
            String URL="http://"+IPAdress+":"+PORT+"/OCASAPI/FingerVerification";

            
            JSONParserPost jsonParserpost = new JSONParserPost();
            JSONObject output = jsonParserpost.makeHttpRequest(URL, "POST", jsonString);
            if (output != null) {
                out.println("");
            } else {
                out.println("Web service is not running or Internet connectivity problem");
                return;
            }
            out.println(output.toString());    
            
            JSONArray jsonArray = output.getJSONArray("Result");
            if (jsonArray == null) {
                out.println("Data is not come in proper format from web service.");
                return;
            } else {
                // for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String errorFlag = jsonObject.getString("resultCode");
                String errorMessage = jsonObject.getString("resultMessage");

                if (errorFlag.equals("N")) {

                    if (cust_type.equals("agent")) {
                        out.println("<font style=\"text-align:justify;\" size=\"2\">Finger Verification is Successful for the agent&nbsp;</font><font size=\"2\" color=\"#ff0000\"><b></b ></ font > ");
                    } else if (cust_type.equals("cust")) {
                        out.println("<font style=\"text-align:justify;\" size=\"2\">Finger Verification is Successful for the customer&nbsp;</font><font size=\"2\" color=\"#ff0000\"><b></b ></ font > ");
                    } else if (cust_type.equals("bo")) {
                        out.println("<font style=\"text-align:justify;\" size=\"2\">Finger Verification is Successful for the Officer&nbsp;</font><font size=\"2\" color=\"#ff0000\"><b></b ></ font > ");
                    } else if (cust_type.equals("usr")) {
                        out.println("<font style=\"text-align:justify;\" size=\"2\">Finger Verification is Successful for the User&nbsp;</font><font size=\"2\" color=\"#ff0000\"><b></b ></ font > ");
                    } else {
                        out.println("cust_type must be \'cust\' or \'agent\'<br> or \'officer\' or \'user\'");
                    }  
                } else {
                     out.println("<p style=\"color:#900\">Sorry! Finger Verification is failed. Please <a href=\"verify.jsp?cust_no="+cust_no+"&cust_type="+cust_type+"\" >Try Again</a> </p>");
                 }
            }
        %>
    </BODY>
</html>