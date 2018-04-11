package view;

import model.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

final class Command {
    public static final String TAKE_CARD = "TAKE_CARD";
    public static final String LAUNCH_GAME = "LAUNCH_GAME";
    public static final String EXIT = "EXIT";
}

public class TwentyOnePointsGui extends JFrame {
    private TwentyOnePoints _top;

    private final int _frameWidth = 800;
    private final int _frameHeight = 600;
    private final int _gridGap = 8;

    private final Image _appIcon = Toolkit.getDefaultToolkit().getImage("images/favicon.gif");

    private JPanel _mainPanel;

    private JPanel _leftPanel;
    private JPanel _rightPanel;
    private JPanel _bottomPanel;

    private JPanel _topLeftPanel;
    private JPanel _bottomLeftPanel;

    private JLayeredPane _playerPane;
    private JLabel _playerTotalPtsLabel;
    private JLabel _cardDeckSizeLabel;

    private final Font _robotoBold = new Font("Roboto", Font.BOLD, 32);

    public TwentyOnePointsGui() throws Exception {
        super("21 Points - The Game");
        _top = new TwentyOnePoints();
    }

    private void updateCardDeckSizeLabel() {
        _cardDeckSizeLabel.setText(Integer.toString(_top.getCardDeckSize()) + " cards");
    }

    private JLabel createDeckLabel(String text, int horizontalAlignment, Font font) {
        // TODO: 4/10/18 Check arguments

        JLabel label = new JLabel(text, horizontalAlignment);
        label.setFont(font);

        return label;
    }

    private JPanel panelAssembly(LayoutManager layout, Dimension preferredSize, Border border) {
        JPanel panel = new JPanel();

        if (layout != null)
            panel.setLayout(layout);
        if (preferredSize != null)
            panel.setPreferredSize(preferredSize);
        if (border != null)
            panel.setBorder(border);

        return panel;
    }

    public void windowAssembly() {
        this.setIconImage(_appIcon);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);

        _mainPanel = panelAssembly(
                new GridBagLayout(),
                new Dimension(_frameWidth, _frameHeight),
                new EmptyBorder(_gridGap, _gridGap, _gridGap, _gridGap));

        _leftPanel = panelAssembly(
                new GridLayout(2, 1),
                null,
                null);

        _rightPanel = panelAssembly(
                new GridBagLayout(),
                null,
                BorderFactory.createTitledBorder("The Deck"));

        _bottomPanel = panelAssembly(
                new FlowLayout(FlowLayout.CENTER),
                null,
                null
        );

        _topLeftPanel = panelAssembly(
                null,
                null,
                BorderFactory.createTitledBorder("Dealer's hands"));
        _topLeftPanel.setLayout(new BoxLayout(_topLeftPanel, BoxLayout.PAGE_AXIS));

        _bottomLeftPanel = panelAssembly(
                null,
                null,
                BorderFactory.createTitledBorder("model.Player's hands"));
        _bottomLeftPanel.setLayout(new BoxLayout(_bottomLeftPanel, BoxLayout.PAGE_AXIS));

        // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

        // for top panel:

        JLayeredPane dealerPane = new JLayeredPane();
        dealerPane.setPreferredSize(new Dimension(_topLeftPanel.getWidth(), _topLeftPanel.getHeight()));

        dealerPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        _topLeftPanel.add(dealerPane);

        // for bottom panel:

        _playerPane = new JLayeredPane();
        _playerPane.setPreferredSize(new Dimension(_bottomLeftPanel.getWidth(), _bottomLeftPanel.getHeight()));
        _playerPane.addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                System.out.println("Hello");
            }
        });

        _playerPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        _playerTotalPtsLabel = createDeckLabel("Total points: ",
                JLabel.CENTER, new Font("Roboto", Font.PLAIN, 12));
        _playerTotalPtsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        _bottomLeftPanel.add(_playerPane);
        _bottomLeftPanel.add(_playerTotalPtsLabel);

        /* -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- */

        JLabel totalPointsLabel = createDeckLabel(
                "Dealer's total points: %points%",
                JLabel.CENTER,
                new Font("Roboto", Font.PLAIN, 12));
        totalPointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        _topLeftPanel.add(totalPointsLabel);


        _leftPanel.add(_topLeftPanel);
        _leftPanel.add(_bottomLeftPanel);


        // Everything about right panel:

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        ImageIcon cardDeck = new ImageIcon("images/card_back/green_back.png");

        JButton cardDeckButton = new JButton(cardDeck);
        cardDeckButton.setActionCommand(Command.TAKE_CARD);
        cardDeckButton.addActionListener(new ButtonClickListener());

        _rightPanel.add(cardDeckButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;

        _cardDeckSizeLabel = new JLabel(Integer.toString(_top.getCardDeckSize()) + " cards");
        _cardDeckSizeLabel.setFont(_robotoBold);

        _rightPanel.add(_cardDeckSizeLabel, gridBagConstraints);


        // Everything about bottom panel:

        JButton gameLaunchButton = new JButton("Launch game");

        gameLaunchButton.setActionCommand(Command.LAUNCH_GAME);
        gameLaunchButton.addActionListener(new ButtonClickListener());

        JButton exitGameButton = new JButton("Exit");

        exitGameButton.setActionCommand(Command.EXIT);
        exitGameButton.addActionListener(new ButtonClickListener());

        _bottomPanel.add(gameLaunchButton);
        _bottomPanel.add(exitGameButton);

        // Adding the elements:

        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;

        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        _mainPanel.add(_leftPanel, gridBagConstraints);

        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;

        _mainPanel.add(_rightPanel, gridBagConstraints);

        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridy = 1;

        _mainPanel.add(_bottomPanel, gridBagConstraints);

        this.add(_mainPanel);
        this.pack();
        this.setVisible(true);
    }

    private int x = 20;
    private int y = 20;
    private int offset = 20;
    private int i = 0;

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String command = actionEvent.getActionCommand();

            switch (command) {
                case Command.LAUNCH_GAME:
                    _top.launchGameGui();
                    break;
                case Command.TAKE_CARD:
                    Card card = _top.getCard();
                    String cardName = card.getCardNameSymbol();
                    String cardSuit = card.getSuitShortNotation();
                    ImageIcon cardImage = new ImageIcon("images/cards/" + cardName + cardSuit + ".png");
                    JLabel label = new JLabel(cardImage);
                    label.setBounds(x, y, cardImage.getIconWidth(), cardImage.getIconHeight());
                    _playerPane.add(label, Integer.valueOf(i++));
                    x += offset;
                    updateCardDeckSizeLabel();
                    break;
                case Command.EXIT:
                    System.exit(0);
                    break;
                case "Popup":
                    JOptionPane.showMessageDialog(
                        _mainPanel,
                        "Hello",
                        "My message",
                        JOptionPane.PLAIN_MESSAGE);
                    break;
            }
        }
    }

}
