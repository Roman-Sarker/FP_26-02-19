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

    public boolean getEnrollStatus(long cust_no,String user_type) {
        boolean enrollStatus = false;
        try {
            DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
            Connection con = dbConnectionHandler.getConnection();
            
            Statement stmt = con.createStatement();
            System.out.println("SELECT USER_ID FROM BIOTPL.FP_ENROLL WHERE USER_ID = "+cust_no);
            ResultSet rs = stmt.executeQuery("SELECT USER_ID FROM BIOTPL.FP_ENROLL WHERE USER_ID = "+cust_no);
            if(rs.next()) 
                enrollStatus = true;
            
            dbConnectionHandler.releaseConnection(con);
            
        } catch (SQLException ex) {
            Logger.getLogger(EnrollStatusFromDB.class.getName()).log(Level.SEVERE, null, ex); 
            enrollStatus = false;
        }
        
        return enrollStatus;
    }

}
