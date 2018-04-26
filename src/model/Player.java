package model;

public class Player extends CardPlayer {
    private static int playerIndex = 0;

    Player() {
        super("Player " + ++playerIndex);
    }

    static void resetPlayerIndex() {
        playerIndex = 0;
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
