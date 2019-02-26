/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.information;

/**
 *
 * @author sultan
 */
import com.futronictech.AnsiSDKLib;

/**
 *
 * @author sultan
 */
public class FingerMatching {

    public boolean fingerPrintIndetify(String cust_no, byte[] fingerData, byte[][] fingerEnrollData) {

         
        //System.out.println("finger data length is "+fingerData.length);
        System.out.println("fingerprintidentify method is called");
        boolean matchingFlag = false;
        AnsiSDKLib ansi_lib = new AnsiSDKLib();

        int i = 0, dataLength = fingerEnrollData.length;
        while (i < dataLength) {
            matchingFlag = FingerprintVerify(ansi_lib, fingerData, fingerEnrollData[i]);
            if (matchingFlag) {
                break;
            }
            ++i;
        }
        return matchingFlag;
    }

    public boolean FingerprintVerify(AnsiSDKLib ansi_lib, byte[] dataFromUser, byte[] dataFromDB) {
        float[] matchResult = new float[1];
        float mMatchScore = AnsiSDKLib.FTR_ANSISDK_MATCH_SCORE_MEDIUM;
        boolean matchingFlag = false;

        if(dataFromUser == null)
        {
            System.out.println("data from user is null");
            return false;
        }
        dataFromUser[10] = 0x00;
        dataFromUser[11] = 0x4D;
        dataFromUser[12] = 0x00;
        dataFromUser[13] = 0x03;
        dataFromUser[14] = 0x00;
        dataFromUser[15] = 0x00;
        
        if(dataFromUser == null || dataFromDB == null){
            System.out.println("null data found");
            return false;
        }else if(dataFromUser.length > 500 ||  dataFromDB.length>500)
        {
            System.out.println("abnormal data found");
            System.out.println("dataFromUser length is "+dataFromUser.length);
            System.out.println("dataFromDB length is "+dataFromDB.length);
            return false;
        }

        if (ansi_lib.MatchTemplates(dataFromDB, dataFromUser, matchResult) && matchResult[0] > mMatchScore) {
            String message = String.format("Matchning Score:%f", matchResult[0]);
            System.out.println(message);
            matchingFlag = true;
        } else {
            int lastError = ansi_lib.GetErrorCode();
            if (lastError == AnsiSDKLib.FTR_ERROR_EMPTY_FRAME
                    || lastError == AnsiSDKLib.FTR_ERROR_NO_FRAME
                    || lastError == AnsiSDKLib.FTR_ERROR_MOVABLE_FINGER) {
                String error = String.format("Verification failed. Error: %s.", ansi_lib.GetErrorMessage());
                System.out.println(error);
                matchingFlag = false;
            } else {
                String error = String.format("Verification failed. Error: %s.", ansi_lib.GetErrorMessage());
                System.out.println(error);
                matchingFlag = false;
            }
        }
        return matchingFlag;
    }
}
