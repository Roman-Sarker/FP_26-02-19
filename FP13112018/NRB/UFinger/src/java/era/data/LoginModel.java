/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package era.data;

/**
 *
 * @author root
 */
public class LoginModel {

   
    
    private long serial;
    private byte[] fingerBytes;
    private String user_id, pAmount, pOperationType, create_by, pDeviceId, pEnrolFrom, pSessionId;
    private String pCustType, pLogId, errorFlag, errorMessage,pAcId;

    public String getpAcId() {
        return pAcId;
    }

    public void setpAcId(String pAcId) {
        this.pAcId = pAcId;
    }

    public String getpAmount() {
        return pAmount;
    }
    
     public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
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
