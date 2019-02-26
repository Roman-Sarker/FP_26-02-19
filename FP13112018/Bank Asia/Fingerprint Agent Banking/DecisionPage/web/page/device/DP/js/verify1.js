/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



var readers;
var reader;
var result;
var fid1, fmd;

var OKAY = 0;
var CONTINUE = 1;
var ERROR = 2;
var start = 0;



function captureFP() {
    var deviceCheckFlag = RefreshList() ;
    if(deviceCheckFlag == 1){ 
        document.getElementById("FPImage1").style.backgroundColor = "#FFFF99";
        Capture();
    }
}

function RefreshList() {
    try {
        readers = new Array();
        readers = objReaderCollection.GetReaders();

        if (readers.count > 0) {
            reader = readers(0);
            objReader.ReaderX = reader.ReaderX; // Replace the reader with registered events with the reader returned by ReaderCollection.
            objReader.Dispose(); // In case user did not wait for reader to clean up.  Must create a new IE session (File->NewSession) or create IE using -nomerge argument.  
            document.getElementById("Sl").value = reader.SerialNumber;  
            return 1;
        } else
            alert('No device is connected to PC!');
    } catch (e) {
        alert(e.message);
    }
    return -1;
}

function Capture() {
    if (start == 1){
        reader.CancelCapture();
        reader.Dispose();
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

        reader.Capture("ANSI", 0, -1, 500);
        start = 1;
        break;
    }
}

function printDataToDOM(j) {
    var formData = j.serializeArray(); // Create array of object
    var jsonConvert = JSON.stringify(formData);
    alert(jsonConvert);
    //  document.getElementById("ok").value = json;
    // document.write(json);
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

function verifyFinger()
{
    var domElement = document.getElementById("fingerData");
    if (domElement.value.length == 0)//if (domElement !== null && domElement.value === "")//if(value!=null || value == "")
        alert('Please provide your fingerprint');
    else{
        document.getElementById("verifyForm").action= "../../verify3.jsp";;
        document.getElementById("verifyForm").submit();
    }

}

function parseXML(text)
{
    var parser, xmlDoc;
    parser = new DOMParser();
    xmlDoc = parser.parseFromString(text, "text/xml");
    return xmlDoc.getElementsByTagName("Bytes")[0].childNodes[0].nodeValue;
}
