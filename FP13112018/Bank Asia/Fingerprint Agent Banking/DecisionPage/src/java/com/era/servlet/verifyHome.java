/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.servlet;
 
import com.era.admin.GetRestAPIInfo;
import com.era.admin.RestAPIInfo;
import com.era.json.JSONParserPost;
import com.era.param.Parameter_Fixed_Verify;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author root
 */
@WebServlet(name = "verifyHome", urlPatterns = {"/verifyHome"})
public class verifyHome extends HttpServlet {
     
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  

        String ailogid = request.getParameter("ailogid");
        String sessid = request.getParameter("sessid");
        String name = request.getParameter("name");
        String amount = request.getParameter("amount");
        String custno = request.getParameter("custno");
        String appuser = request.getParameter("appuser");
        String opr_type = request.getParameter("opr_type");
        String cust_type = request.getParameter("cust_type");

        PrintWriter out = response.getWriter();
        Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();
        name = param_fixed_verify.getParameter(out, name, false);
        ailogid = param_fixed_verify.getParameter(out, ailogid, false);
        sessid = param_fixed_verify.getParameter(out, sessid, false);
        custno = param_fixed_verify.getParameter(out, custno, false);
        appuser = param_fixed_verify.getParameter(out, appuser, false);
        opr_type = param_fixed_verify.getParameter(out, opr_type, false);
        cust_type = param_fixed_verify.getParameter(out, cust_type, false);
        if (amount != null) {
            amount = param_fixed_verify.getParameter(out, amount, false);
        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));

         RestAPIInfo restAPIInfo = GetRestAPIInfo.getRestAPIInfo();
        if (restAPIInfo == null) {
            out.println("web service parameter error");
            return;
        }
        
        String IPAdress = restAPIInfo.ip;
        String PORT = restAPIInfo.portNo;

        if (IPAdress == null || PORT == null) {
            out.println("web service parameter error");
            return;
        }
        
        String URL = "http://" + IPAdress + ":" + PORT + "/BFinger/EnrollStatus";
        // out.println("URL is " + URL);

        JSONParserPost jsonParserpost = new JSONParserPost();
        JSONObject output = jsonParserpost.makeHttpRequest(URL, "POST", params);
        //  out.println("result: " + output.toString());

        if (output != null) {
            try {
                JSONArray jsonArray = output.getJSONArray("enrollStatus");
                // for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String enrollStatus = jsonObject.getString("enrollStatus");
                enrollStatus = enrollStatus.trim();
                String errorFlag = jsonObject.getString("errorFlag");
                String errorMessage = jsonObject.getString("errorMessage");

                if (errorFlag.equals("N")) {
                    if (enrollStatus.equals("E")) {
                        String standard = jsonObject.getString("standard");

                        if (standard.equals("O")) {
                             String redirectURL = "page/device/FO/verify1.jsp?name=" + name + "&standard=" + standard + "&ailogid=" + ailogid + "&appuser=" + appuser + "&amount=" + amount + "&custno=" + custno + "&sessid=" + sessid + "&opr_type= " + opr_type + "&cust_type=" + cust_type;
                             response.sendRedirect(redirectURL);
                        }else{
                            String redirectURL = "page/verify.jsp?name=" + name + "&standard=" + standard + "&ailogid=" + ailogid + "&appuser=" + appuser + "&amount=" + amount + "&custno=" + custno + "&sessid=" + sessid + "&opr_type= " + opr_type + "&cust_type=" + cust_type;
                            response.sendRedirect(redirectURL);
                        }
                    } else {
                        out.println("customer is not enrolled");
                    }
                } else {
                    out.println("Error in fetching data from database");
                    out.println("main error is " + errorMessage);
                }
            } catch (JSONException ex) {
                out.println("An Error is occurred  ! Error is " + ex.getMessage());
                Logger.getLogger(verifyHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else { 
            out.println("Sorry! You are not enrolled in our agent banking system.");
        }
    }

}
