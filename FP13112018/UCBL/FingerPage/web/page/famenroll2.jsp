<%-- 
    Document   : famenroll2
    Created on : Sep 13, 2017, 11:14:05 AM
    Author     : Sultan Ahmed
--%>
 
<%@page import="com.era.IPAddress.UpdateIPAdress"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.era.fingerprint.enroll.EnrollNodes"%>
<%@page language="java" import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import ="com.era.Global.Global" %>
<%@page language="java" import ="com.era.fingerprint.enroll.Famenroll2" %>
<%@page language="java" import ="com.era.json.ENROLL_DATA" %>
<%@page language="java" import ="com.era.json.JSONParserPost" %>
<%@page language="java" import ="java.util.Arrays" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String name = request.getParameter("name");
            String app_user = request.getParameter("app_user");
            String ai_logid = request.getParameter("ai_logid");
            String finger = request.getParameter("finger");
            String cust_type = request.getParameter("cust_type");
            String sl = request.getParameter("Sl");
            String learnModel = request.getParameter("LearnModel");
            String serverVariables = request.getParameter("REMOTE_ADDR"); 
            
            Global global = new Global();
            byte[] data = global.getFingerData(learnModel);
            //   out.println(Arrays.toString(value));

            Famenroll2 famenroll2 = new Famenroll2();
            ENROLL_DATA enroll_data = famenroll2.prepareEnrollData(name, 1, app_user, data, name,
                    "ENROLL", ai_logid, " ", cust_type, "W", sl);
            
            String jsonString = global.objectMappingToJsonString(enroll_data, response);
            // out.println(jsonString);
            
            UpdateIPAdress updateIPAdress = new UpdateIPAdress();            
            String IPAdress = updateIPAdress.getIPAddressFromDb();
            
            if(updateIPAdress.getErrorFlag()){
                out.println("Error in famenroll.jsp : "+IPAdress);
                return;
            }
            
            String PORT = updateIPAdress.getPORT() ;            
            String URL="http://"+IPAdress+":"+PORT+"/FingerEnrollVerify/FingerEnrollment";

            JSONParserPost jsonParserpost = new JSONParserPost();
            JSONObject output = jsonParserpost.makeHttpRequest(URL, "POST", jsonString);
            
            if (output != null) 
            {
                JSONArray jsonArray = output.getJSONArray("enrollNodes");
                if (jsonArray != null) {
                    // for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String errorFlag = jsonObject.getString("errorFlag");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (errorFlag.equals("N")) {
                        out.println("Fingerprint is successfully enrolled <br>");
                       
                        String finger1 = jsonObject.getString("finger1").trim();
                        String finger2 = jsonObject.getString("finger2").trim();
                        
                        if(finger1 == null || finger2== null){
                            out.println("Status of finger1 or finger2 is unknown.");
                        }
                        else{  
                            if(finger2.equals("N"))
                                out.println("<a href=famenroll1.jsp?name=" + name + "&app_user=" + app_user + "&ai_logid=" + ai_logid + "&check=&finger=2&cust_type=" + cust_type + ">Click here to enroll Finger 2</a>");
                        }
                    }else {
                        out.println("Fingerprint is not enrolled");
                        out.println("Error Message is " + errorMessage);
                    }
                } else {
                    out.println("Data is not got in correct format.");
                }
            } else {
                out.println("Fingerprint web service is not running or internet connectivity problem.");
            }
        %>  
    </body>
</html>
