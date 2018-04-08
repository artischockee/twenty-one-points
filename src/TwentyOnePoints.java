import java.util.Collections;
import java.util.Stack;
import java.util.Vector;

public class TwentyOnePoints {
    public static final int DECK_SIZE = 36;
    public static final int MAX_SCORE = 21;
    private Stack<Card> _cardDeck;
    private Vector<CardPlayer> _cardPlayers;

    TwentyOnePoints() throws Exception {
        _cardDeck = CardDeckCreator.createDeck(DECK_SIZE);
        _cardPlayers = new Vector<>();
    }

    // TEMP!
    public void getCardFromDeck() {
        _cardDeck.pop();
    }

    public int getCardDeckSize() {
        return _cardDeck.size();
    }

    public void displayDeck() {
        for (Card card : _cardDeck)
            card.show();
    }

    private void shuffleDeck() {
        System.out.print("Shuffling the deck.. ");
        Collections.shuffle(_cardDeck);
        System.out.println("Success.");
    }

    // ..

    public void playGame() throws Exception {
        System.out.println("Hello and welcome to 'Twenty One Points' game!");
        System.out.println("The deck will right now be shuffled.");
        shuffleDeck();
        System.out.println("You will be playing against computer. He (it) will be a dealer (for now).");

        _cardPlayers.add(new Computer());
        _cardPlayers.add(new Player());
        _cardPlayers.elementAt(0).setDealer(true);

        // first card distribution
        for (CardPlayer cardPlayer : _cardPlayers) {
            // TODO: 4/6/18 add exception handling in case of cardDeck is empty
            cardPlayer.addCard(_cardDeck.pop());
        }

        for (CardPlayer cardPlayer : _cardPlayers) {
            if (cardPlayer.getClass() == Player.class) {
                while (!cardPlayer.hasPassed() && !cardPlayer.hasExceeded()) {
                    TurnAnswer ans = cardPlayer.analyzeTurn();
                    switch (ans) {
                        case TAKE:
                            cardPlayer.addCard(_cardDeck.pop());
                            break;
                        case PASS:
                            cardPlayer.setPass(true);
                            break;
                    }
                }
            }
        }
    }
}
