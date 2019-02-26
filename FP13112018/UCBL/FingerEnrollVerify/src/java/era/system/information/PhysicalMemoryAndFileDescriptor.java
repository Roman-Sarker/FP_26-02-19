/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.system.information;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *
 * @author root
 */
public class PhysicalMemoryAndFileDescriptor {

    FileDescriptor fileDescriptor;
    PhysicalMemoryInfo physicalMemoryInfo;

    public void getMemoryAndFileInfo() {
        fileDescriptor = new FileDescriptor();
        physicalMemoryInfo = new PhysicalMemoryInfo();

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            String methodName = method.getName();
            if (methodName.startsWith("get") && Modifier.isPublic(method.getModifiers())) {
                Object value;
                boolean errorFlag = false;
                try {
                    value = method.invoke(operatingSystemMXBean);
                } catch (Exception e) {
                    value = e.getMessage();
                    errorFlag = true;
                }
                if (methodName.contains("MaxFileDescriptorCount")) {
                    fileDescriptor.setMaxFileDescriptorCount(" MaxFileDescriptorCount " + value);
                } else if (methodName.contains("OpenFileDescriptorCount")) {
                    fileDescriptor.setOpenFileDescriptorCount("OpenFileDescriptorCount " + value);
                } else if (methodName.contains("CommittedVirtualMemorySize")) {

                    String statusMsg;
                    if (!errorFlag) {
                        statusMsg = getStringFromDouble((Long)value);
                    } else {
                        statusMsg = ": error in retrieve";
                    }
                    physicalMemoryInfo.setCommittedVirtualMemorySize("CommittedVirtualMemory " + statusMsg + " GB");
                } else if (methodName.contains("FreePhysicalMemorySize")) {

                    String statusMsg;
                    if (!errorFlag) {
                        statusMsg = getStringFromDouble((Long)value);
                    } else {
                        statusMsg = ": error in retrieve";
                    }
                    physicalMemoryInfo.setFreePhysicalMemorySize("FreePhysicalMemory " + statusMsg + " GB");
                } else if (methodName.contains("FreeSwapSpaceSize")) {

                    String statusMsg;
                    if (!errorFlag) {
                        statusMsg = getStringFromDouble((Long) value);
                    } else {
                        statusMsg = ": error in retrieve";
                    }
                    physicalMemoryInfo.setFreeSwapSpaceSize("FreeSwapSpace " + statusMsg + " GB");
                }

            }
        }
    }

    public FileDescriptor getFileDescriptor() {
        return fileDescriptor;
    }

    public PhysicalMemoryInfo getPhysicalMemoryInfo() {
        return physicalMemoryInfo;
    }

    String getStringFromDouble(Long value) {
        double modifiedValue = value/(1024.0*1024*1024); 
        return String.format("%.2f", modifiedValue);         
    }

    public static void main(String[] arg) {
        PhysicalMemoryAndFileDescriptor physicalMemoryAndFileDescriptor = new PhysicalMemoryAndFileDescriptor();
        physicalMemoryAndFileDescriptor.getMemoryAndFileInfo();
        FileDescriptor fileDescriptor = physicalMemoryAndFileDescriptor.getFileDescriptor();
        PhysicalMemoryInfo physicalMemoryInfo = physicalMemoryAndFileDescriptor.getPhysicalMemoryInfo();
        
        
    }

}
