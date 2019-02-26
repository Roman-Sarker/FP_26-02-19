/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.json;

/**
 *
 * @author Sultan Ahmed
 */
public class ENROLL_DATA {

    
    private String cust_no , app_user ; 
    private int serial ;
    private byte[] fingerData ;
    private String pAcId, pOperationType , pLogId , pSessionId , pCustType , pEnrolFrom , pDeviceId ;
    
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

    public String getpEnrolFrom() {
        return pEnrolFrom;
    }

    public void setpEnrolFrom(String pEnrolFrom) {
        this.pEnrolFrom = pEnrolFrom;
    }

    public String getpDeviceId() {
        return pDeviceId;
    }

    public void setpDeviceId(String pDeviceId) {
        this.pDeviceId = pDeviceId;
    }
     
    public String getCust_no() {
        return cust_no;
    }

    public void setCust_no(String cust_no) {
        this.cust_no = cust_no;
    }

    public String getApp_user() {
        return app_user;
    }

    public void setApp_user(String app_user) {
        this.app_user = app_user;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public byte[] getFingerData() {
        return fingerData;
    }

    public void setFingerData(byte[] fingerData) {
        this.fingerData = fingerData;
    }
 
    
}
