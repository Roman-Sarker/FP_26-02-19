/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.servlet;

import com.era.sqlitedb.DBConnInfo; 
import com.era.sqlitedb.UpdateIpAddress;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sultan
 */
@WebServlet(name = "Database_Info_Insert", urlPatterns = {"/Database_Info_Insert"})
public class Database_Info_Insert extends HttpServlet {

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
            out.println("<title>Servlet Database_Info_Insert</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Database_Info_Insert at " + request.getContextPath() + "</h1>");
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
        
        String ip = request.getParameter("ip");
        String portNo = request.getParameter("portNo");
        String serviceName = request.getParameter("serviceName");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password"); 
        
        Connection conn = DBConnInfo.getConnection();
        String message = UpdateIpAddress.updateIPAddress(conn, ip, portNo, serviceName, userName, password);
        DBConnInfo.releaseConnection(conn);
        String redirectURL = "admin/";
          
        if(message != null){
            response.sendRedirect(redirectURL + "dbInfo.jsp?eMessage="+message); 
        } else { 
            response.sendRedirect(redirectURL + "dbInfo.jsp?sMessage=Database connectivity information is successfully updated!"); 
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
