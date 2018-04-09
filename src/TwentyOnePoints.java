import java.util.*;

public class TwentyOnePoints {
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
//        System.out.print("Shuffling the deck.. ");
        Collections.shuffle(_cardDeck);
//        System.out.println("Success.");
    }

    private void performPlayerActions(CardPlayer player) throws Exception {
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

    public void playGame() throws Exception {
        System.out.println("Hello and welcome to 'Twenty One Points' game!");
        shuffleDeck();
        System.out.println("You will be playing against computer. He (it) will be a dealer (for now).");

        _cardPlayers.add(new Computer());
        _cardPlayers.add(new Player());
        // add new cardPlayers (probably, Computers) here
        _cardPlayers.elementAt(0).setDealer(true);

        // first card distribution
        for (CardPlayer cardPlayer : _cardPlayers) {
            // TODO: 4/6/18 add exception handling in case of cardDeck is empty
            cardPlayer.addCard(_cardDeck.pop());
        }

        // Here are the regular players play
        for (CardPlayer cardPlayer : _cardPlayers) {
            if (!cardPlayer.isDealer())
                performPlayerActions(cardPlayer);
        }

        // Now here should be the dealer's turns,
        // because in the 'for' above it's impossible to do
        for (CardPlayer cardPlayer : _cardPlayers) {
            if (cardPlayer.isDealer())
                performPlayerActions(cardPlayer);
        }

        System.out.println("Now the players open their hands..");
        for (CardPlayer cardPlayer : _cardPlayers) {
            System.out.println(cardPlayer.getPlayerName() + "'s cards:");
            cardPlayer.showCardDeck();
            System.out.println("Total points: " + cardPlayer.getPointsAmount());
        }
    }
}
