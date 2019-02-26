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
        <link href="css/font-awesome.min.css" rel="stylesheet" />
        <link href="css/main_style.css" rel="stylesheet" />
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/js_for_finger.js"></script>

        <style> 
            img {
                border: 1px solid #336699;
                border-radius : 15px;
                box-shadow: none;
            }
            img:hover {
                box-shadow: 8px 8px 5px #888888;
                border: 3px solid #336699;
                background: #FFFF99;
            }
            .inputstl { 
                padding: 9px; 
                border: solid 1px #0077B0; 
                outline: 0; 
                background: -webkit-gradient(linear, left top, left 25, from(#FFFFFF), color-stop(4%, #C6ECFF), to(#FFFFFF)); 
                background: -moz-linear-gradient(top, #FFFFFF, #C6ECFF 1px, #FFFFFF 25px); 
                box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 
                -moz-box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 
                -webkit-box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 
            } 


            .modal-header { 
                padding: 0.2rem;
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
        </script>

        <title>Logon</title>
    </HEAD>
    <BODY>

        <%

            String user_id, ailogid, amount, sessid, create_by, pAcId;

            user_id = request.getParameter("user_id");
          //  out.println("user_id is " + user_id);

        ailogid = request.getParameter("ailogid");
        //   out.println(" ailogid is " + ailogid);

        amount = request.getParameter("amount");
        sessid = request.getParameter("sessid");
        //   out.println("sessid is " + sessid);

            create_by = request.getParameter("create_by");
        //    out.println("create_by is " + create_by);

            String opr_type = request.getParameter("opr_type");
        //   out.println("opr_type is " + opr_type);

            String user_type = request.getParameter("user_type");
        //     out.println("user_type is " + user_type);

            pAcId = request.getParameter("pAcId");
            String redirectURL = "";
        %>


        <a href="verify.jsp?user_id=<%=user_id%>&ailogid=<%=ailogid%>&create_by=<%=create_by%>&sessid=<%=sessid%>&amount=<%=amount%>&user_type=<%=user_type%>&opr_type=<%=opr_type%>&pAcId=<%=pAcId%>" >Please Try Again</a>

        <Form name="scan"  id="scan" method="Post" action="verify3.jsp" >
            <Input type="Hidden" name="fingerData" id ="fingerData" value="">
            <Input type="Hidden" name="Sl"  id="Sl" value="123">
            <Input type="Hidden" name="user_id"  id="user_id" value="<%=user_id%>">
            <Input type="Hidden" name="ailogid"  id="ailogid" value="<%=ailogid%>">
            <Input type="Hidden" name="amount"  id="amount" value="<%=amount%>">
            <Input type="Hidden" name="sessid"  id="sessid" value="<%=sessid%>">
            <Input type="Hidden" name="create_by"  id="create_by" value="<%=create_by%>"> 
            <Input type="Hidden" name="opr_type"  id="opr_type" value="<%=opr_type%>">
            <Input type="Hidden" name="pAcId"  id="pAcId" value=<%=pAcId%>> 
            <Input type="Hidden" name="user_type"  id="user_type" value="<%=user_type%>">
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
                                <img onClick="captureFP()" height="120" width="160" id="FPImage1" src="images/hands/1_verify.png" class="mx-auto d-block"/>
                            </div>
                        </div> 
                    </div>  
                </div>
            </div>
        </div>

        <script type="text/javascript">
            $('#myModal').modal('show');
            var secugen_lic  = "EM8ket5T8HeICYLLMi+4UGGB4kZSOpYILnMMyX/2PSRORgTXn1PEnrq3gtJg/wjy";
             captureFP();
            
            var xmlhttp;
            $('#myModal').on('hidden.bs.modal', function (e) {
                close();
            });

            function verifyFinger()
            {
                var domElement = document.getElementById("fingerData");
                if (domElement.value.length == 0)//if (domElement !== null && domElement.value === "")//if(value!=null || value == "")
                    alert('Please provide your fingerprint');
                else
                    document.getElementById("scan").submit();

            }

            function captureFP() {
                document.getElementById("FPImage1").style.backgroundColor = "#FFFF99";
                CallSGIFPGetData(SuccessFunc, ErrorFunc);
            }

            function SuccessFunc(result) {
                if (result.ErrorCode == 0) {

                    if (result != null && result.BMPBase64.length > 0) {
                        document.getElementById("FPImage1").src = "data:image/bmp;base64," + result.BMPBase64;
                        document.getElementById("Sl").value = result.SerialNumber;
                        document.getElementById("fingerData").value = result.TemplateBase64;
                        setTimeout(verifyFinger, 500);
                    }
                } else {
                    alert("Fingerprint Capture Error Code:  " + result.ErrorCode + ".\nDescription:  " + ErrorCodeToString(result.ErrorCode) + ".");
                }
            }

            function ErrorFunc(status) {
                /* 	
                 If you reach here, user is probabaly not running the 
                 service. Redirect the user to a page where he can download the
                 executable and install it. 
                 */
                alert("Check if SGIBIOSRV is running; status = " + status + ":");
            }

            function CallSGIFPGetData(successCall, failCall) {
                document.getElementById("FPImage1").style.backgroundColor = "#FFFF99";
                var uri = "https://localhost:8443/SGIFPCapture";
                xmlhttp = new XMLHttpRequest();

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
                params += "&templateFormat=ISO";// + document.getElementById("ANSI").value;
                xmlhttp.open("POST", uri, true);
                xmlhttp.send(params);
            }

            function close() {
                xmlhttp.abort();
            }


        </script> 
    </BODY>
</html>
