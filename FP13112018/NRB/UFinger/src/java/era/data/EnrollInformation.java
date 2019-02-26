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
    
    private String user_id,create_by,ai_logid,br_code,
                user_type,serial,errorMessage,errorFlag;
     
    private byte[] lIndex, lLittle, lThumb, lMiddle, lRing, rIndex, rRing, rLittle, rThumb, rMiddle;

    
    public EnrollInformation(){
    }

    public String getBr_code() {
        return br_code;
    }

    public void setBr_code(String br_code) {
        this.br_code = br_code;
    }
    
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getAi_logid() {
        return ai_logid;
    }

    public void setAi_logid(String ai_logid) {
        this.ai_logid = ai_logid;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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
