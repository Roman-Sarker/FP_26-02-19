/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.sqlitedb;

import com.era.admin.AdminRegistration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class DBConnInfo {
    
    private static String dbFileName="UCBLFinger.db";
    private static String url = "jdbc:sqlite:";
    public static String getFileName()
    {
        return dbFileName;
    }
    
    public static String getFileURL()
    {
        return url;
    }
    
    public static Connection getConnection() {
        Connection conn = null;
        String url = getFileURL() + "/" + getFileName();

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void releaseConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] arg){
        Connection conn = getConnection();
        System.out.println("Connection "+conn);
        releaseConnection(conn);        
    }
}
