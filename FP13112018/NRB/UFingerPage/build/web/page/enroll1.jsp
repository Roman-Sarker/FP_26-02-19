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
        <link href="css/bootstrap.min.css" rel="stylesheet" />  
        <link href="css/font-awesome.min.css" rel="stylesheet" /> 
        <link href="css/main_style.css" rel="stylesheet" />
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script> 
        <script src="js/js_for_finger.js"></script> 
        
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
            img {
                border: 1px solid #336699;
                border-radius : 15px;
                box-shadow: none;
            } 
            .inputstl { 
                padding: 9px; 
                border: solid 1px #0077B0; 
                outline: 0;  
            }

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

            ol.horizontal{
                list-style-type:lower-roman;
                width: 800px;
            }

            ol.horizontal li{
                float: left;
                width: 135px;
                padding: 5px 0px;
            }

        </style> 

        <script type="text/javascript">
            var secugen_lic = "EM8ket5T8HeICYLLMi+4UGGB4kZSOpYILnMMyX/2PSRORgTXn1PEnrq3gtJg/wjy";
             
            function CallSGIFPGetData(successCall, failCall) {
                var uri = "https://localhost:8443/SGIFPCapture";
                var xmlhttp = new XMLHttpRequest();

                xmlhttp.onreadystatechange = function () {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        fpobject = JSON.parse(xmlhttp.responseText);
                        successCall(fpobject);
                    } else if (xmlhttp.status == 404) {
                        failCall(xmlhttp.status)
                    }
                }

                xmlhttp.onerror = function () {
                    failCall(xmlhttp.status);
                }

                var params = "Timeout=10000";// + document.getElementById("10000").value;
                params += "&Quality=60";//+ document.getElementById("50").value;
                params += "&licstr=" + encodeURIComponent(secugen_lic);
                params += "&templateFormat=ANSI";// + document.getElementById("ANSI").value;
                xmlhttp.open("POST", uri, true);
                xmlhttp.send(params);
            }


            makeStatusMSgHidden("status_alert");

            function changeImg(index) {
                document.getElementById("FPImage1").src = "images/hands/" + index + ".png";
                document.getElementById("FPImage1").style.backgroundColor = "#FFFF99";
                makeStatusMSgHidden("status_alert");
                captureFP();
            }

            function captureFP() {
                CallSGIFPGetData(SuccessFunc, ErrorFunc);
            }

            function statusMsgchange() {
                $('#myModal').modal('exampleModalCenter');
                makeStatusMSgHidden("status_alert");
            }

            function SuccessFunc(result) {

                if (result.ErrorCode == 0) {
                     if (result != null && result.BMPBase64.length > 0) {
                        var fingerData = result.TemplateBase64;
                        var quality = result.ImageQuality;
                        var imgSrc = document.getElementById("FPImage1").src;

                        if (imgSrc.indexOf("1.png") !== -1)
                            doProcessing("LeftThumb", "LThumb", "Left Thumb", fingerData,quality);
                        else if (imgSrc.indexOf("2.png") !== -1)
                            doProcessing("LeftIndex", "LIndex", "Left Index", fingerData,quality);
                        else if (imgSrc.indexOf("3.png") !== -1)
                            doProcessing("LeftMiddle", "LMiddle", "Left Middle", fingerData,quality);
                        else if (imgSrc.indexOf("4.png") !== -1)
                            doProcessing("LeftRing", "LRing", "Left Ring", fingerData,quality);
                        else if (imgSrc.indexOf("5.png") !== -1)
                            doProcessing("LeftLittle", "LLittle", "Left Little", fingerData,quality);
                        else if (imgSrc.indexOf("6.png") !== -1)
                            doProcessing("RightThumb", "RThumb", "Right Thumb", fingerData,quality);
                        else if (imgSrc.indexOf("7.png") !== -1)
                            doProcessing("RightIndex", "RIndex", "Right Index", fingerData,quality);
                        else if (imgSrc.indexOf("8.png") !== -1)
                            doProcessing("RightMiddle", "RMiddle", "Right Middle", fingerData,quality);
                        else if (imgSrc.indexOf("9.png") !== -1)
                            doProcessing("RightRing", "RRing", "Right Ring", fingerData,quality);
                        else if (imgSrc.indexOf("10.png") !== -1)
                            doProcessing("RightLittle", "RLittle", "Right Little", fingerData,quality);

                        document.getElementById("FPImage1").src = "data:image/bmp;base64," + result.BMPBase64;
                        document.getElementById("serial").value = result.SerialNumber;
                    }
                } else {
                    alert("Fingerprint Capture Error Code:  " + result.ErrorCode + ".\nDescription:  " + ErrorCodeToString(result.ErrorCode) + ".");
                }
                document.getElementById("FPImage1").style.backgroundColor = "#FFFFFF";
            }

            function doProcessing(buttonId, inputDOM, statusMes, fingerData,quality) {
                document.getElementById(inputDOM).value = fingerData;
                makeStatusMSgVisible("status_alert", statusMes + " is successfully captured<br>Quality is "+quality);
                changeClassOfDomElement("status_alert", "success_alert", "danger_alert");
            }

            function changeClassOfDomElement(element, addClass, removeClass) {
                document.getElementById(element).classList.remove(addClass);
                document.getElementById(element).classList.remove(removeClass);
                document.getElementById(element).classList.add(addClass);
            }

            function makeStatusMSgVisible(dom, statusMsg) {
                var x = document.getElementById(dom);
                x.innerHTML = statusMsg;
                x.style.visibility = 'visible';
            }

            function makeStatusMSgHidden(dom) {
                var x = document.getElementById(dom);
                x.style.visibility = 'hidden';
            }

            function ErrorFunc(status) {
                changeClassOfDomElement("status_alert", "danger_alert", "success_alert");
                makeStatusMSgVisible("status_alert", "Check if SGIBIOSRV is running; status = " + status + ":");
            }

            function processData() {

                var LThumbValue = document.getElementById("LThumb").value;
                var LIndexValue = document.getElementById("LIndex").value;
                var RThumbValue = document.getElementById("RThumb").value;
                var RIndexValue = document.getElementById("RIndex").value;

                if (LThumbValue == null || LThumbValue == "") {
                    changeClassOfDomElement("status_alert", "danger_alert", "success_alert");
                    makeStatusMSgVisible("status_alert", "Please provide left thumb finger");
                } else if (LIndexValue == null || LIndexValue == "") {
                    changeClassOfDomElement("status_alert", "danger_alert", "success_alert");
                    makeStatusMSgVisible("status_alert", "Please provide left index finger");
                } else if (RThumbValue == null || RThumbValue == "") {
                    changeClassOfDomElement("status_alert", "danger_alert", "success_alert");
                    makeStatusMSgVisible("status_alert", "Please provide right thumb finger");
                } else if (RIndexValue == null || RIndexValue == "") {
                    changeClassOfDomElement("status_alert", "danger_alert", "success_alert");
                    makeStatusMSgVisible("status_alert", "Please provide right index finger");
                } else {
                    $('#exampleModalCenter').modal('hide');
                    document.getElementById('enrollForm').action = "enroll2.jsp";
                    document.getElementById("enrollForm").submit();
                }
            }


        </script>

        <style>
            .mandatoryBtn{
                background: #f00000; /* fallback for old browsers */
                background: -webkit-linear-gradient(to left, #f00000, #dc281e); /* Chrome 10-25, Safari 5.1-6 */
                background: linear-gradient(to left, #f00000, #dc281e); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
                color:#ffffff; 
            }   

            .optionalBtn{
                background: #56CCF2;  /* fallback for old browsers */
                background: -webkit-linear-gradient(to right, #2F80ED, #56CCF2);  /* Chrome 10-25, Safari 5.1-6 */
                background: linear-gradient(to right, #2F80ED, #56CCF2); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
                color:#ffffff;
            }
        </style>
    </HEAD>
    <body> 
        <%
            String user_id = request.getParameter("user_id");
            String create_by = request.getParameter("create_by");
            String ai_logid = request.getParameter("ai_logid");
            String user_type = request.getParameter("user_type");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_id", user_id));

            UpdateIPAdress updateIPAdress = new UpdateIPAdress();
            String IPAdress = updateIPAdress.getIPAddressFromDb();//"10.11.201.84";

            if (updateIPAdress.getErrorFlag()) {
                out.println("Error in enroll1.jsp : " + IPAdress);
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

                String enrollStatus = jsonObject.getString("enrollStatus");
                enrollStatus = enrollStatus.trim();
                String errorFlag = jsonObject.getString("errorFlag");
                String errorMessage = jsonObject.getString("errorMessage");

                if (errorFlag.equals("N")) {
                    if (enrollStatus.equals("E")) {
        %>
        <a href="#" style=" color:#ffffff ; background: linear-gradient(to bottom, #F24537 5%, #C62D1F 100%) repeat scroll 0% 0% #F24537 !important;
           border: 1px solid #D02718 !important;"> Finger is already enrolled.</a>
        <%
        } else {
        %>
        <form method="post" id="enrollForm" action="">
            <Input type="Hidden" name="user_id"  id="user_id" value="<%=user_id%>"> 
            <Input type="Hidden" name="create_by"  id="create_by" value="<%=create_by%>"> 
            <Input type="Hidden" name="ai_logid"  id="ai_logid" value="<%=ai_logid%>">  
            <Input type="Hidden" name="user_type"  id="user_type" value="<%=user_type%>">
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
            <button id="button" type="button" data-toggle="modal" data-target="#exampleModalCenter" onClick="statusMsgchange()" style="background-color: #63c44c" >
                Please Enroll Your Finger
            </button>
        </form> 

        <%
                    }
                } else {
                    out.println("Error is: " + errorMessage);
                }
            } else {
                out.println("json data is null");
            }
        %>


        <div class="modal fade bd-example-modal-lg" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <!--  <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                      <div class="modal-dialog modal-dialog-centered" role="document"> -->
                <div class="modal-content">
                    <div class="modal-header " style="background-color: #177700; color:#fff;">
                        <h5 class="modal-title" id="exampleModalLongTitle">Fingerprint Enroll Window</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="row"> 
                                        <Button id="LeftThumb" onclick="changeImg(1)"   class="btn mandatoryBtn" ><b>LEFT THUMB</b></Button>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <Button id="LeftIndex" onclick="changeImg(2)"   class="btn mandatoryBtn" ><b>LEFT INDEX</b></Button>  
                                    </div>

                                    <br>

                                    <div class="row"> 
                                        <a id="LeftMiddle" onclick="changeImg(3)" role="button" class="btn btn-warning popover-test" title="LEFT THUMB">LEFT MIDDLE</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a id="LeftRing" onclick="changeImg(4)" role="button" class="btn btn-warning popover-test" title="LEFT RING">LEFT RING</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a id="LeftLittle" onclick="changeImg(5)" role="button" class="btn btn-warning popover-test" title="RIGHT THUMB">LEFT LITTLE</a> 
                                    </div>

                                    <br>

                                    <div class="row"> 
                                        <Button id="RightThumb" onclick="changeImg(6)"  class="btn mandatoryBtn" ><b>RIGHT THUMB</b></Button> &nbsp;&nbsp;&nbsp;&nbsp;
                                        <Button id="RightIndex" onclick="changeImg(7)"  class="btn mandatoryBtn" ><b>RIGHT INDEX</b></Button> 
                                    </div>

                                    <br>

                                    <div class="row"> 
                                        <a id="RightMiddle" onclick="changeImg(8)" role="button" class="btn btn-warning popover-test" title="RIGHT THUMB">RIGHT MIDDLE</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a id="RightRing" onclick="changeImg(9)" role="button" class="btn btn-warning popover-test" title="RIGHT RING">RIGHT INDEX</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a id="RightLittle" onclick="changeImg(10)" role="button" class="btn btn-warning popover-test" title="RIGHT THUMB">RIGHT LITTLE</a> 
                                    </div>

                                    <div class="row">
                                        <div class="col-md-12">
                                            <p id="status_alert" style="visibility: hidden;"  class="success_alert"> </p>
                                        </div>  
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <img height="300" width="240" id="FPImage1" src="images/hands/1.png" class="inputstl"/>
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
                            <button type="button" class="btn btn-success" onclick="processData()">Pick to Finger</button>                         
                        </div>

                    </div>
                </div>
            </div>
        </div>


    </body>
</html>
