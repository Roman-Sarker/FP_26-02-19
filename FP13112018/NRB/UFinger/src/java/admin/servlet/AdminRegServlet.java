/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.servlet;

import com.era.admin.AdminRegistration;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sultan
 */
@WebServlet(name = "AdminRegServlet", urlPatterns = {"/AdminRegServlet"})
public class AdminRegServlet extends HttpServlet {

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
            out.println("<title>Servlet AdminRegServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminRegServlet at " + request.getContextPath() + "</h1>");
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

        String fName = request.getParameter("firstname");
        String lName = request.getParameter("lastname");
        String userName = request.getParameter("uName");
        String password = request.getParameter("passwd");
        String confirmPassword = request.getParameter("con_passwd");

        if (fName == null || fName.equals("")) {
            response.sendRedirect("admin/newAdmin.jsp?eMessage=" + "First Name can not be blank!");
        }else if (lName == null || lName.equals("") ) {
            response.sendRedirect("admin/newAdmin.jsp?eMessage=" + "Last Name can not be blank!");
        } else if (userName == null || userName.equals("") ) {
            response.sendRedirect("admin/newAdmin.jsp?eMessage=" + "Email can not be blank!");
        } else if (password == null || password.equals("") ) {
            response.sendRedirect("admin/newAdmin.jsp?eMessage=" + "password can not be blank!");
        } else if (confirmPassword == null || confirmPassword.equals("") ) {
            response.sendRedirect("admin/newAdmin.jsp?eMessage=" + "confirm password can not be blank!");
        } else if (!password.equals(confirmPassword)) {
            response.sendRedirect("admin/newAdmin.jsp?eMessage=password does not match");
        } else {
            
            AdminRegistration adminRegistration = new AdminRegistration();
            String rootUser = (String) request.getSession(true).getAttribute("uName");
            adminRegistration.setRootUser(rootUser);
            adminRegistration.setInformation(fName, lName, userName, password);
            String errorMessage = adminRegistration.createAdmin();
            if (errorMessage != null) {
                response.sendRedirect("admin/newAdmin.jsp?eMessage=" + errorMessage);
            } else {
                response.sendRedirect("admin/newAdmin.jsp?sMessage=Successfully new admin created");
            }
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
