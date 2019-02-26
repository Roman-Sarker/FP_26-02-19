<%--
    Document   : startverifyHome
    Created on : Sep 17, 2017, 3:10:04 PM
    Author     : Sultan Ahmed
--%>

<%@page import="com.era.fingerprint.verify.DataForVerification"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<HTML>
    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">

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

        <script type="text/javascript">

            function GetSampleModel() {
                try {
                    var wldscan = new ActiveXObject("WebLogonDemoClient.WLDScan"); 
                    
                    var sampleModel = wldscan.GetVerifyTemplate();
                    if (sampleModel.length == 0) {
                        alert("Something is Error!!!", "Error");
                    } else {
                        var Sl_No = "1234";//wldscan.GetSl();
                       // document.getElementById("Sl").setAttribute('value', Sl_No);
                         document.getElementById("SampleModel").setAttribute('value', sampleModel);
                        document.getElementById("scan").submit();
                    }
                } catch (err) {
                    alert(err.message);
                    alert("To verify with a fingerprint device, you should install the WebLogonDemoClient software first.", "Software Not Install Error");
                }
            }
        </script>

        <title>Logon</title>
    </HEAD>
    <BODY Onload="GetSampleModel()" >  
         
            <%
                    String cust_no; 
                    cust_no = request.getParameter("cust_no");
                   
		    String cust_type = request.getParameter("cust_type");
 		    out.println(" "+cust_type); 
		    
		    String redirectURL = ""; 
        %>
        
 <a href="verify.jsp?cust_no=<%=cust_no%>&cust_type=<%=cust_type%>" >Please Try Again</a>   

        <Form name="scan"  id="scan" method="Post" action="famverifyHome.jsp" >
            <Input type="Hidden" name="SampleModel" id ="SampleModel" value=""> 
            <Input type="Hidden" name="cust_no"  id="cust_no" value="<%=cust_no%>">    
            <Input type="Hidden" name="cust_type"  id="cust_type" value="<%=cust_type%>"> 
        </Form>
 
	  
    </BODY>
</html>