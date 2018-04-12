package view;

import model.*;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Application extends JFrame {
    // Attributes:

    private GameModel _model;

    private final int _frameWidth = 800;
    private final int _frameHeight = 600;
    private final int _gridGap = 8;

    private final Image _appIcon = Toolkit.getDefaultToolkit().getImage("images/favicon.png");

    private JPanel _mainPanel;

    private JMenuBar _menuBar;
    private JMenu _gameMenu;
    private JMenuItem _playGameMenuItem;
    private JMenuItem _exitGameMenuItem;
    private JMenu _helpMenu;
    private JMenuItem _aboutMenuItem;

    private JPanel _leftPanel;
    private JPanel _rightPanel;
    private JPanel _bottomPanel;

    private JPanel _topLeftPanel;
    private JLayeredPane _dealerPane;
    private JPanel _dealerPointsPanel;
    private JLabel _dealerTotalStaticLabel;
    private JLabel _dealerTotalPtsLabel;

    private JPanel _bottomLeftPanel;
    private JLayeredPane _playerPane;
    private JPanel _playerPointsPanel;
    private JLabel _playerTotalStaticLabel;
    private JLabel _playerTotalPtsLabel;

    private int _playerPaneBoundX;
    private int _playerPaneBoundY;
    private int _playerPaneOffset;
    private int _playerPaneLayerPosition;

    private JButton _cardDeckButton;
    private JLabel _cardDeckSizeLabel;

    // Bottom control panel:
    private JButton _playButton;
    private JButton _hitButton;
    private JButton _standButton;
    private JButton _exitButton;

    // Initial window:
    private JPanel _initPanel;
    private JLabel _initGreetingsLabel;
    private JLabel _initPictureLabel;
    private JButton _initLaunchButton;
    private JButton _initExitButton;


    // Constructor:

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

        _playerPaneBoundX = 0;
        _playerPaneBoundY = 0;
        _playerPaneOffset = 30;
        _playerPaneLayerPosition = 0;

        this.assembleInnerLabels();

        _cardDeckButton = new JButton(new ImageIcon("images/card_back/green_back.png"));
        _cardDeckButton.setEnabled(false);

        _playButton = new JButton("New Game");
        _hitButton = new JButton("Hit (Take Card)");
        _hitButton.setEnabled(false);
        _standButton = new JButton("Stand (Pass Round)");
        _standButton.setEnabled(false);
        _exitButton = new JButton("Exit");

        this.initialWindowAssembly();

        this.setContentPane(_mainPanel);
        this.pack();
    }


    // Getters and setters:

    public JPanel getMainPanel() {
        return _mainPanel;
    }

    public JPanel getInitialPanel() {
        return _initPanel;
    }

    public JLayeredPane getPlayerPane() {
        return _playerPane;
    }

    public int getPlayerPaneBoundX() {
        return _playerPaneBoundX;
    }

    public void setPlayerPaneBoundX(int newBound) {
        // args check

        this._playerPaneBoundX = newBound;
    }

    public int getPlayerPaneBoundY() {
        return _playerPaneBoundY;
    }

    public void setPlayerPaneBoundY(int newBound) {
        // args check

        this._playerPaneBoundY = newBound;
    }

    public int getPlayerPaneOffset() {
        return _playerPaneOffset;
    }

    public int getPlayerPaneLayerPosition() {
        return _playerPaneLayerPosition;
    }

    public void setPlayerPaneLayerPosition(int newPosition) {
        // args check

        this._playerPaneLayerPosition = newPosition;
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


    // Methods:

    private void assembleInnerLabels() {
        _dealerTotalStaticLabel = new JLabel("Dealer's total points:");
        _dealerTotalPtsLabel = new JLabel("0");

        _playerTotalStaticLabel = new JLabel("Total points:");
        _playerTotalPtsLabel = new JLabel("0");

        // make the same as above to the cardDeckSizeLabel
        _cardDeckSizeLabel = new JLabel(Integer.toString(_model.getCardDeckSize()) + " cards");
        _cardDeckSizeLabel.setFont(new Font("Roboto", Font.BOLD, 32));
    }

    private void assembleInnerPanels() {
        _leftPanel = GuiCreator.createPanel(new GridLayout(2, 1));

        _rightPanel = GuiCreator.createPanel(
                new GridBagLayout(),
                BorderFactory.createTitledBorder("The Deck"));

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
        _exitGameMenuItem.setActionCommand(Controller.ButtonClickListener.EXIT_GAME);

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

    private void initialWindowAssembly() {
        _initPanel = new JPanel(new GridBagLayout());

        _initGreetingsLabel = new JLabel("Welcome to Twenty-One Points Game!");
        _initGreetingsLabel.setFont(new Font("Roboto", Font.BOLD, 16));

        _initPictureLabel = new JLabel(new ImageIcon("images/favicon.png"), JLabel.CENTER);

        _initLaunchButton = new JButton("Launch game");
        _initLaunchButton.setActionCommand(Controller.ButtonClickListener.LAUNCH_APP);

        _initExitButton = new JButton("Exit");
        _initExitButton.setActionCommand(Controller.ButtonClickListener.EXIT_GAME);

        GBConstraints gbc = new GBConstraints(GridBagConstraints.BOTH);

        gbc.assembly(GBConstraints.Y, new Insets(0, 0, 32, 0));
        _initPanel.add(_initGreetingsLabel, gbc);

        gbc.assembly(GBConstraints.Y);
        _initPanel.add(_initPictureLabel, gbc);

        gbc.assembly(GBConstraints.Y, new Insets(16, 0, 0, 0));
        _initPanel.add(_initLaunchButton, gbc);

        gbc.assembly(GBConstraints.Y, new Insets(4, 0, 0, 0));
        _initPanel.add(_initExitButton, gbc);

        _mainPanel.add(_initPanel);
    }

    public void windowAssembly() {
        GBConstraints gbc = new GBConstraints(GridBagConstraints.VERTICAL);

        _rightPanel.add(_cardDeckButton, gbc);
        gbc.assembly(GBConstraints.Y);
        _rightPanel.add(_cardDeckSizeLabel, gbc);

        _cardDeckButton.setActionCommand(Controller.ButtonClickListener.HIT);

        // For bottom panel:
        _playButton.setActionCommand(Controller.ButtonClickListener.NEW_GAME);
        _hitButton.setActionCommand(Controller.ButtonClickListener.HIT);
        _standButton.setActionCommand(Controller.ButtonClickListener.STAND);
        _exitButton.setActionCommand(Controller.ButtonClickListener.EXIT_GAME);

        // Adding the elements:

        _topLeftPanel.add(_dealerPane, BorderLayout.CENTER);
        _dealerPointsPanel.add(_dealerTotalStaticLabel);
        _dealerPointsPanel.add(_dealerTotalPtsLabel);
        _topLeftPanel.add(_dealerPointsPanel, BorderLayout.PAGE_END);

        _bottomLeftPanel.add(_playerPane, BorderLayout.CENTER);
        _playerPointsPanel.add(_playerTotalStaticLabel);
        _playerPointsPanel.add(_playerTotalPtsLabel);
        _bottomLeftPanel.add(_playerPointsPanel, BorderLayout.PAGE_END);

        _leftPanel.add(_topLeftPanel);
        _leftPanel.add(_bottomLeftPanel);

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

    public void addButtonClickListener(ActionListener actionListener) {
        // Initial panel:
        _initLaunchButton.addActionListener(actionListener);
        _initExitButton.addActionListener(actionListener);
        // Menu bar:
        _playGameMenuItem.addActionListener(actionListener);
        _exitGameMenuItem.addActionListener(actionListener);
        _aboutMenuItem.addActionListener(actionListener);
        // Any buttons:
        _cardDeckButton.addActionListener(actionListener);
        _playButton.addActionListener(actionListener);
        _hitButton.addActionListener(actionListener);
        _standButton.addActionListener(actionListener);
        _exitButton.addActionListener(actionListener);
    }

    public void addModelChangeListener(ActionListener actionListener) {
//        _cardDeckButton.addActionListener(actionListener);
    }
}
