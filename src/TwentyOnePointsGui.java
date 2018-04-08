import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

final class OperatingSystem {
    public static final String OS_NAME = System.getProperty("os.name");

    public static String getLookAndFeel() {
        String osNameLowerCase = OS_NAME.toLowerCase();

        if (osNameLowerCase.contains("windows"))
            return "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        else if (osNameLowerCase.contains("linux"))
            return "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
        else
            return "javax.swing.plaf.metal.MetalLookAndFeel"; // too bad
    }
}

public class TwentyOnePointsGui extends JFrame {
    private TwentyOnePoints _top;

    private final int _frameWidth = 800;
    private final int _frameHeight = 600;
    private final int _gridGap = 8;

    private JPanel _mainPanel;
    private JPanel _leftPanel;
    private JPanel _rightPanel;

    private JPanel _upperLeftPanel;
    private JPanel _bottomLeftPanel;

    private JLabel _cardDeckSizeLabel;

    private final Font _robotoBold = new Font("Roboto", Font.BOLD, 32);

    TwentyOnePointsGui() throws Exception {
        super("21 Points - The Game");
        _top = new TwentyOnePoints();
    }

    private void updateCardDeckSizeLabel() {
        _cardDeckSizeLabel.setText(Integer.toString(_top.getCardDeckSize()));
    }

    private void elementsPlacement() {

    }

    private JPanel panelAssembly(LayoutManager layout, Dimension preferredSize, Color color) {
        JPanel panel = new JPanel();

        if (layout != null)
            panel.setLayout(layout);
        if (preferredSize != null)
            panel.setPreferredSize(preferredSize);

        panel.setBorder(new EmptyBorder(_gridGap, _gridGap, _gridGap, _gridGap));
        panel.setBackground(color);

        return panel;
    }

    public void windowAssembly() throws Exception {
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
//        this.setFont(new Font("Roboto", Font.PLAIN, 14)); // doesn't work
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);

        _mainPanel = panelAssembly(
                new GridBagLayout(),
                new Dimension(_frameWidth, _frameHeight),
                Color.LIGHT_GRAY);

        _leftPanel = panelAssembly(
                new GridLayout(2, 1, _gridGap, _gridGap),
                null,
                Color.WHITE);

        _upperLeftPanel = panelAssembly(null, null, Color.LIGHT_GRAY);
        _bottomLeftPanel = panelAssembly(null, null, Color.LIGHT_GRAY);

        // ^ OK

        JLabel upperLabel = new JLabel("Dealer Panel");
        JLabel bottomLabel = new JLabel("Player Panel");

        _upperLeftPanel.add(upperLabel);
        _bottomLeftPanel.add(bottomLabel);

        _leftPanel.add(_upperLeftPanel);
        _leftPanel.add(_bottomLeftPanel);

        _rightPanel = panelAssembly(new GridBagLayout(), null, Color.WHITE);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        ImageIcon cardDeck = new ImageIcon("images/card_back/green_back.png");

        JButton cardDeckButton = new JButton(cardDeck);
        cardDeckButton.setActionCommand("TakeCard");
        cardDeckButton.addActionListener(new ButtonClickListener());

        _rightPanel.add(cardDeckButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;

        _cardDeckSizeLabel = new JLabel(Integer.toString(_top.getCardDeckSize()));
        _cardDeckSizeLabel.setFont(_robotoBold);

        _rightPanel.add(_cardDeckSizeLabel, gridBagConstraints);

        elementsPlacement();

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

        this.add(_mainPanel);
        this.pack();
        this.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String command = actionEvent.getActionCommand();

            switch (command) {
                case "TakeCard":
                    _top.getCardFromDeck();
                    updateCardDeckSizeLabel();
                    break;
                case "Exit":
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
