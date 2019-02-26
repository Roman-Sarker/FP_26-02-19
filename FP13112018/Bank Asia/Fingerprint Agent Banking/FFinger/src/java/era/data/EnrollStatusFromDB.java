/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.data;

import era.dbconnectivity.DBConnectionHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sultan
 */
public class EnrollStatusFromDB {

    public EnrollStatusFromDB() {
    }

    public CheckUserAndDevice getEnrollStatus(long cust_no) {
        CheckUserAndDevice checkUserAndDevice = new CheckUserAndDevice();
        try {
            DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
            Connection con = dbConnectionHandler.getConnection();

            Statement stmt = con.createStatement();
            System.out.println("SELECT CUST_NO FROM BIOTPL.FP_ENROLL WHERE CUST_NO = " + cust_no);
            ResultSet rs = stmt.executeQuery("SELECT STANDARD FROM BIOTPL.FP_ENROLL WHERE CUST_NO = " + cust_no);
            if (rs.next()) {
                checkUserAndDevice.setEnrollStatus(true);
                checkUserAndDevice.setStandard(rs.getString("STANDARD"));
            }
            dbConnectionHandler.releaseConnection(con);

        } catch (SQLException ex) {
            Logger.getLogger(EnrollStatusFromDB.class.getName()).log(Level.SEVERE, null, ex);
            checkUserAndDevice.setEnrollStatus(false);
            checkUserAndDevice.setStandard(null);
        }

        return checkUserAndDevice;
    }

}
