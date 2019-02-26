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
public class PhysicalMemoryInfo {
    
    String CommittedVirtualMemorySize ;
    String FreePhysicalMemorySize ;
    String FreeSwapSpaceSize;

    public String getCommittedVirtualMemorySize() {
        return CommittedVirtualMemorySize;
    }

    public void setCommittedVirtualMemorySize(String CommittedVirtualMemorySize) {
        this.CommittedVirtualMemorySize = CommittedVirtualMemorySize;
    }

    public String getFreePhysicalMemorySize() {
        return FreePhysicalMemorySize;
    }

    public void setFreePhysicalMemorySize(String FreePhysicalMemorySize) {
        this.FreePhysicalMemorySize = FreePhysicalMemorySize;
    }

    public String getFreeSwapSpaceSize() {
        return FreeSwapSpaceSize;
    }

    public void setFreeSwapSpaceSize(String FreeSwapSpaceSize) {
        this.FreeSwapSpaceSize = FreeSwapSpaceSize;
    }
    
    
    
}
