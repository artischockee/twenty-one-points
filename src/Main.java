import controller.Controller;
import model.GameModel;
import view.Application;

import javax.swing.*;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(OperatingSystem.getLookAndFeel());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            try {
                Application view = new Application(new Locale("en", "US"));
//                    Application view = new Application(new Locale("ru", "RU"));
                new Controller(new GameModel(), view);

                view.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}