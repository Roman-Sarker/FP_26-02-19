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
<%@page language="java" import ="com.era.Data.ENROLL_DATA" %>
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
            String user_id = request.getParameter("user_id");
            String create_by = request.getParameter("create_by");
            String ai_logid = request.getParameter("ai_logid"); 
            String user_type = request.getParameter("user_type");
            String serial = request.getParameter("serial"); 
            
            String lThumb = request.getParameter("LThumb"); 
            String lIndex = request.getParameter("LIndex");
            String lMiddle= request.getParameter("LMiddle");
            String lRing= request.getParameter("LRing");
            String lLittle= request.getParameter("LLittle");
            
            String rThumb = request.getParameter("RThumb");
            String rIndex = request.getParameter("RIndex");//LThumb,LIndex,LMiddle,LRing,LLittle
              String rMiddle = request.getParameter("RMiddle");
               String rRing = request.getParameter("RRing");
                String rLittle = request.getParameter("RLittle");
                   
              
             
            Global global = new Global();            
            Famenroll2 famenroll2 = new Famenroll2();
            ENROLL_DATA enroll_data = famenroll2.prepareEnrollData(
                            user_id,create_by,ai_logid,
                            user_type,serial,
                            lThumb,lIndex,lMiddle,lRing,lLittle,
                            rThumb,rIndex,rMiddle,rRing,rLittle);

            String jsonString = global.objectMappingToJsonString(enroll_data, response);
          //  out.println(jsonString);

            UpdateIPAdress updateIPAdress = new UpdateIPAdress();
            String IPAdress = updateIPAdress.getIPAddressFromDb();  //"10.11.201.84";
            if (updateIPAdress.getErrorFlag()) {
                out.println("Error in famenroll.jsp : " + IPAdress);
             //   return;
            }

            String PORT = updateIPAdress.getPORT(); //"8084"
            String URL = "http://" + IPAdress + ":" + PORT + "/UFinger/FingerEnrollment";
          //  out.println(URL);

            JSONParserPost jsonParserpost = new JSONParserPost();
            JSONObject output = jsonParserpost.makeHttpRequest(URL, "POST", jsonString);
        //    out.println(output.toString());
            
            if (output != null) {
                JSONArray jsonArray = output.getJSONArray("enrollNodes");
                if (jsonArray != null) {
                    
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String errorFlag = jsonObject.getString("errorFlag");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (errorFlag.equals("N")) {
                        out.println("Fingerprint is successfully enrolled<br>");  
                    } else {
                        out.println("Fingerprint is not enrolled");
                        out.println("Error Message is " + errorMessage);
                    }
                } else {
                    out.println("Data is not got in correct format from web service.");
                }
            } else {
                out.println("Fingerprint web service is not running or internet connectivity problem.");
            }
        %>  
    </body>
</html>
