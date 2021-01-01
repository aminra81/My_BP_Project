package ir.sharif.math.bp99_1.snake_and_ladder.model;

public class GameState {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private int turn;

    public GameState(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        turn = 0;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer(int i) {
        if (i == 1) return player1;
        else if (i == 2) return player2;
        else return null;
    }

    public boolean isStarted() {
        return turn != 0;
    }

    public int getTurn() {
        return turn;
    }

    /**
     * return null if game is not started.
     * else return a player who's turn is now.
     */
    public Player getCurrentPlayer() {
        if(isStarted() == false)
            return null;
        if(getTurn() % 2 == 1)
            return getPlayer(1);
        else
            return getPlayer(2);
    }


    /**
     * finish current player's turn and update some fields of this class;
     * you can use method "endTurn" in class "Player" (not necessary, but recommanded)
     */
    public void nextTurn() {
        if(getTurn() == 0) {
            turn++;
            return;
        }
        Player curPlayer = getCurrentPlayer();
        curPlayer.endTurn();
        turn++;
        //board is not changed here.
    }


    @Override
    public String toString() {
        return "GameState{" +
                "board=" + board +
                ", playerOne=" + player1 +
                ", playerTwo=" + player2 +
                ", turn=" + turn +
                '}';
    }
}
