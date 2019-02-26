/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.sqlitedb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
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

        try {
            DBConnection dbConnection = new DBConnection();
            Connection conn = dbConnection.getConnection();
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                dbConnection.releaseConnection(conn);
                return 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int createAdminTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS adminInfo (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	firstName text NOT NULL,\n"
                + "	lastName text NOT NULL,\n"
                + "	adminType text NOT NULL,\n"
                + "	username text NOT NULL,\n"
                + "	password text NOT NULL\n"
                + ");";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            DBConnection.releaseConnection(conn);
            return 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            DBConnection.releaseConnection(conn);
            return 0;
        }

    }

    public int createWebServiceInfoTable(Connection conn) {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS WebServiceInfo (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	PORT text NOT NULL,\n"
                + "	ip_address_of_webservice text NOT NULL\n"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return 1;
    }

    public int createNewTable() {

        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.getConnection();
        if (createWebServiceInfoTable(conn) == 1) {
            System.out.println("web service info table is created");
            return createAdminTable(conn);
        } else {
            dbConnection.releaseConnection(conn);
            return 0;
        }
    }

    public int insertWebServiceInfo(String ipAddress, String PORT) {
        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.getConnection();
        String sql = "SELECT ip_address_of_webservice FROM WebServiceInfo";
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
            dbConnection.releaseConnection(conn);
            return -1;
        }

        if (!infoExists) {

            sql = "INSERT INTO WebServiceInfo(ip_address_of_webservice,PORT) VALUES(?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, ipAddress);
                pstmt.setString(2, PORT);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                dbConnection.releaseConnection(conn);
                return -1;
            }
        }
        dbConnection.releaseConnection(conn);
        return 1;

    }

    public int insertAdminInfo(String userName, String password) {
        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.getConnection();
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
            dbConnection.releaseConnection(conn);
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
                dbConnection.releaseConnection(conn);
                return -1;
            }
        }
        dbConnection.releaseConnection(conn);
        return 1;

    }

    public void showInfo() {

        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.getConnection();
        String sql = "SELECT id, ip_address_of_webservice FROM WebServiceInfo";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("ip_address_of_webservice"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "SELECT id, username,password FROM adminInfo";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("username") + "\t" + rs.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        dbConnection.releaseConnection(conn);
    }

    public boolean checkLogin(String username, String password) {
        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.getConnection();
        String sql = "SELECT * FROM adminInfo where username ='" + username + "' and password='" + password + "'";

        boolean LOGINFlag = false;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("username") + "\t" + rs.getString("password"));
                LOGINFlag = true;
                break;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        dbConnection.releaseConnection(conn);
        return LOGINFlag;
    }

    public AdminDetails getAdminDetails(String username, String password) {
        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.getConnection();

        String sql = "SELECT * FROM adminInfo where username ='" + username + "' and password='" + password + "'";
        AdminDetails adminDetails = new AdminDetails();
        String firstName, lastName;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

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

        dbConnection.releaseConnection(conn);
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
                int insertWebServiceFlag = insertWebServiceInfo("10.11.1.31", "8080");
                System.out.println("insertWebServiceFlag: " + insertWebServiceFlag);
                int insertAdminFlag = insertAdminInfo("sultan@gmail.com", "era@123");
                System.out.println("insertAdminFlag: " + insertAdminFlag);
                if (insertWebServiceFlag == 1 && insertAdminFlag == 1) {
                    showInfo(); 
                    return null;
                } else { 
                    return "insertion of webservice ip is not successfull";
                } 
            }  
        } else {
            return "Database is not created.";
        }

    }

    public void dropAdminTable() {
        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.getConnection();
        String sql = "DROP table adminInfo";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("adminInfo table dropped!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        dbConnection.releaseConnection(conn);
    }

    public static void main(String arg[]) {
        DbConnectivity dbConnectivity = new DbConnectivity();
        dbConnectivity.processingAllTask();
        dbConnectivity.checkLogin("sultan@gmail.com", "era@123");
        //   dbConnectivity.dropAdminTable();
    }

}
