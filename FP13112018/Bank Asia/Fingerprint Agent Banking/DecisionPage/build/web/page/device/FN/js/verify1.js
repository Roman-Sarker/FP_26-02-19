/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var fpHTTSrvOpEP = "http://127.0.0.1:15170/fpoperation"
var resultLink;
var fingerFrame;
var lastInitOp;
var nameOfDOM;
var inputDOM, domMsgString;



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
    document.getElementById("fingerframe").style.backgroundColor = "#FFFF99";
    beginOperation()
}

function beginOperation() {
    document.getElementById("fingerframe").style.backgroundColor = "#FFFF99";
    var opName = 'enroll';
    var libName = 'ansisdk';
    var json = JSON.stringify({operation: opName, username: "", usedlib: libName, isoconv: "0", samplenum: 1});

    var req = new XMLHttpRequest();
    req.open("POST", fpHTTSrvOpEP);
    req.setRequestHeader('Content-type', 'application/json; charset=utf-8');

    req.onload = function () {
        if (req.status == 200) {
            parseOperationDsc(JSON.parse(req.response));
        } else
            alert("Error code : "+req.statusText);
    };
    req.onerror = function () {
        alert("You have to install futronic sdk");
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
            if (req.readyState == 4)
                parseOperationDsc(JSON.parse(req.response));
        } else
            alert(" Error : " + req.statusText);
    };
    req.onerror = function () {
        alert("You have to install Futronic SDK");

    };
    req.send();

}

function getOperationImg(opId, frameWidth, frameHeight) {
   // alert("is it ok ? ");
    var url = fpHTTSrvOpEP + '/' + opId + '/image';
    var req = new XMLHttpRequest();
    req.open('GET', url);
    req.responseType = "arraybuffer";
    req.onload = function () {
         
        if (req.status == 200) {
            drawFingerFrame(new Uint8Array(req.response), opId, frameWidth, frameHeight);
        } else {
            alert("fingerprint image is not got");
        }
    };
    req.onerror = function () {
        alert("some error is occurred");
    };
    req.send();    
}

function arrayBufferToBase64(buffer) {
    var binary = '';
    var bytes = buffer;
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) 
        binary += String.fromCharCode(bytes[ i ]);
     
    var base64String = window.btoa(binary); 
    return base64String;
}

function getOperationTemplate(urlOfTemplate) {
    var url = urlOfTemplate; 
    var req = new XMLHttpRequest();
    req.open('GET', url);
    req.responseType = "arraybuffer";

    req.onload = function () {
        if (req.status == 200) {
            var byteArray = new Uint8Array(req.response);
            var b64Encoded = arrayBufferToBase64(byteArray);
            document.getElementById("fingerData").value = b64Encoded;
            setTimeout(verifyFinger, 500);
        } else {
            alert("fingerprint template is not got by system");
        }
    };
    
    req.onerror = function () {
        alert("no error is got by system");
    };

    req.send(); 
}

function linkOperationTemplate(opId, operationName) {
    var target = "/template";
    var url = fpHTTSrvOpEP + '/' + opId + target;
    getOperationTemplate(url);
}

function deleteOperation(opId) {
    var url = fpHTTSrvOpEP + '/' + opId;
    deleteVerb(url);
}

function parseOperationDsc(opDsc) {
    var res = true; 

    if (opDsc.state == 'done') {

        if (opDsc.status == 'success') {
            linkOperationTemplate(opDsc.id, opDsc.operation)
        }

        if (opDsc.status == 'fail') {
            alert("error is " + opDsc.errorstr);
            res = false;

            if (parseInt(opDsc.errornum) != -1) {
                deleteOperation(opDsc.id);
            }
        }
    } else if (opDsc.state == 'init') {
        lastInitOp = opDsc.id ;
        try{
            deviceSN = opDsc.sn;
            document.getElementById('Sl').value=deviceSN;
        }catch(e){
            document.getElementById('Sl').value="";
        }
        setTimeout(getOperationState, 1000, opDsc.id); 
        setTimeout(getOperationImg, 1000, opDsc.id, parseInt(opDsc.devwidth), parseInt(opDsc.devheight));
    } else if (opDsc.state == 'inprogress')
    {
        if (opDsc.fingercmd == 'puton') {
            //  setAskTest("Put finger on scanner");
        }

        if (opDsc.fingercmd == 'takeoff') {
            // setAskTest("Take off finger from scanner");
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
        imgData.data[4 * i] = frameBytes[i]; // red 
        imgData.data[4 * i + 1] = frameBytes[i];// green 
        imgData.data[4 * i + 2] = frameBytes[i]; // blue 
        imgData.data[4 * i + 3] = 255;//alpha
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
            alert("some error occurred"+req.statusText);
        }
    };
    req.onerror = function () {
        alert("You have to install futronic sdk");
    };
    req.send();
}

function put(url) {
    var req = new XMLHttpRequest();
    req.open('PUT', url);

    req.onload = function () {
        if (req.status != 200) {
            alert("Server response(" + req.statusText + ")");
        }
    };

    req.onerror = function () {
        alert("You have to install futronic sdk");
    };

    req.send();
}

function CheckFPHttpSrvConnection()
{
    
    var req = new XMLHttpRequest();
    req.open('GET', fpHTTSrvOpEP);
    req.onload = function () {
        if (req.status != 200) {
            alert("some error occurred "+req.statusText);
        }
    };
    req.onerror = function () {
        alert("You have to install Futronic sdk");
        setTimeout(CheckFPHttpSrvConnection, 1000);
    };
    req.send();
}

function onBodyLoad()
{
    fingerFrame = document.getElementById("fingerframe");
    var defImg = new Image();

    defImg.onload = function () {
        var context = fingerFrame.getContext('2d');
        context.drawImage(defImg, 0, 0);
    };
    defImg.src = "../../assets/images/hands/1_verify.png";

    CheckFPHttpSrvConnection();
}
 
