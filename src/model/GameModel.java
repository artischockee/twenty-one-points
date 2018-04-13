package model;

import java.util.*;

public class GameModel {
    // Attributes ::

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
    private boolean _isRun;

    // Constructor ::

    public GameModel() throws IllegalArgumentException {
        _cardDeck = CardDeckCreator.createDeck(DECK_SIZE);
        _cardPlayers = new Vector<>();
        _isRun = false;
    }


    // Getters and setters ::

    public Card getCardFromCardDeck() throws EmptyStackException {
        if (_cardDeck.isEmpty())
            throw new EmptyStackException();

        return _cardDeck.pop();
    }

    public Stack<Card> getCardDeck() {
        return _cardDeck;
    }

    public int getCardDeckSize() {
        return _cardDeck.size();
    }

    public boolean isCardDeckEmpty() {
        return _cardDeck.isEmpty();
    }

    public Vector<CardPlayer> getCardPlayers() {
        return _cardPlayers;
    }

    public CardPlayer getCardPlayer(int playerIndex) {
        // <- argument checking

        return _cardPlayers.elementAt(playerIndex);
    }

    public boolean isRun() {
        return _isRun;
    }

    // Methods ::

    public void reload() {
        _cardDeck = CardDeckCreator.createDeck(DECK_SIZE);
        _cardPlayers.clear();
    }

    public void shuffleDeck() {
        Collections.shuffle(_cardDeck);

        System.out.println("shuffleDeck - ok");
    }

//    public TurnStatement performPlayerActions(CardPlayer player) {
//        if (player.hasPassed())
//
//        if (!player.hasPassed()
//                && !player.hasExceeded()
//                && !player.hasWon()) {
//            TurnStatement statement = player.getScoreMeaning();
//            switch (statement) {
//                case GET_CARD:
//                    if (_cardDeck.isEmpty())
//                        throw new EmptyStackException();
//                    player.addCard(_cardDeck.pop());
//                    break;
//                case PASS_ROUND:
//                    player.setPass(true);
//                    break;
//                case WIN:
//                    player.setWin(true);
//                    break;
//                case EXCEED:
//                    player.setExceed(true);
//                    break;
//            }
//        }
//
//        return statement;
//    }

    private void performPlayerActions(CardPlayer player) {
        while (!player.hasPassed()
                && !player.hasExceeded()
                && !player.hasWon()) {
            TurnStatement statement = player.analyzeTurn();
            switch (statement) {
                case HIT:
                    if (_cardDeck.isEmpty())
                        throw new EmptyStackException();
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

    public void firstCardDistribution() {
        for (CardPlayer cardPlayer : _cardPlayers) {
            if (_cardDeck.isEmpty())
                throw new EmptyStackException();
            cardPlayer.addCard(_cardDeck.pop());
        }

        System.out.println("firstCardDistribution - ok");
    }

    public void appendPlayers() {
        _cardPlayers.add(new Computer());
        _cardPlayers.add(new Player());
        _cardPlayers.elementAt(0).setDealer(true);

        System.out.println("appendPlayers - ok");
    }

    public void runPlayerTurns(boolean forRegularPlayers) {
        for (CardPlayer cardPlayer : _cardPlayers) {
            if (cardPlayer.isDealer() != forRegularPlayers)
                performPlayerActions(cardPlayer);
        }
    }

    public void run() {
        this.shuffleDeck();
        this.appendPlayers();

        _isRun = true;
    }
}
