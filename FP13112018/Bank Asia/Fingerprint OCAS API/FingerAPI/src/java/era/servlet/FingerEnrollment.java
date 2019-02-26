/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.servlet;

import era.information.EnrollInformation;
import era.model.LoginModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author root
 */
@WebServlet(name = "FingerEnrollment", urlPatterns = {"/FingerEnrollment"})
public class FingerEnrollment extends HttpServlet { 
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            /*report an error*/
        }
        //   System.out.println("Simple String: "+jb.toString());

        String customerNumber = "";
        long serial_no = 0;
        byte[] fingerbytes = null;
        String fingerData = null;
        String app_user = "",pOperationType="",pAcId="",pDeviceId="",pEnrolFrom="",pSessionId="",pCustType="",pLogId="";

        try {
            JSONParser jsonParser = new JSONParser();
            org.json.simple.JSONObject jsonObject = null;
            jsonObject = (org.json.simple.JSONObject) jsonParser.parse(jb.toString()); //HTTP.toJSONObject(jb.toString());
             System.out.println("Simple String using jsonparser: " + jsonObject.toString());
              
            customerNumber = (String) jsonObject.get("cust_no");//getString("cust_no");
            serial_no = (long)jsonObject.get("serial");
            fingerData = (String)jsonObject.get("fingerData");
            app_user = (String)jsonObject.get("app_user");
            pOperationType = (String)jsonObject.get("pOperationType");
            pAcId = (String)jsonObject.get("pAcId");
            pDeviceId = (String)jsonObject.get("pDeviceId");
            pEnrolFrom = (String)jsonObject.get("pEnrolFrom");
            pSessionId = (String)jsonObject.get("pSessionId");
            pCustType = (String)jsonObject.get("pCustType");
            pLogId = (String)jsonObject.get("pLogId");
            
        }  catch (ParseException ex) {
            Logger.getLogger(FingerEnrollment.class.getName()).log(Level.SEVERE, null, ex);
        }
        fingerbytes = java.util.Base64.getDecoder().decode(fingerData);
         LoginModel loginModel = new LoginModel();
        //customerNumber,serial_no,temp,app_user,pOperationType,pAcId,pDeviceId,pEnrolFrom,
        //pSessionId,pCustType,pLogId
        
        loginModel.setCust_no(customerNumber);
        loginModel.setSerial(serial_no);
        loginModel.setFingerBytes(fingerbytes);
        loginModel.setApp_user(app_user);
        loginModel.setpOperationType(pOperationType);
        loginModel.setpAcId(pAcId);
        loginModel.setpDeviceId(pDeviceId);
        loginModel.setpEnrolFrom(pEnrolFrom);
        loginModel.setpSessionId(pSessionId);
        loginModel.setpCustType(pCustType);
        loginModel.setpLogId(pLogId);
        loginModel.setpAmount("0");
        
        System.out.println("cust_no is " + loginModel.getCust_no());
        System.out.println("byte data is " + loginModel.getFingerBytes());

        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            JSONObject json = new JSONObject();
            EnrollInformation dao = new EnrollInformation();
            for (LoginModel model : dao.windowEnroll(loginModel)) {
                
                Map<String, Object> map = new HashMap<String, Object>();
                
                map.put("finger1", model.getFinger1());
                map.put("finger2", model.getFinger2());
                map.put("errorFlag", model.getErrorFlag());
                map.put("errorMessage", model.getErrorMessage());

                list.add(map);
                json.put("enrollNodes", list);

            }
            System.out.println("EnrollNodes = " + list);
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json.toString());
            response.getWriter().flush();
            // System.out.println("Response Completed... ");
        } catch (Exception ex) {
            Logger.getLogger(FingerEnrollment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
