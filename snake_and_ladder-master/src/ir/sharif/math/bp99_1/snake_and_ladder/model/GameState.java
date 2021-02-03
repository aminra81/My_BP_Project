package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.*;

public class GameState {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private int turn;

    public GameState(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.turn = 0;
    }

    public void setTurn(int turn) {
        this.turn = turn;
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
        if (!isStarted())
            return null;
        if (getTurn() % 2 == 1)
            return getPlayer(1);
        else
            return getPlayer(2);
    }


    /**
     * finish current player's turn and update some fields of this class;
     * you can use method "endTurn" in class "Player" (not necessary, but recommanded)
     */
    public void nextTurn() {
        if (getTurn() == 0) {
            turn++;
            this.save();
            return;
        }
        Player curPlayer = getCurrentPlayer();
        curPlayer.endTurn();
        turn++;
        this.save();
    }

    private void createGameStateDirectory() {
        String name = this.getPlayer1().getName() + "_" + this.getPlayer2().getName();
        File gameStatesDirectory = Config.getConfig("mainConfig").getProperty(File.class, "gameStatesDirectory");
        File tempFile = new File(gameStatesDirectory, name);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
            File player1 = new File(tempFile, "player1.txt");
            File player2 = new File(tempFile, "player2.txt");
            File turn = new File(tempFile, "turn.txt");
            File board = new File(tempFile, "1.board");
            try {
                player1.createNewFile();
                player2.createNewFile();
                turn.createNewFile();
                board.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void savePlayer(int playerNumber) {
        String name = this.getPlayer1().getName() + "_" + this.getPlayer2().getName();
        File gameStatesDirectory = Config.getConfig("mainConfig").getProperty(File.class, "gameStatesDirectory");
        File tempFile = new File(gameStatesDirectory, name);
        File player = new File(tempFile, "player" + playerNumber + ".txt");
        Player curPlayer = getPlayer(playerNumber);
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(player));
            printStream.println(curPlayer.getName());
            printStream.println(curPlayer.getScore());
            printStream.println(curPlayer.getId());
            printStream.println(playerNumber);
            for (int i = 1; i <= 6; i++)
                printStream.print(curPlayer.getDice().getChances()[i] + " ");
            printStream.println(curPlayer.getDice().getLastNumber());

            printStream.println(curPlayer.getBomber().isAlive() + " " + curPlayer.getBomber().hasOption());
            printStream.println(curPlayer.getSniper().isAlive() + " " + curPlayer.getSniper().hasOption());
            printStream.println(curPlayer.getHealer().isAlive() + " " + curPlayer.getHealer().hasOption());
            printStream.print(curPlayer.getThief().isAlive() + " " + curPlayer.getThief().hasOption() + " ");
            if(curPlayer.getThief().getHisPrize() == null)
                printStream.println(false);
            else {
                Prize curPrize = curPlayer.getThief().getHisPrize();
                printStream.println(true + " " + curPrize.getPoint() + " " + curPrize.getChance() + " " +
                        curPrize.getDiceNumber());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveBoard() {
        String name = this.getPlayer1().getName() + "_" + this.getPlayer2().getName();
        File gameStatesDirectory = Config.getConfig("mainConfig").getProperty(File.class, "gameStatesDirectory");
        File tempFile = new File(gameStatesDirectory, name);
        File board = new File(tempFile, "1.board");
        //---------------------------------------------
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(board));
            //-----------------------
            printStream.println("CELLS[ 7 16 ]:");
            for (int x = 1; x <= 7; x++) {
                for (int y = 1; y <= 16; y++)
                    printStream.print(this.getBoard().getCell(x, y).getColor() + " ");
                printStream.println();
            }
            printStream.println();
            //-------------------------
            printStream.println("STARTING_CELLS[ 8 ]:");
            printStream.println(this.getPlayer1().getBomber().getCurrentCell().getX() + " " +
                    this.getPlayer1().getBomber().getCurrentCell().getY());
            printStream.println(this.getPlayer1().getHealer().getCurrentCell().getX() + " " +
                    this.getPlayer1().getHealer().getCurrentCell().getY());
            printStream.println(this.getPlayer1().getThief().getCurrentCell().getX() + " " +
                    this.getPlayer1().getThief().getCurrentCell().getY());
            printStream.println(this.getPlayer1().getSniper().getCurrentCell().getX() + " " +
                    this.getPlayer1().getSniper().getCurrentCell().getY());

            printStream.println(this.getPlayer2().getSniper().getCurrentCell().getX() + " " +
                    this.getPlayer2().getSniper().getCurrentCell().getY());
            printStream.println(this.getPlayer2().getThief().getCurrentCell().getX() + " " +
                    this.getPlayer2().getThief().getCurrentCell().getY());
            printStream.println(this.getPlayer2().getHealer().getCurrentCell().getX() + " " +
                    this.getPlayer2().getHealer().getCurrentCell().getY());
            printStream.println(this.getPlayer2().getBomber().getCurrentCell().getX() + " " +
                    this.getPlayer2().getBomber().getCurrentCell().getY());
            printStream.println();
            //----------------------
            printStream.println("WALLS[ " + this.getBoard().getWalls().size() + " ]:");
            for (Wall wall : this.getBoard().getWalls())
                printStream.println(wall.getCell1().getX() + " " + wall.getCell1().getY() + " " +
                        wall.getCell2().getX() + " " + wall.getCell2().getY());
            printStream.println();
            //----------------------
            printStream.println("TRANSMITTERS[ " + this.getBoard().getTransmitters().size() + " ]:");
            for (Transmitter transmitter : this.getBoard().getTransmitters())
                printStream.println(transmitter.getFirstCell().getX() + " " + transmitter.getFirstCell().getY() +
                        " " + transmitter.getLastCell().getX() + " " + transmitter.getLastCell().getY() + " " +
                        transmitter.getType());
            printStream.println();
            //----------------------
            int sizeOfPrizes = 0;
            for (int x = 1; x <= 7; x++)
                for (int y = 1; y <= 16; y++)
                    if(this.getBoard().getCell(x, y).getPrize() != null)
                        sizeOfPrizes++;
            printStream.println("PRIZES[ " + sizeOfPrizes + " ]:");
            for (int x = 1; x <= 7; x++)
                for (int y = 1; y <= 16; y++)
                    if(this.getBoard().getCell(x, y).getPrize() != null) {
                        Prize curPrize = this.getBoard().getCell(x, y).getPrize();
                        printStream.println(x + " " + y + " " + curPrize.getPoint() + " " + curPrize.getChance() +
                                " " + curPrize.getDiceNumber());
                    }
            //-------------------------
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveTurn() {
        String name = this.getPlayer1().getName() + "_" + this.getPlayer2().getName();
        File gameStatesDirectory = Config.getConfig("mainConfig").getProperty(File.class, "gameStatesDirectory");
        File tempFile = new File(gameStatesDirectory, name);
        File turn = new File(tempFile, "turn.txt");
        //----------------------------------------------
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(turn));
            printStream.println(this.getTurn());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void save() {
        createGameStateDirectory();
        saveBoard();
        savePlayer(1);
        savePlayer(2);
        saveTurn();
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
