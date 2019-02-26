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
    
    public Global()
    {
        
    }
    
     public byte[] getFingerData(String strIdentifyTemplate)
        {
            String m_strSample,szPath;
            m_strSample = strIdentifyTemplate;

            String dLen;
            int iPos = (m_strSample.indexOf("$", 0) + 1);

            if ((iPos > 0))
            {
                dLen = m_strSample.substring(0, (iPos - 1));
                m_strSample = m_strSample.substring((m_strSample.length() - (m_strSample.length() - iPos)));
            }
            else
                return null;

            byte[] g_Template = new byte[Integer.parseInt(dLen) + 1];
            int i = 0;
            while ((m_strSample.length() > 0))
            {
                iPos = (m_strSample.indexOf("$", 0) + 1);
                if((iPos > 0))
                {
                    String simpleOne = m_strSample.substring(0, (iPos - 1));
                    UnsignedByte ubyte =new UnsignedByte(simpleOne);
                     
                    g_Template[i] = ubyte.byteValue() ;// System.Text.Encoding.UTF8.GetByte(m_strSample.Substring(0, (iPos - 1))); ;// m_strSample.Substring(0, (iPos - 1));
                    m_strSample = m_strSample.substring((m_strSample.length() - (m_strSample.length() - iPos)));
                    i = (i + 1);
                }
                else
                {
                    break;
                }
            } 
            return g_Template;
        }
     
     public String objectMappingToJsonString(Object obj,HttpServletResponse response ){
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
