/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.model;

/**
 *
 * @author Sultan Ahmed
 */
public class InformationModel {
    private String sysdte,sl,ip,UUID,MAC,PcName,OsUser,DNS,PcInfo,sessid,username ;
    private String errorFlag,errorMessage;
    
    public String getSl() {
        return sl;
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

    public String getSysdte() {
        return sysdte;
    }

    public void setSysdte(String sysdte) {
        this.sysdte = sysdte;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getPcName() {
        return PcName;
    }

    public void setPcName(String PcName) {
        this.PcName = PcName;
    }

    public String getOsUser() {
        return OsUser;
    }

    public void setOsUser(String OsUser) {
        this.OsUser = OsUser;
    }

    public String getDNS() {
        return DNS;
    }

    public void setDNS(String DNS) {
        this.DNS = DNS;
    }

    public String getPcInfo() {
        return PcInfo;
    }

    public void setPcInfo(String PcInfo) {
        this.PcInfo = PcInfo;
    } 

    public String getSessid() {
        return sessid;
    }

    public void setSessid(String sessid) {
        this.sessid = sessid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
