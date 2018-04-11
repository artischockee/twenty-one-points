package view;

import model.*;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
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
    private JPanel _bottomLeftPanel;

    private JLayeredPane _dealerPane;
    private JLayeredPane _playerPane;
    private JLabel _dealerTotalPtsLabel;
    private JLabel _playerTotalPtsLabel;
    private JLabel _cardDeckSizeLabel;

    private JButton _cardDeckButton;

    // Bottom control panel:
    private JButton _playButton;
    private JButton _exitButton;

    private JButton _initialButton;


    // Constructor(s):

    public Application(GameModel model) throws IllegalArgumentException {
        super("21 Points - The Game");

        this.setIconImage(_appIcon);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);

        _model = model;

        _mainPanel = createPanel(
                new GridBagLayout(),
                new Dimension(_frameWidth, _frameHeight),
                new EmptyBorder(_gridGap, _gridGap, _gridGap, _gridGap));
        _mainPanel.setMaximumSize(new Dimension(_frameWidth, _frameHeight));

        _menuBar = new JMenuBar();
        _gameMenu = new JMenu("Game");
        _playGameMenuItem = new JMenuItem("Play Game");
        _exitGameMenuItem = new JMenuItem("Exit");
        _helpMenu = new JMenu("Help");
        _aboutMenuItem = new JMenuItem("About");

        _leftPanel = createPanel(
                new GridLayout(2, 1),
                null,
                null);

        _rightPanel = createPanel(
                new GridBagLayout(),
                null,
                BorderFactory.createTitledBorder("The Deck"));

        _bottomPanel = createPanel(
                new FlowLayout(FlowLayout.CENTER),
                null,
                null);

        _topLeftPanel = createPanel(
                null,
                null,
                BorderFactory.createTitledBorder("Dealer's hands"));
        _topLeftPanel.setLayout(new BoxLayout(_topLeftPanel, BoxLayout.PAGE_AXIS));

        _bottomLeftPanel = createPanel(
                null,
                null,
                BorderFactory.createTitledBorder("Player's hands"));
        _bottomLeftPanel.setLayout(new BoxLayout(_bottomLeftPanel, BoxLayout.PAGE_AXIS));

        _dealerPane = createLayeredPane(
                new Dimension(_topLeftPanel.getWidth(), _topLeftPanel.getHeight()),
                Component.CENTER_ALIGNMENT
        );

        _playerPane = createLayeredPane(
                new Dimension(_bottomLeftPanel.getWidth(), _bottomLeftPanel.getHeight()),
                Component.CENTER_ALIGNMENT
        );

        _dealerTotalPtsLabel = createLabel("Dealer's total points: 0",
                JLabel.CENTER, new Font("Roboto", Font.PLAIN, 12));
        _dealerTotalPtsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        _playerTotalPtsLabel = createLabel("Total points: 0",
                JLabel.CENTER, new Font("Roboto", Font.PLAIN, 12));
        _playerTotalPtsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        _cardDeckSizeLabel = new JLabel(Integer.toString(_model.getCardDeckSize()) + " cards");
        _cardDeckSizeLabel.setFont(new Font("Roboto", Font.BOLD, 32));

        _cardDeckButton = new JButton(new ImageIcon("images/card_back/green_back.png"));

        _playButton = new JButton("Play");
        _exitButton = new JButton("Exit");

        _initialButton = new JButton("Launch");

        this.initialWindowAssembly();

        this.setContentPane(_mainPanel);
        this.pack();
    }


    // Getters and setters:

    public JPanel getMainPanel() {
        return _mainPanel;
    }

    public JButton getInitialButton() {
        return _initialButton;
    }


    // Methods:

    private void updateCardDeckSizeLabel() {
        _cardDeckSizeLabel.setText(Integer.toString(_model.getCardDeckSize()) + " cards");
    }

    private JLabel createLabel(String text, int horizontalAlignment, Font font) {
        // TODO: 4/10/18 Check arguments

        JLabel label = new JLabel(text, horizontalAlignment);
        label.setFont(font);

        return label;
    }

    private JPanel createPanel(LayoutManager layout, Dimension preferredSize, Border border) {
        JPanel panel = new JPanel();

        if (layout != null)
            panel.setLayout(layout);
        if (preferredSize != null)
            panel.setPreferredSize(preferredSize);
        if (border != null)
            panel.setBorder(border);

        return panel;
    }

    private JLayeredPane createLayeredPane(Dimension dimension, float alignmentX) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(dimension);
        layeredPane.setAlignmentX(alignmentX);

        return layeredPane;
    }

    private void assembleInnerPanels() {
        // every inner panel
    }

    private void assembleMenuBar() {
        _gameMenu.setMnemonic(KeyEvent.VK_G);

        _playGameMenuItem.setActionCommand(Controller.ButtonClickListener.PLAY_GAME);
        _exitGameMenuItem.setActionCommand(Controller.ButtonClickListener.EXIT_GAME);

        _helpMenu.setMnemonic(KeyEvent.VK_H);

        _aboutMenuItem.setActionCommand(Controller.ButtonClickListener.ABOUT);

        // Assembling the elements:

        _gameMenu.add(_playGameMenuItem);
        _gameMenu.add(_exitGameMenuItem);
        _helpMenu.add(_aboutMenuItem);

        _menuBar.add(_gameMenu);
        _menuBar.add(_helpMenu);

        this.setJMenuBar(_menuBar);
    }

    public void initialWindowAssembly() {
        this.assembleMenuBar();

        // main panel
        // initial button

        _initialButton.setActionCommand(Controller.ButtonClickListener.LAUNCH_APP);

        _mainPanel.add(_initialButton);
    }

    public void windowAssembly() {
//        this.assembleInnerPanels();

        // dealer pane

        // player pane

        // playertotalptslabel

//        _playerTotalPtsLabel.addPropertyChangeListener(
//                propertyChangeEvent -> _playerTotalPtsLabel.setText(
//                        "Total points: " + _model.getCardPlayer(1).getPointsAmount()));

        // dealertotalptslabel

//        _dealerTotalPtsLabel.addPropertyChangeListener(
//                propertyChangeEvent -> _dealerTotalPtsLabel.setText(
//                        "Total points: " + _model.getCardPlayer(0).getPointsAmount()));

        // carddeckbutton

//        cardDeckButton.addActionListener(new Controller(
//        _playerPane,
//        _model.getCardPlayer(1).getCardDeck(),
//        _model.getCardDeck()));

        // Everything about right panel:

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        _rightPanel.add(_cardDeckButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        _rightPanel.add(_cardDeckSizeLabel, gbc);

        // Bottom panel:

        // playbutton
        _playButton.setActionCommand(Controller.ButtonClickListener.PLAY_GAME);

        // exitbutton
        _exitButton.setActionCommand(Controller.ButtonClickListener.EXIT_GAME);

        // Adding the elements:

        _topLeftPanel.add(_dealerPane);
        _topLeftPanel.add(_dealerTotalPtsLabel);
        _bottomLeftPanel.add(_playerPane);
        _bottomLeftPanel.add(_playerTotalPtsLabel);

        _leftPanel.add(_topLeftPanel);
        _leftPanel.add(_bottomLeftPanel);

        _bottomPanel.add(_playButton);
        _bottomPanel.add(_exitButton);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        _mainPanel.add(_leftPanel, gbc);

        gbc.weightx = 0.5;
        gbc.gridx = 1;
        gbc.gridy = 0;

        _mainPanel.add(_rightPanel, gbc);

        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 1;

        _mainPanel.add(_bottomPanel, gbc);

        this.setContentPane(_mainPanel);
        this.pack();
//        this.setVisible(true);
    }

    public void addButtonClickListener(ActionListener actionListener) {
        _initialButton.addActionListener(actionListener);
        _playGameMenuItem.addActionListener(actionListener);
        _exitGameMenuItem.addActionListener(actionListener);
        _aboutMenuItem.addActionListener(actionListener);
        _playButton.addActionListener(actionListener);
        _exitButton.addActionListener(actionListener);
    }

    public void addAnotherClickListener(ActionListener actionListener) {
        _cardDeckButton.addActionListener(actionListener);
    }
}
