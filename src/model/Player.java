package model;

public class Player extends CardPlayer {
    private static int _playerIndex;

    static {
        _playerIndex = 0;
    }

    Player() {
        super("Player " + ++_playerIndex);
    }

    @Override
    public int getPlayerIndex() {
        return _playerIndex;
    }

    @Override
    public TurnStatement analyzeTurn() {
        int pointsAmount = this.getPointsAmount();

        if (pointsAmount > GameModel.MAX_SCORE)
            return TurnStatement.EXCEED;
        else if (pointsAmount == GameModel.MAX_SCORE)
            return TurnStatement.WIN;

        if (this.isDealer() && pointsAmount >= GameModel.MAX_DEALER_TOTAL)
            return TurnStatement.STAND;

        return TurnStatement.HIT;
    }
}
