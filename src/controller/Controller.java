package controller;

import model.Card;
import model.GameModel;
import view.Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private GameModel _model;
    private Application _view;

    public Controller(GameModel model, Application view) {
        _model = model;
        _view = view;

        view.addButtonClickListener(new ButtonClickListener());
        view.addModelChangeListener(new ModelChangeListener());
    }

    public class ButtonClickListener implements ActionListener {
        public static final String NEW_GAME = "NEW_GAME";
        public static final String EXIT_GAME = "EXIT_GAME";
        public static final String LAUNCH_APP = "LAUNCH_APP";
        public static final String HIT = "HIT";
        public static final String STAND = "STAND";
        public static final String ABOUT = "ABOUT";

        private void invokeGameRoutine() {
            _model.initialActions();

            // Not code, just piece of sheet:

            int layerOffset = _view.getCardPlayerPaneOffset();
            for (int i = 0; i < _model.getCardPlayers().size(); ++i) {
                Card card = _model.getCardPlayer(i).getCardDeck().lastElement();

                int layerBoundX = 0;
                int layerBoundY = 0;
                int layerPosition = 0;

                if (i == 0) {
                    layerBoundX = _view.getDealerPaneBoundX();
                    layerBoundY = _view.getDealerPaneBoundY();
                    layerPosition = _view.getDealerPaneLayerPosition();
                }
                else if (i == 1) {
                    layerBoundX = _view.getPlayerPaneBoundX();
                    layerBoundY = _view.getPlayerPaneBoundY();
                    layerPosition = _view.getPlayerPaneLayerPosition();
                }

                String cardName = card.getCardNameSymbol();
                String cardSuit = card.getSuitShortNotation();
                ImageIcon cardImage = new ImageIcon("images/cards/" + cardName + cardSuit + ".png");
                JLabel label = new JLabel(cardImage);
                label.setBounds(layerBoundX, layerBoundY, cardImage.getIconWidth(), cardImage.getIconHeight());

                if (i == 0) {
                    _view.getDealerPane().add(label, Integer.valueOf(layerPosition));
                    _view.setDealerPaneLayerPosition(++layerPosition);
                    _view.setDealerPaneBoundX(layerBoundX + layerOffset);
                }
                else if (i == 1) {
                    _view.getPlayerPane().add(label, Integer.valueOf(layerPosition));
                    _view.setPlayerPaneLayerPosition(++layerPosition);
                    _view.setPlayerPaneBoundX(layerBoundX + layerOffset);
                }
            }

            this.refreshDynamicFields();

            _view.getCardDeckButton().setEnabled(true);
            _view.getHitButton().setEnabled(true);
            _view.getStandButton().setEnabled(true);
        }

        private void launchApplication() {
            _view.getMainPanel().remove(_view.getInitialPanel());
            _view.windowAssembly();
        }

        private void refreshDynamicFields() {
            _view.getDealerTotalPtsLabel().setText(
                    Integer.toString(_model.getCardPlayer(0).getPointsAmount()));

            _view.getPlayerTotalPtsLabel().setText(
                    Integer.toString(_model.getCardPlayer(1).getPointsAmount()));

            _view.getCardDeckSizeLabel().setText(
                    Integer.toString(_model.getCardDeckSize()));
        }

        private void showExitDialog() {
            int answer = JOptionPane.showConfirmDialog(_view,
                    "Are you sure you want to quit the game?",
                    "Game quit",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            switch (answer) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    return;
            }
        }

        private void showAboutDialog() {
            JOptionPane.showMessageDialog(
                    _view,
                    "Twenty-One Points Game.\nAuthor: Artem Piskarev",
                    "About", JOptionPane.INFORMATION_MESSAGE);
        }

        private void showEmptyDeckWarningDialog() {
            JOptionPane.showMessageDialog(
                    _view, "There are no cards left on the deck!",
                    "The card deck is empty",
                    JOptionPane.WARNING_MESSAGE);
        }

        private void addCardFromDeck() {
            if (_model.getCardDeck().isEmpty()) {
                this.showEmptyDeckWarningDialog();
                return;
            }

            Card card = _model.getCardDeck().pop();

            _view.getCardDeckSizeLabel().setText(Integer.toString(_model.getCardDeckSize()));

            int layerBoundX = _view.getPlayerPaneBoundX();
            int layerBoundY = _view.getPlayerPaneBoundY();
            int layerOffset = _view.getCardPlayerPaneOffset();
            int layerPosition = _view.getPlayerPaneLayerPosition();

            String cardName = card.getCardNameSymbol();
            String cardSuit = card.getSuitShortNotation();
            ImageIcon cardImage = new ImageIcon("images/cards/" + cardName + cardSuit + ".png");
            JLabel label = new JLabel(cardImage);
            label.setBounds(layerBoundX, layerBoundY, cardImage.getIconWidth(), cardImage.getIconHeight());

            _model.getCardPlayer(1).addCard(card);
            _view.getPlayerTotalPtsLabel().setText(
                    Integer.toString(_model.getCardPlayer(1).getPointsAmount()));
            _view.getPlayerPane().add(label, Integer.valueOf(layerPosition));
            _view.setPlayerPaneLayerPosition(++layerPosition);
            _view.setPlayerPaneBoundX(layerBoundX + layerOffset);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String command = actionEvent.getActionCommand();

            switch (command) {
                case NEW_GAME:
                    // TODO: 4/12/18 Check if the game is already played
                    this.invokeGameRoutine();
                    break;
                case EXIT_GAME:
                    this.showExitDialog();
                    break;
                case LAUNCH_APP:
                    this.launchApplication();
                    break;
                case HIT:
                    this.addCardFromDeck();
                    break;
                case STAND:
                    // ..
                    break;
                case ABOUT:
                    this.showAboutDialog();
                    break;
            }
        }
    }

    public class ModelChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.out.println("You are here, pal.");
            // ...
        }
    }
}