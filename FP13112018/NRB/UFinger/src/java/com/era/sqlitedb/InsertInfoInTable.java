/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.sqlitedb;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author root
 */
public class InsertInfoInTable { 
    
    public static int insertDatabaseConnectionInfo(Connection conn,String ipAddress,String PORT,String serviceName,String userName , String password) {
        String sql = "SELECT ip_address_of_DBConnection FROM DBConnection ";
        boolean infoExists = false;

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                infoExists = true;
                break;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
            return -1;
        }

        if (!infoExists) {//username,password

            sql = "INSERT INTO DBConnection(ip_address_of_DBConnection,PORT,serviceName,username,password) VALUES(?,?,?,?,?)";
                                                                                                                       
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, ipAddress);
                pstmt.setString(2, PORT);
                pstmt.setString(3, serviceName);
                pstmt.setString(4, userName);
                pstmt.setString(5, password);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage()); 
                return -1;
            }
        } 
        return 1;

    }

    public static int insertAdminInfo(Connection conn, String userName, String password) {
        System.out.println("username is " + userName);
        String sql = "SELECT * FROM adminInfo where username ='" + userName + "' and password='" + password + "'";
        boolean infoExists = false;

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                infoExists = true;
                break;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }

        if (!infoExists) {

            sql = "INSERT INTO adminInfo(firstName,lastName,adminType,username,password) "
                    + "VALUES(?,?,?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "Sultan");
                pstmt.setString(2, "Ahmed");
                pstmt.setString(3, "ROOT");
                pstmt.setString(4, userName);
                pstmt.setString(5, password);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return -1;
            }
        }
        return 1;

    }
    
}
