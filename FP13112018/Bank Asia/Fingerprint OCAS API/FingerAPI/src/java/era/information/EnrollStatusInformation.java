/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.information;

import era.dbconnectivity.DBConnectionHandler;
import era.model.ENROLL_STATUS_DATA; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sultan Ahmed
 */
public class EnrollStatusInformation {

    public ENROLL_STATUS_DATA getEnrollStatus(String custNo) {
        return procedure_call_for_enroll(custNo);
    }

    public ENROLL_STATUS_DATA procedure_call_for_enroll(String custNo) {
        ENROLL_STATUS_DATA enrollData = new ENROLL_STATUS_DATA();
        enrollData.setCustNo(custNo); 
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        if (con == null) {
            enrollData.setErrorFlag("Y");
            enrollData.setErrorMessage(dbConnectionHandler.getErrorMessge());
            return enrollData;
        } else {
            try {
                Statement stmt = con.createStatement();
                System.out.println("SELECT CUST_NO FROM BIOTPL.FP_ENROLL WHERE CUST_NO = " + enrollData.getCustNo());
                ResultSet rs = stmt.executeQuery("SELECT FINGER1,FINGER2,STANDARD FROM BIOTPL.FP_ENROLL WHERE CUST_NO = " + enrollData.getCustNo());
                if (rs.next()) {
                    enrollData.setEnrollStatus("Y");
                    enrollData.setErrorFlag("N");
                    enrollData.setStandard(rs.getString("STANDARD"));
                    enrollData.setFinger1(rs.getString("FINGER1"));
                    enrollData.setFinger2(rs.getString("FINGER2"));
                } else {
                    enrollData.setEnrollStatus("N");
                    enrollData.setStandard("");
                    enrollData.setErrorFlag("N");
                    enrollData.setErrorMessage("");
                    enrollData.setFinger1("N");
                    enrollData.setFinger2("N");
                }
            } catch (SQLException ex) {
                Logger.getLogger(EnrollStatusInformation.class.getName()).log(Level.SEVERE, null, ex);
                enrollData.setEnrollStatus("N");
                enrollData.setStandard(""); 
                enrollData.setErrorFlag("Y");
                enrollData.setErrorMessage(ex.getMessage());
            } finally {
                DBConnectionHandler.releaseConnection(con);
            }
        }
        return enrollData;
    }

}
