<%-- 
    Document   : deleteEnroll
    Created on : Sep 18, 2017, 12:45:38 PM
    Author     : Sultan Ahmed
--%>

<%@page import="com.era.IPAddress.UpdateIPAdress"%>
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
                String user_id = request.getParameter("user_id");
                String create_by = request.getParameter("create_by");  
                
                List<NameValuePair> params = new ArrayList<NameValuePair>(); 
                params.add(new BasicNameValuePair("create_by", create_by));
                params.add(new BasicNameValuePair("pOperationType", "DELETE"));
                params.add(new BasicNameValuePair("user_id", user_id));     
                
                UpdateIPAdress updateIPAdress = new UpdateIPAdress();            
                String IPAdress = updateIPAdress.getIPAddressFromDb() ;

                if(updateIPAdress.getErrorFlag()){
                    out.println("Error in deletEnroll.jsp : "+IPAdress);
                    return;
                }

                String PORT = updateIPAdress.getPORT() ;
                String URL="http://"+IPAdress+":"+PORT+"/UFinger/DeleteFinger";
                
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
                        out.println("Error Message is "+errorMessage);
                    }
                } else {
                    out.println("web service is not running or internet connectivity problem.");
                }
            %>    
        </p>
    </body>
</html>
