/*
 * DbRecord.java
 */
package era.enroll.verify.sdk;

import era.information.FingerPrintVerification;
import era.dbconnectivity.DBConnectionHandler;
import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.util.Vector;
import com.futronic.SDKHelper.*;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 * This class represent a user fingerprint database record.
 *
 * @author Shustikov
 */
public class DbRecord {

    /**
     * User name
     */
    Vector<DbRecord> Users;
    private static String m_UserName;
    private static final int TEMPLATE_SIZE = 20000;

    /**
     * User unique key
     */
    private static byte[] m_Key;

    /**
     * Finger template.
     */
    private static byte[] m_Template;

    public String getM_UserName() {
        return m_UserName;
    }

    public byte[] getM_Template() {
        return m_Template;
    }

    /**
     * Creates a new instance of DbRecord class.
     */
    public DbRecord() {
        m_UserName = "";
        // Generate user's unique identifier
        m_Key = new byte[16];
        java.util.UUID guid = java.util.UUID.randomUUID();
        long itemHigh = guid.getMostSignificantBits();
        long itemLow = guid.getLeastSignificantBits();
        for (int i = 7; i >= 0; i--) {
            m_Key[i] = (byte) (itemHigh & 0xFF);
            itemHigh >>>= 8;
            m_Key[8 + i] = (byte) (itemLow & 0xFF);
            itemLow >>>= 8;
        }
        m_Template = null;
    }

    public DbRecord(String m_UserName, byte[] template) {
        try {
            this.m_UserName = m_UserName;
            // Generate user's unique identifier
            System.out.println("key length : " + m_UserName.getBytes("UTF-8").length);
            m_Key = new byte[16];
            byte[] a_Key = m_UserName.getBytes("UTF-8");
            System.arraycopy(a_Key, 0, m_Key, 0, a_Key.length);
            m_Template = template;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initialize a new instance of DbRecord class from the file.
     *
     * @param szFileName a file name with previous saved passport.
     */
    public DbRecord(String szFileName)
            throws FileNotFoundException, NullPointerException, AppException {
        // Load( szFileName );
        LoadForWindows(szFileName);
    }

    public DbRecord(byte[] template, byte[] key) {
        m_Template = template;
        m_Key = key;
    }

    public DbRecord(String szFileName, String serverType)
            throws FileNotFoundException, NullPointerException, AppException, IOException {
        //  Load( szFileName );

        if ("W".equals(serverType)) {
            LoadForWindows(szFileName);
        } else {
            Load(szFileName);

        }
    }

    /**
     * Load user's information from file.
     *
     * @param szFileName a file name with previous saved passport.
     *
     * @exception NullPointerException szFileName parameter has null reference.
     * @exception InvalidObjectException the file has invalid structure.
     * @exception FileNotFoundException the file not found or access denied.
     */
    private void Load(String szFileName)
            throws FileNotFoundException, NullPointerException, AppException {
        FileInputStream fs = null;
        File f = null;
        long nFileSize;

        f = new File(szFileName);
        if (!f.exists() || !f.canRead()) {
            throw new FileNotFoundException("File " + f.getPath());
        }

        try {
            nFileSize = f.length();
            fs = new FileInputStream(f);

            CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
            byte[] Data = null;

            // Read user name length and user name in UTF8
            if (nFileSize < 2) {
                throw new AppException("Bad file " + f.getPath());
            }
            int nLength = (fs.read() << 8) | fs.read();
            nFileSize -= 2;
            if (nFileSize < nLength) {
                throw new AppException("Bad file " + f.getPath());
            }
            nFileSize -= nLength;
            Data = new byte[nLength];
            fs.read(Data);
            m_UserName = utf8Decoder.decode(ByteBuffer.wrap(Data)).toString();

            // Read user unique ID
            if (nFileSize < 16) {
                throw new AppException("Bad file " + f.getPath());
            }
            nFileSize -= 16;
            m_Key = new byte[16];
            fs.read(m_Key);

            // Read template length and template data
            if (nFileSize < 2) {
                throw new AppException("Bad file " + f.getPath());
            }
            nLength = (fs.read() << 8) | fs.read();
            nFileSize -= 2;
            if (nFileSize != nLength) {
                throw new AppException("Bad file " + f.getPath());
            }
            m_Template = new byte[nLength];
            fs.read(m_Template);
            fs.close();
        } catch (SecurityException e) {
            if (f == null) {
                throw new AppException("Denies read access to the file " + f.getPath());
            } else {
                throw new AppException("Denies read access to the file " + szFileName);
            }
        } catch (IOException e) {
            if (f == null) {
                throw new AppException("Bad file " + f.getPath());
            } else {
                throw new AppException("Bad file " + szFileName);
            }
        }
    }
     

    static public Vector< DbRecord> loadTemplateFromDirectory(String templatename,String extension) throws IOException {
        Vector<DbRecord> users = new Vector<DbRecord>(10, 10);
        
        
        Long customerNo = Long.parseLong(templatename);
        Long folderNo = customerNo/20000 ;  
        
        try {
            String szFileName = FingerPrintVerification.GetDatabaseDir() + "//" +folderNo+"//"+ templatename+extension;
            File file1 = new File(szFileName);
         
            if (file1.exists()) {
                DbRecord dbRecord = new DbRecord(file1.getAbsolutePath());
                users.add(dbRecord);
            }
          
            
            /* 
            String szFileName = FingerPrintVerification.GetDatabaseDir() + "//" + fileNo + "//" + templatename;
            int fileno = 1;
            File f = new File(szFileName +""+ fileno + ".tml");
            DbRecord dbRecord = null;

            if (f.exists()) {
                dbRecord = new DbRecord(f.getAbsolutePath());
                users.add(dbRecord);
            }
            
            fileno = 2;
            f = new File(szFileName + fileno + ".tml");
            if (f.exists()) {
                dbRecord = new DbRecord(f.getAbsolutePath());
                users.add(dbRecord);
            }

             while (f.exists()) {
                DbRecord dbRecord = new DbRecord(f.getAbsolutePath());
                users.add(dbRecord);
                fileno++;
                f = new File(szFileName + fileno + ".tml");
            } */
        } catch (AppException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }
    
    static public Vector< DbRecord> loadTemplateFromDirectoryOld(String templatename,String extension) throws IOException {
        Vector<DbRecord> users = new Vector<DbRecord>(10, 10);
        
        int customerNo = Integer.parseInt(templatename);
        int folderNo = customerNo/20000 ;  
        
        try {
            String szFileName = FingerPrintVerification.GetDatabaseDir() + "//" + templatename;
            File file1 = new File(szFileName);
         
            if (file1.exists()) {
                DbRecord dbRecord = new DbRecord(file1.getAbsolutePath());
                users.add(dbRecord);
            }
          
            
            /* 
            String szFileName = FingerPrintVerification.GetDatabaseDir() + "//" + fileNo + "//" + templatename;
            int fileno = 1;
            File f = new File(szFileName +""+ fileno + ".tml");
            DbRecord dbRecord = null;

            if (f.exists()) {
                dbRecord = new DbRecord(f.getAbsolutePath());
                users.add(dbRecord);
            }
            
            fileno = 2;
            f = new File(szFileName + fileno + ".tml");
            if (f.exists()) {
                dbRecord = new DbRecord(f.getAbsolutePath());
                users.add(dbRecord);
            }

             while (f.exists()) {
                DbRecord dbRecord = new DbRecord(f.getAbsolutePath());
                users.add(dbRecord);
                fileno++;
                f = new File(szFileName + fileno + ".tml");
            } */
        } catch (AppException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    private void LoadForWindowsTemplateFromDatabase(String szFileName)
            throws FileNotFoundException, NullPointerException, AppException {
        FileInputStream fs = null;
        File f = null;
        long nFileSize;

        f = new File(szFileName);
        if (!f.exists() || !f.canRead()) {
            throw new FileNotFoundException("File " + f.getPath());
        }

        try {
            nFileSize = f.length();
            fs = new FileInputStream(f);
            CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
            byte[] DataSize = new byte[4];
            fs.read(DataSize);
            m_Key = new byte[16];
            fs.read(m_Key);
            m_UserName = utf8Decoder.decode(ByteBuffer.wrap(m_Key)).toString();
            m_Template = new byte[(int) nFileSize - 20];
            fs.read(m_Template);
            fs.close();
        } catch (SecurityException e) {
            if (f == null) {
                throw new AppException("Denies read access to the file " + f.getPath());
            } else {
                throw new AppException("Denies read access to the file " + szFileName);
            }
        } catch (IOException e) {
            if (f == null) {
                throw new AppException("Bad file " + f.getPath());
            } else {
                throw new AppException("Bad file " + szFileName);
            }
        }
    }

    private void LoadForWindows(String szFileName)
            throws FileNotFoundException, NullPointerException, AppException {

        System.out.println("szFileName in load databse = " + szFileName);
        FileInputStream fs = null;
        File f = null;
        long nFileSize;

        f = new File(szFileName);
        if (!f.exists() || !f.canRead()) {
            throw new FileNotFoundException("File " + f.getPath());
        }

        try {
            nFileSize = f.length();
            fs = new FileInputStream(f);
            CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
            byte[] DataSize = new byte[4];
            fs.read(DataSize);
            m_Key = new byte[16];
            fs.read(m_Key);
            m_UserName = utf8Decoder.decode(ByteBuffer.wrap(m_Key)).toString();
            m_Template = new byte[(int) nFileSize - 20];
            fs.read(m_Template);
            fs.close();
        } catch (SecurityException e) {
            System.out.println("Denies read access to the file " + e.getMessage());
            if (f == null) {
                throw new AppException("Denies read access to the file " + f.getPath());
            } else {
                throw new AppException("Denies read access to the file " + szFileName);
            }
        } catch (IOException e) {
            System.out.println("Denies read access to the file " + e.getMessage());
            if (f == null) {
                throw new AppException("Bad file " + f.getPath());
            } else {
                throw new AppException("Bad file " + szFileName);
            }
        }
    }

    private void LoadForWindows_2___(String szFileName)
            throws FileNotFoundException, NullPointerException, AppException {
        /*
        // System.out.println("szFileName in load databse = " + szFileName );
       // FileInputStream fs = null;
        File f = null;
        long nFileSize;

//        f = new File(szFileName);
//        if (!f.exists() || !f.canRead()) {
//            throw new FileNotFoundException("File " + f.getPath());
//        }
        byte[] bioData = getTemplateFromDataBase(szFileName);
        System.out.println("bioData = " + bioData.length);

        try {
           // nFileSize = f.length();
            nFileSize = bioData.length;
            //fs = new FileInputStream(f);
            CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
            byte[] DataSize = new byte[4];
            try {
                // fs.read(DataSize);
                 m_Key = new byte[16];
                //fs.read(m_Key);
              
                m_UserName = utf8Decoder.decode(ByteBuffer.wrap(m_Key)).toString();
              
            } catch (CharacterCodingException ex) {
                Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
            }
            m_Template = new byte[(int) nFileSize - 20];
            System.out.println("m_Template = " + m_Template.length);
            //fs.read(m_Template);
            //fs.close();
        } catch (SecurityException e) {
            if (f == null) {
                throw new AppException("Denies read access to the file " + f.getPath());
            } else {
                throw new AppException("Denies read access to the file " + szFileName);
            }
        }
         */

    }

    Vector< DbRecord> LoadForWindows_2(String customerNo) throws IOException {
        Users = new Vector<DbRecord>(10, 10);
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        PreparedStatement ps;
        int total = 0;
        String sql = "SELECT * FROM FP_BIOMETRIC_DATA WHERE CUST_NO=?";
        try {
            // ps = con.prepareStatement("select USERNAME, FINGERDATA,KEY from FINGERDATA");
            ps = con.prepareStatement(sql);
            ps.setString(1, customerNo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("CUST_NO");
                //System.out.println("userName = " + userName);
                //  String key = userName;
                oracle.sql.CLOB fingerData = (oracle.sql.CLOB) rs.getClob("BIO_DATA");
                // System.out.println("fingerData = " + fingerData.length());
                InputStream inStream = fingerData.getAsciiStream();
                StringWriter fingerString = new StringWriter();
                //IOUtils.copy(inStream, fingerString); 
                //  IOUtils.copy(inStream, fingerString);

                // ++total;
                //   DbRecord dbRecord = new DbRecord(m_UserName,m_Template);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        // System.out.println(" Read records : "+total);
        return Users;
    }

    /*
    public DbRecord(String userName, String key, String fingerData) {
        m_UserName = userName;
        // System.out.println("m_UserName  DbRecord= " + m_UserName);
        //byte[] m_key = Base64.decode(key);
        // m_Key = Base64.decode(key);
        byte[] template = Base64.decode(fingerData);
        // System.out.println("fingerData = " + fingerData.length);
        // m_Template = fingerData;
        m_Template = template;
        System.out.println("Db_Record m_Template = " + m_Template.length);
    }
     */
    /**
     * Save user's information to file.
     *
     * @param szFileName a file name to save.
     *
     * @return true if passport successfully saved to file, otherwise false.
     *
     * @exception NullPointerException szFileName parameter has null reference.
     * @exception IllegalStateException some parameters are not set.
     * @exception IOException can not create file or can not write data into
     * file.
     */
    public boolean Save(String szFileNameM)
            throws NullPointerException, IllegalStateException, IOException {

        FileOutputStream fs = null;
        File f = null;
        boolean bRetcode = false;
        boolean bExist;
        String szFileName = null;

        if (m_Template == null || m_UserName == null || m_UserName.length() == 0) {
            throw new IllegalStateException();
        }
        try {
            Long customeNo = Long.parseLong(szFileNameM);
            Long fileNo = customeNo / TEMPLATE_SIZE;
            szFileName = FingerPrintVerification.GetDatabaseDir() + "//" + fileNo;
            f = new File(szFileName);
            if (!f.exists()) {
                boolean sucessflag = f.mkdir();
                System.out.println("successflag " + sucessflag);
            }
            szFileName = FingerPrintVerification.GetDatabaseDir() + "//" + fileNo + "//" + szFileNameM;
        } catch (AppException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            int fileno = 1;
            f = new File(szFileName + fileno + ".tml");
            if (f.exists()) {
                fileno++;
                f = new File(szFileName + fileno + ".tml");
            }
            /* while (f.exists()) {
                fileno++;
                f = new File(szFileName + fileno + ".tml");
            } */
            System.out.println("File is saved to " + f.getAbsolutePath());
            fs = new FileOutputStream(f);

            CharsetEncoder utf8Encoder = Charset.forName("UTF-8").newEncoder();
            byte[] data = null;

            // Save user name
            ByteBuffer bBuffer = utf8Encoder.encode(CharBuffer.wrap(m_UserName.toCharArray()));
            //   data = new byte[bBuffer.limit()];
            //   bBuffer.get(data);
            //   fs.write(((data.length >>> 8) & 0xFF));
            //  fs.write((data.length & 0xFF));
            //  fs.write(data);
            data = toBytes(m_Template.length);
            fs.write(data);
            // Save user unique ID
            fs.write(m_Key);

            // Save user template
            //   fs.write(((m_Template.length >>> 8) & 0xFF));
            //   fs.write((m_Template.length & 0xFF));
            fs.write(m_Template);
            fs.close();
            bRetcode = true;
        } finally {
            if (!bRetcode && f != null) {
                f.delete();
            }
        }

        return bRetcode;
    }

    public boolean deleteFinger(String name) {
        try {
            int custNo = Integer.parseInt(name);
            int fileNo = custNo / TEMPLATE_SIZE;
            String szFileName = FingerPrintVerification.GetDatabaseDir() + "//" + fileNo;
            File f = new File(szFileName);
            if (!f.exists()) {
                System.out.println(f.getAbsolutePath() + " folder does not exists");
                return true;
                //  System.out.println("successflag "+sucessflag );
            } else {
                szFileName = FingerPrintVerification.GetDatabaseDir() + "//" + fileNo + "//" + name;
                int fileno = 1;
                f = new File(szFileName + fileno + ".tml");
                while (f.exists()) {
                    boolean delFalg = f.delete();
                    if (!delFalg) {
                        return false;
                    }
                    fileno++;
                    f = new File(szFileName + fileno + ".tml");
                }
                return true;
            }
        } catch (AppException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    byte[] toBytes(int i) {
        byte[] result = new byte[4];

        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) (i /*>> 0*/);

        return result;
    }

    /**
     * Get the user name.
     */
    public String getUserName() {
        return m_UserName;
    }

    /**
     * Set the user name.
     */
    public void setUserName(String value) {
        m_UserName = value;
    }

    /**
     * Get the user template.
     */
    public byte[] getTemplate() {
        return m_Template;
    }

    /**
     * Set the user template.
     */
    public void setTemplate(byte[] value) {
        m_Template = value;
    }

    /**
     * Get the user unique identifier.
     */
    public byte[] getUniqueID() {
        return m_Key;
    }

    public FtrIdentifyRecord getFtrIdentifyRecord() {
        FtrIdentifyRecord r = new FtrIdentifyRecord();
        r.m_KeyValue = m_Key;
        r.m_Template = m_Template;
        //System.out.println("r m_Template = " + r.m_Template.length);
        //  System.out.println("r = " + r.m_Template.length);

        return r;
    }

    /**
     * Function read all records from database.
     *
     * @param szDbDir database folder
     *
     * @return reference to Vector objects with records
     */
    static Vector< DbRecord> ReadRecords11(String szDbDir) {
        File DbDir;
        File[] files;
        Vector<DbRecord> Users = new Vector<DbRecord>(10, 10);
        //System.out.println("Users = " + Users);
        // Read all records to identify
        DbDir = new File(szDbDir);
        // System.out.println("Users = " + DbDir.exists());

        DbRecord User;
        try {
            User = new DbRecord(DbDir.getAbsolutePath());
            Users.add(User);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AppException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Users;
    }

    public static Vector< DbRecord> ReadRecordsForWindows_2(String templatename, String serverType) throws IOException {

        Vector<DbRecord> Users = new Vector<DbRecord>(10, 10);
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        
        PreparedStatement ps;
        int total = 0;
        String sql = "SELECT * FROM FP_BIOMETRIC_DATA WHERE CUST_NO=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, templatename);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                byte[] bdata = null;
                Blob blob = rs.getBlob("BIO_DATA_2");
                String userName = rs.getString("CUST_NO");
                System.out.println("userName = " + userName);

                if (blob != null) {
                    bdata = blob.getBytes(1, (int) blob.length());
                    InputStream fs = blob.getBinaryStream();

                    if ("W".equals(serverType)) {
                        fs = blob.getBinaryStream();
                        CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
                        byte[] DataSize = new byte[4];
                        fs.read(DataSize);
                        m_Key = new byte[16];
                        fs.read(m_Key);
                        m_UserName = utf8Decoder.decode(ByteBuffer.wrap(m_Key)).toString();
                        m_Template = new byte[(int) blob.length() - 20];
                        fs.read(m_Template);
                        fs.close();
                    } else if ("L".equals(serverType)) {

                        CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
                        byte[] Data = null;
                        int nLength = (fs.read() << 8) | fs.read();
                        Data = new byte[nLength];
                        fs.read(Data);
                        m_UserName = utf8Decoder.decode(ByteBuffer.wrap(Data)).toString();
                        m_Key = new byte[16];
                        fs.read(m_Key);
                        nLength = (fs.read() << 8) | fs.read();
                        m_Template = new byte[nLength];
                        fs.read(m_Template);
                        fs.close();
                    }

                }
//                 oracle.sql.CLOB fingerData = (oracle.sql.CLOB) rs.getClob("BIO_DATA");
//                System.out.println("fingerData = " + fingerData.length());
//                InputStream inStream = fingerData.getAsciiStream();
//                StringWriter fingerString = new StringWriter(); 
//                try {
//                    IOUtils.copy(inStream, fingerString);
//                } catch (IOException ex) {
//                    Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
//                } 
//                 

                /// System.out.println("fingerString = " + bdata.length);
                DbRecord dbRecord = new DbRecord(m_Template, m_Key);
                Users.add(dbRecord);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(" Read records : "+Users.size());
        return Users;
        //*******

        // return Users;
    }

    public static Vector< DbRecord> ReadRecordsForWindows(String szDbDir, String serverType) throws IOException {
        File DbDir;
        File[] files;
        Vector<DbRecord> Users = new Vector<DbRecord>(10, 10);
        DbDir = new File(szDbDir);

        DbRecord User;
        try {
            User = new DbRecord(DbDir.getAbsolutePath(), serverType);
            Users.add(User);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AppException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  System.out.println("User = " + User);

        return Users;
    }

    public byte[] getTemplateFromDataBase(String customerNo) {
        String sql = "SELECT * FROM FP_BIOMETRIC_DATA WHERE CUST_NO=?";
        byte[] bdata = null;

        try {
            DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
            Connection con = dbConnectionHandler.getConnection();
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, customerNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Blob blob = rs.getBlob("BIO_DATA_2");
                String picture = "";
                if (blob != null) {
                    bdata = blob.getBytes(1, (int) blob.length());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bdata;

    }

    static Vector< DbRecord> loadTemplateFromDatase(String templatename) throws IOException {

        Vector<DbRecord> Users = new Vector<DbRecord>(10, 10);
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        PreparedStatement ps;
        int total = 0;
        String sql = "SELECT * FROM FP_BIOMETRIC_DATA WHERE CUST_NO=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, templatename);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                byte[] bdata = null;
                Blob blob = rs.getBlob("FINGER_DATA");
                String userName = rs.getString("CUST_NO");
                String picture = "";
                if (blob != null) {
                    bdata = blob.getBytes(1, (int) blob.length());
                }
                //DbRecord dbRecord = new DbRecord(userName, userName, "");              
                //Users.add(dbRecord);  
                DbRecord dbRecord = new DbRecord();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Users;

    }

    static Vector< DbRecord> ReadRecordsForVerify(String customerno, String userId) throws IOException {

        Vector<DbRecord> Users = new Vector<DbRecord>(10, 10);
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        //

        try {

            CallableStatement stmt = con.prepareCall("{CALL BIOTPL.PKG_BIOMETRIC_PROCESS.GET_ENROLL_DATA_APPS(?,?,?,?,?,?)}");
            stmt.setString(1, customerno);
            stmt.setString(2, userId);
            stmt.registerOutParameter(3, java.sql.Types.BLOB);
            stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(5, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
            stmt.execute();
            System.out.println("Finger Error Flag  :" + stmt.getString(5));
            if ("N".equals(stmt.getString(5))) {

                System.out.println(stmt.getString(5));
                String serverType = stmt.getString(4);
                System.out.println("serverType = " + serverType);
                Blob blob = stmt.getBlob(3);
                System.out.println("blob = " + blob.length());
                if (serverType != null && blob != null) {
                    //byte []   bdata = blob.getBytes(1, (int) blob.length());
                    InputStream fs = blob.getBinaryStream();
                    if ("W".equals(serverType)) {
                        try {

                            System.out.println("WWWWWW = ");
                            // fs = blob.getAsciiStream();
                            CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
                            utf8Decoder.onMalformedInput(CodingErrorAction.IGNORE);
                            byte[] DataSize = new byte[4];
                            fs.read(DataSize);
                            m_Key = new byte[16];
                            fs.read(m_Key);
                            m_UserName = utf8Decoder.decode(ByteBuffer.wrap(m_Key)).toString();
                            m_Template = new byte[(int) blob.length() - 20];

                            fs.read(m_Template);
                            fs.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if ("L".equals(serverType)) {
                        try {
                            CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
                            utf8Decoder.onMalformedInput(CodingErrorAction.IGNORE);
                            byte[] Data = null;
                            int nLength = (fs.read() << 8) | fs.read();
                            Data = new byte[nLength];
                            fs.read(Data);
                            m_UserName = utf8Decoder.decode(ByteBuffer.wrap(Data)).toString();
                            m_Key = new byte[16];
                            fs.read(m_Key);
                            nLength = (fs.read() << 8) | fs.read();
                            m_Template = new byte[nLength];
                            fs.read(m_Template);
                            fs.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

            } else {
                //   model.se(stmt.getString(5));
                // model.setErrorMessage(stmt.getString(6));
                //list.add(model);
                System.out.println("error Message = " + stmt.getString(6));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            DBConnectionHandler.releaseConnection(con);
        }
        ///

        DbRecord dbRecord = new DbRecord(m_Template, m_Key);
        Users.add(dbRecord);

        return Users;

    }

    static Vector< DbRecord> ReadRecordsForVerify55(String customerno, String userId) throws IOException {

        Vector<DbRecord> Users = new Vector<DbRecord>(10, 10);
        DBConnectionHandler dbConnectionHandler= new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        //

        try {

            CallableStatement stmt = con.prepareCall("{CALL BIOTPL.PKG_BIOMETRIC_PROCESS.GET_ENROLL_DATA_APPS(?,?,?,?,?,?)}");
            stmt.setString(1, customerno);
            stmt.setString(2, userId);
            stmt.registerOutParameter(3, java.sql.Types.BLOB);
            stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(5, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
            stmt.execute();
            System.out.println("Finger Error Flag  :" + stmt.getString(5));
            if ("N".equals(stmt.getString(5))) {

                System.out.println(stmt.getString(5));
                String serverType = stmt.getString(4);
                System.out.println("serverType = " + serverType);
                Blob blob = stmt.getBlob(3);
                System.out.println("blob = " + blob.length());
                if (serverType != null && blob != null) {
                    //byte []   bdata = blob.getBytes(1, (int) blob.length());
                    InputStream fs = blob.getBinaryStream();
                    if ("W".equals(serverType)) {
                        try {

                            System.out.println("WWWWWW = ");
                            // fs = blob.getAsciiStream();
                            CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
                            utf8Decoder.onMalformedInput(CodingErrorAction.IGNORE);
                            byte[] DataSize = new byte[4];
                            fs.read(DataSize);
                            m_Key = new byte[16];
                            fs.read(m_Key);
                            m_UserName = utf8Decoder.decode(ByteBuffer.wrap(m_Key)).toString();
                            m_Template = new byte[(int) blob.length() - 20];

                            fs.read(m_Template);
                            fs.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if ("L".equals(serverType)) {
                        try {
                            CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
                            utf8Decoder.onMalformedInput(CodingErrorAction.IGNORE);
                            byte[] Data = null;
                            int nLength = (fs.read() << 8) | fs.read();
                            Data = new byte[nLength];
                            fs.read(Data);
                            m_UserName = utf8Decoder.decode(ByteBuffer.wrap(Data)).toString();
                            m_Key = new byte[16];
                            fs.read(m_Key);
                            nLength = (fs.read() << 8) | fs.read();
                            m_Template = new byte[nLength];
                            fs.read(m_Template);
                            fs.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

            } else {
                //   model.se(stmt.getString(5));
                // model.setErrorMessage(stmt.getString(6));
                //list.add(model);
                System.out.println("error Message = " + stmt.getString(6));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FingerPrintVerification.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            DBConnectionHandler.releaseConnection(con);
        }
        ///

        DbRecord dbRecord = new DbRecord(m_Template, m_Key);
        Users.add(dbRecord);

        return Users;

    }

    static Vector< DbRecord> ReadRecordsForVerify23(String templatename, String serverType) throws IOException {

        Vector<DbRecord> Users = new Vector<DbRecord>(10, 10);
        DBConnectionHandler dbConnectionHandler= new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        PreparedStatement ps;
        int total = 0;
        String sql = "SELECT * FROM FP_BIOMETRIC_DATA WHERE CUST_NO=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, templatename);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                byte[] bdata = null;
                Blob blob = rs.getBlob("BIO_DATA");
                String userName = rs.getString("CUST_NO");
                System.out.println("userName = " + userName);

                if (blob != null) {
                    bdata = blob.getBytes(1, (int) blob.length());
                    InputStream fs = blob.getBinaryStream();
                    //serverType="L";
                    m_Key = new byte[16];
                    m_Template = new byte[(int) blob.length()];
                    System.out.println("m_Template = " + m_Template.length);
                    fs.read(m_Template);
                    fs.close();
                }

                DbRecord dbRecord = new DbRecord(m_Template, m_Key);
                Users.add(dbRecord);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Users;

    }

    static Vector< DbRecord> ReadRecordsForVerify3(String templatename, String serverType) throws IOException {

        Vector<DbRecord> Users = new Vector<DbRecord>(10, 10);
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        PreparedStatement ps;
        int total = 0;
        String sql = "SELECT * FROM FP_BIOMETRIC_DATA WHERE CUST_NO=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, templatename);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                byte[] bdata = null;
                Clob blob = rs.getClob("BIO_DATA");
                String userName = rs.getString("CUST_NO");
                System.out.println("userName = " + userName);

                if (blob != null) {
                    // bdata = blob.getBytes(1, (int) blob.length());
                    InputStream fs = blob.getAsciiStream();
                    //serverType="L";
                    if ("W".equals(serverType)) {
                        try {

                            fs = blob.getAsciiStream();
                            // System.out.println("fs = " +  blob.length());
                            CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
                            utf8Decoder.onMalformedInput(CodingErrorAction.IGNORE);
                            byte[] DataSize = new byte[4];
                            fs.read(DataSize);
                            m_Key = new byte[16];
                            fs.read(m_Key);
                            m_UserName = utf8Decoder.decode(ByteBuffer.wrap(m_Key)).toString();
                            // m_Template = new byte[(int) blob.length() - 20];
                            m_Template = new byte[(int) blob.length()];

                            fs.read(m_Template);
                            fs.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if ("L".equals(serverType)) {
                        try {
                            CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
                            utf8Decoder.onMalformedInput(CodingErrorAction.IGNORE);
                            byte[] Data = null;
                            int nLength = (fs.read() << 8) | fs.read();
                            Data = new byte[nLength];
                            fs.read(Data);
                            m_UserName = utf8Decoder.decode(ByteBuffer.wrap(Data)).toString();
                            m_Key = new byte[16];
                            fs.read(m_Key);
                            nLength = (fs.read() << 8) | fs.read();
                            m_Template = new byte[nLength];
                            fs.read(m_Template);
                            fs.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

                DbRecord dbRecord = new DbRecord(m_Template, m_Key);
                Users.add(dbRecord);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Users;

    }

    static Vector< DbRecord> getFingerRecord(String customerNo, String usercode) throws IOException {

        Vector<DbRecord> Users = new Vector<DbRecord>(10, 10);
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();

        System.out.println("customerNo = " + customerNo);

        try {
            CallableStatement stmt = con.prepareCall("{CALL BIOTPL.PKG_BIOMETRIC_PROCESS.GET_ENROLL_DATA_APPS(?,?,?,?,?,?)}");
            stmt.setString(1, customerNo);
            stmt.setString(2, usercode);
            stmt.setInt(3, 1);
            stmt.registerOutParameter(4, java.sql.Types.BLOB);
            stmt.registerOutParameter(5, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
            stmt.execute();

            // System.out.println("error Flag  Read firnger = " + stmt.getString(4));
            // System.out.println("finger print = " + stmt.getBlob(3));
            if ("N".equals(stmt.getString(5))) {
                Blob blob = stmt.getBlob(4);
                if (blob != null) {
                    byte[] bdata = blob.getBytes(1, (int) blob.length());
                    InputStream fs = blob.getBinaryStream();
                    //serverType="L";
                    m_Key = new byte[16];
                    m_Template = new byte[(int) blob.length()];
                    System.out.println("m_Template = " + m_Template.length);
                    fs.read(m_Template);
                    fs.close();
                }

                DbRecord dbRecord = new DbRecord(m_Template, m_Key);
                Users.add(dbRecord);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbRecord.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBConnectionHandler.releaseConnection(con);
        }

        return Users;

    }
}
