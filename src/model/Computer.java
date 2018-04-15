package model;

import java.util.Random;

public class Computer extends CardPlayer {
    // Attributes ::

    private static int _computerIndex;

    // These attributes impact on computer's decisions
    private static final Random _random;
    private static final int _maxPossibility = 100;
    private static final int[] _possibilityPercentages = { 90, 70, 40 };

    // Static fields initialization ::

    static {
        _computerIndex = 0;
        _random = new Random();
    }

    // Constructor ::

    Computer() {
        super("Computer " + ++_computerIndex);
    }

    // Methods ::

    static void resetPlayerIndex() {
        _computerIndex = 0;
    }

    private void calculatePossibility(double percentage) {
        int possibility = _random.nextInt(_maxPossibility + 1);
        if (possibility > percentage)
            this.setPass(true);
    }

    // Overridden methods ::

    @Override
    public int getPlayerIndex() {
        return _computerIndex;
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

        // Below is the main decision determination:

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
            this.calculatePossibility(_possibilityPercentages[0]);
        else if (pointsAmount <= breakpoints[1])
            this.calculatePossibility(_possibilityPercentages[1]);
        else if (pointsAmount <= breakpoints[2])
            this.calculatePossibility(_possibilityPercentages[2]);
    }
}