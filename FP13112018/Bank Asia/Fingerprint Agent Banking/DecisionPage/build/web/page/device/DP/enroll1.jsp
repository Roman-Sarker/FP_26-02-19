<%-- 
    Document   : test
    Created on : Sep 12, 2017, 4:41:30 PM
    Author     : Sultan Ahmed
--%>
 
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html> 
<HTML>
    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">
        <title>Enroll Page</title>
        <link rel="shortcut icon" href="../../assets/ico/favicon.jpg">
        <link href="css/bootstrap.min.css" rel="stylesheet" />   
        <link href="css/main_style.css" rel="stylesheet" />
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script> 
        <script src="js/enroll1.js"></script> 
        <script src="js/codeSeen.js"></script> 

        <script type="text/javascript">
            code_seen_off();
        </script>

    <object style="height: 0px; width: 0px;" id="objFeatureExtraction" name="objFeatureExtraction" classid="clsid:733A2D1B-9F3D-423D-8700-4F2C8E88EAF9"></object> 
    <object style='height: 0px; width: 0px;' id='objReaderCollection' name='objReaderCollection' classid='clsid:CAC5592F-EBA5-487C-AF8A-F35A70FAA33B'></object>
    <object style='height: 0px; width: 0px;' id='objReader' name='objReader' classid='clsid:C4287526-1485-48CB-99BB-6CC4A3552B81'></object>

</HEAD>
<body> 
    <%
        String name = request.getParameter("name");
        String app_user = request.getParameter("app_user");
        String ai_logid = request.getParameter("ai_logid");
        String cust_type = request.getParameter("cust_type");

    %>

    <form method="post" id="enrollForm" action="">
        <Input type="Hidden" name="name"  id="name" value="<%=name%>"> 
        <Input type="Hidden" name="app_user"  id="app_user" value="<%=app_user%>"> 
        <Input type="Hidden" name="ai_logid"  id="ai_logid" value="<%=ai_logid%>">  
        <Input type="Hidden" name="cust_type"  id="cust_type" value="<%=cust_type%>">
        <Input type="Hidden" name="LThumb"  id="LThumb" value=">"> 
        <Input type="Hidden" name="LIndex"  id="LIndex" value="">  
        <Input type="Hidden" name="LMiddle"  id="LMiddle" value="">  
        <Input type="Hidden" name="LRing"  id="LRing" value="">   
        <Input type="Hidden" name="LLittle"  id="LLittle" value=""> 
        <Input type="Hidden" name="RThumb"  id="RThumb" value=""> 
        <Input type="Hidden" name="RIndex"  id="RIndex" value="">  
        <Input type="Hidden" name="RMiddle"  id="RMiddle" value="">  
        <Input type="Hidden" name="RRing"  id="RRing" value="">  
        <Input type="Hidden" name="RLittle"  id="RLittle" value="">  
        <Input type="Hidden" name="serial"  id="serial" value="">  
        <button id="button" type="button" data-toggle="modal" data-target="#exampleModalCenter" onClick="showModal()" style="background-color: #135087" >
            Please Enroll Your Finger
        </button>
    </form> 

    <div class="modal fade bd-example-modal-lg" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <!--  <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                  <div class="modal-dialog modal-dialog-centered" role="document"> -->
            <div class="modal-content">
                <div class="modal-header " style="background-color: #135087; color:#fff;">
                    <h5 class="modal-title" id="exampleModalLongTitle">Fingerprint Enroll Window</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-8">
                                <div class="row"> 
                                    <Button id="LeftThumb" onclick="takeFingerData(1)"   class="btn mandatoryBtn" ><b>LEFT THUMB</b></Button>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <Button id="LeftIndex" onclick="takeFingerData(2)"   class="btn mandatoryBtn" ><b>LEFT INDEX</b></Button>  
                                </div>

                                <br>

                                <div class="row"> 
                                    <a id="LeftMiddle" onclick="takeFingerData(3)" role="button" class="btn btn-warning popover-test" title="LEFT THUMB">LEFT MIDDLE</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a id="LeftRing" onclick="takeFingerData(4)" role="button" class="btn btn-warning popover-test" title="LEFT RING">LEFT RING</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a id="LeftLittle" onclick="takeFingerData(5)" role="button" class="btn btn-warning popover-test" title="RIGHT THUMB">LEFT LITTLE</a> 
                                </div>

                                <br>

                                <div class="row"> 
                                    <Button id="RightThumb" onclick="takeFingerData(6)"  class="btn mandatoryBtn" ><b>RIGHT THUMB</b></Button> &nbsp;&nbsp;&nbsp;&nbsp;
                                    <Button id="RightIndex" onclick="takeFingerData(7)"  class="btn mandatoryBtn" ><b>RIGHT INDEX</b></Button> 
                                </div>

                                <br>

                                <div class="row"> 
                                    <a id="RightMiddle" onclick="takeFingerData(8)" role="button" class="btn btn-warning popover-test" title="RIGHT THUMB">RIGHT MIDDLE</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a id="RightRing" onclick="takeFingerData(9)" role="button" class="btn btn-warning popover-test" title="RIGHT RING">RIGHT INDEX</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a id="RightLittle" onclick="takeFingerData(10)" role="button" class="btn btn-warning popover-test" title="RIGHT THUMB">RIGHT LITTLE</a> 
                                </div>

                                <div class="row">
                                    <div class="col-md-12">
                                        <p id="status_alert" style="visibility: hidden;"  class="success_alert"> </p>
                                    </div>  
                                </div>
                            </div>
                            <div class="col-md-4">
                                <img height="300" width="240" id="FPImage1" src="../../assets/images/hands/1.png" class="inputstl"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">

                    <div class ="col-md-10">
                        <div class ="row">
                            <p style="color: red">* Finger indicated by red button must be given.</p>
                        </div>
                    </div>

                    <div class ="col-md-2">
                        <button type="button" class="btn btn-success" onclick="processData()" style="background-color:#135087;">Enroll Finger</button>                         
                    </div>

                </div>
            </div>
        </div>
    </div> 

    <script type="text/javascript">
        makeStatusMSgHidden("status_alert");

        function parseXML(text)
        {
            //document.getElementById("status_msg").innerHTML = text;

            var parser, xmlDoc;
            parser = new DOMParser();
            xmlDoc = parser.parseFromString(text, "text/xml");
            return xmlDoc.getElementsByTagName("Bytes")[0].childNodes[0].nodeValue;
        }

        function objReader::On_Captured(reader, captureResult)
        {
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
                        // console.log(fid1.SerializeXml);
                        fmd = objFeatureExtraction.CreateFmdFromFid(fid1, "ISO").Fmd;
                       // console.info(fmd.SerializeXml);
                        

                        //  alert(fid1.SerializeXml);
                        if (fmd == null)
                        {
                            alert("Error occurred in capture.  Please try again.");
                        } else
                        {
                            //   var fid1Data = parseXML(fid1.SerializeXml);
                            var fmdData = parseXML(fmd.SerializeXml);
                            console.log(fmdData);
                            document.getElementById(finger_name[finger_no]).value = fmdData;
                        }
                    }
                    // CaptureThread();
                } else if (result == ERROR)
                    reader.Dispose();
            } catch (e) {
                alert('error got : ' + e.message);
            } finally {
            }
        }

        $('#exampleModalCenter').on('hidden.bs.modal', function (e) {
            close();
            document.getElementById("imgFingerprint").style.backgroundColor = "#FFFFFF";
            document.getElementById("imgFingerprint").src = "assets/images/hands/1.png";
        });
    </script>
</body>
</html>
