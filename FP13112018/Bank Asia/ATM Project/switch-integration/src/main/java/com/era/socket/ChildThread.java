package com.era.socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.era.atm.ExtractHeader;
import com.era.atm.UnpackISOMessage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.jpos.iso.ISOMsg;

/**
 *
 * @author Sultan Ahmed Sagor BUET , CSE-08 Batch , Dhaka .
 */
public class ChildThread  extends Thread  {

    protected Socket socket;

    public ChildThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
//        while (true) {
            try {
                line = brinp.readLine();
                if (line == null) {
                    socket.close();
                    return;
                } else {
                    doProcessing(line);
                    out.writeBytes(line + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
       // }
    }
    
    void doProcessing(String line ){
        ExtractHeader extractHeader = new ExtractHeader();
        extractHeader.extractHeader(line);
        String header = extractHeader.getHeader();
        String isoMsgStr = extractHeader.getIsoMessage();
        
        System.out.println("header is "+header);
        System.out.println("isoMessage  is "+isoMsgStr);
        
        UnpackISOMessage unpackISOMessage = new UnpackISOMessage();
        ISOMsg isoMsg = unpackISOMessage.parseISOMessage(isoMsgStr);
        
        
    }

}
