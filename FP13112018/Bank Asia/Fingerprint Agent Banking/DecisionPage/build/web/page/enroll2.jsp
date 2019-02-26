<%-- 
    Document   : famenroll2
    Created on : Sep 13, 2017, 11:14:05 AM
    Author     : Sultan Ahmed
--%>

<%@page import="com.era.admin.GetRestAPIInfo"%>
<%@page import="com.era.admin.RestAPIInfo"%>
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
        <title>Enroll Page</title> 
        <link rel="shortcut icon" href="assets/ico/favicon.jpg">
        <script src="assets/js/codeSeen.js"></script> 
        <script type="text/javascript">
            code_seen_off();
        </script>
    </head>
    <body>
        <%
            String ai_logid = request.getParameter("ai_logid");
            String name = request.getParameter("name");
            String app_user = request.getParameter("app_user");
            String cust_type = request.getParameter("cust_type");
            String serial = request.getParameter("serial");

            String lThumb = request.getParameter("LThumb");
            String lIndex = request.getParameter("LIndex");
            String lMiddle = request.getParameter("LMiddle");
            String lRing = request.getParameter("LRing");
            String lLittle = request.getParameter("LLittle");

            String rThumb = request.getParameter("RThumb");
            String rIndex = request.getParameter("RIndex");//LThumb,LIndex,LMiddle,LRing,LLittle
            String rMiddle = request.getParameter("RMiddle");
            String rRing = request.getParameter("RRing");
            String rLittle = request.getParameter("RLittle");
             
            Global global = new Global();
            Famenroll2 famenroll2 = new Famenroll2();
            ENROLL_DATA enroll_data = famenroll2.prepareEnrollData(
                    name, app_user, ai_logid,
                    cust_type, serial,
                    lThumb, lIndex, lMiddle, lRing, lLittle,
                    rThumb, rIndex, rMiddle, rRing, rLittle);

            String jsonString = global.objectMappingToJsonString(enroll_data, response);
            // out.println(jsonString);

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
            String URL = "http://" + IPAdress + ":" + PORT + "/FFinger/FingerEnrollment";
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
