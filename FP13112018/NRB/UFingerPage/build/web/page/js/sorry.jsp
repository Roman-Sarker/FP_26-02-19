<%-- 
    Document   : test2
    Created on : Jan 28, 2018, 6:26:23 PM
    Author     : sultan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <title>Finger Enroll</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
    <link rel="stylesheet" href="css/w3.css">
    <script src="js/jquery.min.js"></script>
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

    </style>
    <body>
        <div class="w3-content" >

            <div  class="w3-light-grey w3-padding-small w3-padding-16 w3-margin-top" style="border:3px solid #ececec;">
                <h3 width="50%" class="w3-center" style="background:#0077B0; color:#FFF; font-weight:bold">Finger Enroll</h3>
                <hr>
                <div class="w3-container" width="">

                    <table class="w3-table" width="50%">
                        <tr>
                            <td width="100%">
                                <select class="w3-select w3-border" name="option" id="option" onchange="handSelect()">
                                    <option value="" disabled selected>Choose Finger Position</option>
                                    <option value="1">LEFT THUMB</option>
                                    <option value="2">LEFT INDEX</option>
                                    <option value="3">RIGHT THUMB</option>
                                    <option value="4">RIGHT INDEX</option>
                                </select>
                            </td>
                            <td width="50%" style="text-align:center" >
                                <img onclick="captureFP()" id="FPImage1" src="1.png" class="inputstl"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <br>
                <p align="right">Developed & Maintained By: Sultan & Saifur</p>
            </div>
        </div> 

        <script type="text/javascript">

            var secugen_lic = "";

            function ErrorCodeToString(ErrorCode) {
                var Description;
                switch (ErrorCode) {
                    // 0 - 999 - Comes from SgFplib.h
                    // 1,000 - 9,999 - SGIBioSrv errors 
                    // 10,000 - 99,999 license errors
                    case 51:
                        Description = "System file load failure";
                        break;
                    case 52:
                        Description = "Sensor chip initialization failed";
                        break;
                    case 53:
                        Description = "Device not found";
                        break;
                    case 54:
                        Description = "Fingerprint image capture timeout";
                        break;
                    case 55:
                        Description = "No device available";
                        break;
                    case 56:
                        Description = "Driver load failed";
                        break;
                    case 57:
                        Description = "Wrong Image";
                        break;
                    case 58:
                        Description = "Lack of bandwidth";
                        break;
                    case 59:
                        Description = "Device Busy";
                        break;
                    case 60:
                        Description = "Cannot get serial number of the device";
                        break;
                    case 61:
                        Description = "Unsupported device";
                        break;
                    case 63:
                        Description = "SgiBioSrv didn't start; Try image capture again";
                        break;
                    default:
                        Description = "Unknown error code or Update code to reflect latest result";
                        break;
                }
                return Description;
            }

            function handSelect() {
                var x = document.getElementById("option").value;
                if (x == 1)
                    x = 1;
                else if (x == 2)
                    x = 2;
                else if (x == 3)
                    x = 6;
                else if (x == 4)
                    x = 7;
                document.getElementById("FPImage1").src = "images/hands/" + x + ".png";

            }

            function captureFP() {
                CallSGIFPGetData(SuccessFunc, ErrorFunc);
            }

            /* 
             This functions is called if the service sucessfully returns some data in JSON object
             */
            function SuccessFunc(result) {
                if (result.ErrorCode == 0) {
                    /* 	Display BMP data in image tag
                     BMP data is in base 64 format 
                     */
                    if (result != null && result.BMPBase64.length > 0) {
                        document.getElementById("FPImage1").src = "data:image/bmp;base64," + result.BMPBase64;
                    }

                    /*   var tbl = "<table border=1>";
                     tbl += "<tr>";
                     tbl += "<td> Serial Number of device </td>";
                     tbl += "<td> <b>" + result.SerialNumber + "</b> </td>";
                     tbl += "</tr>";
                     tbl += "<tr>";
                     tbl += "<td> Image Height</td>";
                     tbl += "<td> <b>" + result.ImageHeight + "</b> </td>";
                     tbl += "</tr>";
                     tbl += "<tr>";
                     tbl += "<td> Image Width</td>";
                     tbl += "<td> <b>" + result.ImageWidth + "</b> </td>";
                     tbl += "</tr>";
                     tbl += "<tr>";
                     tbl += "<td> Image Resolution</td>";
                     tbl += "<td> <b>" + result.ImageDPI + "</b> </td>";
                     tbl += "</tr>";
                     tbl += "<tr>";
                     tbl += "<td> Image Quality (1-100)</td>";
                     tbl += "<td> <b>" + result.ImageQuality + "</b> </td>";
                     tbl += "</tr>";
                     tbl += "<tr>";
                     tbl += "<td> NFIQ (1-5)</td>";
                     tbl += "<td> <b>" + result.NFIQ + "</b> </td>";
                     tbl += "</tr>";
                     tbl += "<tr>";
                     tbl += "<td> Template(base64)</td>";
                     tbl += "<td> <b> <textarea rows=8 cols=50>" + result.TemplateBase64 + "</textarea></b> </td>";
                     tbl += "</tr>";
                     tbl += "</table>";
                     document.getElementById('result').innerHTML = tbl; */
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
                params += "&Quality=50";//+ document.getElementById("50").value;
                params += "&licstr=" + encodeURIComponent(secugen_lic);
                params += "&templateFormat=ANSI";// + document.getElementById("ANSI").value;
                xmlhttp.open("POST", uri, true);
                xmlhttp.send(params);
            }


        </script>
    </body>
</html>

