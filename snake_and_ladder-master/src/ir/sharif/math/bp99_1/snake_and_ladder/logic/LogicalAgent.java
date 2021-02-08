package ir.sharif.math.bp99_1.snake_and_ladder.logic;


import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.File;

/**
 * This class is an interface between logic and graphic.
 * some methods of this class, is called from graphic.
 * DO NOT CHANGE ANY PART WHICH WE MENTION.
 */
public class LogicalAgent {
    private final ModelLoader modelLoader;
    private final GraphicalAgent graphicalAgent;
    private final GameState gameState;

    /**
     * DO NOT CHANGE CONSTRUCTOR.
     */
    public LogicalAgent() {
        this.graphicalAgent = new GraphicalAgent(this);
        this.modelLoader = new ModelLoader();
        this.gameState = loadGameState();
    }


    /**
     * NO CHANGES NEEDED.
     */
    private GameState loadGameState() {
        File gameStatesDirectory = Config.getConfig("mainConfig").getProperty(File.class, "gameStatesDirectory");
        Board board = modelLoader.loadBoard();
        Player player1 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(1), 1);
        Player player2;
        do {
            player2 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(2), 2);
        } while (player1.equals(player2));
        String name = player1.getName() + "_" + player2.getName();
        File tempFile = new File(gameStatesDirectory, name);
        if (tempFile.exists()) {
            Cell.resetAllCells();
            board = modelLoader.loadBoard(new File(tempFile, "1.board"));
            player1 = modelLoader.loadPlayer(new File(tempFile, "player1.txt"));
            player2 = modelLoader.loadPlayer(new File(tempFile, "player2.txt"));
        }
        player1.setRival(player2);
        player2.setRival(player1);
        return new GameState(board, player1, player2);
    }

    /**
     * NO CHANGES NEEDED.
     */
    public void initialize() {
        graphicalAgent.initialize(gameState);
    }
    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who clicks "ReadyButton".) you should somehow change that player state.
     * if both players are ready. then start the game.
     */
    private void startGame() {
        File gameStatesDirectory = Config.getConfig("mainConfig").getProperty(File.class, "gameStatesDirectory");
        String name = gameState.getPlayer1().getName() + "_" + gameState.getPlayer2().getName();
        File tempFile = new File(gameStatesDirectory, name);
        if(tempFile.exists()) {
            int turn = modelLoader.loadTurn(new File(tempFile, "turn.txt"));
            gameState.setTurn(turn);
        }
        //player One pieces.
        gameState.getPlayer1().getBomber().setCurrentCell(gameState.getBoard().getStartingCells().get(0));
        gameState.getBoard().getStartingCells().get(0).setPiece(gameState.getPlayer1().getBomber());

        gameState.getPlayer1().getHealer().setCurrentCell(gameState.getBoard().getStartingCells().get(1));
        gameState.getBoard().getStartingCells().get(1).setPiece(gameState.getPlayer1().getHealer());

        gameState.getPlayer1().getThief().setCurrentCell(gameState.getBoard().getStartingCells().get(2));
        gameState.getBoard().getStartingCells().get(2).setPiece(gameState.getPlayer1().getThief());

        gameState.getPlayer1().getSniper().setCurrentCell(gameState.getBoard().getStartingCells().get(3));
        gameState.getBoard().getStartingCells().get(3).setPiece(gameState.getPlayer1().getSniper());

        //player Two pieces.
        gameState.getPlayer2().getBomber().setCurrentCell(gameState.getBoard().getStartingCells().get(7));
        gameState.getBoard().getStartingCells().get(7).setPiece(gameState.getPlayer2().getBomber());

        gameState.getPlayer2().getHealer().setCurrentCell(gameState.getBoard().getStartingCells().get(6));
        gameState.getBoard().getStartingCells().get(6).setPiece(gameState.getPlayer2().getHealer());

        gameState.getPlayer2().getThief().setCurrentCell(gameState.getBoard().getStartingCells().get(5));
        gameState.getBoard().getStartingCells().get(5).setPiece(gameState.getPlayer2().getThief());

        gameState.getPlayer2().getSniper().setCurrentCell(gameState.getBoard().getStartingCells().get(4));
        gameState.getBoard().getStartingCells().get(4).setPiece(gameState.getPlayer2().getSniper());

        if(gameState.getTurn() == 0)
            gameState.nextTurn();
    }

    public void readyPlayer(int playerNumber) {
        Player curPlayer = gameState.getPlayer(playerNumber);
        curPlayer.setReady(!curPlayer.isReady());
        if (gameState.getPlayer1().isReady() && gameState.getPlayer2().isReady())
            startGame();
        graphicalAgent.update(gameState);
    }

    /**
     * give x,y (coordinates of a cell) :
     * you should handle if user want to select a piece
     * or already selected a piece and now want to move it to a new cell
     */
    // ***
    public void selectCell(int x, int y) {
        if (!gameState.isStarted())
            return;
        Cell curCell = gameState.getBoard().getCell(x, y);
        Player curPlayer = gameState.getCurrentPlayer();
        if (!curPlayer.isDicePlayedThisTurn())
            return;

        if (curPlayer.getSelectedPiece() == null) {
            if (curCell.getPiece() == null || !curCell.getPiece().getPlayer().equals(curPlayer))
                return;
            curPlayer.setSelectedPiece(curCell.getPiece());
            curCell.getPiece().setSelected(true);
            curPlayer.setSelectedPiece(curCell.getPiece());
        } else {
            boolean isValid = curPlayer.getSelectedPiece().thisTurnMove(curCell);
            if (!isValid)
                return;
            gameState.nextTurn();
        }
        graphicalAgent.update(gameState);
        checkForEndGame();
    }

    /**
     * check for endgame and specify winner
     * if player one in winner set winner variable to 1
     * if player two in winner set winner variable to 2
     * If the game is a draw set winner variable to 3
     */
    public static void deleteDirectoryLegacyIO(File file) {
        File[] list = file.listFiles();
        if (list != null)
            for (File temp : list)
                deleteDirectoryLegacyIO(temp);
        file.delete();
    }
    private void checkForEndGame() {
        if (gameState.getTurn() > 40) {
            int winner;
            int firstPlayerScore = gameState.getPlayer1().getScore();
            int secondPlayerScore = gameState.getPlayer2().getScore();
            if (firstPlayerScore == secondPlayerScore)
                winner = 3;
            else if (firstPlayerScore > secondPlayerScore)
                winner = 1;
            else
                winner = 2;
            graphicalAgent.playerWin(winner);
            /* save players*/
            modelLoader.savePlayer(gameState.getPlayer1());
            modelLoader.savePlayer(gameState.getPlayer2());
            modelLoader.archive(gameState.getPlayer1(), gameState.getPlayer2());
            /* remove the directory */
            File gameStatesDirectory = Config.getConfig("mainConfig").getProperty(File.class, "gameStatesDirectory");
            String name = gameState.getPlayer1().getName() + "_" + gameState.getPlayer2().getName();
            File tempFile = new File(gameStatesDirectory, name);
            deleteDirectoryLegacyIO(tempFile);
            /* new game */
            LogicalAgent logicalAgent = new LogicalAgent();
            logicalAgent.initialize();
        }
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who left clicks "dice button".) you should roll his/her dice
     * and update *****************
     */
    public void rollDice(int playerNumber) {
        if (gameState.getCurrentPlayer().getPlayerNumber() != playerNumber)
            return;
        Player curPlayer = gameState.getPlayer(playerNumber);
        if (curPlayer.isDicePlayedThisTurn())
            return;
        int diceNum = curPlayer.getDice().roll();

        //Option Activation
        if (diceNum == 1)
            curPlayer.getHealer().setOption(true);
        if (diceNum == 3)
            curPlayer.getBomber().setOption(true);
        if (diceNum == 5)
            curPlayer.getSniper().setOption(true);


        if (diceNum == 2)
            curPlayer.getDice().reset();
        curPlayer.setDicePlayedThisTurn(true);
        curPlayer.setMoveLeft(diceNum);
        if (diceNum == 6)
            curPlayer.applyOnScore(4);
        if (!curPlayer.hasMove(gameState.getBoard(), diceNum)) {
            curPlayer.applyOnScore(-3);
            gameState.nextTurn();
        }
        graphicalAgent.update(gameState);
        checkForEndGame();
    }

    public String getCellDetails(int x, int y) {
        //cell coordinates.
        Cell curCell = Cell.CellFinder(x, y);
        assert curCell != null;
        if (curCell.getPiece() == null)
            return "empty.";
        else
            return curCell.getPiece().getDetails();
    }

    public String getDiceDetail(int playerNumber) {
        Player curPlayer = gameState.getPlayer(playerNumber);
        return curPlayer.getDice().getDetails();
    }
}
