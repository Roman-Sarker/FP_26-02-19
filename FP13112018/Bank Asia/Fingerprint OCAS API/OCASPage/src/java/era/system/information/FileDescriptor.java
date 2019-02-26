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
public class FileDescriptor {
    String MaxFileDescriptorCount,OpenFileDescriptorCount;

    public String getMaxFileDescriptorCount() {
        return MaxFileDescriptorCount;
    }

    public void setMaxFileDescriptorCount(String MaxFileDescriptorCount) {
        this.MaxFileDescriptorCount = MaxFileDescriptorCount;
    }

    public String getOpenFileDescriptorCount() {
        return OpenFileDescriptorCount;
    }

    public void setOpenFileDescriptorCount(String OpenFileDescriptorCount) {
        this.OpenFileDescriptorCount = OpenFileDescriptorCount;
    }
    
    
}
