package model;

import java.util.Vector;

public abstract class CardPlayer {
    private Vector<Card> _cardDeck;
    private String _playerName;

    private boolean _isDealer;
    private boolean _hasPassed;
    private boolean _hasExceeded;
    private boolean _hasWon;

    CardPlayer(String playerName) {
        if (playerName == null || playerName.isEmpty())
            _playerName = this.getClass().getName();

        _cardDeck = new Vector<>();
        _playerName = playerName;
        _isDealer = _hasPassed = _hasExceeded = _hasWon = false;
    }

    public Vector<Card> getCardDeck() {
        return _cardDeck;
    }

    public int getPointsAmount() {
        int pointsAmount = 0;
        for (Card card : _cardDeck)
            pointsAmount += card.getCardWeight();

        return pointsAmount;
    }

    public String getPlayerName() {
        return _playerName;
    }

    public boolean isDealer() {
        return _isDealer;
    }

    void setDealer(boolean state) {
        _isDealer = state;
    }

    public boolean hasPassed() {
        return _hasPassed;
    }

    void setPass(boolean state) {
        _hasPassed = state;
    }

    public boolean hasExceeded() {
        return _hasExceeded;
    }

    void setExceed(boolean state) {
        _hasExceeded = state;
    }

    public boolean hasWon() {
        return _hasWon;
    }

    void setWin(boolean state) {
        _hasWon = state;
    }

    public abstract int getPlayerIndex();

    // TODO: 4/14/18 to be renamed
    public abstract void analyzeTurn();
}