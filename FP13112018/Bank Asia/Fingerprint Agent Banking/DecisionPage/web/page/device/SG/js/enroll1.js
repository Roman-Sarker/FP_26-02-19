/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var secugen_lic = "";//1eZ6LTz9qRsTaplE1bHO8U3yWdfykHQaVjEPv4/MDE0=";

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

   // var secugen_lic_era = "SpIQ6A3uI9qStHW6Ka1ADzRb5ItpIXZzRe4aV9bVFJnlzyve6NopAvYwEz2mbQ5E";
    //var secugen_lic_era = "SpIQ6A3uI9qStHW6Ka1ADzRb5ItpIXZzRe4aV9bVFJnlzyve6NopAvYwEz2mbQ5E";
     var secugen_lic_BA = "1eZ6LTz9qRsTaplE1bHO8U3yWdfykHQaVjEPv4/MDE0=";
    var params = "Timeout=10000";// + document.getElementById("10000").value;
    params += "&Quality=70";//+ document.getElementById("50").value;
    params += "&licstr=" + encodeURIComponent(secugen_lic_BA);
    params += "&templateFormat=ISO";// + document.getElementById("ANSI").value;
    xmlhttp.open("POST", uri, true);
    xmlhttp.send(params);
    
}
 

function takeFingerData(index) {
    document.getElementById("FPImage1").src = "../../assets/images/hands/" + index + ".png";
    document.getElementById("FPImage1").style.backgroundColor = "#FFFF99";
    makeStatusMSgHidden("status_alert");
    captureFP();
}

function captureFP() {
    CallSGIFPGetData(SuccessFunc, ErrorFunc);
}

function showModal() {
    $('#myModal').modal('exampleModalCenter');
    //document.getElementById("FPImage1").src = "../../images/hands/1.png";
    makeStatusMSgHidden("status_alert");
}

function SuccessFunc(result) {

    if (result.ErrorCode == 0) {
        if (result != null && result.BMPBase64.length > 0) {
            var fingerData = result.TemplateBase64;
            var quality = result.ImageQuality;
            var imgSrc = document.getElementById("FPImage1").src;

            if (imgSrc.indexOf("1.png") !== -1)
                doProcessing("LeftThumb", "LThumb", "Left Thumb", fingerData, quality);
            else if (imgSrc.indexOf("2.png") !== -1)
                doProcessing("LeftIndex", "LIndex", "Left Index", fingerData, quality);
            else if (imgSrc.indexOf("3.png") !== -1)
                doProcessing("LeftMiddle", "LMiddle", "Left Middle", fingerData, quality);
            else if (imgSrc.indexOf("4.png") !== -1)
                doProcessing("LeftRing", "LRing", "Left Ring", fingerData, quality);
            else if (imgSrc.indexOf("5.png") !== -1)
                doProcessing("LeftLittle", "LLittle", "Left Little", fingerData, quality);
            else if (imgSrc.indexOf("6.png") !== -1)
                doProcessing("RightThumb", "RThumb", "Right Thumb", fingerData, quality);
            else if (imgSrc.indexOf("7.png") !== -1)
                doProcessing("RightIndex", "RIndex", "Right Index", fingerData, quality);
            else if (imgSrc.indexOf("8.png") !== -1)
                doProcessing("RightMiddle", "RMiddle", "Right Middle", fingerData, quality);
            else if (imgSrc.indexOf("9.png") !== -1)
                doProcessing("RightRing", "RRing", "Right Ring", fingerData, quality);
            else if (imgSrc.indexOf("10.png") !== -1)
                doProcessing("RightLittle", "RLittle", "Right Little", fingerData, quality);

            document.getElementById("FPImage1").src = "data:image/bmp;base64," + result.BMPBase64;
            document.getElementById("serial").value = result.SerialNumber;
        }
    } else {
        alert("Fingerprint Capture Error Code:  " + result.ErrorCode + ".\nDescription:  " + ErrorCodeToString(result.ErrorCode) + ".");
    }
    document.getElementById("FPImage1").style.backgroundColor = "#FFFFFF";
}

function doProcessing(buttonId, inputDOM, statusMes, fingerData, quality) {
    document.getElementById(inputDOM).value = fingerData;
    makeStatusMSgVisible("status_alert", statusMes + " is successfully captured . Quality is " + quality);
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
        document.getElementById('enrollForm').action = "../../enroll2.jsp";
        document.getElementById("enrollForm").submit();
    }
}



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

