package view;

import model.*;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Application extends JFrame {
    // Attributes ::

    private GameModel _model;

    private final int _frameWidth = 800;
    private final int _frameHeight = 600;
    private final int _gridGap = 8;

    private final Image _appIcon = Toolkit.getDefaultToolkit().getImage("resources/favicon.png");

    private JPanel _mainPanel;

    // Main (and the one) menu bar:
    private JMenuBar _menuBar;
    private JMenu _gameMenu;
    private JMenuItem _playGameMenuItem;
    private JMenuItem _exitGameMenuItem;
    private JMenu _helpMenu;
    private JMenuItem _aboutMenuItem;

    // All the primary panels on which
    // the main action takes place:
    private JPanel _leftPanel;
    private JPanel _rightPanel;
    private JPanel _bottomPanel;

    // Elements of the left panel
    // that belong to the game dealer:
    private JPanel _topLeftPanel;
    private LayeredPane _dealerPane;
    private JPanel _dealerPointsPanel;
//    private JLabel _dealerTotalStaticLabel;
    private JLabel _dealerTotalPtsLabel;

    // Elements of the left panel
    // that belong to the game player:
    private JPanel _bottomLeftPanel;
    private LayeredPane _playerPane;
    private JPanel _playerPointsPanel;
//    private JLabel _playerTotalStaticLabel;
    private JLabel _playerTotalPtsLabel;

    // Elements of the right panel
    // that belong to the card deck:
    private JButton _cardDeckButton;
    private JPanel _cardDeckSizePanel;
//    private JLabel _cardDeckStaticLabel;
    private JLabel _cardDeckSizeLabel;

    // Elements of the bottom panel, where
    // the main control buttons take place
    private JButton _playButton;
    private JButton _hitButton;
    private JButton _standButton;
    private JButton _exitButton;

    // Initial panel and its elements:
    private JPanel _initPanel;
    private JButton _initLaunchButton;
    private JButton _initExitButton;


    // Constructor ::

    public Application(GameModel model) throws IllegalArgumentException {
        super("21 Points - The Game");

        this.setIconImage(_appIcon);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);

        _model = model;

        _mainPanel = GuiCreator.createPanel(
                new GridBagLayout(),
                new Dimension(_frameWidth, _frameHeight),
                new EmptyBorder(_gridGap, _gridGap, _gridGap, _gridGap));
        _mainPanel.setMaximumSize(new Dimension(_frameWidth, _frameHeight));

        this.assembleMenuBar();
        this.assembleInnerPanels();

        _dealerPane = GuiCreator.createLayeredPane(
                new Dimension(_topLeftPanel.getWidth(), _topLeftPanel.getHeight()),
                Component.CENTER_ALIGNMENT
        );

        _playerPane = GuiCreator.createLayeredPane(
                new Dimension(_bottomLeftPanel.getWidth(), _bottomLeftPanel.getHeight()),
                Component.CENTER_ALIGNMENT
        );

        this.assembleInnerLabels();

        _cardDeckButton = new JButton(new ImageIcon("resources/card_back/green_back.png"));
        _cardDeckButton.setEnabled(false);

        _playButton = new JButton("New Game");
        _hitButton = new JButton("Hit (Take Card)");
        _hitButton.setEnabled(false);
        _standButton = new JButton("Stand (Pass Round)");
        _standButton.setEnabled(false);
        _exitButton = new JButton("Exit");

        this.initialPanelAssembly();

        this.setContentPane(_mainPanel);
        this.pack();
    }


    // Getters and setters ::

    public JPanel getMainPanel() {
        return _mainPanel;
    }

    public JPanel getInitialPanel() {
        return _initPanel;
    }

    public LayeredPane getDealerPane() {
        return _dealerPane;
    }

    public LayeredPane getPlayerPane() {
        return _playerPane;
    }

    public JLabel getCardDeckSizeLabel() {
        return _cardDeckSizeLabel;
    }

    public JButton getCardDeckButton() {
        return _cardDeckButton;
    }

    public JButton getPlayButton() {
        return _playButton;
    }

    public JButton getHitButton() {
        return _hitButton;
    }

    public JButton getStandButton() {
        return _standButton;
    }

    public JLabel getPlayerTotalPtsLabel() {
        return _playerTotalPtsLabel;
    }

    public JLabel getDealerTotalPtsLabel() {
        return _dealerTotalPtsLabel;
    }


    // Methods ::

    public boolean containsInitPanel() {
        return _mainPanel.isAncestorOf(_initPanel);
    }

    private void assembleInnerLabels() {
//        _dealerTotalStaticLabel = new JLabel("Dealer's total points:");
        _dealerTotalPtsLabel = new JLabel();

//        _playerTotalStaticLabel = new JLabel("Total points:");
        _playerTotalPtsLabel = new JLabel();

//        _cardDeckStaticLabel = new JLabel("Cards:");
//        _cardDeckStaticLabel.setFont(_cardDeckStaticLabel.getFont().deriveFont(24.0f));
        _cardDeckSizeLabel = new JLabel();
        _cardDeckSizeLabel.setFont(_cardDeckSizeLabel.getFont().deriveFont(24.0f));
    }

    private void assembleInnerPanels() {
        _leftPanel = GuiCreator.createPanel(new GridLayout(2, 1));

        _rightPanel = GuiCreator.createPanel(
                new GridBagLayout(),
                BorderFactory.createTitledBorder("The Deck"));

        _cardDeckSizePanel = new JPanel();

        _bottomPanel = GuiCreator.createPanel(new FlowLayout(FlowLayout.CENTER));

        _topLeftPanel = GuiCreator.createPanel(
                new BorderLayout(),
                BorderFactory.createTitledBorder("Dealer's hands"));

        _dealerPointsPanel = new JPanel();

        _bottomLeftPanel = GuiCreator.createPanel(
                new BorderLayout(),
                BorderFactory.createTitledBorder("Player's hands"));

        _playerPointsPanel = new JPanel();
    }

    private void assembleMenuBar() {
        _menuBar = new JMenuBar();

        _gameMenu = new JMenu("Game");
        _gameMenu.setMnemonic(KeyEvent.VK_G);

        _playGameMenuItem = new JMenuItem("Play Game");
        _playGameMenuItem.setActionCommand(Controller.ButtonClickListener.NEW_GAME);

        _exitGameMenuItem = new JMenuItem("Exit");
        _exitGameMenuItem.setActionCommand(Controller.ButtonClickListener.EXIT_GAME_NO_DIALOG);

        _helpMenu = new JMenu("Help");
        _helpMenu.setMnemonic(KeyEvent.VK_H);

        _aboutMenuItem = new JMenuItem("About");
        _aboutMenuItem.setActionCommand(Controller.ButtonClickListener.ABOUT);

        // Assembling the elements:

        _gameMenu.add(_playGameMenuItem);
        _gameMenu.add(_exitGameMenuItem);
        _helpMenu.add(_aboutMenuItem);

        _menuBar.add(_gameMenu);
        _menuBar.add(_helpMenu);

        this.setJMenuBar(_menuBar);
    }

    private void initialPanelAssembly() {
        _initPanel = new JPanel(new GridBagLayout());

        JLabel greetingsLabel = new JLabel("Welcome to Twenty-One Points Game!");
        greetingsLabel.setFont(greetingsLabel.getFont().deriveFont(16.0f));

        JLabel pictureLabel = new JLabel(new ImageIcon("resources/favicon.png"), JLabel.CENTER);

        _initLaunchButton = new JButton("Launch game");
        _initLaunchButton.setActionCommand(Controller.ButtonClickListener.LAUNCH_APP);

        _initExitButton = new JButton("Exit");
        _initExitButton.setActionCommand(Controller.ButtonClickListener.EXIT_GAME);

        GBConstraints gbc = new GBConstraints(GridBagConstraints.BOTH);

        gbc.assembly(GBConstraints.Y, new Insets(0, 0, 32, 0));
        _initPanel.add(greetingsLabel, gbc);

        gbc.assembly(GBConstraints.Y);
        _initPanel.add(pictureLabel, gbc);

        gbc.assembly(GBConstraints.Y, new Insets(16, 0, 0, 0));
        _initPanel.add(_initLaunchButton, gbc);

        gbc.assembly(GBConstraints.Y, new Insets(4, 0, 0, 0));
        _initPanel.add(_initExitButton, gbc);

        _mainPanel.add(_initPanel);
    }

    private void setActionCommands() {
        _cardDeckButton.setActionCommand(Controller.ButtonClickListener.GET_CARD);
        _playButton.setActionCommand(Controller.ButtonClickListener.NEW_GAME);
        _hitButton.setActionCommand(Controller.ButtonClickListener.GET_CARD);
        _standButton.setActionCommand(Controller.ButtonClickListener.PASS_ROUND);
        _exitButton.setActionCommand(Controller.ButtonClickListener.EXIT_GAME);
    }

    public void windowAssembly() {
        this.setActionCommands();

        // Adding the elements:

        _topLeftPanel.add(_dealerPane, BorderLayout.CENTER);
        _dealerPointsPanel.add(new JLabel("Dealer's total points:"));
        _dealerPointsPanel.add(_dealerTotalPtsLabel);
        _topLeftPanel.add(_dealerPointsPanel, BorderLayout.PAGE_END);

        _bottomLeftPanel.add(_playerPane, BorderLayout.CENTER);
        _playerPointsPanel.add(new JLabel("Total points:"));
        _playerPointsPanel.add(_playerTotalPtsLabel);
        _bottomLeftPanel.add(_playerPointsPanel, BorderLayout.PAGE_END);

        _leftPanel.add(_topLeftPanel);
        _leftPanel.add(_bottomLeftPanel);

        JLabel cardDeckStaticLabel = new JLabel("Cards:");
        cardDeckStaticLabel.setFont(cardDeckStaticLabel.getFont().deriveFont(24.0f));

        _cardDeckSizePanel.add(cardDeckStaticLabel);
        _cardDeckSizePanel.add(_cardDeckSizeLabel);

        GBConstraints gbc = new GBConstraints(GridBagConstraints.VERTICAL);

        _rightPanel.add(_cardDeckButton, gbc);
        gbc.assembly(GBConstraints.Y);
        _rightPanel.add(_cardDeckSizePanel, gbc);

        _bottomPanel.add(_playButton);
        _bottomPanel.add(_hitButton);
        _bottomPanel.add(_standButton);
        _bottomPanel.add(_exitButton);

        gbc.reset();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        _mainPanel.add(_leftPanel, gbc);

        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.assembly(GBConstraints.X);
        _mainPanel.add(_rightPanel, gbc);

        gbc.reset();
        gbc.assembly(GBConstraints.Y);
        gbc.gridwidth = 2;
        _mainPanel.add(_bottomPanel, gbc);

        this.setContentPane(_mainPanel);
        this.pack();
    }

    public void reloadPanes() {
        _dealerPane.reset();
        _playerPane.reset();
    }

    // JOptionPane's dialogs:

    public int showWinGameDialog() {
        Object[] options = { "Play another game!", "Quit" };

        return JOptionPane.showOptionDialog(this,
                "Congratulations, you win in this game!\n" +
                        "What are you going to do next?",
                "You are the winner!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options, options[0]);
    }

    public int showLoseGameDialog() {
        Object[] options = { "Play another game!", "I'm tired, just quit" };

        return JOptionPane.showOptionDialog(this,
                "Sorry, you lose in this game.\n" +
                        "What are you going to do next?",
                "Regretfully, you lose",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options, options[0]);
    }

    public int showExitDialog() {
        return JOptionPane.showConfirmDialog(this,
                "Are you sure you want to quit the game?",
                "Game quit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    public int showNewGameDialog() {
        return JOptionPane.showConfirmDialog(this,
                "Current game will be reloaded. Are you sure?",
                "New Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    public void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "Twenty-One Points Game.\nAuthor: Artem Piskarev",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void showEmptyDeckWarningDialog() {
        JOptionPane.showMessageDialog(this,
                "There are no cards left on the deck!",
                "The card deck is empty",
                JOptionPane.WARNING_MESSAGE);
    }

    // ActionListeners:

    public void addButtonClickListener(ActionListener actionListener) {
        _initLaunchButton.addActionListener(actionListener);
        _initExitButton.addActionListener(actionListener);

        _playGameMenuItem.addActionListener(actionListener);
        _exitGameMenuItem.addActionListener(actionListener);
        _aboutMenuItem.addActionListener(actionListener);

        _cardDeckButton.addActionListener(actionListener);
        _playButton.addActionListener(actionListener);
        _hitButton.addActionListener(actionListener);
        _standButton.addActionListener(actionListener);
        _exitButton.addActionListener(actionListener);
    }
}
