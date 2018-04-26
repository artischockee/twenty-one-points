package model;

import java.util.Random;

public class Computer extends CardPlayer {
    private static int computerIndex = 0;

    // These attributes impact on computer's decisions
    private static final Random RANDOM = new Random();
    private static final int MAX_POSSIBILITY = 100;
    private static final int[] POSSIBILITY_PERCENTAGES = { 98, 86, 58 };

    Computer() {
        super("Computer " + ++computerIndex);
    }

    static void resetPlayerIndex() {
        computerIndex = 0;
    }

    private void calculatePossibility(double percentage) {
        int possibility = RANDOM.nextInt(MAX_POSSIBILITY + 1);
        if (possibility > percentage)
            this.setPass(true);
    }

    @Override
    public void analyzeTurn() {
        int pointsAmount = this.getPointsAmount();

        if (pointsAmount > GameModel.MAX_SCORE) {
            this.setExceed(true);
            return;
        }
        else if (pointsAmount == GameModel.MAX_SCORE) {
            this.setWin(true);
            return;
        }

        if (this.isDealer() && pointsAmount >= GameModel.MAX_DEALER_TOTAL) {
            this.setPass(true);
            return;
        }

        // Below is the main decision determination:
        // TODO: 4/26/18 Improve the computer's logic

        int upperBound = GameModel.MAX_SCORE - GameModel.MIN_WEIGHT + 1;
        int lowerBound = GameModel.MAX_SCORE - GameModel.MAX_WEIGHT;

        if (pointsAmount == upperBound)
            this.setPass(true);

        if (pointsAmount <= lowerBound)
            return;

        int[] breakpoints = {
            lowerBound + (upperBound - lowerBound) / 5,
            lowerBound + (upperBound - lowerBound) / 2,
            upperBound - 1
        };

        if (pointsAmount <= breakpoints[0])
            this.calculatePossibility(POSSIBILITY_PERCENTAGES[0]);
        else if (pointsAmount <= breakpoints[1])
            this.calculatePossibility(POSSIBILITY_PERCENTAGES[1]);
        else if (pointsAmount <= breakpoints[2])
            this.calculatePossibility(POSSIBILITY_PERCENTAGES[2]);
    }
}