package view;

import controller.Controller;
import model.ResBundle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Application extends JFrame {
    public static final int FRAME_WIDTH = 720;
    public static final int FRAME_HEIGHT = 500;

    private final Image appIcon = ImageIO.read(new File("resources/favicon.png"));

    private ResourceBundle resBundle;
    private ResourceBundle dialogs;
    private JPanel mainPanel;

    // Application's menu bar:
    private JMenuItem playGameMenuItem;
    private JMenuItem exitGameMenuItem;
    private JMenuItem howToPlayMenuItem;
    private JMenuItem aboutMenuItem;

    // The primary panels on which the main action takes place:
    private JPanel gameDeskPanel; // i.e. 'left panel'
    private JPanel controlsPanel; // i.e. 'right panel'

    // Elements of the gameDeskPanel (top):
    private LayeredPane dealerCardsPane;
    private JLabel dealerTotalPtsLabel;

    // Elements of the gameDeskPanel (bottom):
    private LayeredPane playerCardsPane;
    private JLabel playerTotalPtsLabel;

    // Elements of the controlsPanel (top):
    private JPanel gameLogPanel;

    // Elements of the controlsPanel (bottom):
    private JButton playButton;
    private JButton hitButton;
    private JButton standButton;
    private JButton exitButton;

    // Elements of the initial panel:
    private JPanel initPanel;
    private JButton initLaunchButton;
    private JButton initExitButton;

    public Application(Locale locale) throws IllegalArgumentException, IOException {
        super("21 Points - The Game");

        resBundle = ResBundle.getBundle("resources/MessageBundle", locale);
        dialogs = ResBundle.getBundle("resources/DialogMessages", locale);

        this.setIconImage(appIcon);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);

        mainPanel = GuiCreator.createPanel(new GridBagLayout(), new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        assembleMenuBar();
        assembleGameDeskPanel();
        assembleControlsPanel();

        assembleInitPanel();

        this.setContentPane(mainPanel);
        this.pack();
    }

    public LayeredPane getDealerCardsPane() {
        return dealerCardsPane;
    }

    public LayeredPane getPlayerCardsPane() {
        return playerCardsPane;
    }

    public JLabel getPlayerTotalPtsLabel() {
        return playerTotalPtsLabel;
    }

    public JLabel getDealerTotalPtsLabel() {
        return dealerTotalPtsLabel;
    }

    public boolean interactButtonsAreEnabled() {
        return hitButton.isEnabled() && standButton.isEnabled();
    }

    public boolean containsInitPanel() {
        return mainPanel.isAncestorOf(initPanel);
    }

    public void launchApplication() {
        mainPanel.remove(initPanel);

        GBConstraints gbc = new GBConstraints(GridBagConstraints.BOTH);

        gbc.weightx = 0.8D;
        gbc.weighty = 1.0D;

        mainPanel.add(gameDeskPanel, gbc);

        gbc.weightx = 0.2D;
        gbc.gridx = 1;

        mainPanel.add(controlsPanel, gbc);

        this.setContentPane(mainPanel);
        this.pack();
    }

    public void switchInteractButtons(boolean state) {
        hitButton.setEnabled(state);
        standButton.setEnabled(state);
    }

    private void assembleMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu(resBundle.getString("menu-game"));
        gameMenu.setMnemonic(KeyEvent.VK_G);

        playGameMenuItem = new JMenuItem(resBundle.getString("menu-item-play-game"));
        playGameMenuItem.setActionCommand(Controller.ButtonClickListener.NEW_GAME);

        exitGameMenuItem = new JMenuItem(resBundle.getString("common-exit"));
        exitGameMenuItem.setActionCommand(Controller.ButtonClickListener.EXIT_GAME_NO_DIALOG);

        JMenu helpMenu = new JMenu(resBundle.getString("menu-help"));
        helpMenu.setMnemonic(KeyEvent.VK_H);

        howToPlayMenuItem = new JMenuItem(resBundle.getString("menu-item-how-to-play"));
        howToPlayMenuItem.setActionCommand(Controller.ButtonClickListener.HOW_TO_PLAY);

        aboutMenuItem = new JMenuItem(resBundle.getString("menu-item-about"));
        aboutMenuItem.setActionCommand(Controller.ButtonClickListener.ABOUT);

        gameMenu.add(playGameMenuItem);
        gameMenu.add(exitGameMenuItem);
        helpMenu.add(howToPlayMenuItem);
        helpMenu.add(aboutMenuItem);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);
    }

    private void assembleGameDeskPanel() {
        // Main panel:

        gameDeskPanel = GuiCreator.createPanel(new GridLayout(2, 1));

        // Sub-panel (top):

        JPanel dealerPanel = GuiCreator.createPanel(new GridBagLayout());

        dealerCardsPane = GuiCreator.createLayeredPane(
            LayeredPane.DEFAULT_DIMENSION,
            Component.CENTER_ALIGNMENT
        );

        JPanel dealerPointsPanel = new JPanel();
        dealerPointsPanel.add(new JLabel(resBundle.getString("label-total-pts-dealer")));

        dealerTotalPtsLabel = new JLabel();
        dealerPointsPanel.add(dealerTotalPtsLabel);

        dealerPanel.add(dealerCardsPane, new GBConstraints(0, 0));
        dealerPanel.add(dealerPointsPanel, new GBConstraints(0, 1));

        // Sub-panel (bottom):

        JPanel playerPanel = GuiCreator.createPanel(new GridBagLayout());

        playerCardsPane = GuiCreator.createLayeredPane(
            LayeredPane.DEFAULT_DIMENSION,
            Component.CENTER_ALIGNMENT
        );

        JPanel playerPointsPanel = new JPanel();
        playerPointsPanel.add(new JLabel(resBundle.getString("label-total-pts-player")));

        playerTotalPtsLabel = new JLabel();
        playerPointsPanel.add(playerTotalPtsLabel);

        playerPanel.add(playerCardsPane, new GBConstraints(0, 0));
        playerPanel.add(playerPointsPanel, new GBConstraints(0, 1));

        // Adding all the elements from above to the gameDeskPanel:

        gameDeskPanel.add(dealerPanel);
        gameDeskPanel.add(playerPanel);
    }

    private void assembleControlsPanel() {
        // Main panel:

        controlsPanel = GuiCreator.createPanel(new GridLayout(2, 1));

        // Sub-panel (top):

        gameLogPanel = GuiCreator.createPanel(new GridBagLayout());

        // Sub-panel (bottom):

        JPanel controlButtonsPanel = GuiCreator.createPanel(new GridBagLayout());

        hitButton = new JButton(resBundle.getString("button-hit"));
        hitButton.setActionCommand(Controller.ButtonClickListener.GET_CARD);
        hitButton.setEnabled(false);

        standButton = new JButton(resBundle.getString("button-pass"));
        standButton.setActionCommand(Controller.ButtonClickListener.PASS_ROUND);
        standButton.setEnabled(false);

        playButton = new JButton(resBundle.getString("button-new-game"));
        playButton.setActionCommand(Controller.ButtonClickListener.NEW_GAME);

        exitButton = new JButton(resBundle.getString("common-exit"));
        exitButton.setActionCommand(Controller.ButtonClickListener.EXIT_GAME);

        // Placing the items with GridBagConstraints:

        GBConstraints gbc = new GBConstraints(GridBagConstraints.HORIZONTAL);

        gbc.ipady = 30;
        gbc.gridwidth = 2;

        controlButtonsPanel.add(hitButton, gbc);

        gbc.ipady = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(4, 0, 0, 0);

        controlButtonsPanel.add(standButton, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 0, 0, 2);
        gbc.ipadx = 30;

        controlButtonsPanel.add(playButton, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(8, 2, 0, 0);

        controlButtonsPanel.add(exitButton, gbc);

        // Adding the sub-panels to the main panel:

        controlsPanel.add(gameLogPanel);
        controlsPanel.add(controlButtonsPanel);
    }

    private void assembleInitPanel() {
        initPanel = new JPanel(new GridBagLayout());

        JLabel greetingsLabel = new JLabel(resBundle.getString("common-greetings"), JLabel.CENTER);
        greetingsLabel.setFont(greetingsLabel.getFont().deriveFont(16.0f));

        Image image = appIcon.getScaledInstance(256, 256, Image.SCALE_SMOOTH);
        JLabel pictureLabel = new JLabel(new ImageIcon(image), JLabel.CENTER);

        initLaunchButton = new JButton(resBundle.getString("common-launch-game"));
        initLaunchButton.setActionCommand(Controller.ButtonClickListener.NEW_GAME);

        initExitButton = new JButton(resBundle.getString("common-exit"));
        initExitButton.setActionCommand(Controller.ButtonClickListener.EXIT_GAME);

        // Adding components to initPanel using GridBagConstraints:

        GBConstraints gbc = new GBConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 32, 0);

        initPanel.add(greetingsLabel, gbc);

        gbc.gridy = 1;
        gbc.resetInsets();

        initPanel.add(pictureLabel, gbc);

        gbc.gridy = 2;
        gbc.ipady = 25;
        gbc.insets = new Insets(16, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;

        initPanel.add(initLaunchButton, gbc);

        gbc.gridy = 3;
        gbc.ipady = 0;
        gbc.insets = new Insets(4, 0, 0, 0);

        initPanel.add(initExitButton, gbc);

        mainPanel.add(initPanel);
    }

    public void reloadPanes() {
        dealerCardsPane.reset();
        playerCardsPane.reset();
    }

    // JOptionPane's dialogs:

    public int showWinGameDialog(String winnerName) {
        Object[] options = {
            dialogs.getString("common-play"),
            dialogs.getString("common-quit")
        };

        return JOptionPane.showOptionDialog(this,
            winnerName + ", " + dialogs.getString("win-1"),
            winnerName + " " + dialogs.getString("win-2"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options, options[0]
        );
    }

    public int showLoseGameDialog(String loserName) {
        Object[] options = {
                dialogs.getString("common-play"),
                dialogs.getString("common-quit")
        };

        return JOptionPane.showOptionDialog(this,
            loserName + ", " + dialogs.getString("lose-1"),
            loserName + " " +  dialogs.getString("lose-2"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options, options[1]
        );
    }

    public int showExitDialog() {
        return JOptionPane.showConfirmDialog(this,
            dialogs.getString("exit-1"),
                dialogs.getString("exit-2"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
    }

    public int showNewGameDialog() {
        return JOptionPane.showConfirmDialog(this,
                dialogs.getString("new-game-1"),
                dialogs.getString("new-game-2"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
    }

    // TODO: 4/25/18 Make it more informative and decent
    public void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "Twenty-One Points Game.\nAuthor: Artem Piskarev",
            "About",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void showEmptyDeckWarningDialog() {
        JOptionPane.showMessageDialog(this,
            dialogs.getString("empty-deck-1"),
            dialogs.getString("empty-deck-2"),
            JOptionPane.WARNING_MESSAGE
        );
    }

    // ActionListeners:

    public void addButtonClickListener(ActionListener actionListener) {
        initLaunchButton.addActionListener(actionListener);
        initExitButton.addActionListener(actionListener);

        playGameMenuItem.addActionListener(actionListener);
        exitGameMenuItem.addActionListener(actionListener);
        howToPlayMenuItem.addActionListener(actionListener);
        aboutMenuItem.addActionListener(actionListener);

        playButton.addActionListener(actionListener);
        hitButton.addActionListener(actionListener);
        standButton.addActionListener(actionListener);
        exitButton.addActionListener(actionListener);
    }
}
