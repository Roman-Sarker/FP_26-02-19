/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.sqliteinfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class SqliteInfoShow {

    public static List<DBConnectionInfo> getDBInfo() {
        List<DBConnectionInfo> dbList = new ArrayList<DBConnectionInfo>();

        Connection conn = SqliteConnectivity.getConnection();

        String sql = "SELECT id, ip_address_of_DBConnection,PORT,username,password FROM DBConnection";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DBConnectionInfo dbInfo = new DBConnectionInfo();
                dbInfo.setId(rs.getString("id"));
                dbInfo.setIp_address_of_DBConnection(rs.getString("ip_address_of_DBConnection"));
                dbInfo.setPORT(rs.getString("PORT"));
                dbInfo.setUsername(rs.getString("username"));
                dbInfo.setPassword(rs.getString("password"));
                dbList.add(dbInfo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        SqliteConnectivity.releaseConnection(conn);
        return dbList;
    }

    public static List<AdminInfo> getAdminInfo() {
        List<AdminInfo> adminList = new ArrayList<AdminInfo>();
        Connection conn = SqliteConnectivity.getConnection();

        String sql = "SELECT id, username,password FROM adminInfo";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) { 
                AdminInfo adminInfo = new AdminInfo();
                adminInfo.setId( rs.getString("id"));
                adminInfo.setUsername(rs.getString("username"));
                adminInfo.setPassword( rs.getString("password"));
                adminList.add(adminInfo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        SqliteConnectivity.releaseConnection(conn);

        return adminList;
    }
}
