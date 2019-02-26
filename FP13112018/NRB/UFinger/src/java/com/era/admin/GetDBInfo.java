package com.era.admin;

import com.era.sqlitedb.DBConnInfo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetDBInfo {

    public static DBInfo getDbInfo() {
       
        try {
            DBInfo dbInfo = new DBInfo();
             
            
            Connection conn = DBConnInfo.getConnection();
            String sql = "SELECT id, ip_address_of_DBConnection,PORT,serviceName,username,password FROM DBConnection";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
             
            
            while (rs.next()) { 
                dbInfo.ip = rs.getString("ip_address_of_DBConnection");
                dbInfo.portNo = rs.getString("PORT");
                dbInfo.serviceName = rs.getString("serviceName");
                dbInfo.userName = rs.getString("username");
                dbInfo.password = rs.getString("password"); 
            }
            DBConnInfo.releaseConnection(conn);
            return dbInfo; 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
        return null;
    }
}
