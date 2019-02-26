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
public class DataForVerification {
    
    String name,logid,appuser,depamount,custno,sessid,opr_type,cust_type;

    public String getOpr_type() {
        return opr_type;
    }

    public void setOpr_type(String opr_type) {
        this.opr_type = opr_type;
    }

    public String getCust_type() {
        return cust_type;
    }

    public void setCust_type(String cust_type) {
        this.cust_type = cust_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getAppuser() {
        return appuser;
    }

    public void setAppuser(String appuser) {
        this.appuser = appuser;
    }

    public String getDepamount() {
        return depamount;
    }

    public void setDepamount(String depamount) {
        this.depamount = depamount;
    }

    public String getCustno() {
        return custno;
    }

    public void setCustno(String custno) {
        this.custno = custno;
    }

    public String getSessid() {
        return sessid;
    }

    public void setSessid(String sessid) {
        this.sessid = sessid;
    }
    
}
