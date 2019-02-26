/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.servlet;

import com.era.admin.GetRestAPIInfo;
import com.era.admin.RestAPIInfo;
import com.era.json.JSONParserPost;
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
@WebServlet(name = "EnrollCheck", urlPatterns = {"/EnrollCheck"})
public class EnrollCheck extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String app_user = request.getParameter("app_user");
        String ai_logid = request.getParameter("ai_logid");
        String cust_type = request.getParameter("cust_type");
        String dName = request.getParameter("dName");

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

        String URL = "http://" + IPAdress + ":" + PORT + "/FFinger/EnrollStatus";

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
                        out.println("<a href=\"#\" style=\" color:#ffffff ; background: linear-gradient(to bottom, #F24537 5%, #C62D1F 100%) repeat scroll 0% 0% #F24537 !important;\n"
                                + "           border: 1px solid #D02718 !important;\"> Finger is already enrolled.</a>\n");
                    } else {
                        String redirectURL = "page/device/" + dName + "/enroll1.jsp?name=" + name + "&app_user=" + app_user + ""
                                + "&ai_logid=" + ai_logid + "&cust_type=" + cust_type + "&cust_type=" + cust_type
                                + "&dName=" + dName;

                        response.sendRedirect(redirectURL);
                    }
                }
            } catch (JSONException ex) {
                Logger.getLogger(EnrollCheck.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            out.println("json data is null");
        }
    }

}
