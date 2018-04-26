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

        Locale eng = new Locale("en", "US");
        Locale rus = new Locale("ru", "RU");

        SwingUtilities.invokeLater(() -> {
            try {
                Application view = new Application(eng);
                new Controller(new GameModel(), view);

                view.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}