/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.param;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspWriter;

/**
 *
 * @author root
 */
public class Parameter_Fixed_Verify {

    String sessid ,username ,depamount,custno,appuser,logid,opr_type,cust_type ;

    void setParameter(String sessid , String username , String depamount, String custno, String appuser, String logid,
            String opr_type,String cust_type ) {
        this.sessid =sessid;
        this.username =username;
        this.depamount=depamount;
        this.custno=custno;
        this.appuser=appuser;
        this.logid=logid;
        this.opr_type=opr_type;
        this.cust_type=cust_type;
    }
    
    public String  getParameter(String param,boolean invertedCommaFlag)
    {
        param = param.replaceAll("'", "");
        if(invertedCommaFlag)
            return insertInvertedComma(param);
        else 
            return param;
         
    } 
    
    public String  getParameter(PrintWriter out,String param,boolean invertedCommaFlag)
    {
        param = param.replaceAll("'", "");
        if(invertedCommaFlag)
            return insertInvertedComma(param);
        else 
            return param;
         
    } 
    
    String insertInvertedComma(String param)
    {
        param = new StringBuffer(param).insert(0, "'").toString();
        param = new StringBuffer(param).insert(param.length(), "'").toString();
        return param;
    }
}
