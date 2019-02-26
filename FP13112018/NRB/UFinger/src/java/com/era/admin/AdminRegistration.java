/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.admin;
 
import com.era.sqlitedb.DBConnInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class AdminRegistration {
 
    private String firstName, lastName, userName, password, rootUser;

    public AdminRegistration() {

    } 

    public void setRootUser(String rootUser) {
        this.rootUser = rootUser;
    }

    public void setInformation(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public boolean adminExists() {
        Connection conn = DBConnInfo.getConnection();
        String sql = "SELECT username FROM adminInfo where username ='" + userName + "'";
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
        DBConnInfo.releaseConnection(conn);
        return infoExists;
    }

    public boolean checkPermissionOfCreatingAdmin() {
        Connection conn = DBConnInfo.getConnection();
        String sql = "SELECT * FROM adminInfo where username ='" + rootUser + "'";

        System.out.println(rootUser);
        boolean infoExists = false;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
            /*    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String name = rsmd.getColumnName(i);
                    System.out.println((i) + "th column label is " + name);
                } */

                String adminType = rs.getString(4);
                adminType = adminType.trim();
                System.out.println("adminType" + adminType);
                if (adminType.equals("ROOT")) {
                    infoExists=true;
                } else {
                    infoExists=false;
                }
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } 
        DBConnInfo.releaseConnection(conn);
        return infoExists;
    }

    int insertRowInAdminTable() {
        Connection conn = DBConnInfo.getConnection();
        String sql = "INSERT INTO adminInfo(firstName,lastName,adminType,username,password) "
                + "VALUES(?,?,?,?,?)";
        int insertionFlag = 0;
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, "GEN");
            pstmt.setString(4, userName);
            pstmt.setString(5, password);
            pstmt.executeUpdate();
            insertionFlag = 1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            insertionFlag= -1;
        }
        
        DBConnInfo.releaseConnection(conn);
        return insertionFlag;
    }

    public String createAdmin() {
        if (adminExists()) {
            return userName + " admin already exists!";
        } else {
            if (checkPermissionOfCreatingAdmin()) {
                int creatAdminFlag = insertRowInAdminTable();
                if (creatAdminFlag == 1) {
                    return null;
                } else {
                    return "insertion problem of data in admin table!";
                }
            } else {
                return "You are not eligible for creating admin!";
            }
        }
    }

    public void showInfo() {
        Connection conn = DBConnInfo.getConnection();
        String sql = "SELECT id, firstName,lastName,adminType,username,password FROM adminInfo";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getString("firstName") + "\t"
                        + rs.getString("lastName") + "\t"
                        + rs.getString("adminType") + "\t"
                        + rs.getString("username") + "\t"
                        + rs.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        DBConnInfo.releaseConnection(conn);
    }

    public static void main(String arg[]) {
        AdminRegistration adminRegistration = new AdminRegistration();
        adminRegistration.showInfo();
        adminRegistration.setRootUser("sultan@gmail.com");
        adminRegistration.setInformation("Sultan Ahmed", "Sagor", "sagor@gmail.com", "era@123");
        adminRegistration.createAdmin();
        //System.out.println(adminRegistration.checkPermissionOfCreatingAdmin());
    }
}
