/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.Global;

import org.apache.axis2.databinding.types.UnsignedByte;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sultan Ahmed
 */

public class Global {

    public Global() {

    }

    public String objectMappingToJsonString(Object obj, HttpServletResponse response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(obj);
            return jsonInString;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Global.class.getName()).log(Level.SEVERE, null, ex);
            try {
                response.getWriter().print(ex);
            } catch (IOException ex1) {
                Logger.getLogger(Global.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }
}
