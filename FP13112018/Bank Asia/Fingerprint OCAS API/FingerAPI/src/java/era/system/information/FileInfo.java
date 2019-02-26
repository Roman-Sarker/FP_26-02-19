/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.system.information;

/**
 *
 * @author root
 */
public class FileInfo {
    String totalSpace,freeSpace,usableSpace,rootFileSystem;

    public String getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(String totalSpace) {
        this.totalSpace = totalSpace;
    }

    public String getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(String freeSpace) {
        this.freeSpace = freeSpace;
    }

    public String getUsableSpace() {
        return usableSpace;
    }

    public void setUsableSpace(String usableSpace) {
        this.usableSpace = usableSpace;
    }

    public String getRootFileSystem() {
        return rootFileSystem;
    }

    public void setRootFileSystem(String rootFileSystem) {
        this.rootFileSystem = rootFileSystem;
    }
    
    
}
