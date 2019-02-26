/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.sqlitedb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sultan
 */
public class UpdateIpAddress {

    public static String updateIPAddress(Connection conn , String IPAddress , String portNo, String serviceName, String userName, String password) {
        String errorMessage = null; 
        
        if (conn != null) { 
            try {
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM DBConnection");
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            try {
                String sql = "INSERT INTO DBConnection(ip_address_of_DBConnection,PORT"
                        + ",serviceName,username,password) VALUES(?,?,?,?,?)";
                PreparedStatement preparedStmt = conn.prepareStatement(sql);
                preparedStmt.setString(1, IPAddress);
                preparedStmt.setString(2, portNo);
                preparedStmt.setString(3, serviceName);
                preparedStmt.setString(4, userName);
                preparedStmt.setString(5, password);

                // execute the java preparedstatement
                int flag = preparedStmt.executeUpdate();
                System.out.println("update information status: " + flag);
            } catch (SQLException ex) {
                Logger.getLogger(UpdateIpAddress.class.getName()).log(Level.SEVERE, null, ex);
                errorMessage = ex.getMessage();
            }
        } else {
            errorMessage = "db(sqlite) connectivity problem";
        } 
        return errorMessage;
    }

}
