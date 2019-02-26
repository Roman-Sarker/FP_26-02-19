/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.sqliteinfo;

/**
 *
 * @author root
 */
public class DBConnectionInfo {

    String id, ip_address_of_DBConnection, PORT, username, password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp_address_of_DBConnection() {
        return ip_address_of_DBConnection;
    }

    public void setIp_address_of_DBConnection(String ip_address_of_DBConnection) {
        this.ip_address_of_DBConnection = ip_address_of_DBConnection;
    }

    public String getPORT() {
        return PORT;
    }

    public void setPORT(String PORT) {
        this.PORT = PORT;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
