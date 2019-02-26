/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.information;

import era.dbconnectivity.DBConnectionHandler;
import era.model.ENROLL_STATUS_DATA;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sultan Ahmed
 */
public class EnrollStatusInformation {
    
   public List<ENROLL_STATUS_DATA>  getEnrollStatus(ENROLL_STATUS_DATA loginstatus ){  
       List<ENROLL_STATUS_DATA> list = procedure_call_for_enroll(loginstatus);
       return list ; 
   }
   
   public List<ENROLL_STATUS_DATA> procedure_call_for_enroll(ENROLL_STATUS_DATA loginModel) {
        List<ENROLL_STATUS_DATA> list = new ArrayList<ENROLL_STATUS_DATA>();

        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        if (con == null) {
            ENROLL_STATUS_DATA model = new ENROLL_STATUS_DATA();
            model.setErrorFlag("Y");
            model.setErrorMessage(dbConnectionHandler.getErrorMessge());
            list.add(model);
            return list;
        } else {
            try {
                 
                CallableStatement stmt = con.prepareCall("{CALL BIOTPL.dfn_enroll_verification_status(?,?,?,?,?)}");
                System.out.println("cust no in loginModel is "+loginModel.getCustNo());
                stmt.setLong(1, loginModel.getCustNo()); 
                stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
                stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
                stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
                stmt.registerOutParameter(5, java.sql.Types.VARCHAR);
                stmt.execute();

                System.out.println("ex error Flag = " + stmt.getString(4));
                String errorFlag = stmt.getString(4);
                errorFlag = errorFlag.trim();
                loginModel.setErrorFlag(errorFlag); 
                
                if (errorFlag.equals("N")) {
                    loginModel.setFinger1(stmt.getString(2));
                    loginModel.setFinger2(stmt.getString(3));
                    loginModel.setErrorMessage("No Error Message");
                    System.out.println(" Error message is "+"No Error Message");
                } else {
                    String simErrorMessage = stmt.getString(5);
                     
                    System.out.println(" Error message is "+simErrorMessage);
                    loginModel.setErrorMessage(simErrorMessage);
                    System.out.println("ex error Message = " + stmt.getString(12));
                }
                list.add(loginModel);
            } catch (SQLException ex) {
                Logger.getLogger(EnrollStatusInformation.class.getName()).log(Level.SEVERE, null, ex);

            } finally {
                DBConnectionHandler.releaseConnection(con);
            }
        } 
        return list;
    }
    
}
