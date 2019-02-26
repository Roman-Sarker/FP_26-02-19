/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.sqlitedb;
 
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author root
 */
public class CreateTable {
    
    public CreateTable(){
    }
    
    public static int createAdminTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS adminInfo (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	firstName text NOT NULL,\n"
                + "	lastName text NOT NULL,\n"
                + "	adminType text NOT NULL,\n"
                + "	username text NOT NULL,\n"
                + "	password text NOT NULL\n"
                + ");";

        try{
            Statement stmt = conn.createStatement(); 
            stmt.execute(sql); 
            return 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
            return 0;
        }

    }

    public static int createDatabaseInfoTable(Connection conn) {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS DBConnection (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	PORT text NOT NULL,\n"
                + "	serviceName text NOT NULL,\n"
                + "	username text NOT NULL,\n"
                + "	password text NOT NULL,\n"
                + "	ip_address_of_DBConnection text NOT NULL\n"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return 1;
    }
    
}
