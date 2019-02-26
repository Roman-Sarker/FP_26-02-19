/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.enroll.verify.sdk;

import era.information.FingerPrintVerification;
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
import java.awt.image.BufferedImage;
import java.io.File;
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
public class FingerAuthenticationProcess implements IVerificationCallBack, IIdentificationCallBack {
    
      private FutronicSdkBase m_Operation;

    private String m_DbDir;

    private Object m_OperationObj;

    public FingerAuthenticationProcess() {
        try {
            m_DbDir = GetDatabaseDir();
        } catch (AppException ex) {
            Logger.getLogger(FingerAuthenticationProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void OnVerificationComplete(boolean bln, int i, boolean bln1) {
      
    }

    @Override
    public void OnPutOn(FTR_PROGRESS f) {
        
    }

    @Override
    public void OnTakeOff(FTR_PROGRESS f) {
       
    }

    @Override
    public void UpdateScreenImage(BufferedImage bi) {
        
    }

    @Override
    public boolean OnFakeSource(FTR_PROGRESS f) {
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
    static private String GetDatabaseDir()
            throws AppException {
        // System.out.println("Database = ");
        String szDbDir;
//        File f = new File("/u01/BIOMETRIC/PROJECTS/WorkedExJava/Database");
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

    private void CreateFile(String szFileName)
            throws AppException {
        File f = new File(m_DbDir, szFileName);
        try {
            f.createNewFile();
            f.delete();
        } catch (IOException e) {
            throw new AppException("Can not create file " + szFileName + " in database.");
        } catch (SecurityException e) {
            throw new AppException("Can not create file " + szFileName + " in database. Access denied");
        }
    }
    public List<LoginModel> androidFingerVeryProcess(String tranType, String customerNumber, String userCode, String logID,
            String accountID, String amount, String sessionID,
            String custType, String enrollFrom, String deviceId, String deviceMnfc,
            byte[] bytes, String templatename) throws IOException {
        List<LoginModel> list = new ArrayList<LoginModel>();
        LoginModel model = new LoginModel();

        //Begin finer print match
        //  Vector<DbRecord> Users = DbRecord.ReadRecordsForWindows_2(m_DbDir + "/" + templatename, enrollFrom);
        Vector<DbRecord> Users = DbRecord.ReadRecordsForVerify(templatename, enrollFrom);

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

            int i = 0;

            if (nResult == FutronicSdkBase.RETCODE_OK) {

                if (result.m_Index != -1) {
                    System.out.println(nResult);

                    System.out.println("Identification Completed");
                    String userName = Users.get(result.m_Index).getUserName();
                    //System.out.println("userName = " + userName);

                    //Bgine DataBaseCheck
                    DBConnectionHandler dbConnectionHandler =new DBConnectionHandler();
                    Connection con = dbConnectionHandler.getConnection();
                    try {

                        CallableStatement stmt = con.prepareCall("{CALL BIOTPL.dfn_process_finger_data(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                        stmt.setString(1, tranType);
                        stmt.setString(2, customerNumber);
                        stmt.setString(3, userCode);
                        stmt.setString(4, logID);
                        stmt.setString(5, accountID);
                        stmt.setString(6, amount);
                        stmt.setString(7, sessionID);
                        stmt.setString(8, custType);
                        stmt.setString(9, enrollFrom);
                        stmt.setString(10, deviceId);
                        stmt.setString(11, deviceMnfc);
                         stmt.setString(12, deviceMnfc);
                        stmt.registerOutParameter(13, java.sql.Types.VARCHAR);
                        stmt.registerOutParameter(14, java.sql.Types.VARCHAR);
                        stmt.registerOutParameter(15, java.sql.Types.VARCHAR);
                        stmt.execute();

                        if ("N".equals(stmt.getString(13))) {
                            //f.delete();
                            // System.out.println("111 ");
                            model.setErrorFlag("N");
                            model.setErrorMessage("Finger Verification Is Successfull");
                            list.add(model);
                        } else {
                            model.setErrorFlag(stmt.getString(13));
                            model.setErrorMessage(stmt.getString(14));
                            list.add(model);
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);

                    } finally {
                        DBConnectionHandler.releaseConnection(con);
                    }
                } else {
                    model.setErrorFlag("Y");
                    model.setErrorMessage("Verify Fail. Please Try Again 2");
                    list.add(model);
                }
            } else {
                model.setErrorFlag("Y");
                model.setErrorMessage("Verify Fail. Please Try Again 1");
                list.add(model);
            }

            m_Operation = null;
            m_OperationObj = null;

        } catch (FutronicException e) {
            System.out.println("Can not start identification operation.\nError description: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // System.out.println("Exception aa = " + e.getMessage());
            e.printStackTrace();
        }
        //End finer print match
        return list;

    }
    
}
