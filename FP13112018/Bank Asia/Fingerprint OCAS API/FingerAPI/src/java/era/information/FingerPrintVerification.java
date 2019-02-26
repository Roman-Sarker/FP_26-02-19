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
import com.futronictech.AnsiSDKLib;
import era.model.LoginModel; 

import java.awt.image.BufferedImage;
import java.io.File; 
import java.io.FileNotFoundException;
import java.io.IOException; 
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    int checkUserEnroll(String cust_number) {

        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection conn = dbConnectionHandler.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT STANDARD FROM BIOTPL.FP_ENROLL WHERE CUST_NO = " + cust_number);
            if (rs.next()) {
                String standard = rs.getString("STANDARD");
                if (standard.equals("S")) {
                    return 1;
                } else {
                    return 2;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

        DBConnectionHandler.releaseConnection(conn);
        return 3;
    }

    public LoginModel fingerDataVerfy(String cust_number, byte[] fingerImage, byte[] fingerTemplate) {
        int flag = checkUserEnroll(cust_number);
        System.out.println("flag is "+flag);
        if (flag == 1) {
            return fingerVerfyFromDB(cust_number, fingerImage);
        } else if (flag == 2) {
            return fingerVerfyFromTemplate(cust_number, fingerTemplate);
        } else if (flag == 3) {
            LoginModel loginModel = new LoginModel();
            loginModel.setErrorFlag("Y");
            loginModel.setErrorMessage("User not enrolled");
            return loginModel;
        }

        LoginModel loginModel = new LoginModel();
        loginModel.setErrorFlag("Y");
        loginModel.setErrorMessage("sql exception is got");
        return loginModel;
    }
    
    byte[] getByteDataFromBlob(Blob blob) {
        if (blob != null) {
            try {
                return blob.getBytes(1, (int) blob.length());
            } catch (SQLException ex) {
                Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public LoginModel fingerVerfyFromDB(String cust_number, byte[] fingerImage) {
        byte[][] data = new byte[10][];
        DBConnectionHandler dBConnectionHandler = new DBConnectionHandler();
        Connection con = dBConnectionHandler.getConnection();
        try {

            String selectSQL = "SELECT LINDEX,LTHUMB,RINDEX,RTHUMB,LMIDDLE,LRING,LLITTLE,"
                    + "RMIDDLE,RRING,RLITTLE from  Biotpl.FP_ENROLL where  CUST_NO = ?";
            System.out.println(selectSQL);
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setLong(1,Long.valueOf(cust_number ));

            ResultSet rs = preparedStatement.executeQuery();
            if (rs == null) {
                System.out.println("Resultset is null");
            }

            if (rs.next()) {
                byte[] LINDEX = getByteDataFromBlob(rs.getBlob("LINDEX"));
                byte[] LTHUMB = getByteDataFromBlob(rs.getBlob("LTHUMB"));
                byte[] RINDEX = getByteDataFromBlob(rs.getBlob("RINDEX"));
                byte[] RTHUMB = getByteDataFromBlob(rs.getBlob("RTHUMB"));

                byte[] LMIDDLE = getByteDataFromBlob(rs.getBlob("LMIDDLE"));
                byte[] LRING = getByteDataFromBlob(rs.getBlob("LRING"));
                byte[] LLITTLE = getByteDataFromBlob(rs.getBlob("LLITTLE"));

                byte[] RMIDDLE = getByteDataFromBlob(rs.getBlob("RMIDDLE"));
                byte[] RRING = getByteDataFromBlob(rs.getBlob("RRING"));
                byte[] RLITTLE = getByteDataFromBlob(rs.getBlob("RLITTLE"));

                data[0] = LINDEX;
                data[1] = LTHUMB;
                data[2] = RINDEX;
                data[3] = RTHUMB;

                data[4] = LMIDDLE;
                data[5] = LRING;
                data[6] = LLITTLE;

                data[7] = RMIDDLE;
                data[8] = RRING;
                data[9] = RLITTLE;
                data = getNonNullData(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if (data == null || data.length == 0) {
            LoginModel loginModel=new LoginModel();
            loginModel.setErrorFlag("Y");
            loginModel.setErrorMessage("Fingerprint data is not"
                    + " found for customer number " + cust_number);
            DBConnectionHandler.releaseConnection(con);
            return loginModel;
        } else {
            boolean flag_to_check_null_data = false;
            for (int i = 0; i < data.length; i++) {
                if (data[i] == null) {
                    flag_to_check_null_data = true;
                    break;
                }
            }
            if (flag_to_check_null_data) {
                LoginModel loginModel=new LoginModel();
                loginModel.setErrorFlag("Y");
                loginModel.setErrorMessage("Fingerprint data is null"
                        + " for customer number " + cust_number);
                DBConnectionHandler.releaseConnection(con);
                return loginModel;
            }
        }
        byte[] ansiData = getAnsiData(fingerImage);
        FingerMatching fingerMatching = new FingerMatching();
        boolean matchingFlag = fingerMatching.fingerPrintIndetify(cust_number,
                        ansiData , data);
        System.out.println("Customer number "+
                cust_number+" matching result "+matchingFlag+" fingerImage");
        
        if (matchingFlag) {
            LoginModel loginModel = new LoginModel();
            loginModel.setErrorFlag("N");
            loginModel.setErrorMessage(" ");
            DBConnectionHandler.releaseConnection(con);
            System.out.println("Fingerprint is matched");
            return loginModel;
        } else {
            LoginModel loginModel = new LoginModel();
            loginModel.setErrorFlag("Y");
            loginModel.setErrorMessage("Fingerprint does not match");
            System.out.println("Fingerprint is not matched");
            DBConnectionHandler.releaseConnection(con);
            return loginModel;
        }
         
    }
    
    public byte[] getAnsiData(byte[] imageData) {
        AnsiSDKLib ansi_lib = new AnsiSDKLib();
        if (!ansi_lib.OpenDevice()) {
            System.out.println("can not open device");
            return null;
        }
        int tmplSize = ansi_lib.GetMaxTemplateSize();
        byte mFinger = 1;
        byte[] templateBase = new byte[tmplSize];
        int[] realSize = new int[1];

        int width = 320;
        int height = 480;
       // width = ansi_lib.GetImageWidth();
       // height = ansi_lib.GetImageHeight();
        if (ansi_lib.CreateTemplateFromBuffer(
                mFinger, imageData, width,
                height, templateBase, realSize))
        {
            System.out.println("ansi data is got successful"+templateBase.length);
        } else {
            int lastError = ansi_lib.GetErrorCode();
            System.out.println("lastError is " + lastError);
            System.out.println("Create failed. Error: "+ansi_lib.GetErrorMessage());
            
        }
        
        byte[] writeTemplate = new byte[realSize[0]];
        System.arraycopy(templateBase, 0, writeTemplate, 0, realSize[0]);
                
        ansi_lib.CloseDevice();
        return writeTemplate;
        
     
    }
    
    byte[][] getNonNullData(byte[][] data) {
        byte[][] nonNullData;
        int index = 0;

        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                ++index;
            }
        }

        nonNullData = new byte[index][];
        index = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                nonNullData[index++] = data[i];
            }
        }
        return nonNullData;
    }

    public LoginModel fingerVerfyFromTemplate(String cust_number, byte[] fingerTemplate) {

        LoginModel model = new LoginModel();

        try {
            String templatename = cust_number;

            templatename = templatename.trim();
            Vector<DbRecord> users = DbRecord.loadTemplateFromDirectory(templatename, "1.tml");
            System.out.println("Hello result " + users.size());
            boolean resultFlag = matchingServer(users, fingerTemplate);
            System.out.println("Hello result" + resultFlag);

            if (resultFlag) {
                System.out.println("Sucess ");
                model = getResultBack("N", "success");
                return model;
            } else {
                Vector<DbRecord> users2 = DbRecord.loadTemplateFromDirectory(templatename, "2.tml");
                boolean resultFlag2 = matchingServer(users2, fingerTemplate);

                if (resultFlag2) {
                    System.out.println("Sucess ");
                    model = getResultBack("N", "success");
                    return model;
                } else {
                    System.out.println("Fail");
                    model.setErrorFlag("Y");
                    model.setErrorMessage("Verification Fail");
                }
            }
            System.out.println("Web Service Error Message " + model.getErrorMessage());
            return model;
        } catch (IOException ex) {
            Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fail");
            model.setErrorFlag("Y");
            model.setErrorMessage(ex.getMessage());
            System.out.println("Web Service Error Message " + model.getErrorMessage());
            return model;
        }
    }

    public LoginModel getResultBack(String resultFlag, String resultMessage) {
        LoginModel loginModel = new LoginModel();
        loginModel.setErrorFlag(resultFlag);
        loginModel.setErrorMessage(resultMessage);
        return loginModel;
    }
}
