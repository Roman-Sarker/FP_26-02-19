/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var fpHTTSrvOpEP = "http://127.0.0.1:15170/fpoperation";
var resultLink;
var fingerFrame;
var lastInitOp;
var nameOfDOM;
var inputDOM, domMsgString;

function takeFingerData(index) {
    document.getElementById("FPImage1").src = "../../assets/images/hands/" + index + ".png";
    document.getElementById("FPImage1").style.backgroundColor = "#FFFF99";
    makeStatusMSgHidden("status_alert");
    captureFP();
}

function captureFP() {
    
}

function showModal() {
    $('#myModal').modal('exampleModalCenter');
    //document.getElementById("FPImage1").src = "../../images/hands/1.png";
    makeStatusMSgHidden("status_alert");
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


function fixError(statusText, errorText) {
    textResult.style = "color:blue";
    changeClassOfDomElement("status_alert", "danger_alert");

    if (errorText != "") {
        if (statusText != "") {
            textResult.innerHTML = errorText + "(" + statusText + ")";
        } else {
            textResult.innerHTML = errorText;
        }
    } else {
        textResult.innerHTML = statusText
    }
}

function setAskTest(textMes) {
    textResult.style = "color:blue"
    textResult.innerHTML = textMes;
}

function setOperationResult(textMes) {
    textResult.style = "color:blue"
    textResult.innerHTML = textMes;
}

function beginOperation(nameOfDOM, msgString, opName, libName, sendSampleNum) {
    inputDOM = nameOfDOM;
    domMsgString = msgString;

    var json = JSON.stringify({operation: opName, username: "", usedlib: libName, isoconv: "1", samplenum: "1"});
    changeClassOfDomElement("status_alert", "success_alert");

    var req = new XMLHttpRequest();
    req.open("POST", fpHTTSrvOpEP);
    req.setRequestHeader('Content-type', 'application/json; charset=utf-8');

    req.onload = function () {
        if (req.status == 200) {
            setAskTest("Operation begin");
            parseOperationDsc(JSON.parse(req.response));
        } else
            fixError(req.statusText, "Server response");
    };
    req.onerror = function () {
        changeClassOfDomElement("status_alert", "danger_alert");
        makeStatusMSgVisible("status_alert", "Yu have to install futronic sdk");
    };
    req.send(json);
}

function cancelOperation() {
    var url = fpHTTSrvOpEP + '/' + lastInitOp + '/cancel';
    put(url);
}

function getOperationState(opId) {
    var url = fpHTTSrvOpEP + '/' + opId;
    var req = new XMLHttpRequest();
    req.open('GET', url);
    req.onload = function () {
        if (req.status == 200) {
            if (req.readyState == 4) {
                parseOperationDsc(JSON.parse(req.response));
            }
        } else {
            fixError(req.statusText, "Server response");

        }
    };
    req.onerror = function () {
        fixError("", "You have to install Futronic SDK");

    };
    req.send();
}

function getOperationImg(opId, frameWidth, frameHeight) {
    var url = fpHTTSrvOpEP + '/' + opId + '/image';
    var req = new XMLHttpRequest();
    req.open('GET', url);
    req.responseType = "arraybuffer";
    req.onload = function () {
        if (req.status == 200) {
            drawFingerFrame(new Uint8Array(req.response), opId, frameWidth, frameHeight);
        } else {
            changeClassOfDomElement("status_alert", "danger_alert");
            makeStatusMSgVisible("status_alert", "fingerprint image is not got");
        }
    };
    req.onerror = function () {
        enableControlsForOp(false);
    };

    req.send();    
}

function arrayBufferToBase64(buffer) {
    var binary = '';
    var bytes = buffer;
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[ i ]);
    }

    var base64String = window.btoa(binary);
    // window.alert(base64String);
    return base64String;
}

function getOperationTemplate(urlOfTemplate) {
    try {
        var url = urlOfTemplate;
        var req = new XMLHttpRequest();
        req.open('GET', url);
        req.responseType = "arraybuffer";

        req.onload = function () {
            if (req.status == 200) {
                var byteArray = new Uint8Array(req.response); 
                var b64Encoded = arrayBufferToBase64(byteArray);
                document.getElementById(inputDOM).value = b64Encoded;
                changeClassOfDomElement("status_alert", "success_alert");
                makeStatusMSgVisible("status_alert", domMsgString + " is successfully captured");
            } else {
                changeClassOfDomElement("status_alert", "danger_alert");
                makeStatusMSgVisible("status_alert", "fingerprint template is not got by the system");
            }
        };
        req.onerror = function () {
            changeClassOfDomElement("status_alert", "danger_alert");
            makeStatusMSgVisible("status_alert", "fingerprint template is not got by the system");
        };

        req.send();
    } catch (e) {
        alert(e.message);
    }
}



function linkOperationTemplate(opId, operationName) {
    var target = "/template";
    var url = fpHTTSrvOpEP + '/' + opId + target;
    getOperationTemplate(url);
  
 /* var saveAs = "template.bin";
    var resultText = "Result template";
    resultLink.href = url;
    
    resultLink.download = saveAs;
    resultLink.innerHTML = resultText;
    resultLink.click();  */
}

function deleteOperation(opId) {
    var url = fpHTTSrvOpEP + '/' + opId;
    deleteVerb(url);
}

function parseOperationDsc(opDsc) {
    var res = true;

    if (opDsc.state == 'done') {
        enableControlsForOp(false);

        if (opDsc.status == 'success') {
            setOperationResult(opDsc.message);
            linkOperationTemplate(opDsc.id, opDsc.operation)
        }

        if (opDsc.status == 'fail') {
            fixError("", opDsc.errorstr)
            res = false;

            if (parseInt(opDsc.errornum) != -1) {
                deleteOperation(opDsc.id);
            }
        }
    } else if (opDsc.state == 'init') {
        lastInitOp = opDsc.id ;
        try{
            deviceSN = opDsc.sn;
            document.getElementById('serial').value=deviceSN;
        }catch(e){
            document.getElementById('serial').value="";
        }
        setTimeout(getOperationState, 1000, opDsc.id);
        setTimeout(getOperationImg, 1000, opDsc.id, parseInt(opDsc.devwidth), parseInt(opDsc.devheight));
    } else if (opDsc.state == 'inprogress')
    {
        if (opDsc.fingercmd == 'puton') {
            setAskTest("Put finger on scanner");
        }

        if (opDsc.fingercmd == 'takeoff') {
            setAskTest("Take off finger from scanner");
        }

        setTimeout(getOperationState, 1000, opDsc.id);
        setTimeout(getOperationImg, 1000, opDsc.id, parseInt(opDsc.devwidth), parseInt(opDsc.devheight));
    }

    return res;
}

function drawFingerFrame(frameBytes, opId, frameWidth, frameHeight) {
    var ctx = fingerFrame.getContext('2d');
    var tempCanvas = document.createElement("canvas");
    tempCanvas.width = frameWidth;
    tempCanvas.height = frameHeight;
    var tempCtx = tempCanvas.getContext("2d");
    var imgData = tempCtx.createImageData(frameWidth, frameHeight);
    for (var i = 0; i < frameBytes.length; i++) {
        // red
        imgData.data[4 * i] = frameBytes[i];
        // green
        imgData.data[4 * i + 1] = frameBytes[i];
        // blue
        imgData.data[4 * i + 2] = frameBytes[i];
        //alpha
        imgData.data[4 * i + 3] = 255;
    }
    tempCtx.putImageData(imgData, 0, 0, 0, 0, frameWidth, frameHeight);
    ctx.drawImage(tempCanvas, 0, 0, frameWidth, frameHeight, 0, 0, fingerFrame.width, fingerFrame.height);
}

function deleteVerb(url) {
    var req = new XMLHttpRequest();
    req.open("DELETE", url);
    req.onload = function () {
        if (req.status == 200) {
        } else {
            fixError(req.statusText, "Server response");
        }
    };
    req.onerror = function () {
        fixError("", "You have to install futronic sdk");
    };
    req.send();
}

function put(url) {
    var req = new XMLHttpRequest();
    req.open('PUT', url);
    req.onload = function () {
        if (req.status != 200) {
            fixError(req.statusText, "Server response");
        }
    };
    req.onerror = function () {
        fixError("", "FPHttpServer not available");
    };
    req.send();
}

function enableControls() {
}

function enableControlsForOp(opBegin) {
}

function CheckFPHttpSrvConnection()
{
    var req = new XMLHttpRequest();
    req.open('GET', fpHTTSrvOpEP);
    req.onload = function () {
        if (req.status == 200) {
            enableControls();
            setAskTest("Press operation button");
        } else {
            fixError(req.statusText, "Server response")
        }
    };
    req.onerror = function () {
        fixError("", "You have to install Futronic sdk");
        setTimeout(CheckFPHttpSrvConnection, 1000);
    };
    req.send();
}

function onBodyLoad()
{
    textResult = document.getElementById("status_alert");

    fingerFrame = document.getElementById("fingerframe");
    resultLink = document.getElementById("resultLink");


    var defImg = new Image();

    defImg.onload = function () {
        var context = fingerFrame.getContext('2d');
        context.drawImage(defImg, 0, 0);
    };
    defImg.src = "../../assets/images/hands/1.png";

    CheckFPHttpSrvConnection();
}

