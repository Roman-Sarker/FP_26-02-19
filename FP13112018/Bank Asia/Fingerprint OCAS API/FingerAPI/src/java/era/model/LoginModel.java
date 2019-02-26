/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.model;

/**
 *
 * @author root
 */
public class LoginModel {

    private long serial;
    private byte[] fingerBytes;
    private String pAmount, cust_no, pOperationType, pAcId, app_user, pDeviceId, pEnrolFrom, pSessionId;
    private String finger1, finger2, pCustType, pLogId, errorFlag, errorMessage;

    public String getFinger1() {
        return finger1;
    }

    public void setFinger1(String finger1) {
        this.finger1 = finger1;
    }

    public String getFinger2() {
        return finger2;
    }

    public void setFinger2(String finger2) {
        this.finger2 = finger2;
    }

    public String getpAmount() {
        return pAmount;
    }

    public void setpAmount(String pAmount) {
        this.pAmount = pAmount;
    }

    public String getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(String errorFlag) {
        this.errorFlag = errorFlag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getSerial() {
        return serial;
    }

    public void setSerial(long serial) {
        this.serial = serial;
    }

    public String getCust_no() {
        return cust_no;
    }

    public void setCust_no(String cust_no) {
        this.cust_no = cust_no;
    }

    public byte[] getFingerBytes() {
        return fingerBytes;
    }

    public void setFingerBytes(byte[] fingerBytes) {
        this.fingerBytes = fingerBytes;
    }

    public String getpOperationType() {
        return pOperationType;
    }

    public void setpOperationType(String pOperationType) {
        this.pOperationType = pOperationType;
    }

    public String getpAcId() {
        return pAcId;
    }

    public void setpAcId(String pAcId) {
        this.pAcId = pAcId;
    }

    public String getApp_user() {
        return app_user;
    }

    public void setApp_user(String app_user) {
        this.app_user = app_user;
    }

    public String getpDeviceId() {
        return pDeviceId;
    }

    public void setpDeviceId(String pDeviceId) {
        this.pDeviceId = pDeviceId;
    }

    public String getpEnrolFrom() {
        return pEnrolFrom;
    }

    public void setpEnrolFrom(String pEnrolFrom) {
        this.pEnrolFrom = pEnrolFrom;
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

    public String getpLogId() {
        return pLogId;
    }

    public void setpLogId(String pLogId) {
        this.pLogId = pLogId;
    }

}
