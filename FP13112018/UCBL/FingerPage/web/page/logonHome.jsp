<%-- 
    Document   : logonHome
    Created on : Sep 14, 2017, 2:48:40 PM
    Author     : Sultan Ahmed
--%>

<%@page import="com.era.IPAddress.UpdateIPAdress"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.era.json.JSONParserPost"%>
<%@page import="org.apache.http.message.BasicNameValuePair"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.http.NameValuePair"%>
<%@page import="java.util.List"%>
<%@page import="java.io.IOException"%>
<%@page import="com.era.fingerprint.verify.DataForVerification"%>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<HTML>
    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252"> 
        <Script Language="JavaScript">
            <!--
            
            function inStrGrp(src, reg)
            {
                var regex = new RegExp("[" + reg + "]", "i");
                return regex.test(src);
            }

            function check()
            {
                var uname = document.form_logon.elements[0].value
                var bError = false
                //window.alert()
                if (uname.length == 0)
                {
                    window.alert("Name is required.\n")
                    return false
                }
                if (uname.indexOf("\\") >= 0)
                    bError = true

                if (inStrGrp(uname, '/.:*?"<>| '))
                    bError = true

                if (bError)
                {
                    window.alert('User name can not contain the following characters:\n \\/. :*?"<>|\n')
                    return false
                } else
                    return true
            }
-->
        </Script>

        <script type="text/javascript">
              if (document.layers) {
                //Capture the MouseDown event.
                document.captureEvents(Event.MOUSEDOWN);

                //Disable the OnMouseDown event handler.
                document.onmousedown = function () {
                    return false;
                };
             }
             else { 
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

        <style type="text/css">
            #submit_btn{
                position:absolute !important; 
                top:1px !important; 
                left:5px !important;
                width:170px !important;
                color: rgb(255, 255, 255);  
                background-color: rgb(111, 43, 43); 
                border-radius: 5px 5px 5px 5px;
                background-image: -moz-linear-gradient(center center , rgb(111, 43, 43) 0%, rgb(111, 43, 43) 100%);              
                border-radius: 2px 2px 2px 2px;
                border: 1px solid rgb(111, 43, 43); 
                height: 44px;  
                font-weight: bold;
                cursor: pointer;
                text-align: top;line-height: 1.22em;
                font-size: 13px !important;
                font-family: Helvetica Neue,Helvetica,Arial,sans-serif;
            }

            /*  .test{
                  font-size:12px;
                  line-height:14px;
                  text-align:center;
                  height:100px;
                  width:100px;
              } */

            button span{ 
                display: block;
                position: absolute;
                top:5px;
                left:0;
            }  

        </style>

        <title>Logon</title>
    </HEAD>
    <BODY>
        <%
            String alogid = request.getParameter("ailogid");
            String sessid = request.getParameter("sessid");
            String username = request.getParameter("name");
            String depamount = request.getParameter("amount");
            String custno = request.getParameter("custno");
            String appuser = request.getParameter("appuser");
            String logid = request.getParameter("ailogid");
            String opr_type = request.getParameter("opr_type");
            String cust_type = request.getParameter("cust_type");
           // out.println("<br><br><br><br><br><br>amount is "+depamount);

            /* out.println("<br><br><br><br><br><br>"
               + "ailogid is "+alogid+"<br>sessid is "+sessid+"<br>username is "+username+
               "<br>depamount is "+depamount+"<br>custno is "+custno+"<br>appuser is "+appuser
               +"<br> logid is "+logid+"<br>cust_type is "+cust_type); */
            String check_submit_form = request.getParameter("submit");
            if ((request.getParameter("btnLogon") == null) ? false : true) {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("cust_no", username));
                
                UpdateIPAdress updateIPAdress = new UpdateIPAdress();            
                String IPAdress = updateIPAdress.getIPAddressFromDb() ;

                if(updateIPAdress.getErrorFlag()){
                    out.println("Error in logonHome.jsp : "+IPAdress);
                    return;
                }
                String PORT = updateIPAdress.getPORT() ;
                String URL="http://"+IPAdress+":"+PORT+"/FingerEnrollVerify/ENROLL_STATUS";

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
                        String finger1 = jsonObject.getString("finger1");
                        String finger2 = jsonObject.getString("finger2");
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
        <p>The name you input &nbsp;<Font size="4" color="#ff0000"> <%=username%> </Font> &nbsp; is not register yet. Please     try again!</p>
            <%
                        }
                        else 
                        {
                            String reqname = request.getParameter("name");
                            String redirectURL = "startverifyHome.jsp?name=" + reqname + "&logid=" + logid + "&appuser=" + appuser + "&amount=" + depamount + "&custno=" + custno + "&sessid=" + sessid + "&opr_type= " + opr_type + "&cust_type=" + cust_type;
                            response.sendRedirect(redirectURL);
                        }
                    }
                    else{
                        out.println("error in fetching data from database.");
                        out.println("Main error is "+errorMessage);    
                    }    
                } 
                else{
                    out.println("Fingerprint web service is not running or internet connectivity problem.");
                }
            } else {
                String verifyMessage = "";
                out.println("cust_type is" + cust_type);
                if (cust_type.equals("cust")) {
                    verifyMessage = "Finger Verification For Customer";
                } else if (cust_type.equals("agent")) {
                    verifyMessage = "Finger Verification For Agent";
                } else if (cust_type.equals("bo")) {
                    verifyMessage = "Finger Verification For Officer";
                } else if (cust_type.equals("usr")) {
                    verifyMessage = "Finger Verification For User";
                } else {
                    out.println("cust_type must be \'cust\' or \'agent\'<br> or \'officer\' or \'user\'");
                    return;
                }
            %>

        <form name="form_logon" method="post" action ="logonHome.jsp"  onSubmit="return check()">
            <input type="hidden" name="name" value="<%=username%>" />
            <input type="hidden" name="amount" value="<%=depamount%>" />
            <input type="hidden" name="custno" value="<%=custno%>" />
            <input type="hidden" name="sessid" value="<%=sessid%>" />
            <input type="hidden" name="appuser" value="<%=appuser%>" />
            <input type="hidden" name="ailogid" value="<%=alogid%>" />  
            <Input type="Hidden" name="opr_type"  id="opr_type" value=<%=opr_type%> /> 
            <Input type="Hidden" name="cust_type"  id="cust_type" value="<%=cust_type%>"> 
            <button type="submit" class="test" name="btnLogon" id ="submit_btn"><span><%=verifyMessage%> </span></button>
        </form>
        <%
            }
        %>        
    </body>
</html>