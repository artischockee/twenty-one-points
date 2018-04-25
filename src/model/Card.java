package model;

enum Suit {
    DIAMONDS, CLUBS, HEARTS, SPADES;

    public String getShortNotation() {
        switch (this) {
            case DIAMONDS:
                return "D";
            case CLUBS:
                return "C";
            case HEARTS:
                return "H";
            case SPADES:
                return "S";
            default:
                return null;
        }
    }

    public String getUnicodeSymbol() {
        switch (this) {
            case DIAMONDS:
                return "♦"; // U+2666
            case CLUBS:
                return "♣"; // U+2663
            case HEARTS:
                return "♥"; // U+2665
            case SPADES:
                return "♠"; // U+2660
            default:
                return null;
        }
    }
}

enum CardName {
    JACK, QUEEN, KING, SIX, SEVEN, EIGHT, NINE, TEN, ACE;

    public String getSymbol() {
        switch (this) {
            case JACK:
                return "J";
            case QUEEN:
                return "Q";
            case KING:
                return "K";
            case SIX:
                return "6";
            case SEVEN:
                return "7";
            case EIGHT:
                return "8";
            case NINE:
                return "9";
            case TEN:
                return "10";
            case ACE:
                return "A";
            default:
                return null;
        }
    }
}

public class Card {
    private final Suit suit;
    private final CardName cardName;
    private int cardWeight;

    public String getSuitUnicodeSymbol() {
        return suit.getUnicodeSymbol();
    }

    public String getSuitShortNotation() {
        return suit.getShortNotation();
    }

    public String getCardNameSymbol() {
        return cardName.getSymbol();
    }

    public int getCardWeight() {
        return cardWeight;
    }

    Card(Suit suit, CardName cardName, int cardWeight) throws NullPointerException, IllegalArgumentException {
        if (suit == null || cardName == null)
            throw new NullPointerException(
                this.getClass().getName() + ": null "
                + Suit.class.getName() + " or "
                + CardName.class.getName() + " argument.");
        if (cardWeight <= 0)
            throw new IllegalArgumentException("Error in argument 'cardWeight': negative value.");
        // TODO: 4/6/18 here should probably be another arguments checking

        this.suit = suit;
        this.cardName = cardName;
        this.cardWeight = cardWeight;
    }
}