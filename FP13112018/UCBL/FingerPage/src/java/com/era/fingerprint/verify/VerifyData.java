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

    private int serial;
    private byte[] temp;
    private String depamount, name, app_user, pAcId, pOperationType, pLogId, pSessionId, pCustType, pEnrolFrom, pDeviceId;

    public String getDepamount() {
        return depamount;
    }

    public void setDepamount(String depamount) {
        this.depamount = depamount;
    }

    public String getApp_user() {
        return app_user;
    }

    public void setApp_user(String app_user) {
        this.app_user = app_user;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public byte[] getTemp() {
        return temp;
    }

    public void setTemp(byte[] temp) {
        this.temp = temp;
    }
}
