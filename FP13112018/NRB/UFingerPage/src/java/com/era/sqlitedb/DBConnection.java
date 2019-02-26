/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.sqlitedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class DBConnection {
    
    private static String dbFileName="UCBLFingerpage.db";
    private static String urlDB = "jdbc:sqlite:";
    
    public DBConnection(){
    }
    
    public static String getFileName()
    {
        return dbFileName;
    }
    
    public static String getDBURL()
    {
        return dbFileName;
    }
    
    public Connection getConnection() {
        Connection conn = null;
        String url =  urlDB + dbFileName;

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
        DBConnection dbConnection =new  DBConnection(); 
        Connection con = dbConnection.getConnection();
        System.out.println(con);
        DBConnection.releaseConnection(con);
    } 
}
