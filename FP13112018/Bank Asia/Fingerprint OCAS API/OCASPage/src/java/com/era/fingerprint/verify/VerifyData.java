/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.fingerprint.verify;

/**
 *
 * @author Sultan Ahmed
 */
public class VerifyData {
 
    private byte[] fingerData;
    private String cust_no,cust_type; 

    public byte[] getFingerData() {
        return fingerData;
    }

    public void setFingerData(byte[] fingerData) {
        this.fingerData = fingerData;
    }

    public String getCust_no() {
        return cust_no;
    }

    public void setCust_no(String cust_no) {
        this.cust_no = cust_no;
    }

    public String getCust_type() {
        return cust_type;
    }

    public void setCust_type(String cust_type) {
        this.cust_type = cust_type;
    }
    
    
}
