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
    
    private String temp;
    private String amount, user_id, create_by, pAcId, pOperationType, pLogId, pSessionId, pCustType , pDeviceId;

    public String getAmount() {
        return amount;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    } 
     

    public String getpAcId() {
        return pAcId;
    }

    public void setpAcId(String pAcId) {
        this.pAcId = pAcId;
    }

    public String getpOperationType() {
        return pOperationType;
    }

    public void setpOperationType(String pOperationType) {
        this.pOperationType = pOperationType;
    }

    public String getpLogId() {
        return pLogId;
    }

    public void setpLogId(String pLogId) {
        this.pLogId = pLogId;
    }

    public String getpSessionId() {
        return pSessionId;
    }

    public void setpSessionId(String pSessionId) {
        this.pSessionId = pSessionId;
    }

    public String getpCustType() {
        return pCustType;
    }

    public void setpCustType(String pCustType) {
        this.pCustType = pCustType;
    } 

    public String getpDeviceId() {
        return pDeviceId;
    }

    public void setpDeviceId(String pDeviceId) {
        this.pDeviceId = pDeviceId;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    } 

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
