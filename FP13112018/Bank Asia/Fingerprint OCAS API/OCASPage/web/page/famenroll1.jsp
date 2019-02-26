<%-- 
    Document   : famenroll1
    Created on : Sep 13, 2017, 10:34:49 AM
    Author     : Sultan Ahmed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<HTML>
    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">
        <meta http-equiv="x-ua-compatible" content="IE=10">
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
        <script src="js/jquery-3.2.1.min.js"></script>
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

            $('#myForm').click(function (e)
            {
                e.preventDefault(); // prevent the link from actually redirecting

                var destination = $(this).attr('href');
                $('#myForm').attr('action', destination);
                $('#myForm').submit();
            });
        </script>
        <!-- 
        <Script Language="VBScript"  >
             Sub GetLearnModel
                 Dim sModel, wldscan ,Sl
                 On Error Resume Next
                 Set wldscan=CreateObject("WebLogonDemoClient.WLDScan")
                 If Err <> 0 Then
                     MsgBox "To enroll with a fingerprint device, you should install the WebLogonDemoClient software first." , , "Software Not Install Error"
                 Else
                     Sl=wldscan.GetSl()
                     sModel=wldscan.GetEnrollTemplate()
                     If Len(sModel) <> 0 Then
                         scan.Sl.Value=Sl
                         scan.LearnModel.Value=sModel
                         scan.submit
                     Else
                         MsgBox "Something is Error!!!"
                     End If
                 End If
                 Set wldscan = Nothing
             End Sub
         </Script>  
        -->
        <Script type="text/javascript" >
            function GetLearnModel() {
                try {
                    var wldscan = new ActiveXObject("WebLogonDemoClient.WLDScan");

                    var sampleModel = wldscan.GetEnrollTemplate();
                    if (sampleModel.length == 0) {
                        alert("Something is Error!!!", "Error");
                    } else {
                        var Sl_No = "1234";//wldscan.GetSl();
                        // document.getElementById("Sl").setAttribute('value', Sl_No);
                        document.getElementById("LearnModel").setAttribute('value', sampleModel);
                        document.getElementById("scan").submit();
                    }
                } catch (err) {
                    alert(err.message);
                    alert("To verify with a fingerprint device, you should install the WebLogonDemoClient software first.", "Software Not Install Error");
                }
            }
        </Script>   

    <body  Onload="GetLearnModel()">
        <%
            String name = request.getParameter("cust_no");
            String app_user = request.getParameter("app_user");
            String ai_logid = request.getParameter("ai_logid"); 
            String cust_type = request.getParameter("cust_type");
            String check = request.getParameter("check");
            int fingerNo = 1;
String finger="1";
            /*   out.println( "Customer No. = " + name + "<br>");
            out.println( "<br>APP_USER =" +app_user + "<br>");
            out.println( "<br>AI_LogID =" + ai_logid + "<br>");
            out.println( "<br>Customer Type ="+ cust_type + "<br>");
            out.println( "<br>finger =" + finger + "<br>");   */
        %>


        <Form name="scan" id="scan"  method="Post" action="famenroll2.jsp">
            <Input type="hidden" name="LearnModel" id="LearnModel"  value="">
            <Input type="hidden" name="Sl" value="">
            <Input type="Hidden" name="cust_no"  id="cust_no" value="<%=name%>"> 
            <Input type="Hidden" name="app_user"  id="app_user" value="<%=app_user%>"> 
            <Input type="Hidden" name="ai_logid"  id="ai_logid" value="<%=ai_logid%>"> 
            <Input type="Hidden" name="check"  id="check" value="<%=check%>">  
            <Input type="Hidden" name="finger"  id="finger" value="<%=finger%>"> 
            <Input type="Hidden" name="cust_type"  id="cust_type" value="<%=cust_type%>"> 
        </Form>
    </body>
</html>
