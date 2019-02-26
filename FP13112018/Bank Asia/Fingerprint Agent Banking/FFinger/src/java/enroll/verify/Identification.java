/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enroll.verify;

import era.dbconnectivity.DBConnectionHandler;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.Datum;
import oracle.sql.STRUCT;

/**
 *
 * @author sultan
 */
public class Identification {

    void getIdentificationData(long custNo) {
        
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection conn = dbConnectionHandler.getConnection();

        try {
            
            OracleCallableStatement cs = (OracleCallableStatement) conn.prepareCall(
                    "begin EMOB.PKG_FINGER.CUSTOMER_LIST_FOR_SUSPICIOUS_CHECK(?,?,?,?); end;"
                    );
            cs.setInt(1,15);
            cs.registerOutParameter(2, OracleTypes.ARRAY, "BIOTPL.PKG_FINGER.CUSTOMER_LIST");
            cs.registerOutParameter(3, OracleTypes.VARCHAR);
            cs.registerOutParameter(4, OracleTypes.VARCHAR);
            
            cs.execute();
            ARRAY array_to_pass = cs.getARRAY(1);
            Datum[] elements = array_to_pass.getOracleArray();

            for (int i = 0; i < elements.length; i++) {
                Object[] element = ((STRUCT) elements[i]).getAttributes();
                String CUST_NO = (String) element[0];
                String CUST_CODE = (String) element[1];
                String CUST_NAME = (String) element[2];
                
                System.out.println("cust_no is "+CUST_NO);
                System.out.println("CUST_CODE is "+CUST_CODE);
                System.out.println("CUST_NAME is "+CUST_NAME);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Identification.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnectionHandler.releaseConnection(conn);
    }

    public static void main(String[] arg) {
        Identification identification = new Identification();
        identification.getIdentificationData(0);
    }

}
