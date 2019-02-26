/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.system.information;

import java.io.File;

/**
 *
 * @author root
 */
public class FileInfoRetrieve {

    public static FileInfo getFileInfo() {
        FileInfo fileInfo = new FileInfo();
        
        File[] root = File.listRoots();
        String rootFileSystem = "File system root: " + root[0].getAbsolutePath();
        fileInfo.setRootFileSystem(rootFileSystem);
        
        double value = root[0].getTotalSpace()/(1024.0*1024*1024); 
        String totalSpace =  "Total space " +  String.format("%.2f", value)+" GB";
        fileInfo.setTotalSpace(totalSpace);
        
        value = root[0].getFreeSpace()/(1024.0*1024*1024); 
        String freeSpace =  "Free space  " + String.format("%.2f", value)+" GB";
        fileInfo.setFreeSpace(freeSpace);
        
        value = root[0].getUsableSpace() /(1024.0*1024*1024); 
        String usableSpace = "Usable space (bytes): " + String.format("%.2f", value)+" GB";
        fileInfo.setUsableSpace(usableSpace);
                
        return fileInfo;

    }

}
