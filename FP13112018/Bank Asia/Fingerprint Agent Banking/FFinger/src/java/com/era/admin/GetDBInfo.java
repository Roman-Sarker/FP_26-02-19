package com.era.admin;
 
import java.io.FileInputStream; 
import java.io.IOException;
import java.io.InputStream; 
import java.util.Properties;

public class GetDBInfo {

    public static DBInfo getDbInfo() {

        Properties prop = new Properties();
        InputStream inputStream = null;
        DBInfo dbInfo = new DBInfo();

        try {
            inputStream = new FileInputStream("dbInfo.properties");
            prop.load(inputStream); 
            dbInfo.ip = prop.getProperty("ip");
            dbInfo.portNo = prop.getProperty("portNo");
            dbInfo.serviceName = prop.getProperty("serviceName");
            dbInfo.userName = prop.getProperty("userName");
            dbInfo.password = prop.getProperty("password");
            inputStream.close();
            return dbInfo; 
        } catch (IOException e) {
            return null;
        } 
    }
}
