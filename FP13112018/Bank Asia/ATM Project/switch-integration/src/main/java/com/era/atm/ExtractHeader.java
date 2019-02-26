/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.era.atm;

/**
 *
 * @author Sultan Ahmed Sagor
 * BUET , CSE-08 Batch , Dhaka . 
 */
public class ExtractHeader {
    
    private String header , isoMessage ; 
    
    public void extractHeader(String msg){
        header = msg.substring(0, 8);
        isoMessage = msg.substring(8, msg.length()); 
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getIsoMessage() {
        return isoMessage;
    }

    public void setIsoMessage(String isoMessage) {
        this.isoMessage = isoMessage;
    }
    
    

}
