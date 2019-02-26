/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.sqlitedb;

import com.era.admin.AdminRegistration;
import com.era.admin.AdminDetails;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sultan Ahmed
 */
public class DbConnectivity {

    public DbConnectivity() {
    }

    public int createNewDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnectivity.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        String url = DBConnInfo.getFileURL() + "/" + DBConnInfo.getFileName();
        System.out.println(url);
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                return 1;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    } 

    public int createNewTable() {

        Connection conn = DBConnInfo.getConnection();
        if (CreateTable.createDatabaseInfoTable(conn) == 1) {
            System.out.println("Database connectivity table is created");            
            int flag = CreateTable.createAdminTable(conn);
            DBConnInfo.releaseConnection(conn);
            return flag;
        } else {
            DBConnInfo.releaseConnection(conn);
            return 0;
        }
    }

    public void showInfo() {
        Connection conn = DBConnInfo.getConnection();
        String sql = "SELECT id, ip_address_of_DBConnection,PORT,username,password FROM DBConnection";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getString("ip_address_of_DBConnection"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "SELECT id, username,password FROM adminInfo";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getString("username") + "\t" + rs.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnInfo.releaseConnection(conn);
    }

    public boolean checkLogin(String username, String password) {
        Connection con = DBConnInfo.getConnection();
        String sql = "SELECT * FROM adminInfo where username ='" + username + "' and password='" + password + "'";
        boolean LOGINFlag = false;

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getString("username") + "\t" + rs.getString("password"));
                LOGINFlag = true;
                break;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnInfo.releaseConnection(con);
        return LOGINFlag;
    }

    public AdminDetails getAdminDetails(String username, String password) {
        Connection con = DBConnInfo.getConnection();
        String sql = "SELECT * FROM adminInfo where username ='" + username + "' and password='" + password + "'";
        AdminDetails adminDetails = new AdminDetails();
        String firstName, lastName;

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                adminDetails.setFirstName(firstName);
                adminDetails.setLastName(lastName);
                break;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        DBConnInfo.releaseConnection(con);
        return adminDetails;
    }

    public String processingAllTask() {
        int dbCreateFlag = createNewDatabase();
        if (dbCreateFlag == 1) { 
            int createTableFlag = createNewTable();
            System.out.println("create new table flag : " + createTableFlag);
            if (createTableFlag != 1) {
                return "table is not created";
            } else {
                Connection conn = DBConnInfo.getConnection();
                int insertdbConnFlag = InsertInfoInTable.insertDatabaseConnectionInfo(conn, "10.11.201.170", "1521", "xe", "biotpl", "biotpl");
                System.out.println("insertDatabaseConnectionInfo flag: " + insertdbConnFlag);

                int insertAdminFlag = InsertInfoInTable.insertAdminInfo(conn, "sultan@gmail.com", "era@123");
                System.out.println("insertAdminFlag: " + insertAdminFlag);

                if (insertdbConnFlag == 1 && insertAdminFlag == 1) {
                    showInfo();
                    DBConnInfo.releaseConnection(conn);
                    return null;
                } else {
                    DBConnInfo.releaseConnection(conn);
                    return "insertion of webservice ip is not successfull";
                } 
            } 
        } else {
            return "Database is not created.";
        }

    }

    public static void main(String arg[]) {
        DbConnectivity dbConnectivity = new DbConnectivity();
        AdminDetails adminDetails = dbConnectivity.getAdminDetails("sultan@gmail.com", "era@123");
        System.out.println(adminDetails.getFirstName() + " " + adminDetails.getLastName());
        //    dbConnectivity.processingAllTask();
        //    dbConnectivity.checkLogin("sultan@gmail.com", "era@1234") ;
        //    dbConnectivity.showInfo();
        //   dbConnectivity.dropAdminTable();
    }

}
