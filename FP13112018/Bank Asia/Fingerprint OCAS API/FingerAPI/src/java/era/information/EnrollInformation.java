/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.information;

import era.enroll.verify.sdk.DbRecord;
import era.dbconnectivity.DBConnectionHandler;
import era.model.LoginModel;
import era.model.Parameter_Fixed_Verify;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class EnrollInformation {

    public List<LoginModel> windowEnroll(LoginModel loginModel) {
        List<LoginModel> list = new ArrayList<LoginModel>();
        DbRecord dbRecord = new DbRecord(loginModel.getCust_no(), loginModel.getFingerBytes());
        boolean saveFlag = false;
        LoginModel model = new LoginModel();
        System.out.println("byte data length : " + loginModel.getFingerBytes().length);
        try {
            saveFlag = dbRecord.Save(loginModel.getCust_no());
            if (saveFlag) {
                System.out.println("Enroll is successfull");
                list = procedure_call_for_enroll(loginModel);
                return list;
            } else {
                System.out.println("ex error Flag = " + "Y");
                model.setErrorFlag("Y");
                model.setErrorMessage("File is not saved");
                System.out.println("ex error Message = " + "File is not saved");
            }
        } catch (NullPointerException ex) {
            Logger.getLogger(EnrollInformation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ex error Flag = " + "Y");
            model.setErrorFlag("Y");
            model.setErrorMessage(ex.getMessage());
            System.out.println(ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(EnrollInformation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ex error Flag = " + "Y");
            model.setErrorFlag("Y");
            model.setErrorMessage(ex.getMessage());
            System.out.println(ex);
        } catch (IOException ex) {
            Logger.getLogger(EnrollInformation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ex error Flag = " + "Y");
            model.setErrorFlag("Y");
            model.setErrorMessage(ex.getMessage());
            System.out.println(ex);
        }
        list.add(model);
        return list;
    }

    public List<LoginModel> procedure_call_for_enroll(LoginModel loginModel) {
        List<LoginModel> list = new ArrayList<LoginModel>();

        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        if (con == null) {
            LoginModel model = new LoginModel();
            model.setErrorFlag("Y");
            model.setErrorMessage(dbConnectionHandler.getErrorMessge());
            list.add(model);
            return list;
        } else {
            try {
                Parameter_Fixed_Verify param_fixed_verify = new Parameter_Fixed_Verify();
                String opr_type = loginModel.getpOperationType();
                //    opr_type = param_fixed_verify.getParameter(opr_type, true);
                System.out.println("OPR_TYPE is " + opr_type);

                String cust_type = loginModel.getpCustType();
                cust_type = param_fixed_verify.getParameter(cust_type, true);
                System.out.println("cust_type is " + cust_type);

                String appUser = loginModel.getApp_user();
                appUser = param_fixed_verify.getParameter(appUser, false);
                System.out.println("appUser is " + appUser);

                String pLogiD = loginModel.getpLogId();
                //  pLogiD = param_fixed_verify.getParameter(pLogiD, true);
                System.out.println("pLogiD is " + pLogiD);

                String enrollFrom = loginModel.getpEnrolFrom();
                //   enrollFrom = param_fixed_verify.getParameter(enrollFrom, true);
                System.out.println("enrollFrom is " + enrollFrom);

                CallableStatement stmt = con.prepareCall("{CALL BIOTPL.dfn_process_finger_data(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

                stmt.setString(1, opr_type);
                stmt.setLong(2, Long.parseLong(loginModel.getCust_no()));
                stmt.setString(3, appUser);
                stmt.setString(4, pLogiD);
                stmt.setString(5, loginModel.getpAcId());
                stmt.setString(6, loginModel.getpAmount());
                stmt.setString(7, loginModel.getpSessionId());
                stmt.setString(8, cust_type);
                stmt.setString(9, enrollFrom);
                stmt.setString(10, loginModel.getpDeviceId());
                stmt.registerOutParameter(11, java.sql.Types.VARCHAR);
                stmt.registerOutParameter(12, java.sql.Types.VARCHAR);
                stmt.registerOutParameter(13, java.sql.Types.VARCHAR);
                stmt.registerOutParameter(14, java.sql.Types.VARCHAR);
                stmt.registerOutParameter(15, java.sql.Types.VARCHAR);
                stmt.execute();

                LoginModel model = new LoginModel();
                System.out.println("ex error Flag = " + stmt.getString(11));
                String errorFlag = stmt.getString(13);
                errorFlag = errorFlag.trim();
                model.setErrorFlag(errorFlag);
                System.out.println("Flag" + errorFlag);
                if (errorFlag.equals("N")) {
                    String finger1 = stmt.getString(11);
                    String finger2 = stmt.getString(12);
                    model.setFinger1(finger1);
                    model.setFinger2(finger2);
                    model.setErrorMessage("No Error Message");
                    System.out.println(" Error message is " + "No Error Message");
                } else {
                    String simErrorMessage = stmt.getString(14);
                    String oracleErrorMessage = stmt.getString(15);

                    System.out.println(" Error message is " + simErrorMessage);
                    if (simErrorMessage != null) {
                        model.setErrorMessage(simErrorMessage);
                    } else {
                        model.setErrorMessage(oracleErrorMessage);
                    }
                    System.out.println("ex error Message = " + stmt.getString(12));
                }
                list.add(model);
            } catch (SQLException ex) {
                Logger.getLogger(EnrollInformation.class.getName()).log(Level.SEVERE, null, ex);

            } finally {
                DBConnectionHandler.releaseConnection(con);
            }
        }

        return list;
    }

    //public List<LoginModel> deleteFinger(String custNo) {
    public List<LoginModel> deleteFinger(LoginModel model) {
        List<LoginModel> list = new ArrayList<LoginModel>();
        DbRecord dbRecord = new DbRecord();

        boolean deleteFlag = false; 

        try {
            deleteFlag = dbRecord.deleteFinger(model.getCust_no());//custNo);
            if (deleteFlag) {
                System.out.println("ex error Flag = " + "N");
                list = procedure_call_for_enroll(model);
                return list;
                //model.setErrorFlag("N");
               // model.setErrorMessage("Fingerprint Cancellation is sucessfull");
               // System.out.println("ex error Message = " + "No Error Message");
            } else {
                System.out.println("ex error Flag = " + "Y");
                model.setErrorFlag("Y");
                model.setErrorMessage("File is not deleted");
                System.out.println("ex error Message = " + "File is not deleted");
            }
        } catch (NullPointerException ex) {
            Logger.getLogger(EnrollInformation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ex error Flag = " + "Y");
            model.setErrorFlag("Y");
            model.setErrorMessage(ex.getMessage());
            System.out.println(ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(EnrollInformation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ex error Flag = " + "Y");
            model.setErrorFlag("Y");
            model.setErrorMessage(ex.getMessage());
            System.out.println(ex);
        }

        list.add(model);
        return list;
    }

}
