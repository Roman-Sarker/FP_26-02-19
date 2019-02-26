/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.sqlitedb;

import static com.era.sqlitedb.DbConnectivity.releaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author root
 */
public class DropTable {
    
    public void dropAdminTable(Connection conn) { 
        String sql = "DROP table adminInfo";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("adminInfo table dropped!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        releaseConnection(conn);
    }
    
}
