/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.IPAddress;

import com.era.sqlitedb.AdminRegistration;
import com.era.sqlitedb.DBFileName;
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

    private String PortNo, userName, serviceName, password;
    private boolean errorFlag = false;

    public UpdateIPAdress() {
    }

    public Connection getConnection() {
        Connection conn = null;
        String url = "jdbc:sqlite:" + DBFileName.getFileName();

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

    public boolean adminExists(String rootUser) {
        Connection conn = getConnection();
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
        releaseConnection(conn);
        return infoExists;
    }

    public String updateIPAddress(String rootUser, String IPAddress, String portNo, String serviceName, String userName, String password) {
        if (!adminExists(rootUser)) {
            return "You are not permitted to change IP address";
        } else {
            Connection conn = getConnection();
            String errorMessage = null;

            if (conn != null) {

                

                try {
                    PreparedStatement pstmt = conn.prepareStatement("DELETE FROM DBConnection") ;
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
                    Logger.getLogger(UpdateIPAdress.class.getName()).log(Level.SEVERE, null, ex);
                    errorMessage = ex.getMessage();
                }
            } else {
                errorMessage = "db(sqlite) connectivity problem";
            }
            releaseConnection(conn);
            return errorMessage;
        }
    }

    public String getIPAddressFromDb() {
        Connection conn = getConnection();
        String sql = "SELECT ip_address_of_DBConnection,PORT,serviceName,username,password FROM DBConnection";
        String ipAddress = null;
        errorFlag = false;

        if (conn == null) {
            errorFlag = true;
            return "db Connectivity problem";
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ipAddress = rs.getString("ip_address_of_DBConnection");
                PortNo = rs.getString("PORT");
                serviceName = rs.getString("serviceName");
                userName = rs.getString("username");
                password = rs.getString("password");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorFlag = true;
            ipAddress = e.getMessage();
        }

        releaseConnection(conn);
        return ipAddress;
    }

    public String getPORT() {
        return PortNo;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean getErrorFlag() {
        return errorFlag;
    }

    public static void main(String arg[]) {
        UpdateIPAdress updateIPAdress = new UpdateIPAdress();
        String IP = updateIPAdress.getIPAddressFromDb();
        String port = updateIPAdress.getPORT();
        String serviceName = updateIPAdress.getServiceName();
        String userName = updateIPAdress.getUserName();
        String password = updateIPAdress.getPassword();
        System.out.println("IP: " + IP + " PORT: " + port);
        System.out.println("serviceName: " + serviceName + " userName: " + userName);
        System.out.println("password: " + password);

        String str = updateIPAdress.updateIPAddress("sagor@gmail.com", "10.11.201.170", "1521", "xe", "biotpl", "biotpl");
        System.out.println("message: " + str);
    }

}
