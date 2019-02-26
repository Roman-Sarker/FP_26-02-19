/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var readers;
var reader;
var result;
var fid1, fmd;
var finger_name = ["LThumb", "LIndex", "LMiddle", "LRing", "LLittle", "RThumb", "RIndex", "RMiddle", "RRing", "RLittle"], finger_no;
var OKAY = 0;
var CONTINUE = 1;
var ERROR = 2;
var start = 0;
var refreshFlag = -1;

var keyStr = "ABCDEFGHIJKLMNOP" +
        "QRSTUVWXYZabcdef" +
        "ghijklmnopqrstuv" +
        "wxyz0123456789+/=";

function decode64(input) {

    var output = "";

    var chr1, chr2, chr3 = "";

    var enc1, enc2, enc3, enc4 = "";

    var i = 0;


    // remove all characters that are not A-Z, a-z, 0-9, +, /, or =

    var base64test = /[^A-Za-z0-9\+\/\=]/g;

    if (base64test.exec(input)) {

        alert("There were invalid base64 characters in the input text.\n" +
                "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                "Expect errors in decoding.");

    }

    input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
    do {
        enc1 = keyStr.indexOf(input.charAt(i++));
        enc2 = keyStr.indexOf(input.charAt(i++));
        enc3 = keyStr.indexOf(input.charAt(i++));
        enc4 = keyStr.indexOf(input.charAt(i++));
        chr1 = (enc1 << 2) | (enc2 >> 4);
        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
        chr3 = ((enc3 & 3) << 6) | enc4;

        output = output + String.fromCharCode(chr1);
        if (enc3 != 64) {
            output = output + String.fromCharCode(chr2);
        }
        if (enc4 != 64) {
            output = output + String.fromCharCode(chr3);
        }
        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";
    } while (i < input.length);

    return unescape(output);

}

function takeFingerData(index) {
    try {
        document.getElementById("FPImage1").src = "../../assets/images/hands/" + index + ".png";
        document.getElementById("FPImage1").style.backgroundColor = "#FFFF99";
        makeStatusMSgHidden("status_alert");
        finger_no = index - 1;
        Capture();
    } catch (e) {
        alert(e.message);
    }

}


function Capture() {
    if (start == 1)
    {
        reader.CancelCapture();
        reader.Dispose();
    }

    if (reader == null) {
        RefreshList();
    }
    result = reader.Open("DP_PRIORITY_COOPERATIVE");

    if (result != "DP_SUCCESS") {
        document.getElementById("FPImage1").style.backgroundColor = "#FFFFFF";
        alert("Non-success result:(capture)  " + result + ".");
        reader.Dispose();
    } else {
        CaptureThread();
        document.getElementById("FPImage1").style.backgroundColor = "#FFFF99";
    }
}


function CaptureThread() {
    result = reader.GetStatus();

    while (1) {
        if (result == "DP_STATUS_BUSY") {
            Pause(50);
            continue;
        } else if (result == "DP_STATUS_NEED_CALIBRATION") {
            reader.Calibrate();
        } else if (result != "DP_STATUS_READY" && result != "DP_SUCCESS") {
            alert("Non-success result: (CaptureThread) " + result + ".");
            reader.Dispose();
            document.getElementById("FPImage1").style.backgroundColor = "#FFFFFF";
            break;
        }

        reader.Capture("ISO", 0, -1, 500);
        start = 1;
        break;
    }
}


function ValidateCapture(captureResult)
{
    if (captureResult == null)
    {
        alert("Error occurred in capture.  Reselect a reader and try again.");
        return ERROR;
    }

    if (captureResult.ResultCode != "DP_SUCCESS") {
        alert("Non-success result:(ValidateCapture) " + captureResult.ResultCode + ".");
        return ERROR;
    }

    if (captureResult.Quality == "DP_QUALITY_CANCELED") {
        return CONTINUE;
    } else if (captureResult.Quality == "DP_QUALITY_GOOD") {
        return OKAY;
    } else if (captureResult.Quality == "DP_QUALITY_TIMED_OUT") {
        alert("Warning: A timeout occurred while capturing. Please start the operation over.");
        return CONTINUE;
    } else if (captureResult.Quality == "DP_QUALITY_NO_FINGER") {
        alert("Warning: No finger detected.");
        return OKAY;
    } else if (captureResult.Quality == "DP_QUALITY_FAKE_FINGER") {
        alert("Warning: Fake finger detected.");
        return OKAY;
    }

    return OKAY;
}

function close() {
    if (reader != null) {
        reader.CancelCapture();
        reader.Dispose();
    }
}




function showModal() {
    $('#myModal').modal('exampleModalCenter');
    //document.getElementById("FPImage1").src = "../../images/hands/1.png";
    makeStatusMSgHidden("status_alert");
    refreshFlag = RefreshList();
}


function RefreshList() {
    try {
        readers = new Array();
        readers = objReaderCollection.GetReaders();

        if (readers.count > 0) {
            $('#fingerModal').modal('show');
            reader = readers(0);
            objReader.ReaderX = reader.ReaderX; // Replace the reader with registered events with the reader returned by ReaderCollection.
            objReader.Dispose(); // In case user did not wait for reader to clean up.  Must create a new IE session (File->NewSession) or create IE using -nomerge argument.  
            document.getElementById("serial").value = reader.SerialNumber;
            return 1;
        } else
            alert('No device is connected to PC!');
    } catch (e) {
        alert("Error " + e.message);
    }
    return -1;
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
