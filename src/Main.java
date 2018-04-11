import controller.Controller;
import model.GameModel;
import view.Application;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(OperatingSystem.getLookAndFeel());

            SwingUtilities.invokeLater(() -> {
                try {
                    GameModel model = new GameModel();
                    Application view = new Application(model);
                    new Controller(model, view);

                    view.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
