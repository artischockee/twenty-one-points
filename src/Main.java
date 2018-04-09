import javax.swing.*;

final class OperatingSystem {
    public static final String OS_NAME = System.getProperty("os.name");

    private OperatingSystem() {
        System.out.println("There is nothing to construct.");
    }

    static String getLookAndFeel() {
        String osNameLowerCase = OS_NAME.toLowerCase();

        if (osNameLowerCase.contains("windows"))
            return "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        else if (osNameLowerCase.contains("linux"))
            return "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
        else
            return "javax.swing.plaf.metal.MetalLookAndFeel"; // too bad
    }
}

public class Main {
    private static void runConsoleApplication() throws Exception {
        TwentyOnePoints top = new TwentyOnePoints();
        top.playGame();
    }

    private static void runGuiApplication() throws Exception {
        UIManager.setLookAndFeel(OperatingSystem.getLookAndFeel());

        TwentyOnePointsGui top = new TwentyOnePointsGui();

        SwingUtilities.invokeLater(() -> {
            try {
                top.windowAssembly();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        try {
//            runGuiApplication();
            runConsoleApplication();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
