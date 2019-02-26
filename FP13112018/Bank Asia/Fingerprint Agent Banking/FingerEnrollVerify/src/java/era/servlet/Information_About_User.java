/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.servlet;
 
import era.dbconnectivity.DBConnectionHandler;  
import era.model.InformationModel;
import era.model.Parameter_Fixed_Verify;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Sultan Ahmed
 */
@WebServlet(name = "Information_About_User", urlPatterns = {"/Information_About_User"})
public class Information_About_User extends HttpServlet {

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
            out.println("<title>Servlet Information_About_User</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Information_About_User at " + request.getContextPath() + "</h1>");
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

        String sl = request.getParameter("SlNo");
        String ip = request.getParameter("IP");
        String UUID = request.getParameter("UUID");
        String MAC = request.getParameter("MAC");
        String PcName = request.getParameter("PcName");
        String OsUser = request.getParameter("OsUser");
        String DNS = request.getParameter("DNS");
        String PcInfo = request.getParameter("PcInfo");
        String sysdte = request.getParameter("sysdte");
        String sessid = request.getParameter("sessid");
        String username = request.getParameter("username");
        
        InformationModel informationModel = new InformationModel();
        informationModel.setSl(sl);
        informationModel.setIp(ip);
        informationModel.setUUID(UUID);
        informationModel.setMAC(MAC);
        informationModel.setPcName(PcName);
        informationModel.setOsUser(OsUser);
        informationModel.setDNS(DNS);
        informationModel.setPcInfo(PcInfo);
        informationModel.setSysdte(sysdte);
        informationModel.setSessid(sessid);
        informationModel.setUsername(username);
        
        System.out.println("sl is " + sl);
        System.out.println("ip is " + ip);
        System.out.println("UUID is " + UUID);
        System.out.println("MAC is " + MAC);
        System.out.println("PcName is " + PcName);
        System.out.println("OsUser is " + OsUser);
        System.out.println("DNS is " + DNS);
        System.out.println("PcInfo is " + PcInfo);
        System.out.println("sysdte is " + sysdte);
        System.out.println("sessid is " + sessid);
        System.out.println("username is " + username);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONObject json = new JSONObject();
        procedure_call_for_information_insert(informationModel);
        
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("outCode", informationModel.getErrorFlag());
            map.put("outMessage", informationModel.getErrorMessage());
            list.add(map);
            json.put("informationNodes", list);
        } catch (JSONException ex) {
            Logger.getLogger(Information_About_User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("informationNodes = " + list);
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());
        response.getWriter().flush();
     }   
    
    private void procedure_call_for_information_insert(InformationModel informationModel){
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        if (con == null) { 
            informationModel.setErrorFlag("Y");
            informationModel.setErrorMessage(dbConnectionHandler.getErrorMessge()); 
           
        } else {
            try {
                Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();
             /*   String opr_type = loginModel.getpOperationType();
                //    opr_type = param_fixed_verify.getParameter(opr_type, true);
                System.out.println("OPR_TYPE is " + opr_type);

                String cust_type = loginModel.getpCustType();
                cust_type = param_fixed_verify.getParameter(cust_type, true);
                System.out.println("cust_type is " + cust_type);

                String appUser = loginModel.getApp_user();
                appUser = param_fixed_verify.getParameter(appUser, false);
                System.out.println("appUser is " + appUser);

                String pLogiD = loginModel.getpLogId();
                //  pLogiD = param_fixed_verify.getParameter(pLogiD, true);
                System.out.println("pLogiD is " + pLogiD);

                String enrollFrom = loginModel.getpEnrolFrom();
                //   enrollFrom = param_fixed_verify.getParameter(enrollFrom, true);
                System.out.println("enrollFrom is " + enrollFrom); */

                CallableStatement stmt = con.prepareCall("{CALL BIOTPL.dfn_Information_About_User(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
 
                stmt.setString(1, informationModel.getUsername());
                stmt.setString(2, informationModel.getSessid());
                stmt.setString(3, informationModel.getPcName());
                stmt.setString(4, informationModel.getIp());
                stmt.setString(5, informationModel.getMAC());
                stmt.setString(6, informationModel.getSl());
                stmt.setString(7, informationModel.getUUID());
                stmt.setString(8, informationModel.getOsUser());
                stmt.setString(9, informationModel.getDNS());
                stmt.setString(10, informationModel.getSysdte()); 
                stmt.setString(11, informationModel.getPcInfo()); 
                stmt.registerOutParameter(12, java.sql.Types.VARCHAR);
                stmt.registerOutParameter(13, java.sql.Types.VARCHAR);
                stmt.registerOutParameter(14, java.sql.Types.VARCHAR); 
                stmt.execute();
 
                System.out.println("ex error Flag = " + stmt.getString(12));
                String errorFlag = stmt.getString(12);
                errorFlag = errorFlag.trim();
                informationModel.setErrorFlag(errorFlag);
                System.out.println("Flag" + errorFlag);
                if (errorFlag.equals("N")) {
                    informationModel.setErrorMessage("No Error Message");
                    System.out.println(" Error message is " + "No Error Message");
                } else {
                    String simErrorMessage = stmt.getString(13);
                    String oracleErrorMessage = stmt.getString(14);

                    System.out.println(" Error message is " + simErrorMessage);
                    if (simErrorMessage != null) {
                        informationModel.setErrorMessage(simErrorMessage);
                    } else {
                        informationModel.setErrorMessage(oracleErrorMessage);
                    }
                    System.out.println("ex error Message = " + stmt.getString(12));
                } 
                return;
            } catch (SQLException ex) {
                Logger.getLogger(Information_About_User.class.getName()).log(Level.SEVERE, null, ex);
                informationModel.setErrorFlag("Y");
                informationModel.setErrorMessage(ex.toString());
            } finally {
                DBConnectionHandler.releaseConnection(con);
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
