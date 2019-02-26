package com.era.fingerprint.enroll;
 
import com.era.Data.ENROLL_DATA; 

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

    public ENROLL_DATA prepareEnrollData(String user_id,String create_by,String ai_logid,
                    String user_type,String serial,
                    String lThumb,String lIndex,String lMiddle,String lRing,String lLittle,
                    String rThumb,String rIndex,String rMiddle,String rRing,String rLittle) {
        
        ENROLL_DATA enroll_data = new ENROLL_DATA();
         enroll_data.setUser_id(user_id);
         enroll_data.setCreate_by(create_by);
         
         enroll_data.setAi_logid(ai_logid);
         enroll_data.setUser_type(user_type);
         enroll_data.setSerial(serial); 
         
         enroll_data.setLThumb(lThumb);
         enroll_data.setLIndex(lIndex);
         enroll_data.setLMiddle(lMiddle);
         enroll_data.setLRing(lRing);
         enroll_data.setLLittle(lLittle);
         
         enroll_data.setRThumb(rThumb);
         enroll_data.setRIndex(rIndex);
         enroll_data.setRMiddle(rMiddle);
         enroll_data.setRRing(rRing);
         enroll_data.setRLittle(rLittle);
         
         return enroll_data;
        
    } 

}
