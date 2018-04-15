package controller;

import model.*;
import view.Application;
import view.LayeredPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Controller {
    // Attributes ::

    private GameModel _model;
    private Application _view;


    // Constructor ::

    public Controller(GameModel model, Application view) {
        _model = model;
        _view = view;

        view.addButtonClickListener(new ButtonClickListener());
    }


    // Inner class(es) ::

    public class ButtonClickListener implements ActionListener {
        // Attributes ::

        public static final String NEW_GAME = "NEW_GAME";
        public static final String EXIT_GAME = "EXIT_GAME";
        public static final String EXIT_GAME_NO_DIALOG = "EXIT_GAME_NO_DIALOG";
        public static final String LAUNCH_APP = "LAUNCH_APP";
        public static final String GET_CARD = "GET_CARD";
        public static final String PASS_ROUND = "PASS_ROUND";
        public static final String ABOUT = "ABOUT";


        // Methods ::

        private ImageIcon getCardImage(Card card) {
            String cardName = card.getCardNameSymbol();
            String cardSuit = card.getSuitShortNotation();

            return new ImageIcon("resources/cards/" + cardName + cardSuit + ".png");
        }

        private void pushCardToPane(LayeredPane targetPane, Vector<Card> targetDeck) {
            if (_model.isCardDeckEmpty()) {
                _view.showEmptyDeckWarningDialog();
                return;
            }

            int layerBoundX = targetPane.getBoundX();
            int layerBoundY = targetPane.getBoundY();
            int layerPosition = targetPane.getLayerPosition();

            Card card = _model.getCardFromCardDeck();

            ImageIcon cardImage = this.getCardImage(card);
            JLabel label = new JLabel(cardImage);
            label.setBounds(
                    layerBoundX, layerBoundY,
                    cardImage.getIconWidth(), cardImage.getIconHeight());

            targetDeck.add(card);

            targetPane.add(label, Integer.valueOf(layerPosition));
            targetPane.setLayerPosition(++layerPosition);
            targetPane.setBoundX(layerBoundX + LayeredPane.OFFSET);
        }

        private void invokeGameRoutine() {
            if (_view.containsInitPanel())
                _view.launchApplication();

            if (_model.isRun()) {
                int response = _view.showNewGameDialog();

                switch (response) {
                    // if user wants to reload the game
                    case 0:
                        _model.reload();
                        _view.reloadPanes();
                        break;
                    // if user does not want to do so
                    case 1:
                        return;
                }
            }

            _model.run();

            this.pushCardToPane(
                    _view.getDealerPane(),
                    _model.getCardPlayer(0).getCardDeck());

            this.pushCardToPane(
                    _view.getPlayerPane(),
                    _model.getCardPlayer(1).getCardDeck());

            this.refreshDynamicFields();

            _view.getCardDeckButton().setEnabled(true);
            _view.getHitButton().setEnabled(true);
            _view.getStandButton().setEnabled(true);
        }

        private void refreshDynamicFields() {
            _view.getDealerTotalPtsLabel().setText(
                    Integer.toString(_model.getCardPlayer(0).getPointsAmount()));

            _view.getPlayerTotalPtsLabel().setText(
                    Integer.toString(_model.getCardPlayer(1).getPointsAmount()));

            _view.getCardDeckSizeLabel().setText(
                    Integer.toString(_model.getCardDeckSize()));
        }

        // Apparently, this method is used here temporarily
        // (may be it's not)
        private void processUserResponse(int response) {
            switch (response) {
                // if user wants to play the game again
                case 0:
                    this.invokeGameRoutine();
                    break;
                // if user wants to quit
                case 1:
                    System.exit(0);
            }
        }

        private void winDialog() {
            int response = _view.showWinGameDialog();

            processUserResponse(response);
        }

        private void exceedDialog() {
            int response = _view.showLoseGameDialog();

            processUserResponse(response);
        }

        private void exitDialog() {
            int response = _view.showExitDialog();

            switch (response) {
                case 0:
                    System.exit(0);
                case 1:
                    return;
            }
        }

        private void checkComputerCondition(CardPlayer computer) {
            TurnStatement condition = computer.analyzeTurn();

            switch (condition) {
                case HIT:
                    this.addCardFromDeck(0);
                    this.checkComputerCondition(_model.getCardPlayer(0));
                    break;
                case STAND:
                    JOptionPane.showMessageDialog(_view, "Stop, I'm tired (c) Computer.");
                    break;
                case EXCEED:
                    JOptionPane.showMessageDialog(_view, "Computer has exceeded the points limit.");
                    break;
                case WIN:
                    JOptionPane.showMessageDialog(_view, "Computer wins with 21 points!");
                    break;
            }

            System.out.println("end of the method checkpoint");

//            TurnStatement condition = computerPlayer.analyzeTurn();
//
//            switch (condition) {
//                case HIT:
//                    this.addCardFromDeck(0);
//                    this.checkComputerCondition(_model.getCardPlayer(0));
//                    break;
//                case STAND:
//                    JOptionPane.showMessageDialog(_view, "Stop, I'm tired (c) Computer.");
//                    break;
//                case EXCEED:
//                    JOptionPane.showMessageDialog(_view, "Computer has exceeded the points limit.");
//                    break;
//                case WIN:
//                    JOptionPane.showMessageDialog(_view, "Computer wins with 21 points!");
//                    break;
//            }
        }

        private void checkCondition(CardPlayer cardPlayer) {
            TurnStatement condition = cardPlayer.analyzeTurn();

            switch (condition) {
                case EXCEED:
                    this.exceedDialog();
                    break;
                case WIN:
                    this.winDialog();
                    break;
            }
        }

        private void addCardFromDeck(int playerIndex) {
            // lame logic (temporarily):
            if (playerIndex == 0)
                this.pushCardToPane(
                    _view.getDealerPane(),
                    _model.getCardPlayer(playerIndex).getCardDeck());
            else
                this.pushCardToPane(
                    _view.getPlayerPane(),
                    _model.getCardPlayer(playerIndex).getCardDeck());

            this.refreshDynamicFields();
        }

        // TODO: 4/15/18 to be renamed and probably placed in the View impl.
        private void lockButtons() {
            _view.getCardDeckButton().setEnabled(false);
            _view.getHitButton().setEnabled(false);
            _view.getStandButton().setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String command = actionEvent.getActionCommand();

            switch (command) {
                case NEW_GAME:
                    this.invokeGameRoutine();
                    break;
                case EXIT_GAME:
                    this.exitDialog();
                    break;
                case EXIT_GAME_NO_DIALOG:
                    System.exit(0);
                    break;
                case LAUNCH_APP:
                    _view.launchApplication();
                    break;
                case GET_CARD:
                    this.addCardFromDeck(1);
                    this.checkCondition(_model.getCardPlayer(1));
                    break;
                case PASS_ROUND:
                    this.lockButtons();
                    this.checkComputerCondition(_model.getCardPlayer(0));
                    break;
                case ABOUT:
                    _view.showAboutDialog();
                    break;
            }
        }
    }
}