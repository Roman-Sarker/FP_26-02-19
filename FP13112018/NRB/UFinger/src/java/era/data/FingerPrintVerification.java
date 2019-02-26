/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.data;
  
import enroll.verify.FingerMatching;
import era.dbconnectivity.DBConnectionHandler;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class FingerPrintVerification {

    String errorMessage = "";

    public FingerPrintVerification() {

    }
    
    public List<LoginModel> deleteFinger(LoginModel loginModel)
    {
        return procedure_call_for_login(loginModel);
    }

    byte[] getByteDataFromBlob(Blob blob) {
        if(blob!=null){
            try {
                return blob.getBytes(1, (int) blob.length());
            } catch (SQLException ex) {
                Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        return null;
    }

    public List<LoginModel> fingerVerfyFromTemplate(LoginModel loginModel) {

        List<LoginModel> list = new ArrayList<LoginModel>();
        LoginModel model = new LoginModel();

        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();

        byte[][] data = new byte[10][];
        try {

            String selectSQL = "SELECT LINDEX,LTHUMB,RINDEX,RTHUMB,LMIDDLE,LRING,LLITTLE,"
                    + "RMIDDLE,RRING,RLITTLE from  Biotpl.FP_ENROLL where  USER_ID = ?";
            System.out.println(selectSQL);
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setLong(1, Long.parseLong(loginModel.getUser_id()));
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs == null) {
                System.out.println("Resultset is null");
            }

            if (rs.next()) {
                System.out.println("Yes we are done");
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

        FingerMatching fingerMatching = new FingerMatching();
        boolean matchingFlag = fingerMatching.fingerPrintIndetify(
                loginModel.getUser_id() ,loginModel.getFingerBytes(), data);

        if (matchingFlag) {
            loginModel.setErrorFlag("N");
            loginModel.setErrorMessage(" ");
            procedure_call_for_login(loginModel);
            System.out.println("Fingerprint is matched");
        } else {
            loginModel.setErrorFlag("Y");
            loginModel.setErrorMessage("Fingerprint does not match");
            System.out.println("Fingerprint is not matched");
        }

        DBConnectionHandler.releaseConnection(con);
        list.add(loginModel);
        return list;

    }
    
    byte[][] getNonNullData(byte[][] data)
    {
        byte[][] nonNullData;
        int index = 0 ;
        
        for(int i=0;i<data.length;i++)
            if(data[i] != null)
                ++index;
        
        nonNullData = new byte[index][];
        index = 0;
         for(int i=0;i<data.length;i++)
            if(data[i] != null)
                nonNullData[index++]=data[i];
        return nonNullData;
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
                System.out.println("cust_type is " + cust_type);

                String create_by = loginModel.getCreate_by(); 
                System.out.println("appUser is " + create_by);

                String pLogiD = loginModel.getpLogId();
                //  pLogiD = param_fixed_verify.getParameter(pLogiD, true);
                System.out.println("pLogiD is " + pLogiD); 

                CallableStatement stmt = con.prepareCall("{CALL BIOTPL.dfn_process_finger_data(?,?,?,?,?,?,?,?,?,?,?,?)}"); 
                stmt.setString(1, opr_type);
                stmt.setInt(2, Integer.parseInt(loginModel.getUser_id()));
                stmt.setString(3, create_by);
                stmt.setString(4, pLogiD); 
                stmt.setInt(5, Integer.parseInt(loginModel.getpAcId())); 
                stmt.setString(6, loginModel.getpAmount());
                stmt.setString(7, loginModel.getpSessionId());
                stmt.setString(8, cust_type); 
                stmt.setString(9, loginModel.getpDeviceId());
                stmt.registerOutParameter(10, java.sql.Types.VARCHAR);
                stmt.registerOutParameter(11, java.sql.Types.VARCHAR);
                stmt.registerOutParameter(12, java.sql.Types.VARCHAR);
                stmt.execute(); 
                
                LoginModel model = new LoginModel();
                System.out.println("ex error Flag = " + stmt.getString(11));
                String errorFlag = stmt.getString(10);
                errorFlag = errorFlag.trim();
                String errorMessage = stmt.getString(11);
                model.setErrorFlag(errorFlag);
                model.setErrorMessage(errorMessage);
                list.add(model);
                
                dbConnectionHandler.releaseConnection(con);
            } catch (SQLException ex) {
                Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);

            }  
            
            
        }

        return list;
    }

    public static void main(String[] arg) {
        FingerPrintVerification fingerPrintVerification = new FingerPrintVerification();

        LoginModel loginModel = new LoginModel();
        loginModel.setUser_id("291");

        String str = "Rk1SACAyMAAA8gAKADUAAAEsAZAAxQDFAQAAAEcjgHYAE1QAQFsAFLIAgIYAGaoAQLgAIUwAQKQAKk8AgEoAK1sAgEcASgQAgHQAS1QAgD8AZwIAQC8AdWMAgDMAfQcAgHAAslEAQFYAwrAAgG8Ay1AAgCAA1yUAQEAA138AQHQA2U0AQFkA2wIAQFgA4HwAQKsA4D8AQF0A7YwAQDcA74sAQDIA8icAQFgA9ScAQEUA9y8AgDABAY8AQNEBCYsAgJEBDkYAQDQBEjYAgEABHTYAQKABIkwAQIUBJaYAgIIBLUwAQJwBL1EAQIoBOVIAAAA=";
        byte[] fingerBytes = null;//javax.xml.bind.DatatypeConverter.parseBase64Binary(str);
        fingerBytes = Base64.getDecoder().decode(str);
        loginModel.setFingerBytes(fingerBytes);

        //   JSGFPLib fplib = new JSGFPLib();
        //   fplib.Init(SGFDxDeviceName.SG_DEV_AUTO);
        //   fplib.SetTemplateFormat(SGFDxTemplateFormat.TEMPLATE_FORMAT_ANSI378);
        fingerPrintVerification.fingerVerfyFromTemplate(loginModel);
        //  boolean flag = fingerPrintVerification.fingerprintVerify(fplib, fingerBytes, fingerBytes);
        // System.out.println("result " + flag);
        //   fplib.Close();

        //fingerPrintVerification.fingerVerfyFromTemplate(loginModel);
    }
}
