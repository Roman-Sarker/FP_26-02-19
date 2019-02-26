<%-- 
    Document   : sysinfo2
    Created on : Sep 21, 2017, 2:57:44 PM
    Author     : Sultan Ahmed
--%>
<%@page import="com.era.IPAddress.UpdateIPAdress"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.era.json.JSONParserPost"%>
<%@page import="org.apache.http.message.BasicNameValuePair"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.http.NameValuePair"%> 
<HTML>
    <HEAD>
        <META http-equiv=Content-Language content=en-us>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">
        <meta http-equiv="x-ua-compatible" content="IE=10" >
    </HEAD>
    <BODY>
    <center>
        <table border="0" width="800">
            <%
                String sl = request.getParameter("SlNo");
                String ip = request.getParameter("IP");
                String UUID = request.getParameter("UUID");
                String MAC = request.getParameter("MAC");
                String PcName = request.getParameter("PcName");
                String OsUser = request.getParameter("OsUser");
                String DNS = request.getParameter("DNS");
                String PcInfo = request.getParameter("PcInfo");
                String sysdte = request.getParameter("sysdte");
                String sessid = request.getParameter("sessid");
                String username = request.getParameter("username");
                
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username)); 
                params.add(new BasicNameValuePair("SlNo", sl)); 
                params.add(new BasicNameValuePair("IP", ip)); 
                params.add(new BasicNameValuePair("UUID", UUID)); 
                params.add(new BasicNameValuePair("MAC", MAC)); 
                params.add(new BasicNameValuePair("PcName", PcName)); 
                params.add(new BasicNameValuePair("OsUser", OsUser)); 
                params.add(new BasicNameValuePair("DNS", DNS)); 
                params.add(new BasicNameValuePair("PcInfo", PcInfo)); 
                params.add(new BasicNameValuePair("sysdte", sysdte)); 
                params.add(new BasicNameValuePair("sessid", sessid)); 
                
                UpdateIPAdress updateIPAdress = new UpdateIPAdress();            
                String IPAdress = updateIPAdress.getIPAddressFromDb() ;

                if(updateIPAdress.getErrorFlag()){
                    out.println("Error in famVerifyHome.jsp : "+IPAdress);
                    return;
                }

                String PORT = updateIPAdress.getPORT() ;
                String URL="http://"+IPAdress+":"+PORT+"/FingerEnrollVerify/Information_About_User";

                
                JSONParserPost jsonParserpost = new JSONParserPost();
                JSONObject output = jsonParserpost.makeHttpRequest(URL, "POST", params);
                if (output != null) {
                    JSONArray jsonArray = output.getJSONArray("informationNodes");

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String errorFlag = jsonObject.getString("outCode");
                    String errorMessage = jsonObject.getString("outMessage");
                    if (errorFlag.equals("N")) {
                        out.println("Information of users is successfully inserted into database.");
                    } else {
                        out.println("Sorry,Information of users in inserting into database is failed.");
                        out.println("Error Message is "+errorMessage);
                    }
                } else {
                    out.println("web service is not running or internet connectivity problem.");
                }
            %>
        </table>
    </center>
</BODY>
</HTML>
