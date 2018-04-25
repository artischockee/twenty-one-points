//
// This class is used for detecting an OS name that the application is run on.
// It returns the absolute path to native (for the OS) look and feel in java.swing library
//

final class OperatingSystem {
    public static final String OS_NAME = System.getProperty("os.name");

    // Private constructor is used for prevent class's instantiation
    private OperatingSystem() {}

    static String getLookAndFeel() {
        String osNameLowerCase = OS_NAME.toLowerCase();

        if (osNameLowerCase.contains("windows"))
            return "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        else if (osNameLowerCase.contains("linux"))
            return "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
        else
            return "javax.swing.plaf.metal.MetalLookAndFeel"; // if everything's bad
    }
}