/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.servlet;

import era.information.FingerPrintVerification;
import era.model.LoginModel;
import era.model.Parameter_Fixed_Verify;
import java.io.BufferedReader;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.Base64; 
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author root
 */
@WebServlet(name = "FingerVerification", urlPatterns = {"/FingerVerification"})
public class FingerVerification extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().println("Hello");
    }
    
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
            Logger.getLogger(FingerVerification.class.getName()).log(Level.SEVERE, null, e);
            sendBackResponse(response , "Y",e.getMessage());
            return;
        }

        String customerNumber = "18" , fingerImageData = "",fingerTemplateData = ""; 
        byte[] fingerImage = null;
        byte[] fingerTemplate = null;
        
        
        try {
            Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify(); 
            JSONParser jsonParser = new JSONParser();
            org.json.simple.JSONObject jsonObject = null;
            jsonObject = (org.json.simple.JSONObject) jsonParser.parse(jb.toString()); //HTTP.toJSONObject(jb.toString());
            
            customerNumber = (String) jsonObject.get("cust_no");
            fingerImageData = (String) jsonObject.get("fingerImage");
            fingerTemplateData = (String) jsonObject.get("fingerTemplate");

        } catch (ParseException ex) {
            //Logger.getLogger(FingerVerification.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("exception is "+ex.getMessage());
            sendBackResponse(response , "Y",ex.getMessage());
            return;
        }

        JSONObject json = new JSONObject();

        try {
            fingerImage = Base64.getDecoder().decode(fingerImageData);
            fingerTemplate = Base64.getDecoder().decode(fingerTemplateData);
        } catch (IllegalArgumentException e) {
            sendBackResponse(response , "Y",e.getMessage());
            return;
        }
        System.out.println("fingerbytes length: " + fingerImage.length);

        FingerPrintVerification dao = new FingerPrintVerification();  
        LoginModel model = dao.fingerDataVerfy(customerNumber,fingerImage,fingerTemplate);
        sendBackResponse(response , model.getErrorFlag(),model.getErrorMessage());
              
    }

    void sendBackResponse(HttpServletResponse response, String resultFlag,
            String resultMessage) {

        JSONArray jArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();

        try {
            jsonObject.put("resultCode", resultFlag);
            jsonObject.put("resultMessage", resultMessage);
            jArray.put(jsonObject);
            json.put("Result", jArray);

            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json.toString());
            response.getWriter().flush();
        } catch (JSONException ex) {
            Logger.getLogger(FingerVerification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FingerVerification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
