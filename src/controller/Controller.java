package controller;

import model.GameModel;
import view.Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private GameModel _model;
    private Application _view;

    private int _x;
    private final int _y = 10;
    private final int _offset = 20;
    private int _layerPosition;

    public Controller(GameModel model, Application view) {
        _model = model;
        _view = view;

        _x = 10;
        _layerPosition = 0;

        view.addButtonClickListener(new ButtonClickListener());
        view.addAnotherClickListener(new AnotherClickListener());
    }

    public class ButtonClickListener implements ActionListener {
        public static final String PLAY_GAME = "PLAY_GAME";
        public static final String EXIT_GAME = "EXIT_GAME";
        public static final String LAUNCH_APP = "LAUNCH_APP";
        public static final String ABOUT = "ABOUT";

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String command = actionEvent.getActionCommand();

            switch (command) {
                case PLAY_GAME:
                    _model.initialActions();
                    break;
                case EXIT_GAME:
                    System.exit(0);
                    break;
                case LAUNCH_APP:
                    _view.getMainPanel().remove(_view.getInitialPanel());
                    _view.windowAssembly();
                    break;
                case ABOUT:
                    JOptionPane.showMessageDialog(_view,
                            "Twenty-One Points Game.\nAuthor: Artem Piskarev",
                            "About", JOptionPane.INFORMATION_MESSAGE);
                    break;
                // ...
            }
        }
    }

    public class AnotherClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.out.println("You are here / " + actionEvent.paramString());
//            Card card = _deck.pop();
//            String cardName = card.getCardNameSymbol();
//            String cardSuit = card.getSuitShortNotation();
//            ImageIcon cardImage = new ImageIcon("images/cards/" + cardName + cardSuit + ".png");
//            JLabel label = new JLabel(cardImage);
//            label.setBounds(_x, _y, cardImage.getIconWidth(), cardImage.getIconHeight());
//
//            _playerCards.add(card);
//            _pane.add(label, Integer.valueOf(++_layerPosition));
//            _x += _offset;
        }
    }
}