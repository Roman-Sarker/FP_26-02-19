/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function parseOperationDsc(opDsc) {
    if (opDsc.state == 'done') {
        if (opDsc.status == 'success')
            return true;
        else if (opDsc.status == 'fail')
            return false;
    } else if (opDsc.state == 'init') {
        return true;
    }
}


var fpHTTSrvOpEP = "http://127.0.0.1:15170/fpoperation";
function beginOperation() {
    var json = JSON.stringify({operation: "enroll", username: "", usedlib: "ansisdk", isoconv: "1", samplenum: 1});
    var req = new XMLHttpRequest(); 
    req.open("POST", fpHTTSrvOpEP, false);
    req.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    req.send(json);
    return req;
}

function CheckFPHttpSrvConnection()
{
    var req = new XMLHttpRequest(); 
    req.open('GET', fpHTTSrvOpEP, false);
    req.send();
    return req;
}

function checkFutronicDevice() {
    try {
        var req = CheckFPHttpSrvConnection();
        if (req.status == 200) {
            var req1 = beginOperation();
            if (req1.status == 200) 
                return parseOperationDsc(JSON.parse(req1.response));
            else
                return false;
        } else
            return false;
    } catch (e) {
        return false;
    }

}

function checkDigitalPersonaDevice() {
    var readers = new Array();
    try {
        var readers = objReaderCollection.GetReaders();
        if (readers.count <= 0)
            return false;
        else
            return true;
    } catch (e) {
        return false;
    }
}

function checkSecugenDevice() {
    try {
        var xmlhttp = checkSGDevice();
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            fpobject = JSON.parse(xmlhttp.responseText);
            if (fpobject.ErrorCode == 54)
                return true;
            else
                return false;
        } else
            return false;
    } catch (e) {
        return false;
    }
}

function checkSGDevice() {
    var uri = "https://localhost:8443/SGIFPCapture";
    var xmlhttp, secugenFlag;

    if (window.XMLHttpRequest)
        xmlhttp = new XMLHttpRequest();
    else
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

    //var secugen_lic_era = "SpIQ6A3uI9qStHW6Ka1ADzRb5ItpIXZzRe4aV9bVFJnlzyve6NopAvYwEz2mbQ5E";
    var secugen_lic_BA = "1eZ6LTz9qRsTaplE1bHO8U3yWdfykHQaVjEPv4/MDE0=";
    var params = "Timeout=1";// + document.getElementById("10000").value;
    params += "&Quality=60";//+ document.getElementById("50").value;
    params += "&licstr=" + encodeURIComponent(secugen_lic_BA);
    params += "&templateFormat=ISO";// + document.getElementById("ANSI").value;
    xmlhttp.open("POST", uri, false);
    xmlhttp.send(params);
    return xmlhttp;

}

