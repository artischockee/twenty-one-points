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

    public CardPlayer getCardPlayer(int playerIndex) throws IndexOutOfBoundsException {
        if (playerIndex < 0 || playerIndex >= _cardPlayers.size())
            throw new IndexOutOfBoundsException(
                "getCardPlayer: invalid argument 'playerIndex'");

        return _cardPlayers.elementAt(playerIndex);
    }

    public boolean isRun() {
        return _isRun;
    }


    // Methods ::

    public void reload() {
        _cardDeck = CardDeckCreator.createDeck(DECK_SIZE);
        _cardPlayers.clear();

        // poor logic because there is no way to make
        // abstract static method in CardPlayer class
        Computer.resetPlayerIndex();
        Player.resetPlayerIndex();
    }

    public void shuffleDeck() {
        Collections.shuffle(_cardDeck);
    }

    public void appendPlayers() {
        _cardPlayers.add(new Computer());
        _cardPlayers.add(new Player());
        _cardPlayers.elementAt(0).setDealer(true);
    }

    public void run() {
        this.shuffleDeck();
        this.appendPlayers();

        _isRun = true;
    }
}
