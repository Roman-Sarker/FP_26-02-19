/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.data;

/**
 *
 * @author sultan
 */
public class EnrollInformation {
    
    private String serial,name,app_user,ai_logid,
                cust_type,errorMessage,errorFlag;
     
    private byte[] lIndex, lLittle, lThumb, lMiddle, lRing, rIndex, rRing, rLittle, rThumb, rMiddle;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    
    public EnrollInformation(){
    }  

    public String getAi_logid() {
        return ai_logid;
    }

    public void setAi_logid(String ai_logid) {
        this.ai_logid = ai_logid;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApp_user() {
        return app_user;
    }

    public void setApp_user(String app_user) {
        this.app_user = app_user;
    }

    public String getCust_type() {
        return cust_type;
    }

    public void setCust_type(String cust_type) {
        this.cust_type = cust_type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(String errorFlag) {
        this.errorFlag = errorFlag;
    }

    public byte[] getlIndex() {
        return lIndex;
    }

    public void setlIndex(byte[] lIndex) {
        this.lIndex = lIndex;
    }

    public byte[] getlLittle() {
        return lLittle;
    }

    public void setlLittle(byte[] lLittle) {
        this.lLittle = lLittle;
    }

    public byte[] getlThumb() {
        return lThumb;
    }

    public void setlThumb(byte[] lThumb) {
        this.lThumb = lThumb;
    }

    public byte[] getlMiddle() {
        return lMiddle;
    }

    public void setlMiddle(byte[] lMiddle) {
        this.lMiddle = lMiddle;
    }

    public byte[] getlRing() {
        return lRing;
    }

    public void setlRing(byte[] lRing) {
        this.lRing = lRing;
    }

    public byte[] getrIndex() {
        return rIndex;
    }

    public void setrIndex(byte[] rIndex) {
        this.rIndex = rIndex;
    }

    public byte[] getrRing() {
        return rRing;
    }

    public void setrRing(byte[] rRing) {
        this.rRing = rRing;
    }

    public byte[] getrLittle() {
        return rLittle;
    }

    public void setrLittle(byte[] rLittle) {
        this.rLittle = rLittle;
    }

    public byte[] getrThumb() {
        return rThumb;
    }

    public void setrThumb(byte[] rThumb) {
        this.rThumb = rThumb;
    }

    public byte[] getrMiddle() {
        return rMiddle;
    }

    public void setrMiddle(byte[] rMiddle) {
        this.rMiddle = rMiddle;
    } 
    
}
