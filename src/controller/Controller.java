package controller;

import model.*;
import view.Application;
import view.LayeredPane;

import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

public class Controller {
    private GameModel model;
    private Application view;

    public Controller(GameModel model, Application view) {
        this.model = model;
        this.view = view;

        this.view.addButtonClickListener(new ButtonClickListener());
    }

    public class ButtonClickListener implements ActionListener {
        public static final String NEW_GAME = "NEW_GAME";
        public static final String EXIT_GAME = "EXIT_GAME";
        public static final String EXIT_GAME_NO_DIALOG = "EXIT_GAME_NO_DIALOG";
        public static final String LAUNCH_APP = "LAUNCH_APP";
        public static final String GET_CARD = "GET_CARD";
        public static final String PASS_ROUND = "PASS_ROUND";
        public static final String HOW_TO_PLAY = "HOW_TO_PLAY";
        public static final String ABOUT = "ABOUT";


        private ImageIcon getCardImage(Card card) {
            String cardName = card.getCardNameSymbol();
            String cardSuit = card.getSuitShortNotation();

            return new ImageIcon("resources/cards/" + cardName + cardSuit + ".png");
        }

        private void pushCardToPane(LayeredPane targetPane, Vector<Card> targetDeck) {
            if (model.isCardDeckEmpty()) {
                view.showEmptyDeckWarningDialog();
                return;
            }

            int layerBoundX = targetPane.getBoundX();
            int layerBoundY = targetPane.getBoundY();
            int layerPosition = targetPane.getLayerPosition();

            Card card = model.getCardFromCardDeck();

            ImageIcon cardImage = getCardImage(card);
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
            if (view.containsInitPanel())
                view.launchApplication();

            if (model.isRun()) {
                int response = view.showNewGameDialog();

                switch (response) {
                    // if user wants to reload the game
                    case 0:
                        model.reload();
                        view.reloadPanes();
                        break;
                    // if user does not want to do so
                    case 1:
                        return;
                }
            }

            model.run();

            this.pushCardToPane(
                    view.getDealerPane(),
                    model.getCardPlayer(0).getCardDeck());

            this.pushCardToPane(
                    view.getPlayerPane(),
                    model.getCardPlayer(1).getCardDeck());

            this.refreshDynamicFields();

            view.switchInteractButtons(true);
        }

        private void refreshDynamicFields() {
            view.getDealerTotalPtsLabel().setText(
                    Integer.toString(model.getCardPlayer(0).getPointsAmount()));

            view.getPlayerTotalPtsLabel().setText(
                    Integer.toString(model.getCardPlayer(1).getPointsAmount()));
        }

        // Apparently, this method is used here temporarily (may be it's not)
        private void processUserResponse(int response) {
            switch (response) {
                // if user wants to play the game again
                case 0:
                    invokeGameRoutine();
                    break;
                // if user wants to quit
                case 1:
                    System.exit(0);
            }
        }

        private void winDialog(CardPlayer wonPlayer) {
            int response = view.showWinGameDialog(wonPlayer.getPlayerName());

            processUserResponse(response);
        }

        private void exceedDialog(CardPlayer exceededPlayer) {
            int response = view.showLoseGameDialog(exceededPlayer.getPlayerName());

            processUserResponse(response);
        }

        private void exitDialog() {
            int response = view.showExitDialog();

            switch (response) {
                case 0:
                    System.exit(0);
                case 1:
                    return;
            }
        }

        private void checkComputerCondition(CardPlayer computer) {
            Timer timer = new Timer(800, null);

            timer.addActionListener(actionEvent -> {
                computer.analyzeTurn();

                if (computer.hasPassed() || computer.hasWon() || computer.hasExceeded()) {
                    timer.stop();

                    if (computer.hasPassed())
                        JOptionPane.showMessageDialog(view, computer.getPlayerName() + " has passed.");
                    else if (computer.hasExceeded())
                        JOptionPane.showMessageDialog(view, computer.getPlayerName() + " has exceeded the points limit.");
                    else if (computer.hasWon())
                        JOptionPane.showMessageDialog(view, computer.getPlayerName() + " has won!");
                }
                else
                    addCardFromDeck(0);
            });

            timer.start();
        }

        private void checkCondition(CardPlayer cardPlayer) {
            cardPlayer.analyzeTurn();

            if (cardPlayer.hasExceeded())
                exceedDialog(cardPlayer);
            else if (cardPlayer.hasWon())
                winDialog(cardPlayer);
        }

        private void addCardFromDeck(int playerIndex) {
            // lame logic (temporarily):
            if (playerIndex == 0)
                pushCardToPane(
                    view.getDealerPane(),
                    model.getCardPlayer(playerIndex).getCardDeck());
            else
                pushCardToPane(
                    view.getPlayerPane(),
                    model.getCardPlayer(playerIndex).getCardDeck());

            refreshDynamicFields();
        }

        private void getPlayersTable() {
            Vector<String> table = new Vector<>();

            for (CardPlayer cardPlayer : model.getCardPlayers()) {
                String message = cardPlayer.isDealer()
                        ? "[Dealer] " + cardPlayer.getPlayerName()
                        : cardPlayer.getPlayerName();
                message = message + ": " + cardPlayer.getPointsAmount();

                table.add(message);
            }
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String command = actionEvent.getActionCommand();

            switch (command) {
                case NEW_GAME:
                    invokeGameRoutine();
                    break;
                case EXIT_GAME:
                    exitDialog();
                    break;
                case EXIT_GAME_NO_DIALOG:
                    System.exit(0);
                    break;
                case LAUNCH_APP:
                    view.launchApplication();
                    break;
                case GET_CARD:
                    addCardFromDeck(1);
                    checkCondition(model.getCardPlayer(1));
                    break;
                case PASS_ROUND:
                    view.switchInteractButtons(false);
                    checkComputerCondition(model.getCardPlayer(0));
                    break;
                case HOW_TO_PLAY:
//                    view.showHowToPlayDialog();
                    break;
                case ABOUT:
                    view.showAboutDialog();
                    break;
            }
        }
    }
}