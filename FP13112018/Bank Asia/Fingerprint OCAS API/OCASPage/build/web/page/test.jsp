<%-- 
    Document   : test
    Created on : Sep 12, 2017, 4:41:30 PM
    Author     : Sultan Ahmed
--%>

<%@page import="com.era.IPAddress.UpdateIPAdress"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.apache.http.message.BasicNameValuePair"%>
<%@page import="org.apache.http.NameValuePair"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.era.json.JSONParserPost"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<%@page language="java" import ="com.era.fingerprint.enroll.FingerData" %>
<HTML>

    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">
        <title>Enroll New Fingerprint.</title>
        <script src="js/jquery-3.2.1.min.js"></script>
        <style>
            #button{   
                padding: 10px !important;width:200px;
                text-decoration:none !important;        
                background-color: #0B70BE;
                background-image: -moz-linear-gradient(center center , rgb(11, 112, 190) 0%, rgb(11, 112, 190) 100%) !important;
                border-radius: 2px !important;
                border: 2px solid rgb(43, 125, 185) !important;
                font-weight: bold;
                font-size: 13px !important;
                font-family: Helvetica Neue,Helvetica,Arial,sans-serif;
                height: 44px !important;
                text-shadow: none !important;
                color: rgb(255, 255, 255) !important;
            }
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


            document.getElementById('#myForm1').click(function (e)
            {
                e.preventDefault(); // prevent the link from actually redirecting
                document.getElementById('#myForm1').submit();
            });

            document.getElementById('#myForm2').click(function (e)
            {
                e.preventDefault(); // prevent the link from actually redirecting

                //  var destination = $(this).attr('href');
                //  $('#myForm2').attr('action', destination);
                document.getElementById('#myForm2').submit();
            });
            
            document.getElementById('#myForm3').click(function (e)
            {
                e.preventDefault(); // prevent the link from actually redirecting

                //   var destination = $(this).attr('href');
                //   $('#myForm3').attr('action', destination);
                document.getElementById(('#myForm3').submit();
            });
        </script>
    </HEAD>
    <body> 
        <%
            String name = request.getParameter("cust_no");
            String cust_no = name;
            String app_user = request.getParameter("app_user");
            String ai_logid = request.getParameter("ai_logid");
            String cust_type = request.getParameter("cust_type");
            String finger = request.getParameter("finger");
            String check = "";

            int fingerNo = 1;
            String str = "name is " + name + "<br>app_user is " + app_user + "<br>cust_type is " + cust_type
                    + "<br>ai_logid is " + ai_logid + "<br>finger is " + finger;
            // out.println(str+"<br><br><br><br><br>");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("cust_no", cust_no));

            JSONParserPost jsonParserpost = new JSONParserPost();
            
            UpdateIPAdress updateIPAdress = new UpdateIPAdress();            
            String IPAdress = updateIPAdress.getIPAddressFromDb() ;
            
            if(updateIPAdress.getErrorFlag()){
                out.println("Error in test.jsp : "+IPAdress);
                return;
            }
            String PORT = updateIPAdress.getPORT() ;
            String URL="http://"+IPAdress+":"+PORT+"/FingerAPI/ENROLL_STATUS";
            
            JSONObject output = jsonParserpost.makeHttpRequest(URL, "POST", params);
           //  out.println("result: " + output.toString());
          //  int i = 1 ; 
           // if(i==1)
             //   return ; 

            if (output != null) {
                JSONArray jsonArray = output.getJSONArray("enrollStatus");
                // for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String errorFlag = jsonObject.getString("errorFlag");
                String errorMessage = ""; // jsonObject.getString("errorMessage");

                if (errorFlag.equals("N")) {
                    String finger1 =  jsonObject.getString("finger1");
                    String finger2 =  jsonObject.getString("finger2");
                    boolean finger1Flag, finger2Flag;
                    if (finger1.trim().equals("N")) {
                        finger1Flag = false;
                    } else {
                        finger1Flag = true;
                    }

                    if (finger2.trim().equals("N")) {
                        finger2Flag = false;
                    } else {
                        finger2Flag = true;
                    }
                    if (!finger1Flag && !finger2Flag) {
        %>
        <form method="post" id="myForm1" action="famenroll1.jsp">
            <Input type="Hidden" name="cust_no"  id="name1" value="<%=name%>"> 
            <Input type="Hidden" name="app_user"  id="app_user" value="<%=app_user%>"> 
            <Input type="Hidden" name="ai_logid"  id="ai_logid" value="<%=ai_logid%>"> 
            <Input type="Hidden" name="check"  id="check" value="<%=check%>"> 
            <Input type="Hidden" name="finger"  id="finger" value="<%=fingerNo%>">   
            <Input type="Hidden" name="cust_type"  id="cust_type" value="<%=cust_type%>"> 
            <button id="button">Please Enroll Finger 1</button> 
        </form>

        <%
        } else if (finger1Flag && !finger2Flag) {
        %>
        <a href="#" style="background: linear-gradient(to bottom, #F24537 5%, #C62D1F 100%) repeat scroll 0% 0% #F24537 !important;
           border: 1px solid #D02718 !important;color: white !important;"> Finger 1 is Enrolled.</a>

        <form method="post" id="myForm2" action="famenroll1.jsp">
            <Input type="Hidden" name="cust_no"  id="name" value="<%=name%>"> 
            <Input type="Hidden" name="app_user"  id="app_user" value="<%=app_user%>"> 
            <Input type="Hidden" name="ai_logid"  id="ai_logid" value="<%=ai_logid%>"> 
            <Input type="Hidden" name="check"  id="check" value="<%=check%>"> 
            <Input type="Hidden" name="finger"  id="finger" value="<%=fingerNo%>">  
            <Input type="Hidden" name="cust_type"  id="cust_type" value="<%=cust_type%>"> 
            <button id="button">Click here to Enroll Finger 2</button> 
        </form> 
        <%
        } else if (!finger1Flag && finger2Flag) {
        %>
        <a href="#" style="background: linear-gradient(to bottom, #F24537 5%, #C62D1F 100%) repeat scroll 0% 0% #F24537 !important;
           border: 1px solid #D02718 !important;"> Finger 2 is Enrolled.</a>

        <form method="post" id="myForm3" action="famenroll1.jsp">
            <Input type="Hidden" name="cust_no"  id="name" value="<%=name%>"> 
            <Input type="Hidden" name="app_user"  id="app_user" value="<%=app_user%>"> 
            <Input type="Hidden" name="ai_logid"  id="ai_logid" value="<%=ai_logid%>"> 
            <Input type="Hidden" name="check"  id="check" value="<%=check%>"> 
            <Input type="Hidden" name="finger"  id="finger" value="<%=fingerNo%>">  
            <Input type="Hidden" name="cust_type"  id="cust_type" value="<%=cust_type%>"> 
            <button id="button">Click here to Enroll Finger 1</button> 
        </form> 
        <%
        } else if (finger1Flag && finger2Flag) {
        %>
        <a href="#" style="background: linear-gradient(to bottom, #F24537 5%, #C62D1F 100%) repeat scroll 0% 0% #F24537 !important;
           border: 1px solid #D02718 !important;"> Finger 1 is Enrolled.</a>

        <a href="#" style="background: linear-gradient(to bottom, #F24537 5%, #C62D1F 100%) repeat scroll 0% 0% #F24537 !important;
           border: 1px solid #D02718 !important;"> Finger 2 is Enrolled.</a>
        <%
                    }
                } else {
                    out.println("Data is not come  in proper format from web service in test page");
                    out.println("Error Message is " + errorMessage);
                }
            } else {
                out.println("Fingerprint web service is not running or internet connectivity problem.");
            }
        %> 

    </body>
</html>
