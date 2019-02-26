/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.atm;

/**
 *
 * @author DELL
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnpackISOMessage { 

    public ISOMsg parseISOMessage(String message){
        
        try {            
            InputStream is = new FileInputStream(new File("fields.xml"));
            GenericPackager packager = new GenericPackager(is);
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(message.getBytes());
            return isoMsg;
        } catch (ISOException ex) {
            Logger.getLogger(UnpackISOMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UnpackISOMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void printISOMessage(ISOMsg isoMsg) {
        try {
            System.out.printf("MTI = %s%n", isoMsg.getMTI());
            for (int i = 1; i <= isoMsg.getMaxField(); i++) {
                if (isoMsg.hasField(i)) {
                    System.out.printf("Field (%s) = %s%n", i, isoMsg.getString(i));
                }
            }
        } catch (ISOException e) {
            e.printStackTrace();
        }
    }
}