/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function verifyFinger()
{
    var domElement = document.getElementById("fingerData");
    if (domElement.value.length == 0)//if (domElement !== null && domElement.value === "")//if(value!=null || value == "")
        alert('Please provide your fingerprint');
    else {
        document.getElementById('verifyForm').action = "../../verify3.jsp";
        document.getElementById("verifyForm").submit();
    }

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

    //var secugen_lic_era = "SpIQ6A3uI9qStHW6Ka1ADzRb5ItpIXZzRe4aV9bVFJnlzyve6NopAvYwEz2mbQ5E";
    var secugen_lic_BA = "1eZ6LTz9qRsTaplE1bHO8U3yWdfykHQaVjEPv4/MDE0=";
    var params = "Timeout=10000";// + document.getElementById("10000").value;
    params += "&Quality=60";//+ document.getElementById("50").value;
    params += "&licstr=" + encodeURIComponent(secugen_lic_BA);
    params += "&templateFormat=ANSI";// + document.getElementById("ANSI").value;
    xmlhttp.open("POST", uri, true);
    xmlhttp.send(params);
}

function close() {
    xmlhttp.abort();
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


