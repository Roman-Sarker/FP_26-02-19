<%-- 
    Document   : b
    Created on : Mar 13, 2018, 6:36:02 PM
    Author     : sultan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="assets/js/jquery-3.2.1.min.js"></script>
    </head>
    <body>
        <input id="portNo" placeholder="port" onkeypress="return isNumberKey(event, this)" type="text" name="portNo" required>
    </body>

    <script type="text/javascript">
        function isNumberKey(evt, element)
        {
            var value = $(element).val(); 
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            var flag = true;
            if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
                flag = false; 
            else{
                if(value){
                    var index = value.indexOf(".");
                    var diff =  value.length-index ;
                    
                    if(index != -1 && diff>2) 
                        flag = false; 
                } 
            } 
            return flag;
        } 
    </script> 
</html>
