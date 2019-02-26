/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.servlet;
  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties; 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sultan
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        String uNameFromDB = "", pwdFromDB = "", fName = "", lName = "", username, password;
        Properties prop = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("admin.properties");
            prop.load(inputStream);
            uNameFromDB = prop.getProperty("username");
            pwdFromDB = prop.getProperty("password");
            fName = prop.getProperty("fName");
            lName = prop.getProperty("lName"); 
            inputStream.close();
        } catch (FileNotFoundException e) {
            uNameFromDB = "sultan@gmail.com";
            pwdFromDB = "era@123";
            fName = "Sultan";
            lName = "Ahmed";
        }

        username = request.getParameter("username");
        password = request.getParameter("password");
        String redirectURL = "admin/";

        if (username.equals(uNameFromDB) && password.equals(pwdFromDB)) {
            HttpSession session = request.getSession();
            session.setAttribute("LOGIN", "TRUE");
            session.setAttribute("fName", "Sultan");
            session.setAttribute("lName", "Ahmed");
            session.setAttribute("uName", username);
            response.sendRedirect(redirectURL + "home.jsp");
        } else {
            response.sendRedirect(redirectURL + "index.jsp?eMessage=username or password does not exist");
        }

        /*  DbConnectivity dbConnectivity = new DbConnectivity();
        String dbMessage = dbConnectivity.processingAllTask();
         

        if (dbMessage == null) {
            boolean loginStatus = dbConnectivity.checkLogin(username, password);
            AdminDetails adminDetails = dbConnectivity.getAdminDetails(username, password);

            if (loginStatus) {
                HttpSession session = request.getSession(); 
                session.setAttribute("LOGIN", "TRUE");
                session.setAttribute("fName", adminDetails.getFirstName());
                session.setAttribute("lName", adminDetails.getLastName());
                session.setAttribute("uName", username);
                response.sendRedirect(redirectURL + "home.jsp");
            }
            else{
                response.sendRedirect(redirectURL + "index.jsp?eMessage=username or password does not exists"); 
            }
        } else { 
            response.sendRedirect(redirectURL + "index.jsp?eMessage="+dbMessage); 
        } */
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
