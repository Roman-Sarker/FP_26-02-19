/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.dbconnectivity;

import com.era.IPAddress.UpdateIPAdress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author E R A
 */
public class DBConnectionHandler {

    Connection con = null;
    String errorMessge;

    public DBConnectionHandler() {
        errorMessge = "";
    }

    public Connection getConnection() {
        Connection con = null;

        UpdateIPAdress updateIPAdress = new UpdateIPAdress();
        String IPAdress = updateIPAdress.getIPAddressFromDb();

        if (updateIPAdress.getErrorFlag()) {
            errorMessge = "Error in jsp page : " + IPAdress;
            return null;
        }

        String PORT = updateIPAdress.getPORT();
        String serviceName = updateIPAdress.getServiceName();
        String URL = "jdbc:oracle:thin:@" + IPAdress + ":" + PORT + "/" + serviceName + "";
        System.out.println("url: " + URL);
        String userName = updateIPAdress.getUserName();
        String password = updateIPAdress.getPassword();

        System.out.println("username: " + userName);
        System.out.println("password: " + password);

        try {//
            Class.forName("oracle.jdbc.driver.OracleDriver");//For oracle Connection
            // Class.forName("com.mysql.jdbc.Driver");//Mysql Connection
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(URL, userName, password);//database for xe      
        } catch (SQLException ex) {
          //  Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            errorMessge = ex.getMessage();
        }
        return con;
    }

    public boolean getConnectivityStatus() {
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection conn = dbConnectionHandler.getConnection();
        if (conn != null) {
            DBConnectionHandler.releaseConnection(conn);
            errorMessge = null;
            return true;
        } else {
            errorMessge = dbConnectionHandler.getErrorMessge();
            return false;
        }
    }

    public String getErrorMessge() {
        return errorMessge;
    }

    public static void releaseConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        System.out.println("args = " + new DBConnectionHandler().getConnection());
    }

}
