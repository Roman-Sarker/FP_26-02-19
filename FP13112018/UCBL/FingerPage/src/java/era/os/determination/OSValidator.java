/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.os.determination;

/**
 *
 * @author root
 */
public class OSValidator {

    private String OS = System.getProperty("os.name").toLowerCase();

    public boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

    public boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }

    public static void main(String[] args) {

        OSValidator osValidator = new OSValidator();
        System.out.println(osValidator.OS);

        if (osValidator.isWindows()) {
            System.out.println("This is Windows");
        } else if (osValidator.isMac()) {
            System.out.println("This is Mac");
        } else if (osValidator.isUnix()) {
            System.out.println("This is Unix or Linux");
        } else if (osValidator.isSolaris()) {
            System.out.println("This is Solaris");
        } else {
            System.out.println("Your OS is not support!!");
        }
    }
}
