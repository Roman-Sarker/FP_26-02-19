/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enroll.verify;

import era.data.EnrollInformation;
import era.dbconnectivity.DBConnectionHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException; 
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sultan
 */
public class Enroll {

    public EnrollInformation enrollFinger(EnrollInformation enrollInformation) {

        
        System.out.println("System is running in its own way.");
        try {
            DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
            Connection con = dbConnectionHandler.getConnection();

            String sql = "INSERT INTO Biotpl.FP_ENROLL (USER_ID,CREATE_DATE,USER_TYPE,"
                    + "CREATE_BY,DEVICE_ID,LINDEX,LTHUMB,RINDEX,RTHUMB,LMIDDLE,LRING,LLITTLE,"
                    + "RMIDDLE,RRING,RLITTLE)"
                    + "VALUES (?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, Long.parseLong(enrollInformation.getUser_id())); 
            //pstmt.setInt(2,Integer.parseInt(enrollInformation.getBr_code()));
            pstmt.setString(2,enrollInformation.getUser_type());
            pstmt.setString(3,enrollInformation.getCreate_by());
            pstmt.setString(4,enrollInformation.getSerial());
            
            pstmt.setBytes(5, enrollInformation.getlIndex());
            pstmt.setBytes(6, enrollInformation.getlThumb());
            pstmt.setBytes(7, enrollInformation.getrIndex());
            pstmt.setBytes(8, enrollInformation.getrThumb()); 
            
            pstmt.setBytes(9, enrollInformation.getlMiddle());
            pstmt.setBytes(10, enrollInformation.getlRing());
            pstmt.setBytes(11, enrollInformation.getlLittle());
            
            pstmt.setBytes(12, enrollInformation.getrMiddle());
            pstmt.setBytes(13, enrollInformation.getrRing());
            pstmt.setBytes(14, enrollInformation.getrLittle());
            pstmt.executeUpdate();

            dbConnectionHandler.releaseConnection(con);
            enrollInformation.setErrorFlag("N");
            enrollInformation.setErrorMessage("");

            return enrollInformation;
        } catch (SQLException ex) {
            Logger.getLogger(Enroll.class.getName()).log(Level.SEVERE, null, ex);
            enrollInformation.setErrorFlag("Y");
            enrollInformation.setErrorMessage(ex.getMessage());
            return enrollInformation;
        }
    }

}
