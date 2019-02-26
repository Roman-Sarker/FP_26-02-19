<%--
    Document   : startverifyHome
    Created on : Sep 17, 2017, 3:10:04 PM
    Author     : Sultan Ahmed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<HTML>
    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link href="css/main_style.css" rel="stylesheet" />
        <title>Verify Page</title>
        <link rel="shortcut icon" href="../../assets/ico/favicon.jpg">
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/verify1.js"></script>
        <script src="js/codeSeen.js"></script> 
        <script type="text/javascript">
             code_seen_off();
        </script>

    <object style="height: 0px; width: 0px;" id="objFeatureExtraction" name="objFeatureExtraction" classid="clsid:733A2D1B-9F3D-423D-8700-4F2C8E88EAF9"></object> 
    <object style='height: 0px; width: 0px;' id='objReaderCollection' name='objReaderCollection' classid='clsid:CAC5592F-EBA5-487C-AF8A-F35A70FAA33B'></object>
    <object style='height: 0px; width: 0px;' id='objReader' name='objReader' classid='clsid:C4287526-1485-48CB-99BB-6CC4A3552B81'></object>


     
</HEAD>
<BODY>

    <%

        String standard = "" , verifyMessage = "", name, cust_type = "", opr_type, custno, ailogid, amount, sessid, appuser;

        name = request.getParameter("name");
        custno = request.getParameter("custno");
        appuser = request.getParameter("appuser");
        opr_type = request.getParameter("opr_type");
        cust_type = request.getParameter("cust_type");
        ailogid = request.getParameter("ailogid");
        amount = request.getParameter("amount");
        sessid = request.getParameter("sessid");
        standard = request.getParameter("standard");

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

        String redirectURL = "";
    %>


   <!--     <a href="verify.jsp?name=<%=name%>&custno=<%=custno%>&ailogid=<%=ailogid%>&appuser=<%=appuser%>&sessid=<%=sessid%>&amount=<%=amount%>&cust_type=<%=cust_type%>&opr_type=<%=opr_type%>" >Please Try Again</a>
    -->
    <Form name="verifyForm"  id="verifyForm" method="Post" action="" >
        <Input type="Hidden" name="fingerData" id ="fingerData" value="">
        <Input type="Hidden" name="Sl"  id="Sl" value="">
        <Input type="Hidden" name="name"  id="name" value="<%=name%>">
        <Input type="Hidden" name="ailogid"  id="ailogid" value="<%=ailogid%>">
        <Input type="Hidden" name="amount"  id="amount" value="<%=amount%>">
        <Input type="Hidden" name="sessid"  id="sessid" value="<%=sessid%>">
        <Input type="Hidden" name="appuser"  id="appuser" value="<%=appuser%>">
        <Input type="Hidden" name="custno"  id="custno" value="<%=custno%>">
        <Input type="Hidden" name="opr_type"  id="opr_type" value="<%=opr_type%>">
        <Input type="Hidden" name="cust_type"  id="cust_type" value="<%=cust_type%>">
        <Input type="Hidden" name="standard"  id="standard" value="<%=standard%>">

        <button id="submit_btn" type="button" data-toggle="modal" data-target="#myModal" class="test" name="btnLogon" id ="submit_btn" onClick = " captureFP()">
            <%=verifyMessage%>  
        </button>
    </Form>


    <!-- The Modal -->
    <div class="modal fade" id="myModal">
        <div class="modal-dialog modal-dialog-centered modal-sm">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header" style="background-color: darkblue;color:#fff;height:5% !important;">
                    <h6>Please Give Your Finger</h6> 
                    <button type="button" class="close btn-info btn-sm" data-dismiss="modal">&times;</button>						
                </div>

                <!-- Modal body -->
                <div class="modal-body"> 
                    <div class="row">
                        <div class="col-sm-12">
                            <img onClick="captureFP()" height="120" width="160" id="FPImage1" src="../../assets/images/hands/1_verify.png"/>
                        </div>
                    </div> 
                </div>  
            </div>
        </div>
    </div>

    <script type="text/javascript">

        function objReader::On_Captured(reader, captureResult){
            try {
                var result = ValidateCapture(captureResult);
                document.getElementById("FPImage1").style.backgroundColor = "#FFFFFF";

                if (result == OKAY)
                {
                    if (captureResult.Quality == "DP_QUALITY_GOOD")
                    {
                        var fivs = captureResult.Fid.Fivs;
                        if (fivs.count != 0) {
                            FPImage1.src = "data:image/png;base64," + fivs(0).Bitmap;//(.25); // IE 8 has a 32KB limit on data URI schemes so we shrink for viewing only.  fivs(0).Bitmap has the full image.
                            FPImage1.style.width = "400";
                            FPImage1.style.height = "400";
                            FPImage1.style.visibility = "visible";
                        }

                        fid1 = captureResult.Fid;
                        //alert(fid1.length)
                        fmd = objFeatureExtraction.CreateFmdFromFid(fid1, "ANSI").Fmd;

                        if (fmd == null)
                        {
                            alert("Error occurred in capture.  Please try again.");
                        } else
                        { 
                            var fmdData = parseXML(fmd.SerializeXml);
                            document.getElementById("fingerData").value = fmdData;
                            setTimeout(verifyFinger, 500);
                        }
                    } 
                } else if (result == ERROR)
                    reader.Dispose();
            } catch (e) {
            } finally {
            }
        }
        $('#myModal').on('hidden.bs.modal', function (e) {
            close();
        });


    </script> 
</BODY>
</html>