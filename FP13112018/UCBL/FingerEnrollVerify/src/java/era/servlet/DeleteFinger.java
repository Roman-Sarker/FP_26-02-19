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
import org.json.simple.JSONObject;

/**
 *
 * @author root
 */
@WebServlet(name = "DeleteFinger", urlPatterns = {"/DeleteFinger"})
public class DeleteFinger extends HttpServlet {

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
            out.println("<title>Servlet DeleteFinger</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteFinger at " + request.getContextPath() + "</h1>");
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

        String custNo = request.getParameter("custno");
        System.out.println("Customer No is " + custNo);

        String serial_no = request.getParameter("serial"); 
        System.out.println("serial is " + serial_no);
 
        String app_user = request.getParameter("app_user");
        System.out.println("app_user is " + app_user);
        
        String pOperationType = request.getParameter("pOperationType");
        System.out.println("pOperationType is " + pOperationType);
        
        String pAcId = request.getParameter("pAcId");
        System.out.println("pAcId is " + pAcId);
        
        String pDeviceId =   request.getParameter("pDeviceId");
        System.out.println("pDeviceId is " + pDeviceId);
        
        String pEnrolFrom = (String) request.getParameter("pEnrolFrom");
        System.out.println("pEnrolFrom is " + pEnrolFrom);
        
        String pSessionId = (String) request.getParameter("pSessionId");
        System.out.println("pSessionId is " + pSessionId);
        
        String pCustType = (String) request.getParameter("pCustType");
        System.out.println("pCustType is " + pCustType);
        
        String pLogId = (String) request.getParameter("pLogId");
        System.out.println("pLogId is " + pLogId);
        
        LoginModel loginModel = new LoginModel(); 
        
        loginModel.setCust_no(custNo);
        loginModel.setSerial(Long.parseLong(serial_no)); 
        loginModel.setApp_user(app_user);
        loginModel.setpOperationType(pOperationType);
        loginModel.setpAcId(pAcId);
        loginModel.setpDeviceId(pDeviceId);
        loginModel.setpEnrolFrom(pEnrolFrom);
        loginModel.setpSessionId(pSessionId);
        loginModel.setpCustType(pCustType);
        loginModel.setpLogId(pLogId);
        loginModel.setpAmount("0");
        
        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            JSONObject json = new JSONObject();
            EnrollInformation dao = new EnrollInformation();
            for (LoginModel model : dao.deleteFinger(loginModel)) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorFlag", model.getErrorFlag());
                map.put("errorMessage", model.getErrorMessage());

                list.add(map);
                json.put("DeleteFinger", list);

            }
            System.out.println("DeleteFinger = " + list);
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json.toString());
            response.getWriter().flush();
            // System.out.println("Response Completed... ");
        } catch (Exception ex) {
            Logger.getLogger(DeleteFinger.class.getName()).log(Level.SEVERE, null, ex);
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
