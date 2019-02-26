/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.sqliteinfo;

import com.era.sqlitedb.DBFileName;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class SqliteConnectivity {
    public static Connection getConnection() {
        String name = DBFileName.getFileName();
        Connection conn = null;
        String url = "jdbc:sqlite:" + name;

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
            Logger.getLogger(SqliteConnectivity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
