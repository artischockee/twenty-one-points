import view.TwentyOnePointsGui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(OperatingSystem.getLookAndFeel());

            TwentyOnePointsGui top = new TwentyOnePointsGui();

            SwingUtilities.invokeLater(() -> {
                top.windowAssembly();
            });
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
