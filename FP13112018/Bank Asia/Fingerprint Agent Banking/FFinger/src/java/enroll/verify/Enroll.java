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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sultan
 */
public class Enroll {

    public EnrollInformation enrollFinger(EnrollInformation enrollInformation) {

    /*    byte[][] userdata = new byte[10][];

        userdata[0] = enrollInformation.getlIndex();
        userdata[1] = enrollInformation.getlThumb();
        userdata[2] = enrollInformation.getrIndex();
        userdata[3] = enrollInformation.getrThumb();
        userdata[4] = enrollInformation.getlMiddle();
        userdata[5] = enrollInformation.getlRing();
        userdata[6] = enrollInformation.getlLittle();
        userdata[7] = enrollInformation.getrMiddle();
        userdata[8] = enrollInformation.getrRing();
        userdata[9] = enrollInformation.getrLittle();

        boolean matchingFlag = checkWhetherClientMatchesAgent(Long.parseLong(enrollInformation.getName()),
                userdata);

        if (matchingFlag) {
            enrollInformation.setErrorFlag("W");
            enrollInformation.setErrorMessage("Agent's Finger is matched with Customer");
            return enrollInformation;
        } */

        try {
            DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
            Connection con = dbConnectionHandler.getConnection();

            String sql = "INSERT INTO Biotpl.FP_ENROLL (CUST_NO,CREATE_DATE,"
                    + "CREATE_BY,DEVICE_ID,LINDEX,LTHUMB,RINDEX,RTHUMB,LMIDDLE,LRING,LLITTLE,"
                    + "RMIDDLE,RRING,RLITTLE,ENROLL_STATUS,FINGER1,FINGER2,STANDARD)"
                    + "VALUES (?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, Long.parseLong(enrollInformation.getName()));
            pstmt.setString(2, enrollInformation.getApp_user());
            pstmt.setString(3, enrollInformation.getSerial());

            pstmt.setBytes(4, enrollInformation.getlIndex());
            pstmt.setBytes(5, enrollInformation.getlThumb());
            pstmt.setBytes(6, enrollInformation.getrIndex());
            pstmt.setBytes(7, enrollInformation.getrThumb());

            pstmt.setBytes(8, enrollInformation.getlMiddle());
            pstmt.setBytes(9, enrollInformation.getlRing());
            pstmt.setBytes(10, enrollInformation.getlLittle());

            pstmt.setBytes(11, enrollInformation.getrMiddle());
            pstmt.setBytes(12, enrollInformation.getrRing());
            pstmt.setBytes(13, enrollInformation.getrLittle());
            pstmt.setString(14, "Y");
            pstmt.setString(15, "Y");
            pstmt.setString(16, "Y");
            pstmt.setString(17, "S");
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

    private boolean checkWhetherClientMatchesAgent(long custNo, byte[][] userData) {
        List<Long> agentNumber = getAgentNumber(custNo);
        byte[][] usrData = getNonNullData(userData);
        boolean matchingFlag = false;
        String customerNo = String.valueOf(custNo);

        FingerMatching fingerMatching = new FingerMatching();
        for (int i = 0; i < usrData.length; i++) {
            for (int j = 0; j < agentNumber.size(); j++) {
                byte[][] agentData = getAgentData(custNo);
                byte[][] agntData = getNonNullData(agentData);
                
                matchingFlag = fingerMatching.fingerPrintIndetify(customerNo, usrData[i], agntData);
                if (matchingFlag) {
                    return true;
                }
            }
        }
        return matchingFlag;
    }

    private byte[][] getAgentData(long custNo) {
        List<Long> agentNumber = getAgentNumber(custNo);
        if (agentNumber == null) {
            return null;
        }
        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();
        dbConnectionHandler.releaseConnection(con);
        return null;
    }

    private List<Long> getAgentNumber(long custNo) {
        try {
            List<Long> list = new ArrayList<Long>();
            DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
            Connection con = dbConnectionHandler.getConnection();
            Statement stmt = con.createStatement();
            String sql = "SELECT b.CUST_NO As CUSTNo"
                    + "  FROM EMOB.MB_CUSTOMER_MST a, EMOB.MB_CUSTOMER_MST b, GUMS.MB_USER_MST c "
                    + "  WHERE a.CUST_NO = " + custNo
                    + "  AND a.AGENT_POINT_ID = b.AGENT_POINT_ID "
                    + "  AND b.CUST_NO = c.CUST_NO "
                    + "  AND b.CUST_TYPE = 'SAG'"
                    + "  AND c.USER_STS != 'I'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(Long.valueOf(rs.getString("CUSTNo")));
            }
            dbConnectionHandler.releaseConnection(con);
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Enroll.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private byte[][] getNonNullData(byte[][] data) {
        if (data == null) {
            return null;
        }
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

    public static void main(String[] args) {
        Enroll enroll = new Enroll();
        EnrollInformation enrollInformation = new EnrollInformation();
        enrollInformation.setName("208");
        enroll.enrollFinger(enrollInformation);
    }

}
