public class Computer extends CardPlayer {
    private static int _computerIndex;

    static {
        _computerIndex = 0;
    }

    Computer() throws Exception {
        super("Computer " + ++_computerIndex);
    }

    @Override
    public int getPlayerIndex() {
        return _computerIndex;
    }

    @Override
    public TurnAnswer analyzeTurn() throws Exception  {
        // here is a computer logic..
        return TurnAnswer.PASS; // tmp
    }
}