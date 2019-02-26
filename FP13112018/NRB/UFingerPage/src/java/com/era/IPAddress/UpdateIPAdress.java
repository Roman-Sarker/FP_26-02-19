/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.IPAddress;

import com.era.sqlitedb.AdminRegistration; 
import com.era.sqlitedb.DBConnection; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class UpdateIPAdress {
 
    private String rootUser,PortNo;
    private boolean errorFlag = false;

    public UpdateIPAdress() {
    }    

    

    public boolean adminExists(String rootUser) {
        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.getConnection();
        String sql = "SELECT username FROM adminInfo where username ='" + rootUser + "'";
        boolean infoExists = false;

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                infoExists = true;
                break;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AdminRegistration.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        dbConnection.releaseConnection(conn);
        return infoExists;
    }

    public String updateIPAddress(String rootUser,String IPAddress,String portNo) {
        if (!adminExists(rootUser)) {
            return "You are not permitted to change IP address";
        } else {
             DBConnection dbConnection = new DBConnection();
             Connection conn = dbConnection.getConnection();
            Statement stmt = null;
            String errorMessage = null;

            if (conn != null) {
                try {
                    PreparedStatement pstmt = conn.prepareStatement("DELETE FROM WebServiceInfo") ;
                    pstmt.executeUpdate();

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                
                try {
                    String sql = "insert into WebServiceInfo(ip_address_of_webservice,PORT) values(?,?)";
                   
                    PreparedStatement preparedStmt = conn.prepareStatement(sql);
                    preparedStmt.setString(1, IPAddress);
                    preparedStmt.setString(2, portNo);  
                    
                    int flag = preparedStmt.executeUpdate();
                    System.out.println("update information status: " + flag);                    
                     
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateIPAdress.class.getName()).log(Level.SEVERE, null, ex);
                    errorMessage = ex.getMessage();
                }
            } else {
                errorMessage = "db(sqlite) connectivity problem";
            }
            dbConnection.releaseConnection(conn);
            return errorMessage;
        }
    }
    
    public String getIPAddressFromDb(){
        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.getConnection();
        String sql = "SELECT ip_address_of_webservice,PORT FROM WebServiceInfo";
        String ipAddress = null;
        errorFlag = false;
        
        if(conn == null){
            errorFlag = true; 
            return "db Connectivity problem";
        }
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql); 
            
            while (rs.next()) {
                ipAddress=rs.getString("ip_address_of_webservice"); 
                PortNo=rs.getString("PORT");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorFlag = true;
            ipAddress = e.getMessage();
        }
        
        dbConnection.releaseConnection(conn);
        return ipAddress;
    }  
    
    public String getPORT(){
        return PortNo;
    }
    
    public boolean getErrorFlag()
    {
       return errorFlag;
    }
    
    public static void main(String arg[]){
        UpdateIPAdress updateIPAdress=new UpdateIPAdress();
        String IP = updateIPAdress.getIPAddressFromDb();
        String port = updateIPAdress.getPORT();
        System.out.println("IP: "+IP+" PORT: "+port);
    }

}
