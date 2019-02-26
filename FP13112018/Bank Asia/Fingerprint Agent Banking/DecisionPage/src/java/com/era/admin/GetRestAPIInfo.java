package com.era.admin;
 
import java.io.FileInputStream; 
import java.io.IOException;
import java.io.InputStream; 
import java.util.Properties;

public class GetRestAPIInfo {

    public static RestAPIInfo getRestAPIInfo() {

        Properties prop = new Properties();
        InputStream inputStream = null;
        RestAPIInfo restAPIInfo = new RestAPIInfo();

        try {
            inputStream = new FileInputStream("api_info.properties");
            prop.load(inputStream); 
            restAPIInfo.ip = prop.getProperty("ip");
            restAPIInfo.portNo = prop.getProperty("portNo"); 
            inputStream.close();
            return restAPIInfo; 
        } catch (IOException e) {
            return null;
        } 
    }
}
