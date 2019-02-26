<%-- 
    Document   : deleteFile
    Created on : May 22, 2018, 10:38:13 AM
    Author     : root
--%>
 
<%@page import="com.era.admin.GetRestAPIInfo"%>
<%@page import="com.era.admin.RestAPIInfo"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.http.message.BasicNameValuePair"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.http.NameValuePair"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.era.json.JSONParserPost"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
             #not_enroll_message{
                color: rgb(255, 255, 255);  
                background-color: rgb(111, 43, 43); 
                border-radius: 5px 5px 5px 5px;
                background-image: -moz-linear-gradient(center center , rgb(111, 43, 43) 0%, rgb(111, 43, 43) 100%);              
                border-radius: 2px 2px 2px 2px;
                border: 1px solid rgb(111, 43, 43);
                font-size: 13px !important;
                font-family: Helvetica Neue,Helvetica,Arial,sans-serif;
            }
            #test{
                padding: 1px !important;
                cursor:pointer;
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
        <%
            String name = request.getParameter("name");
            String app_user = request.getParameter("app_user");
            String logid = request.getParameter("ai_logid");
            String cust_type = request.getParameter("cust_type");
            String sessid = request.getParameter("sessid");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));

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
            String URL = "http://" + IPAdress + ":" + PORT + "/BFinger/EnrollStatus";

            JSONParserPost jsonParserpost = new JSONParserPost();
            JSONObject output = jsonParserpost.makeHttpRequest(URL, "POST", params);

            if (output != null) {
                JSONArray jsonArray = output.getJSONArray("enrollStatus");
                // for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String enrollStatus = jsonObject.getString("enrollStatus");
                enrollStatus = enrollStatus.trim();
                String errorFlag = jsonObject.getString("errorFlag");
                String errorMessage = jsonObject.getString("errorMessage");

                if (errorFlag.equals("N")) {
                    if (enrollStatus.equals("E")) {
                        String standard = jsonObject.getString("standard").trim();

        %>
        <script type="text/javascript">
            function cancelBio() {
                var r = confirm("Do you want to cancel Bio-Enrollment for this customer?");
                if (r == true) {
                    document.getElementById("deleteForm").submit();
                } else
                    x = "You pressed Cancel!";

                document.getElementById("demo").innerHTML = x;
            }
        </script>
        <input type="button" name="test" id="test" value="Discard/Delete Finger Enrollment for this Customer" onclick="cancelBio();"/>
        <p id="demo"></p>

        <Form name="deleteForm"  id="deleteForm" method="Post" action="deleteEnroll.jsp" >
            <Input type="Hidden" name="name"  id="name" value="<%=name%>"> 
            <Input type="Hidden" name="app_user"  id="app_user" value="<%=app_user%>"> 
            <Input type="Hidden" name="standard"  id="standard" value="<%=standard%>"> 
        </Form>
        <%

                    } else {
                        out.println("<spand id=\"not_enroll_message\">The customer is not enrolled<span>");
                    }
                } else {
                    out.println("Error in fetching data from database");
                    out.println("main error is " + errorMessage);
                }
            } else {
                out.println("Fingerprint web service is not running or internet connectivity problem.");
            }
        %>
    </body>
</html>
