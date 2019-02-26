<%-- 
    Document   : famverifyHome
    Created on : Sep 17, 2017, 3:30:25 PM
    Author     : Sultan Ahmed
--%>
 
<%@page import="com.era.admin.GetRestAPIInfo"%>
<%@page import="com.era.admin.RestAPIInfo"%> 
<%@page import="java.sql.SQLException"%>
<%@page import="java.io.IOException"%> 
<%@page import="org.json.JSONArray"%>
<%@page import="com.era.json.JSONParserPost"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.era.fingerprint.verify.VerifyData"%>
<%@page import="com.era.Global.Global"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<HTML>
    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">
        <title>Verify Page</title> 
        <link rel="shortcut icon" href="assets/ico/favicon.jpg">
        <script src="assets/js/codeSeen.js"></script> 
        <script type="text/javascript">
            code_seen_off();
        </script>
        <style TYPE="text/css">
            <!-- BODY				{ font-family:arial,helvetica; margin-left:5; margin-top:0}
            A 					{ color:#FF5500; text-decoration:underline}
            A:hover,A:active	{ color:#0055FF; text-decoration:underline}
            -->
        </style>        
    </HEAD>
    <BODY>
        <%
            
            String name = request.getParameter("name");
            String custno = request.getParameter("custno");
            String appuser = request.getParameter("appuser");
            String opr_type = request.getParameter("opr_type");
            String cust_type = request.getParameter("cust_type");
            String ailogid = request.getParameter("ailogid");
            String amount = request.getParameter("amount");
            String sessid = request.getParameter("sessid");
            String standard = request.getParameter("standard");
            
//            int i = 1; 
//            if(i==1){
//                out.println("accha pore kotha hobe");
//                return;
//            }
            
            String serial = request.getParameter("Sl");    
            String fingerData = request.getParameter("fingerData"); 
            opr_type = opr_type.trim(); 
             
            
           /* out.println("name is "+name+"<br>"); 
            out.println("ailogid is "+ailogid+"<br>");
            out.println("appuser is "+appuser+"<br>");
            out.println("sessid is "+sessid+"<br>");
            out.println("amount is "+amount+"<br>");
            out.println("opr_type is "+opr_type+"<br>");
            out.println("cust_type is "+cust_type+"<br>"); 
            out.println("standard is "+standard+"<br>"); 
            out.println("serial is "+serial+"<br>");  */
              
            Global global = new Global(); 
            //  out.println(data.length);
 
            VerifyData verifyData = new VerifyData();
            verifyData.setCustno(custno);
            verifyData.setName(name); 
            verifyData.setpOperationType(opr_type.toUpperCase());
            verifyData.setpLogId(ailogid);
            verifyData.setpSessionId(sessid);
            verifyData.setCust_type(cust_type); 
            verifyData.setpDeviceId(serial);
            verifyData.setAppuser(appuser);
            verifyData.setAmount(amount);  
            verifyData.setTemp(fingerData);
            
            String jsonString = global.objectMappingToJsonString(verifyData, response);
            // out.println(jsonString);
              
           if (jsonString == null) {
                out.println("error : json string sent to webservice is null");
                return;
            }
            
            RestAPIInfo restAPIInfo = GetRestAPIInfo.getRestAPIInfo();
            if (restAPIInfo == null) {
                out.println("web service parameter error in enroll2 page");
                return;
            }

            String IPAdress = restAPIInfo.ip;
            String PORT = restAPIInfo.portNo;

            if (IPAdress == null || PORT == null) {
                out.println("web service parameter error in enroll2 page");
                return;
            }
            String projectName = "";
            String URL="";
            
            if(standard.equals("S")){
                projectName="FFinger";
                PORT = "8084";
                URL="http://"+IPAdress+":"+PORT+"/"+projectName+"/FingerVerification";
            }
            else{
               projectName="FingerEnrollVerify";
               URL="http://"+IPAdress+":"+PORT+"/"+projectName+"/FingerVerification";
            }
            
            
             
            JSONParserPost jsonParserpost = new JSONParserPost();
            JSONObject output = jsonParserpost.makeHttpRequest(URL, "POST", jsonString);
            //  out.println(output.toString());    
            if (output != null) {
                out.println("");
            } else {
                out.println("Web service is not running or Internet connectivity problem");
                return;
            }  

             JSONArray jsonArray = output.getJSONArray("verifyNodes");
            if (jsonArray == null) {
                out.println("Data is not come in proper format from web service.");
                return;
            } else {
                // for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String errorFlag = jsonObject.getString("outCode");

                if (errorFlag.equals("N")) {
                    if (cust_type.equals("agent")) {
                        out.println("<font style=\"text-align:justify;\" size=\"2\">Finger Verification is Successful for the agent&nbsp;</font><font size=\"2\" color=\"#ff0000\"><b></b ></ font > ");
                    } else if (cust_type.equals("cust")) {
                        out.println("<font style=\"text-align:justify;\" size=\"2\">Finger Verification is Successful for the customer&nbsp;</font><font size=\"2\" color=\"#ff0000\"><b></b ></ font > ");
                    } else if (cust_type.equals("bo")) {
                        out.println("<font style=\"text-align:justify;\" size=\"2\">Finger Verification is Successful for the Officer&nbsp;</font><font size=\"2\" color=\"#ff0000\"><b></b ></ font > ");
                    } else if (cust_type.equals("usr")) {
                        out.println("<font style=\"text-align:justify;\" size=\"2\">Finger Verification is Successful for the User&nbsp;</font><font size=\"2\" color=\"#ff0000\"><b></b ></ font > ");
                    }else if (cust_type.equals("ADM")) {
                        out.println("<font style=\"text-align:justify;\" size=\"2\">Finger Verification is Successful for the Admin&nbsp;</font><font size=\"2\" color=\"#ff0000\"><b></b ></ font > ");
                    }
                    else {
                        out.println("cust_type must be \'cust\' or \'agent\'<br> or \'officer\' or \'user\'");
                    } 
                } else {
                    out.println("<p style=\"color:#900\">Sorry! Finger Verification is failed. Please <a href=\"verifyHome.jsp?name=" + name +"&ailogid=" + ailogid + "&appuser=" + appuser + "&sessid=" + sessid + "&amount=" + amount + "&cust_type=" + cust_type + "&opr_type=" + opr_type + "&custno="+custno+"\" >Try Again</a> </p>");
                 }
            }  
        %>
    </BODY>
</html>