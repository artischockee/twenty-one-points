package model;

import java.util.Scanner;

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
        String playerName = this.getPlayerName();
        int pointsAmount = this.getPointsAmount();

        System.out.println(playerName + ", so far you have " + pointsAmount + " points.");
        this.showCardDeck();

        if (pointsAmount > TwentyOnePoints.MAX_SCORE) {
            System.out.println("You have exceeded the maximum score. Sorry, you lost.");
            return TurnStatement.EXCEED;
        }
        else if (pointsAmount == TwentyOnePoints.MAX_SCORE) {
            System.out.println("You have earned 21 points! Congratulations!");
            return TurnStatement.WIN;
        }

        if (this.isDealer() && pointsAmount >= TwentyOnePoints.MAX_DEALER_TOTAL) {
            System.out.println("Since you are the dealer in this game, and you have "
                + pointsAmount + " points (minimum is "
                + TwentyOnePoints.MAX_DEALER_TOTAL + "), you are not able to take cards from the deck anymore.");
            return TurnStatement.STAND;
        }

        System.out.println("1) Take one more card from the deck");
        System.out.println("2) Pass the turn and wait for results");

        Scanner scanner = new Scanner(System.in);
        int answer;
        do {
            answer = scanner.nextInt();
            switch (answer) {
                case 1:
                    return TurnStatement.HIT;
                case 2:
                    return TurnStatement.STAND;
                default:
                    System.out.println("Please choose valid option.");
            }
        } while (true);
    }
}
