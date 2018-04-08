import java.util.Scanner;

public class Player extends CardPlayer {
    private static int _playerIndex;

    static {
        _playerIndex = 0;
    }

    Player() throws Exception {
        super("Player " + ++_playerIndex);
    }

    @Override
    public int getPlayerIndex() {
        return _playerIndex;
    }

    @Override
    public TurnAnswer analyzeTurn() throws Exception {
        String playerName = getPlayerName();
        int pointsAmount = getPointsAmount();

        if (pointsAmount > TwentyOnePoints.MAX_SCORE) {
            setExceed(true);
        }

        System.out.println(playerName + ", so far you have " + pointsAmount + " points.");
        if (hasExceeded()) {
            System.out.println("You have exceeded the maximum score. Sorry, you lost.");
            return TurnAnswer.PASS; // bad shit
        }
        // TODO: 4/6/18 if Player is Dealer and has 17 or more points, warn him and restrict to take cards from the deck
        showCardDeck();
        System.out.println("What are you gonna do?");
        System.out.println("1) Take one more card from the deck");
        System.out.println("2) Pass the turn and wait for results");

        Scanner scanner = new Scanner(System.in);
        int answer = scanner.nextInt();
        switch (answer) {
            case 1:
                return TurnAnswer.TAKE;
            case 2:
                return TurnAnswer.PASS;
            default:
                throw new Exception("Only 2 options are available.");
        }
    }
}
