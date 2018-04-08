import java.util.Vector;

public abstract class CardPlayer {
    private Vector<Card> _cardDeck;
    private String _playerName;
    private boolean _isDealer;
    private boolean _hasPassed;
    private boolean _hasExceeded;

    CardPlayer(String playerName) throws Exception {
        if (playerName == null || playerName.isEmpty())
            throw new Exception("CardPlayer: argument 'playerName' is null or empty.");

        _cardDeck = new Vector<>();
        _playerName = playerName;
        _isDealer = _hasPassed = _hasExceeded = false;
    }

    public int getPointsAmount() {
        int pointsAmount = 0;
        for (Card card : _cardDeck)
            pointsAmount += card.getCardWeight();

        return pointsAmount;
    }

    public void addCard(Card card) {
        _cardDeck.add(card);
    }

    public void showCardDeck() {
        for (Card card : _cardDeck) {
            card.show();
        }
    }

    public String getPlayerName() {
        return _playerName;
    }

    public boolean isDealer() {
        return _isDealer;
    }

    public void setDealer(boolean state) {
        _isDealer = state;
    }

    public boolean hasPassed() {
        return _hasPassed;
    }

    public void setPass(boolean state) {
        _hasPassed = state;
    }

    public boolean hasExceeded() {
        return _hasExceeded;
    }

    public void setExceed(boolean state) {
        _hasExceeded = state;
    }

    public abstract int getPlayerIndex();

    public abstract TurnAnswer analyzeTurn() throws Exception ;
}