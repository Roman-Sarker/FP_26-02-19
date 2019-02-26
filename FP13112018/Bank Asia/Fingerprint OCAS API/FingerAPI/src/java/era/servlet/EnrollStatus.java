/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.servlet;

import era.information.EnrollStatusInformation;
import era.model.ENROLL_STATUS_DATA;
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

/**
 *
 * @author root
 */
@WebServlet(name = "EnrollStatus", urlPatterns = {"/EnrollStatus"})
public class EnrollStatus extends HttpServlet {

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
            out.println("<title>Servlet ENROLL_STATUS</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ENROLL_STATUS at " + request.getContextPath() + "</h1>");
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
        String custNo = request.getParameter("cust_no");
        System.out.println("custNo is " + custNo);
        ENROLL_STATUS_DATA loginstatus = new ENROLL_STATUS_DATA();
        loginstatus.setCustNo(custNo);
        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            JSONObject json = new JSONObject();
            EnrollStatusInformation dao = new EnrollStatusInformation();
            ENROLL_STATUS_DATA statusData = dao.getEnrollStatus(custNo);
            Map<String, Object> map = new HashMap<String, Object>();
            System.out.println("Cust No is " + statusData.getCustNo());

            map.put("custNo", statusData.getCustNo());
            map.put("finger1", statusData.getFinger1());
            map.put("finger2", statusData.getFinger2());
            map.put("errorFlag", statusData.getErrorFlag());
            map.put("errorMessage", statusData.getErrorMessage());
            list.add(map);
            json.put("enrollStatus", list);

            System.out.println("EnrollNodes = " + list);
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json.toString());
            response.getWriter().flush();
            // System.out.println("Response Completed... ");
        } catch (Exception ex) {
            Logger.getLogger(ENROLL_STATUS.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        String custNo = request.getParameter("cust_no");
        ENROLL_STATUS_DATA loginstatus = new ENROLL_STATUS_DATA();
        loginstatus.setCustNo(custNo);
        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            JSONObject json = new JSONObject();
            EnrollStatusInformation dao = new EnrollStatusInformation();

            ENROLL_STATUS_DATA statusData = dao.getEnrollStatus(custNo);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("custNo", statusData.getCustNo());
            map.put("finger1", statusData.getFinger1());
            map.put("finger2", statusData.getFinger2());
            map.put("errorFlag", statusData.getErrorFlag());
            map.put("errorMessage", statusData.getErrorMessage());
            list.add(map);
            json.put("enrollStatus", list);

            System.out.println("EnrollNodes = " + list);
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json.toString());
            response.getWriter().flush();
            // System.out.println("Response Completed... ");
        } catch (Exception ex) {
            Logger.getLogger(ENROLL_STATUS.class.getName()).log(Level.SEVERE, null, ex);
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
