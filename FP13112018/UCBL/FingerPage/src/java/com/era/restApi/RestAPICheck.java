/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.restApi;

import com.era.IPAddress.UpdateIPAdress;
import com.era.json.JSONParserPost;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author root
 */
public class RestAPICheck {

    public static boolean checkRestAPI() {
        UpdateIPAdress updateIPAdress = new UpdateIPAdress();
        String IPAdress = updateIPAdress.getIPAddressFromDb();

        String PORT = updateIPAdress.getPORT();
        String URL = "http://" + IPAdress + ":" + PORT + "/FingerEnrollVerify/RestAPIStatus";
 
        String jsonString = "";
        JSONParserPost jsonParserpost = new JSONParserPost();
        JSONObject output = jsonParserpost.makeHttpRequest(URL, "POST", jsonString);

        if (output != null) {
            
            try {
                JSONArray jsonArray = output.getJSONArray("statusNodes");
                if (jsonArray != null) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String errorFlag = jsonObject.getString("status"); 
                   
                    if(errorFlag == null)
                        return false;
                    else  
                        return true;
                    
                }
                else
                    return false;
            } catch (JSONException ex) {
                Logger.getLogger(RestAPICheck.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        else
           return false;          
    }

}
