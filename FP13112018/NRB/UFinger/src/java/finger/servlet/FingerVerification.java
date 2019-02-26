/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finger.servlet;

import era.data.FingerPrintVerification;
import era.data.LoginModel;
import era.data.Parameter_Fixed_Verify;
import java.io.BufferedReader; 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EnrolledFingerVerifyWeb</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EnrolledFingerVerifyWeb at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            /* report an error */
        }

        System.out.println(jb.toString());

        String user_id = "18", pAcId = "";
        byte[] fingerbytes = null;
        String amount = "", fingerData = "", pOperationType = "", create_by = "", pDeviceId = "", pEnrolFrom = "", pSessionId = "", pCustType = "", pLogId = "";

        try {
            Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();

            JSONParser jsonParser = new JSONParser();
            org.json.simple.JSONObject jsonObject = null;
            jsonObject = (org.json.simple.JSONObject) jsonParser.parse(jb.toString()); //HTTP.toJSONObject(jb.toString());
            //  System.out.println("Simple String using jsonparser: " + jsonObject.toString());
            //temp,amount,user_id,create_by,pAcId,pOperationType,pLogId,pSessionId,pCustType,pDeviceId 

            user_id = (String) jsonObject.get("user_id");
            fingerData = (String) jsonObject.get("temp");
            pOperationType = (String) jsonObject.get("pOperationType");
            pOperationType = pOperationType.trim();
            create_by = (String) jsonObject.get("create_by");
            pDeviceId = (String) jsonObject.get("pDeviceId");
            pSessionId = (String) jsonObject.get("pSessionId");
            pCustType = (String) jsonObject.get("pCustType");
            pLogId = (String) jsonObject.get("pLogId");
            amount = (String) jsonObject.get("amount");
            pAcId = (String) jsonObject.get("pAcId");
            if (pAcId == null) {
                pAcId = "";
            }

            System.out.println("user_id is "+user_id);
            System.out.println("fingerData is "+fingerData);
            System.out.println("pOperationType is "+pOperationType);
            System.out.println("create_by is "+create_by);
            System.out.println("pDeviceId is "+pDeviceId);
            System.out.println("pEnrolFrom is "+pEnrolFrom);
            System.out.println("pSessionId is "+pSessionId);
            System.out.println("pCustType is "+pCustType);
            System.out.println("pLogId is "+pLogId);
            System.out.println("amount is "+amount);
            System.out.println("pAcId is "+pAcId);
        } catch (ParseException ex) {
            Logger.getLogger(FingerVerification.class.getName()).log(Level.SEVERE, null, ex);
        }
        fingerbytes = Base64.getDecoder().decode(fingerData);
        System.out.println("fingerbytes length: " + fingerbytes.length);
        
        FingerPrintVerification dao = new FingerPrintVerification();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONObject json = new JSONObject();

        LoginModel loginModel = new LoginModel();
        loginModel.setUser_id(user_id);
        loginModel.setFingerBytes(fingerbytes);
        loginModel.setpOperationType(pOperationType);
        loginModel.setCreate_by(create_by);
        loginModel.setpDeviceId(pDeviceId);
        loginModel.setpEnrolFrom(pEnrolFrom);
        loginModel.setpSessionId(pSessionId);
        loginModel.setpCustType(pCustType);
        loginModel.setpLogId(pLogId);
        loginModel.setpAmount(amount);
        if(pAcId == null)
            pAcId="-1";
        loginModel.setpAcId(pAcId);

        try {
            for (LoginModel model : dao.fingerVerfyFromTemplate(loginModel)) {

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("outCode", model.getErrorFlag());
                map.put("outMessage", model.getErrorMessage());
                list.add(map);
                json.put("verifyNodes", list);

            }
        } catch (JSONException ex) {
            Logger.getLogger(FingerVerification.class.getName()).log(Level.SEVERE, null, ex);

        }

        System.out.println("verifyNodes = " + list);
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());
        response.getWriter().flush();
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
