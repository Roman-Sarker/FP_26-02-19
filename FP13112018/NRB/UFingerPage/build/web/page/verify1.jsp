<%-- 
    Document   : logonHome
    Created on : Sep 14, 2017, 2:48:40 PM
    Author     : Sultan Ahmed
--%>

<%@page import="com.era.param.Parameter_Fixed_Verify"%>
<%@page import="com.era.IPAddress.UpdateIPAdress"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.era.json.JSONParserPost"%>
<%@page import="org.apache.http.message.BasicNameValuePair"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.http.NameValuePair"%>
<%@page import="java.util.List"%>
<%@page import="java.io.IOException"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<HTML>
    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252"> 
        <link href="css/bootstrap.min.css" rel="stylesheet" />  
        <link href="css/font-awesome.min.css" rel="stylesheet" /> 
        <link href="css/main_style.css" rel="stylesheet" />
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script> 

        <Script Language="JavaScript">


            function inStrGrp(src, reg)
            {
                var regex = new RegExp("[" + reg + "]", "i");
                return regex.test(src);
            }

            function check()
            {
                var uname = document.getElementById("user_id").value;//document.form_logon.elements[0].value
                var bError = false;
                //window.alert()
                if (uname.length == 0)
                {
                    window.alert("user_id is required.\n")
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
            String user_id = request.getParameter("user_id");
            String ailogid = request.getParameter("ailogid");
            String sessid = request.getParameter("sessid");
            String amount = request.getParameter("amount");
            String create_by = request.getParameter("create_by");
            String opr_type = request.getParameter("opr_type");
            String user_type = request.getParameter("user_type");
            String pAcId = request.getParameter("pAcId");

            // out.println("<br><br><br><br><br><br>amount is "+depamount);

            /* out.println("<br><br><br><br><br><br>"
               + "ailogid is "+ailogid+"<br>sessid is "+sessid+"<br>user_id is "+user_id+
               "<br>amount is "+amount+"<br>"+"<br>create_by is "+create_by
               +"<br> alogid is "+ailogid+"<br>user_type is "+user_type); */
            String check_submit_form = request.getParameter("submit");
            if ((request.getParameter("btnLogon") == null) ? false : true) {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user_id", user_id));

                UpdateIPAdress updateIPAdress = new UpdateIPAdress();
                String IPAdress = updateIPAdress.getIPAddressFromDb(); //"10.11.201.84"; 

                if (updateIPAdress.getErrorFlag()) {
                    out.println("Error in logonHome.jsp : " + IPAdress);
                    return;
                }

                String PORT = updateIPAdress.getPORT();
                String URL = "http://" + IPAdress + ":" + PORT + "/UFinger/EnrollStatus";
 
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
                        if (enrollStatus.equals("NE")) {

        %>
        <p>The user_id  given by you &nbsp;<Font size="4" color="#ff0000"> <%=user_id%> </Font> &nbsp; is not register yet. Please     try again!</p>

        <%
                        } else {
                            String reqname = request.getParameter("name");
                            String redirectURL = "verify2.jsp?user_id=" + user_id + "&ailogid=" + ailogid + "&create_by=" + create_by + "&amount=" + amount + "&sessid=" + sessid + "&opr_type= " + opr_type + "&user_type=" + user_type + "&pAcId=" + pAcId;
                            response.sendRedirect(redirectURL);
        %>

        <%
                        }
                    } else {
                        out.println("error in fetching data from database.");
                        out.println("Main error is " + errorMessage);
                    }
                } else {
                    out.println("Fingerprint web service is not running or internet connectivity problem.");
                }
            } else {
                String verifyMessage = "";
                  
                Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();
                user_type = param_fixed_verify.getParameter(out, user_type, false);
                if (user_type.equals("cust")) {
                    verifyMessage = "Finger Verification For Customer";
                } else if (user_type.equals("agent")) {
                    verifyMessage = "Finger Verification For Agent";
                } else if (user_type.equals("agnt")) {
                    verifyMessage = "Finger Verification For Agent";
                } else if (user_type.equals("bo")) {
                    verifyMessage = "Finger Verification For Officer";
                } else if (user_type.equals("usr")) {
                    verifyMessage = "Finger Verification For User";
                } else if (user_type.equalsIgnoreCase("adm")) {
                    verifyMessage = "Finger Verification For ADMIN";
                } else {
                    out.println("user_type must be \'ADM\' \'cust\' or \'agent\'<br> or \'officer\' or \'user\'");
                    return;
                }
        %>

        <form name="form_logon" method="post" action ="verify1.jsp"  onSubmit="return check()">
            <input type="hidden" name="user_id" value="<%=user_id%>" />
            <input type="hidden" name="amount" value="<%=amount%>" /> 
            <input type="hidden" name="sessid" value="<%=sessid%>" />
            <input type="hidden" name="create_by" value="<%=create_by%>" />
            <input type="hidden" name="ailogid" value="<%=ailogid%>" />  
            <Input type="Hidden" name="opr_type"  id="opr_type" value=<%=opr_type%> /> 
            <Input type="Hidden" name="user_type"  id="user_type" value="<%=user_type%>"> 
            <Input type="Hidden" name="pAcId"  id="pAcId" value=<%=pAcId%>> 
            <table style="height:200px">
                <tr>
                    <td class="align-middle">
                        <button type="submit" class="test" name="btnLogon" id ="submit_btn">
                            <span><%=verifyMessage%> </span>
                        </button>
                    </td>
                </tr>
            </table>        

        </form>
        <%
            }
        %>    

    </body>
</html>
