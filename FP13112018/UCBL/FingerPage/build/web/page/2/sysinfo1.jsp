<HTML>
    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">
        <meta http-equiv="x-ua-compatible" content="IE=10" >
        <style TYPE="text/css">
            <!-- BODY				{ font-family:arial,helvetica; margin-left:5; margin-top:0}
            A 					{ color:#FF5500; text-decoration:underline}
            A:hover,A:active	{ color:#0055FF; text-decoration:underline}
            -->
        </style> 

        <script type="text/javascript">
            function GetLearnModel() {
                try {
                    var wldscan = new ActiveXObject("WebLogonDemoClient.GETInfo");
                    var gSl = wldscan.getHardDiskSerial().trim();
                    document.getElementById("SlNo").setAttribute('value', gSl);
		     
                    var gIp = wldscan.getIp().trim();
                    document.getElementById("IP").setAttribute('value', gIp);
		     		
                    var UUID = wldscan.GetUUID(".").trim();
                    document.getElementById("UUID").setAttribute('value', UUID);
                     
		    var MAC = wldscan.macADD().trim();
                    document.getElementById("MAC").setAttribute('value', MAC);
                     
		    var PcName = wldscan.GetComputerName().trim(); 
                    document.getElementById("PcName").setAttribute('value', PcName);
                     
		    var OsUser = wldscan.GetUserName().trim();
                    document.getElementById("OsUser").setAttribute('value', OsUser);
                     

                    var PcInfo = wldscan.PcInfo().trim();
                    document.getElementById("PcInfo").setAttribute('value', PcInfo); 
                    
                    
		    var systemDate = wldscan.getSystemDte();
                    var a = new Date(); 
		    document.getElementById("sysdte").setAttribute('value', systemDate.toGMTString());
                  

                    var DNS = wldscan.getDNS(); 
                    document.getElementById("DNS").setAttribute('value', DNS); 

                } catch (err) {
                  //   alert(err.toString());
                  /*  var vDebug = "";
                    for (var prop in err)
                    {
                        vDebug += "property: " + prop + " value: [" + err[prop] + "]\n";
                    }
                    vDebug += "toString(): " + " value: [" + err.toString() + "]";
                    alert(vDebug); */
//alert("To enroll with a fingerprint device, you should install the WebLogonDemoClient software first.", "Software Not Install Error");
                }
document.getElementById("scan").submit();
            }
        </script>
        <title>Enroll Fingerprint.</title>
    </HEAD>
    <BODY Onload="GetLearnModel()">
    <center>
        <%
                String sessid = request.getParameter("sessid");
                String username = request.getParameter("username");
                String name = request.getParameter("name"); 
                String check = request.getParameter("check");
                String finger = request.getParameter("finger");
            %> 
            
      <!--  <table border="0" width="800"> --> 
            <Form name="scan"  id="scan" method="Post" action="sysinfo2.jsp" >
                <Input type="text" name="LearnModel" id="LearnModel" value="">
                <Input type="text" name="SlNo" id="SlNo" value="">
                <Input type="text" name="IP" id="IP" value="">
                <Input type="hidden" name="UUID" id="UUID" value="">
                <Input type="hidden" name="MAC" id="MAC"  value="">
                <Input type="hidden" name="PcName" id="PcName"  value="">
                <Input type="hidden" name="OsUser"  id="OsUser" value="">
                <Input type="hidden" name="DNS" id="DNS" value="">
                <Input type="hidden" name="PcInfo" id="PcInfo"  value="">
                <Input type="text" name="sysdte" id="sysdte" value="">
                <Input type="text" name="name"  value=<%=name%> />
                <Input type="text" name="check"  value=<%=check%> />
                <Input type="text" name="sessid"  value=<%=sessid%> />
                <Input type="hidden" name="username"  value= <%=username%> />
            </Form> 
      <!--  </table>  --> 
	 
    </center>
</BODY>
</HTML>
