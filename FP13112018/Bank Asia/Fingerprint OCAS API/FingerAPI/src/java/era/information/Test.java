/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.information;

import era.dbconnectivity.DBConnectionHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sun.security.krb5.Confounder.bytes;

/**
 *
 * @author root
 */
public class Test {

    public static void main(String[] args) {
        FingerPrintVerification test = new FingerPrintVerification();

        Test t = new Test();
        byte[] bytes = t.LoadBEnrollTemplate2();
        System.out.println("bytes = " + bytes.length);

         
     /*  try {
             test.fingerVerfyFromTemplate("2491", bytes);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }  */

        
    }

    private byte[] LoadBEnrollTemplate2() {
        byte[] baseTemplate = null;
        FileInputStream fs = null;
        File f = null;
        long nFileSize;

        f = new File("/u01/text.txt");
        if (!f.exists() || !f.canRead()) {
            return null;
        }
        try {
            nFileSize = f.length();
            fs = new FileInputStream(f);
            baseTemplate = new byte[(int) nFileSize];
            // Toast.makeText(getApplicationContext(), ""+nFileSize,
            // Toast.LENGTH_LONG).show();
            fs.read(baseTemplate);
            fs.close();
        } catch (SecurityException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return baseTemplate;
    }

    void loadTemplateFromDatase(String templatename) {
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        
        PreparedStatement ps;
        int total = 0;
        String sql = "SELECT * FROM FP_BIOMETRIC_DATA WHERE CUST_NO=? and serial_no=2";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "989");
            ResultSet rs = ps.executeQuery();
            System.out.println("sql = " + sql);
            if (rs.next()) {
                byte[] bdata = null;

                Blob blob = rs.getBlob("FINGER_DATA");
                String userName = rs.getString("CUST_NO");
                String picture = "";
                if (blob != null) {
                    bdata = blob.getBytes(1, (int) blob.length());
                }
                //System.out.println("bdata = " + bdata.length);
            }
        } catch (SQLException ex) {

        }

    }

}
