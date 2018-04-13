package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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

    static Collection<Integer> getWeights21() {
        return WEIGHT_MAP_TWENTY_ONE.values();
    }

    static Stack<Card> createDeck(int deckSize) throws IllegalArgumentException {
        if (deckSize != GameModel.DECK_SIZE) {
            throw new IllegalArgumentException("There is no suitable deck size for the specified number.");
            // This is probably a blank for further improvements (e.g. different deck size)
        }

        Stack<Card> deck = new Stack<>();

        for (CardName cardName: CardName.values())
            for (Suit suit: Suit.values()) {
                try {
                    deck.add(new Card(suit, cardName, WEIGHT_MAP_TWENTY_ONE.get(cardName)));
                }
                catch (NullPointerException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

        return deck;
    }
}