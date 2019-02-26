package com.era.fingerprint.enroll;
 
import com.era.json.ENROLL_DATA;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sultan Ahmed
 */
public class Famenroll2 {

    public Famenroll2() {

    }

    public ENROLL_DATA prepareEnrollData(String name, int serial, String app_user, byte[] data , 
        String pAcId,String pOperationType , String pLogId ,String pSessionId ,String pCustType ,
        String pEnrolFrom ,String pDeviceId) {
        ENROLL_DATA enroll_data = new ENROLL_DATA();
        enroll_data.setCust_no(name);
        enroll_data.setApp_user(app_user);
        enroll_data.setSerial(serial);
        enroll_data.setTemp(data);
        enroll_data.setpAcId(pAcId);
        enroll_data.setpOperationType(pOperationType);
        enroll_data.setpLogId(pLogId);
        enroll_data.setpSessionId(pSessionId);
        enroll_data.setpCustType(pCustType);
        enroll_data.setpEnrolFrom(pEnrolFrom);
        enroll_data.setpDeviceId(pDeviceId);
        return enroll_data;
    } 

}
