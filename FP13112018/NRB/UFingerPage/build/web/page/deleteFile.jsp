<%-- 
    Document   : deleteFile
    Created on : Sep 18, 2017, 11:34:06 AM
    Author     : Sultan Ahmed
--%>

<%@page import="com.era.IPAddress.UpdateIPAdress"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.era.json.JSONParserPost"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.http.message.BasicNameValuePair"%>
<%@page import="org.apache.http.NameValuePair"%>
<%@page import="java.util.List"%>
<%@page import="java.io.IOException"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">
            if (document.layers) {
                document.captureEvents(Event.MOUSEDOWN);
                document.onmousedown = function ()
                {
                    return false;
                };
            } else {
                document.onmouseup = function (e) {
                    if (e != null && e.type == "mouseup") {
                        if (e.which == 2 || e.which == 3) {
                            return false;
                        }
                    }
                };
            }

            document.oncontextmenu = function () {
                return false;
            };
        </script>

        <style type="text/css">
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
            String user_id = request.getParameter("user_id");
            String create_by = request.getParameter("create_by");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_id", user_id));

            UpdateIPAdress updateIPAdress = new UpdateIPAdress();
            String IPAdress = updateIPAdress.getIPAddressFromDb();

            if (updateIPAdress.getErrorFlag()) {
                out.println("Error in famVerifyHome.jsp : " + IPAdress);
                return;
            }

            String PORT = updateIPAdress.getPORT();
            String URL = "http://" + IPAdress + ":" + PORT + "/UFinger/EnrollStatus";

            JSONParserPost jsonParserpost = new JSONParserPost();
            JSONObject output = jsonParserpost.makeHttpRequest(URL, "POST", params);
            //  out.println("result: " + output.toString());

            if (output != null) {
                JSONArray jsonArray = output.getJSONArray("enrollStatus");
                // for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String errorFlag = jsonObject.getString("errorFlag");
                String errorMessage = jsonObject.getString("errorMessage");

                if (errorFlag.equals("N")) {
                    String enrollStatus = jsonObject.getString("enrollStatus");
                    enrollStatus = enrollStatus.trim();

                    if (enrollStatus.equals("NE")) {
        %>
                            <b id="not_enroll_message" > This customer is not Enrolled with finger yet.</b>
        <%        } else {
        %>
                        <script type="text/javascript">
                            function cancelBio() {
                                var r = confirm("Do you want to cancel Bio-Enrollment for this customer?");
                                if (r == true) {
                                    document.getElementById("scan").submit();
                                } else
                                    x = "You pressed Cancel!";

                                document.getElementById("demo").innerHTML = x;
                            }
                        </script>
                        <input type="button" name="test" id="test" value="Discard/Delete Finger Enrollment for this Customer" onclick="cancelBio();"/>
                        <p id="demo"></p>

                        <Form name="scan"  id="scan" method="Post" action="deleteEnroll.jsp" >
                            <Input type="Hidden" name="create_by"  id="create_by" value="<%=create_by%>"> 
                            <Input type="Hidden" name="user_id"  id="user_id" value="<%=user_id%>">  
                        </Form>
        <%
                    }
                } else {
                    out.println("Data is not come  in proper format from web service in delete file page");
                    out.println("Error Message is " + errorMessage);
                }
            } else {
                out.println("Fingerprint web service is not running or internet connectivity problem.");
            }
        %>
    </body>
</html>
