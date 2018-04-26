package model;

import java.util.Vector;

public abstract class CardPlayer {
    private Vector<Card> cardDeck;
    private String playerName;

    private boolean isDealer;
    private boolean hasPassed;
    private boolean hasExceeded;
    private boolean hasWon;

    CardPlayer(String playerName) {
        if (playerName == null || playerName.isEmpty())
            this.playerName = this.getClass().getName();

        cardDeck = new Vector<>();
        this.playerName = playerName;
        isDealer = hasPassed = hasExceeded = hasWon = false;
    }

    public Vector<Card> getCardDeck() {
        return cardDeck;
    }

    public int getPointsAmount() {
        int pointsAmount = 0;
        for (Card card : cardDeck)
            pointsAmount += card.getCardWeight();

        return pointsAmount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isDealer() {
        return isDealer;
    }

    void setDealer(boolean state) {
        isDealer = state;
    }

    public boolean hasPassed() {
        return hasPassed;
    }

    public void setPass(boolean state) {
        hasPassed = state;
    }

    public boolean hasExceeded() {
        return hasExceeded;
    }

    void setExceed(boolean state) {
        hasExceeded = state;
    }

    public boolean hasWon() {
        return hasWon;
    }

    void setWin(boolean state) {
        hasWon = state;
    }

    public boolean hasFinished() {
        return hasExceeded || hasPassed || hasWon;
    }

    // TODO: 4/14/18 to be renamed
    public abstract void analyzeTurn();
}