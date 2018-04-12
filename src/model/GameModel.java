package model;

import java.util.*;

public class GameModel {
    public static final int DECK_SIZE = 36;
    public static final int MAX_SCORE = 21;
    public static final int MAX_DEALER_TOTAL = 17;

    public static final int MAX_WEIGHT = Collections.max(
            CardDeckCreator.getWeights21(),
            Comparator.comparingInt(Integer::intValue));

    public static final int MIN_WEIGHT = Collections.min(
            CardDeckCreator.getWeights21(),
            Comparator.comparingInt(Integer::intValue));

    private Stack<Card> _cardDeck;
    private Vector<CardPlayer> _cardPlayers;

    public GameModel() throws IllegalArgumentException {
        _cardDeck = CardDeckCreator.createDeck(DECK_SIZE);
        _cardPlayers = new Vector<>();
    }

    public Stack<Card> getCardDeck() {
        return _cardDeck;
    }

    public int getCardDeckSize() {
        return _cardDeck.size();
    }

    public Vector<CardPlayer> getCardPlayers() {
        return _cardPlayers;
    }

    public CardPlayer getCardPlayer(int playerIndex) {
        // <- argument checking

        return _cardPlayers.elementAt(playerIndex);
    }

//    public Vector<Card> getPlayerDeck(int playerIndex) {
//        // <- argument checking
//
//        return _cardPlayers.elementAt(playerIndex).getCardDeck();
//    }

    private void shuffleDeck() {
        Collections.shuffle(_cardDeck);

        System.out.println("shuffleDeck - ok");
    }

    private void performPlayerActions(CardPlayer player) {
        while (!player.hasPassed()
                && !player.hasExceeded()
                && !player.hasWon()) {
            TurnStatement statement = player.analyzeTurn();
            switch (statement) {
                case HIT:
                    // TODO: 4/9/18 Check if there is any card left in the deck before taking it
                    player.addCard(_cardDeck.pop());
                    break;
                case STAND:
                    player.setPass(true);
                    break;
                case WIN:
                    player.setWin(true);
                    break;
                case EXCEED:
                    player.setExceed(true);
                    break;
            }
        }
    }

    private void firstCardDistribution() {
        for (CardPlayer cardPlayer : _cardPlayers) {
            // TODO: 4/6/18 add exception handling in case of cardDeck is empty
            cardPlayer.addCard(_cardDeck.pop());
        }

        System.out.println("firstCardDistribution - ok");
    }

    public void initialActions() {
        this.shuffleDeck();

        _cardPlayers.add(new Computer());
        _cardPlayers.add(new Player());
        _cardPlayers.elementAt(0).setDealer(true);

        this.firstCardDistribution();

        System.out.println("initialActions - ok");
    }

    public void runPlayerTurns(boolean forRegularPlayers) {
        for (CardPlayer cardPlayer : _cardPlayers) {
            if (cardPlayer.isDealer() != forRegularPlayers)
                performPlayerActions(cardPlayer);
        }
    }

    public void playGame() {
//        this.initialActions();
//        this.runPlayerTurns(true);
//        this.runPlayerTurns(false);

        System.out.println("Now the players open their hands..");
        for (CardPlayer cardPlayer : _cardPlayers) {
            System.out.println(cardPlayer.getPlayerName() + "'s cards:");
            cardPlayer.showCardDeck();
            System.out.println("Total points: " + cardPlayer.getPointsAmount());
        }
    }
}
