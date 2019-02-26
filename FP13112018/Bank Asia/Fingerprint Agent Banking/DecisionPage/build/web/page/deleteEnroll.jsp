<%-- 
    Document   : deleteEnroll
    Created on : Sep 18, 2017, 12:45:38 PM
    Author     : Sultan Ahmed
--%>

<%@page import="com.era.admin.GetRestAPIInfo"%>
<%@page import="com.era.admin.RestAPIInfo"%>
<%@page import="org.apache.http.message.BasicNameValuePair"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.http.NameValuePair"%> 
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import ="com.era.json.JSONParserPost" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Page</title> 
        <link rel="shortcut icon" href="assets/ico/favicon.jpg">
        <script src="assets/js/codeSeen.js"></script> 
        <script type="text/javascript">
            code_seen_off();
        </script>
        <style>
            a{   
                padding: 1px !important;
                text-decoration:none !important;        
                background-color: #0B70BE;
                background-image: -moz-linear-gradient(center center , rgb(11, 112, 190) 0%, rgb(11, 112, 190) 100%) !important;
                border-radius: 2px !important;
                border: 2px solid rgb(43, 125, 185) !important;
                font-weight: bold;
                font-size: 13px !important;
                font-family: Helvetica Neue,Helvetica,Arial,sans-serif;
                height: 28px !important;
                text-shadow: none !important;
                color: rgb(255, 255, 255) !important;
            }
        </style>

    </head>
    <body>
        <p style="color:#900">
            <%

                String username = request.getParameter("name");
                String appuser = request.getParameter("app_user");
                String standard = request.getParameter("standard");

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", username));
                params.add(new BasicNameValuePair("app_user", appuser));

                //   params.add(new BasicNameValuePair("custno", custno));
                //    params.add(new BasicNameValuePair("custno", custno));
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
                if (standard.equals("O")) {
                    projectName = "FingerEnrollVerify";
                } else if (standard.equals("S")) {
                    projectName = "BFinger";
                } else {
                    out.println("standard not defined");
                    return;
                }

                String URL = "http://" + IPAdress + ":" + PORT + "/" + projectName + "/DeleteFinger";

                JSONParserPost jsonParserpost = new JSONParserPost();
                JSONObject output = jsonParserpost.makeHttpRequest(URL, "POST", params);
                //   out.println(output);
                if (output != null) {
                    JSONArray jsonArray = output.getJSONArray("DeleteFinger");

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String errorFlag = jsonObject.getString("errorFlag");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (errorFlag.equals("N")) {
                        out.println("Bio-Enrollment has been cancelled for this customer");
                    } else {
                        out.println("Fingerprint is not cancelled by web service");
                        out.println("Error Message is " + errorMessage);
                    }
                } else {
                    out.println("web service is not running or internet connectivity problem.");
                }
            %>    
        </p>
    </body>
</html>
