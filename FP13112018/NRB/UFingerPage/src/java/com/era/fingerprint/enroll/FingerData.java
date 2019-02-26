/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.fingerprint.enroll;

/**
 *
 * @author Sultan Ahmed
 */
public class FingerData {
    
    boolean finger1,finger2;

    public boolean isFinger1() {
        return finger1;
    }

    public void setFinger1(String finger) {
        if(finger.equals("Y"))
           finger1 = true;
        else
            finger1 = false;
    }

    public boolean isFinger2() {
        return finger2;
    }

    public void setFinger2(String finger) {
        if(finger.equals("Y"))
           finger2 = true;
        else
            finger2 = false;
    }
    
}
