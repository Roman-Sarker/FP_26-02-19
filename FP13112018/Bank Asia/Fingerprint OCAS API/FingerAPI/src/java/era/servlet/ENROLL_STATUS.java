/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.servlet;

import era.information.EnrollStatusInformation;
import era.model.ENROLL_STATUS_DATA;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Sultan Ahmed
 */
@WebServlet(name = "ENROLL_STATUS", urlPatterns = {"/ENROLL_STATUS"})
public class ENROLL_STATUS extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String custNo = request.getParameter("cust_no");
        ENROLL_STATUS_DATA loginstatus = new ENROLL_STATUS_DATA();

        EnrollStatusInformation enrollStatusInfo = new EnrollStatusInformation();
        ENROLL_STATUS_DATA statusData = enrollStatusInfo.getEnrollStatus(custNo); 
        
        sendBackResponse(response,statusData.getFinger1(),
                statusData.getFinger1(), statusData.getCustNo(),
                statusData.getEnrollStatus(),statusData.getStandard(),
                statusData.getErrorFlag(),statusData.getErrorMessage());
    }

    void sendBackResponse(HttpServletResponse response,String finger1,String finger2 ,
            String custNo, String status,
            String standard, String resultFlag, String resultMessage) {

        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();

        try { 
            jSONObject.put("custNo", custNo);
            jSONObject.put("enrollStatus", status);
            jSONObject.put("standard", standard);
            jSONObject.put("errorFlag", resultFlag);
            jSONObject.put("errorMessage", resultMessage);
            jSONObject.put("finger1", finger1);
            jSONObject.put("finger2", finger2);
            jsonArray.put(jSONObject);
            json.put("enrollStatus", jsonArray);

            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json.toString());
            response.getWriter().flush();
        } catch (JSONException ex) {
            Logger.getLogger(FingerVerification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FingerVerification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
