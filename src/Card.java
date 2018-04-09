import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

enum Suit {
    DIAMONDS, CLUBS, HEARTS, SPADES;

    public static int length() {
        return values().length;
    }

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

    public String getSymbol() {
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

    public static int length() {
        return values().length;
    }

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

class Card {
    private Suit _suit;
    private CardName _cardName;
    private int _cardWeight;

    public String getSuitSymbol() {
        return _suit.getSymbol();
    }

    public String getCardNameSymbol() {
        return _cardName.getSymbol();
    }

    public int getCardWeight() {
        return _cardWeight;
    }

    Card(Suit suit, CardName cardName, int cardWeight) throws Exception {
        if (suit == null || cardName == null)
            throw new NullPointerException("Card: null "
                    + Suit.class.getName() + " or "
                    + CardName.class.getName() + " argument.");
        if (cardWeight <= 0)
            throw new Exception("Error in argument 'cardWeight': negative value.");
        // TODO: 4/6/18 here should probably be another arguments checking

        _suit = suit;
        _cardName = cardName;
        _cardWeight = cardWeight;
    }

    public void show() {
        System.out.printf("%s %s : %s points\n", _cardName.getSymbol(), _suit.getSymbol(), _cardWeight);
    }
}

final class CardDeckCreator {
    private static final Map<CardName, Integer> WEIGHT_MAP_TWENTY_ONE = new HashMap<>();

    static {
        WEIGHT_MAP_TWENTY_ONE.put(CardName.JACK, 2);
        WEIGHT_MAP_TWENTY_ONE.put(CardName.QUEEN, 3);
        WEIGHT_MAP_TWENTY_ONE.put(CardName.KING, 4);
        WEIGHT_MAP_TWENTY_ONE.put(CardName.SIX, 6);
        WEIGHT_MAP_TWENTY_ONE.put(CardName.SEVEN, 7);
        WEIGHT_MAP_TWENTY_ONE.put(CardName.EIGHT, 8);
        WEIGHT_MAP_TWENTY_ONE.put(CardName.NINE, 9);
        WEIGHT_MAP_TWENTY_ONE.put(CardName.TEN, 10);
        WEIGHT_MAP_TWENTY_ONE.put(CardName.ACE, 11);
    }

    public static Stack<Card> createDeck(int deckSize) throws Exception {
        if (deckSize != TwentyOnePoints.DECK_SIZE) {
            throw new Exception("There is no suitable deck size for the specified number.");
            // TODO: 4/6/18 this is probably a blank for further improvements (e.g. different deck size)
        }

        Stack<Card> deck = new Stack<>();

        for (CardName cardName: CardName.values())
            for (Suit suit: Suit.values())
                deck.add(new Card(suit, cardName, WEIGHT_MAP_TWENTY_ONE.get(cardName)));

        return deck;
    }
}