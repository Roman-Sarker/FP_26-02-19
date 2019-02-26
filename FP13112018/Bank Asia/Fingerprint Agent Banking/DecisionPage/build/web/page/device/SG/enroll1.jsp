<%-- 
    Document   : test
    Created on : Sep 12, 2017, 4:41:30 PM
    Author     : Sultan Ahmed
--%>
  
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html> 
<HTML>
    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">
        <title>Enroll Page</title>
        <link rel="shortcut icon" href="../../assets/ico/favicon.jpg">
        <link href="css/bootstrap.min.css" rel="stylesheet" />   
        <link href="css/main_style.css" rel="stylesheet" />
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script> 
        <script src="js/enroll1.js"></script> 
        <script src="js/codeSeen.js"></script> 
        <script type="text/javascript">
            code_seen_off();
        </script>

        
    </HEAD>
    <body> 
        <% 
            String name = request.getParameter("name"); 
            String app_user = request.getParameter("app_user");
            String ai_logid = request.getParameter("ai_logid");
            String cust_type = request.getParameter("cust_type"); 

        %>
            
        <form method="post" id="enrollForm" action="">
            <Input type="Hidden" name="name"  id="name" value="<%=name%>"> 
            <Input type="Hidden" name="app_user"  id="app_user" value="<%=app_user%>"> 
            <Input type="Hidden" name="ai_logid"  id="ai_logid" value="<%=ai_logid%>">  
            <Input type="Hidden" name="cust_type"  id="cust_type" value="<%=cust_type%>">
            <Input type="Hidden" name="LThumb"  id="LThumb" value=">"> 
            <Input type="Hidden" name="LIndex"  id="LIndex" value="">  
            <Input type="Hidden" name="LMiddle"  id="LMiddle" value="">  
            <Input type="Hidden" name="LRing"  id="LRing" value="">   
            <Input type="Hidden" name="LLittle"  id="LLittle" value=""> 
            <Input type="Hidden" name="RThumb"  id="RThumb" value=""> 
            <Input type="Hidden" name="RIndex"  id="RIndex" value="">  
            <Input type="Hidden" name="RMiddle"  id="RMiddle" value="">  
            <Input type="Hidden" name="RRing"  id="RRing" value="">  
            <Input type="Hidden" name="RLittle"  id="RLittle" value="">  
            <Input type="Hidden" name="serial"  id="serial" value="">  
            <button id="button" type="button" data-toggle="modal" data-target="#exampleModalCenter" onClick="showModal()">
                Please Enroll Your Finger
            </button>
        </form> 
 
        <div class="modal fade bd-example-modal-lg" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <!--  <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                      <div class="modal-dialog modal-dialog-centered" role="document"> -->
                <div class="modal-content">
                    <div class="modal-header " style="background-color: #135087; color:#fff;">
                        <h5 class="modal-title" id="exampleModalLongTitle">Fingerprint Enroll Window</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="row"> 
                                        <Button id="LeftThumb" onclick="takeFingerData(1)"   class="btn mandatoryBtn" ><b>LEFT THUMB</b></Button>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <Button id="LeftIndex" onclick="takeFingerData(2)"   class="btn mandatoryBtn" ><b>LEFT INDEX</b></Button>  
                                    </div>

                                    <br>

                                    <div class="row"> 
                                        <a id="LeftMiddle" onclick="takeFingerData(3)" role="button" class="btn btn-warning popover-test" title="LEFT THUMB">LEFT MIDDLE</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a id="LeftRing" onclick="takeFingerData(4)" role="button" class="btn btn-warning popover-test" title="LEFT RING">LEFT RING</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a id="LeftLittle" onclick="takeFingerData(5)" role="button" class="btn btn-warning popover-test" title="RIGHT THUMB">LEFT LITTLE</a> 
                                    </div>

                                    <br>

                                    <div class="row"> 
                                        <Button id="RightThumb" onclick="takeFingerData(6)"  class="btn mandatoryBtn" ><b>RIGHT THUMB</b></Button> &nbsp;&nbsp;&nbsp;&nbsp;
                                        <Button id="RightIndex" onclick="takeFingerData(7)"  class="btn mandatoryBtn" ><b>RIGHT INDEX</b></Button> 
                                    </div>

                                    <br>

                                    <div class="row"> 
                                        <a id="RightMiddle" onclick="takeFingerData(8)" role="button" class="btn btn-warning popover-test" title="RIGHT THUMB">RIGHT MIDDLE</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a id="RightRing" onclick="takeFingerData(9)" role="button" class="btn btn-warning popover-test" title="RIGHT RING">RIGHT INDEX</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a id="RightLittle" onclick="takeFingerData(10)" role="button" class="btn btn-warning popover-test" title="RIGHT THUMB">RIGHT LITTLE</a> 
                                    </div>

                                    <div class="row">
                                        <div class="col-md-12">
                                            <p id="status_alert" style="visibility: hidden;"  class="success_alert"> </p>
                                        </div>  
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <img height="300" width="240" id="FPImage1" src="../../assets/images/hands/1.png" class="inputstl"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">

                        <div class ="col-md-10">
                            <div class ="row">
                                <p style="color: red">* Finger indicated by red button must be given.</p>
                            </div>
                        </div>
                        
                        <div class ="col-md-2">
                            <button type="button" class="btn btn-success" onclick="processData()" style="background-color: #135087">Enroll Finger</button>                         
                        </div>

                    </div>
                </div>
            </div>
        </div> 
    </body>
</html>
