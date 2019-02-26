/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.information;

import era.enroll.verify.sdk.AppException;
import era.enroll.verify.sdk.DbRecord;
import era.dbconnectivity.DBConnectionHandler;
import com.futronic.SDKHelper.FTR_PROGRESS;
import com.futronic.SDKHelper.FtrIdentifyRecord;
import com.futronic.SDKHelper.FtrIdentifyResult;
import com.futronic.SDKHelper.FutronicException;
import com.futronic.SDKHelper.FutronicIdentification;
import com.futronic.SDKHelper.FutronicSdkBase;
import com.futronic.SDKHelper.IIdentificationCallBack;
import com.futronic.SDKHelper.IVerificationCallBack;
import era.model.LoginModel;
import era.model.LoginModel;
import era.model.Parameter_Fixed_Verify;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class FingerPrintVerification implements IVerificationCallBack, IIdentificationCallBack {

    private FutronicSdkBase m_Operation;

    private String m_DbDir;

    private Object m_OperationObj;

    public FingerPrintVerification() {
        try {
            m_DbDir = GetDatabaseDir();
        } catch (AppException ex) {
            Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<LoginModel> identify(byte[] bytes, String userName1, String serverType) throws FileNotFoundException, NullPointerException, AppException, IOException {

        List<LoginModel> list = new ArrayList<LoginModel>();
        LoginModel model = new LoginModel();

        if (!isUserExists(userName1)) {
            System.out.println("Users not found. Please, run enrollment process first.");

            model.setErrorFlag("N");
            model.setErrorMessage("Enroll Template Not Found, Please Enroll First");
            //System.out.println("model = " + model);
            list.add(model);

        } else {

            Vector<DbRecord> Users = DbRecord.ReadRecordsForWindows(m_DbDir + "/" + userName1, serverType);
            try {
                m_Operation = new FutronicIdentification();

                ((FutronicIdentification) m_Operation).setBaseTemplate(bytes);
                // m_Operation.setFARN(245);

                FtrIdentifyRecord[] rgRecords = new FtrIdentifyRecord[Users.size()];
                for (int i = 0; i < Users.size(); i++) {
                    rgRecords[i] = Users.get(i).getFtrIdentifyRecord();
                }

                FtrIdentifyResult result = new FtrIdentifyResult();

                int nResult = ((FutronicIdentification) m_Operation).Identification(rgRecords, result);
                System.out.println("nResutlt  = " + nResult);

                System.out.println("result = " + result.m_Index);

                if (nResult == FutronicSdkBase.RETCODE_OK) {
                    if (result.m_Index != -1) {
                        // System.out.println("Identification Completed");
                        String userName = Users.get(result.m_Index).getUserName();
                        System.out.println("userName = " + userName);
                        //LoginModel model = new LoginModel();
                        model.setErrorFlag("0");
                        model.setErrorMessage("Verify Successfull Match! Customer Id :=" + userName);
                        list.add(model);
                        return list;

                    } else {
                        //System.out.println("Not found");
                        // LoginModel model = new LoginModel();
                        model.setErrorFlag("0");
                        model.setErrorMessage("Verify Fail. Please Try Again");
                        list.add(model);
                    }
                }

                m_Operation = null;
                m_OperationObj = null;

            } catch (FutronicException e) {

                System.out.println("Can not start identification operation.\nError description: " + e.getMessage());

                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Exception aa = " + e.getMessage());
                e.printStackTrace();
            }
        }
        return list;

    }

    public void OnPutOn(FTR_PROGRESS f) {

    }

    public void OnTakeOff(FTR_PROGRESS f) {

    }

    public void UpdateScreenImage(BufferedImage bi) {

    }

    public boolean OnFakeSource(FTR_PROGRESS f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void OnVerificationComplete(boolean bln, int i, boolean bln1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void OnGetBaseTemplateComplete(boolean bSuccess, int nResult) {
        StringBuffer szMessage = new StringBuffer();
        if (bSuccess) {
            // txtMessage.setText("Starting identification...");
            System.out.println("Starting identification...");
            Vector<DbRecord> Users = (Vector<DbRecord>) m_OperationObj;
            FtrIdentifyRecord[] rgRecords = new FtrIdentifyRecord[Users.size()];
            for (int iUsers = 0; iUsers < Users.size(); iUsers++) {
                rgRecords[iUsers] = Users.get(iUsers).getFtrIdentifyRecord();
            }

            FtrIdentifyResult result = new FtrIdentifyResult();

            nResult = ((FutronicIdentification) m_Operation).Identification(rgRecords, result);
            if (nResult == FutronicSdkBase.RETCODE_OK) {
                szMessage.append("Identification process complete. User: ");
                if (result.m_Index != -1) {
                    szMessage.append(Users.get(result.m_Index).getUserName());
                } else {
                    szMessage.append("not found");
                }
            } else {
                szMessage.append("Identification failed.");
                szMessage.append(FutronicSdkBase.SdkRetCode2Message(nResult));
            }
            // SetIdentificationLimit( m_Operation.getIdentificationsLeft() );
        } else {
            szMessage.append("Can not retrieve base template.");
            szMessage.append("Error description: ");
            szMessage.append(FutronicSdkBase.SdkRetCode2Message(nResult));
        }
        //txtMessage.setText( szMessage.toString() );
        System.out.println(szMessage.toString());
        m_Operation = null;
        m_OperationObj = null;
        //EnableControls(true);
    }

    //Begin Database create 
    static public String GetDatabaseDir()
            throws AppException {
        // System.out.println("Database = ");
        String szDbDir;
        File f = new File("/u01/J2EE/Database");

        if (f.exists()) {
            if (!f.isDirectory()) {
                throw new AppException("Can not create database directory " + f.getAbsolutePath()
                        + ". File with the same name already exist.");
            }
        } else {
            try {
                f.mkdir();
            } catch (SecurityException e) {
                throw new AppException("Can not create database directory " + f.getAbsolutePath()
                        + ". Access denied.");
            }
        }
        szDbDir = f.getAbsolutePath();

        return szDbDir;
    }

    //Begin Database create 
    private boolean isUserExists(String szUserName) {
        File f = new File(m_DbDir, szUserName);
        return f.exists();
    }

    boolean matchingServer(Vector<DbRecord> users, byte[] bytes) {
        boolean resultFlag = false;

        try {
            m_Operation = new FutronicIdentification();
        //    int value = ((FutronicIdentification) m_Operation).getFARN();
        //    System.out.println("Before FARN Value " + value);

            ((FutronicIdentification) m_Operation).setFARN(245);
            int afterValue = ((FutronicIdentification) m_Operation).getFARN();
            System.out.println("After FARN Value " + afterValue);

            ((FutronicIdentification) m_Operation).setBaseTemplate(bytes);

            FtrIdentifyRecord[] rgRecords = new FtrIdentifyRecord[users.size()];
            for (int i = 0; i < users.size(); i++) {
                rgRecords[i] = users.get(i).getFtrIdentifyRecord();
            }
            FtrIdentifyResult result = new FtrIdentifyResult();
            int nResult = ((FutronicIdentification) m_Operation).Identification(rgRecords, result);

            int i = 0;
            if (nResult == FutronicSdkBase.RETCODE_OK) {
                System.out.println("result.m_Index = " + result.m_Index);
                if (result.m_Index != -1) {
                    System.out.println("Sucess");
                    resultFlag = true;
                } else {
                    resultFlag = false;
                }
            } else {
                System.out.println("Fail");
                resultFlag = false;
            }
            m_Operation = null;
            m_OperationObj = null;
        } catch (FutronicException e) {
            System.out.println("Can not start identification operation.\nError description: " + e.getMessage());
            return false;
        } catch (IllegalStateException | IllegalArgumentException | NullPointerException e) {
            System.out.println("Can not start identification operation.\nError description: " + e.getMessage());
            return false;
        }

        return resultFlag;
    }

    public List<LoginModel> fingerVerfyFromTemplate(LoginModel loginModel) {

        List<LoginModel> list = new ArrayList<LoginModel>();
        LoginModel model = new LoginModel();
        
        try {
            String templatename = loginModel.getCust_no();
            byte[] bytes = loginModel.getFingerBytes();

            templatename = templatename.trim();
            Vector<DbRecord> users = DbRecord.loadTemplateFromDirectory(templatename, "1.tml");
            System.out.println("Hello result "+users.size());
            boolean resultFlag = matchingServer(users, bytes);
            System.out.println("Hello result"+resultFlag);
            
            if (resultFlag) {
                System.out.println("Sucess ");
                list = procedure_call_for_login(loginModel);
                return list;
            } else {
                Vector<DbRecord> users2 = DbRecord.loadTemplateFromDirectory(templatename, "2.tml");
                boolean resultFlag2 = matchingServer(users2, bytes);

                if (resultFlag2) {
                    System.out.println("Sucess ");
                    list = procedure_call_for_login(loginModel);
                    return list;
                    //model.setErrorFlag("N");
                    //model.setErrorMessage("Verification Success");
                } else {
                    System.out.println("Fail");
                    model.setErrorFlag("Y");
                    model.setErrorMessage("Verification Fail");
                }
            }
            list.add(model);

            System.out.println("Web Service Error Message " + model.getErrorMessage());
            return list;
        } catch (IOException ex) {
            Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fail");
            model.setErrorFlag("Y");
            model.setErrorMessage(ex.getMessage());
            list.add(model);

            System.out.println("Web Service Error Message " + model.getErrorMessage());
            return list;
        }
    }

    public List<LoginModel> procedure_call_for_login(LoginModel loginModel) {
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
                stmt.setInt(2, Integer.parseInt(loginModel.getCust_no()));
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
                Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);

            } finally {
                DBConnectionHandler.releaseConnection(con);
            }
        }

        return list;
    }

}
