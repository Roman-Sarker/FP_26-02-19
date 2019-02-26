/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finger.servlet;

import enroll.verify.Enroll; 
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
import era.data.EnrollInformation;
import org.json.JSONException;

/**
 *
 * @author root
 */
@WebServlet(name = "FingerEnrollment", urlPatterns = {"/FingerEnrollment"})
public class FingerEnrollment extends HttpServlet {

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
            out.println("<title>Servlet FingerEnrollWeb</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FingerEnrollWeb at " + request.getContextPath() + "</h1>");
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
            /*report an error*/
        }

        String user_id = "", create_by = "", ai_logid = "",
                user_type = "",br_code="", serial = "";

        String lindex = "", llittle = "", lthumb = "", lmiddle = "", lring = "",
                rindex = "", rring = "", rlittle = "", rthumb = "", rmiddle = "";

        System.out.println(jb.toString());

        try {
            JSONParser jsonParser = new JSONParser();
            org.json.simple.JSONObject jsonObject = null;
            jsonObject = (org.json.simple.JSONObject) jsonParser.parse(jb.toString()); //HTTP.toJSONObject(jb.toString());
            System.out.println("Simple String using jsonparser: " + jsonObject.toString());

            user_id = (String) jsonObject.get("user_id");//getString("cust_no");
            create_by = (String) jsonObject.get("create_by");
            ai_logid = (String) jsonObject.get("ai_logid");
            user_type = (String) jsonObject.get("user_type");
            br_code = (String) jsonObject.get("br_code");
            serial = (String) jsonObject.get("serial");
            serial = serial.trim();
            
            System.out.println("user_id "+user_id);
            System.out.println("create_by is "+create_by);
            System.out.println("ai_logid is "+ai_logid);
            System.out.println("user_type is "+user_type);
            System.out.println("br_code is "+br_code);
            System.out.println("serial is "+serial);

            rindex = (String) jsonObject.get("rindex");
            rthumb = (String) jsonObject.get("rthumb");
            rmiddle = (String) jsonObject.get("rmiddle");
            rring = (String) jsonObject.get("rring");
            rlittle = (String) jsonObject.get("rlittle");

            lthumb = (String) jsonObject.get("lthumb");
            lindex = (String) jsonObject.get("lindex");
            lmiddle = (String) jsonObject.get("lmiddle");
            lring = (String) jsonObject.get("lring");
            llittle = (String) jsonObject.get("llittle");
             
            
            System.out.println("rindex length "+rindex.length());
            System.out.println("rthumb length "+rthumb.length());
            System.out.println("lthumb length "+lthumb.length());
            System.out.println("lindex length "+lindex.length()); 
            
        } catch (ParseException ex) {
            Logger.getLogger(FingerEnrollment.class.getName()).log(Level.SEVERE, null, ex);
        }

         
        byte[] lIndex, lLittle, lThumb, lMiddle, lRing, rIndex, rRing, rLittle, rThumb, rMiddle;

        lThumb = java.util.Base64.getDecoder().decode(lthumb);  
        lIndex = java.util.Base64.getDecoder().decode(lindex); 
        lMiddle = java.util.Base64.getDecoder().decode(lmiddle); 
        lRing = java.util.Base64.getDecoder().decode(lring); 
        lLittle = java.util.Base64.getDecoder().decode(llittle);
         

        rIndex = java.util.Base64.getDecoder().decode(rindex); 
        rThumb = java.util.Base64.getDecoder().decode(rthumb); 
        rMiddle = java.util.Base64.getDecoder().decode(rmiddle); 
        rRing = java.util.Base64.getDecoder().decode(rring); 
        rLittle = java.util.Base64.getDecoder().decode(rlittle); 

        EnrollInformation enrollInformation = new EnrollInformation();
        enrollInformation.setUser_id(user_id);
        enrollInformation.setAi_logid(ai_logid);
        enrollInformation.setCreate_by(create_by);

        enrollInformation.setSerial(serial);
        enrollInformation.setUser_type(user_type);
        enrollInformation.setBr_code(br_code);
        
        enrollInformation.setlIndex(lIndex);
        enrollInformation.setlThumb(lThumb);
        enrollInformation.setlMiddle(lMiddle);
        enrollInformation.setlRing(lRing);
        enrollInformation.setlLittle(lLittle);
         
        enrollInformation.setrIndex(rIndex);
        enrollInformation.setrThumb(rThumb);
        enrollInformation.setrMiddle(rMiddle);
        enrollInformation.setrRing(rRing);
        enrollInformation.setrLittle(rLittle);
         
        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            JSONObject json = new JSONObject();

            Enroll enroll = new Enroll();
            enrollInformation = enroll.enrollFinger(enrollInformation);
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("errorFlag", enrollInformation.getErrorFlag());
            map.put("errorMessage", enrollInformation.getErrorMessage());

            list.add(map);
            json.put("enrollNodes", list);

            System.out.println("EnrollNodes = " + list);
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json.toString());
            System.out.println(json.toString());
            response.getWriter().flush();
            // System.out.println("Response Completed... ");
        } catch (IOException | JSONException ex) {
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
