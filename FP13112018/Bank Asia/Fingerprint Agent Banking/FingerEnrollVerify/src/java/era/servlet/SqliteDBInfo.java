/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.servlet;

import com.era.sqliteinfo.AdminInfo;
import com.era.sqliteinfo.DBConnectionInfo;
import com.era.sqliteinfo.SqliteInfoShow;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
@WebServlet(name = "SqliteDBInfo", urlPatterns = {"/SqliteDBInfo"})
public class SqliteDBInfo extends HttpServlet {

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
            out.println("<title>Servlet SqliteDBInfo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SqliteDBInfo at " + request.getContextPath() + "</h1>");
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
        PrintWriter writer = response.getWriter();
        List<DBConnectionInfo> dbList = SqliteInfoShow.getDBInfo();
        for(DBConnectionInfo dbInfo:dbList){
            writer.println("*************************************************************");
            writer.println("id is "+dbInfo.getId());
            writer.println("ip_address_of_DBConnection is "+dbInfo.getIp_address_of_DBConnection());
            writer.println("PORT is "+dbInfo.getPORT());
            writer.println("username is "+dbInfo.getUsername());
            writer.println("password is "+dbInfo.getPassword());
            writer.println("**************************************************************");  
        } 
        
        writer.println("\n\n\n##################################################################"); 
        writer.println("##################################################################\n\n");
        
        List<AdminInfo>adminList= SqliteInfoShow.getAdminInfo();
        for(AdminInfo adminInfo:adminList){
           writer.println("*************************************************************");
            writer.println("id is "+adminInfo.getId());
             writer.println("username is "+adminInfo.getUsername());
            writer.println("password is "+adminInfo.getPassword());
           writer.println("**************************************************************");  
             
        }
        
        writer.flush();
        //print("Sultan ! You have successfully printed hello world."); 
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
        response.getWriter().print("Sultan ! What is happened ?");
        processRequest(request, response);
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
