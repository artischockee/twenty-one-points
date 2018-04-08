import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(OperatingSystem.getLookAndFeel());

        TwentyOnePointsGui top = new TwentyOnePointsGui();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    top.windowAssembly();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
