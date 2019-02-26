/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.system.information;
 
import java.util.logging.Level;
import java.util.logging.Logger;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

/**
 *
 * @author root
 */
public class SystemInfoRetrieve {
    
    public static MemoryInformation getMemoryInfoRetrieve(){
        MemoryInformation memoryInformation = new MemoryInformation();
        
         /* Total number of processors or cores available to the JVM */
        int availProcessor = Runtime.getRuntime().availableProcessors();
        String noOfProcessor = "Available processors "+ availProcessor+"  (cores)";
        System.out.println(noOfProcessor);
        memoryInformation.setAvailableProcessors(noOfProcessor);
        
        long freeMemory = Runtime.getRuntime().freeMemory();
        double freeMemory1 = freeMemory/(1024.0*1024*1024);
        String freeMemory2 = "Free memory "+ String.format("%.2f", freeMemory1) +" (GB)";
        memoryInformation.setFreeMemory(freeMemory2);
        
        /* This will return Long.MAX_VALUE if there is no preset limit */
        /* Maximum amount of memory the JVM will attempt to use */
        
        long maxMemory = Runtime.getRuntime().maxMemory();
        double maxMemory1 = maxMemory/(1024.0*1024*1024);
        String maxMemory2 = "Max memory "+ (maxMemory == Long.MAX_VALUE ? " no limit" : String.format("%.2f", maxMemory1))+" (GB)";
        memoryInformation.setMaxMemory(maxMemory2);
        
        /* Total memory currently available to the JVM */
        long totalMemory = Runtime.getRuntime().totalMemory(); 
        double totalMemory1 = totalMemory/(1024.0*1024*1024);
        String totalMemory2 = "Total memory available to JVM  " + String.format("%.2f", totalMemory1)+ " (GB)  " ;
        memoryInformation.setTotalMemory(totalMemory2);
                
        return memoryInformation;
    }
    
    public static synchronized String getCpuUsage() {

        int availableProcessors = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
        long lastSystemTime = 0;
        long lastProcessCpuTime = 0;

        OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        /* if (lastSystemTime == 0) {
            baselineCounters(operatingSystemMXBean);
        } */

        lastSystemTime = System.nanoTime();
        lastProcessCpuTime = operatingSystemMXBean.getProcessCpuTime();

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SystemInfoRetrieve.class.getName()).log(Level.SEVERE, null, ex);
        }

        long systemTime = System.nanoTime();
        long processCpuTime = 0;

        processCpuTime = operatingSystemMXBean.getProcessCpuTime();

        double cpuUsage = (double) (processCpuTime - lastProcessCpuTime) / (systemTime - lastSystemTime);

        lastSystemTime = systemTime;
        lastProcessCpuTime = processCpuTime;
        
        double cpuPercentage = (cpuUsage * 100) / availableProcessors;
        return String.format("%.2f", cpuPercentage);
    }
    
    public static void main(String[] arg){
        System.out.println(getCpuUsage());
    }
    
}
