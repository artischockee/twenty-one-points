package model;

public class Player extends CardPlayer {
    // Attributes ::

    private static int _playerIndex;

    // Static fields initialization ::

    static {
        _playerIndex = 0;
    }

    // Constructor ::

    Player() {
        super("Player " + ++_playerIndex);
    }

    // Methods ::

    static void resetPlayerIndex() {
        _playerIndex = 0;
    }

    // Overridden methods ::

    @Override
    public int getPlayerIndex() {
        return _playerIndex;
    }

    @Override
    public void analyzeTurn() {
        int pointsAmount = this.getPointsAmount();

        if (pointsAmount > GameModel.MAX_SCORE)
            this.setExceed(true);
        else if (pointsAmount == GameModel.MAX_SCORE)
            this.setWin(true);

        if (this.isDealer() && pointsAmount >= GameModel.MAX_DEALER_TOTAL)
            this.setPass(true);
    }
}
