/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finger.servlet;
  
import era.data.FingerPrintVerification;
import era.data.LoginModel;
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

        String name = request.getParameter("name");
        System.out.println("Customer No is " + name); 
        
        String pOperationType = "DELETE";
        System.out.println("pOperationType is " + pOperationType);   
         
        
        String app_user = (String) request.getParameter("app_user");
        System.out.println("app_user is " + app_user);
        
        LoginModel loginModel = new LoginModel();
        loginModel.setName(name);
        loginModel.setCustno(name);
        loginModel.setAppuser(app_user);
        loginModel.setpOperationType(pOperationType);
        
        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            JSONObject json = new JSONObject();
            
            FingerPrintVerification dao = new FingerPrintVerification();
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
