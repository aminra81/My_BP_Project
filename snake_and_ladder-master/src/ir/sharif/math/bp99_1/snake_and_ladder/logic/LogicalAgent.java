package ir.sharif.math.bp99_1.snake_and_ladder.logic;


import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

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
        Board board = modelLoader.loadBord();
        Player player1 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(1), 1);
        Player player2;
        do {
            player2 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(2), 2);
        } while (player1.equals(player2));
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
        for (int playerNumber = 1; playerNumber <= 2; playerNumber++) {
            for (Piece piece : gameState.getPlayer(playerNumber).getPieces())
                for (Cell cell : gameState.getBoard().getStartingCells().keySet())
                    if(gameState.getBoard().getStartingCells().get(cell) == playerNumber && piece.getColor().equals(cell.getColor())) {
                        piece.setCurrentCell(cell);
                        cell.setPiece(piece);
                    }

        }
        gameState.nextTurn();
    }
    public void readyPlayer(int playerNumber) {
        Player curPlayer = gameState.getPlayer(playerNumber);
        curPlayer.setReady(!curPlayer.isReady());
        if(gameState.getPlayer1().isReady() && gameState.getPlayer2().isReady())
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
        if(gameState.isStarted() == false)
            return;
        Cell curCell = gameState.getBoard().getCell(x, y);
        Player curPlayer = gameState.getCurrentPlayer();
        if(curPlayer.isDicePlayedThisTurn() == false)
            return;
        if(curCell.getPiece() != null) {
            if(!curCell.getPiece().getPlayer().equals(curPlayer))
                return;
            if(curPlayer.getSelectedPiece() != null)
                curPlayer.getSelectedPiece().setSelected(false);
            curCell.getPiece().setSelected(true);
            curPlayer.setSelectedPiece(curCell.getPiece());
        }
        else {
            if(curPlayer.getSelectedPiece() == null)
                return;
            if(!curPlayer.getSelectedPiece().isValidMove(curCell, curPlayer.getMoveLeft()))
                return;
            curPlayer.getSelectedPiece().moveTo(curCell);
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
    private void checkForEndGame() {
        if (gameState.getTurn() > 30) {
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


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who right clicks "dice button".) you should return the dice detail of that player.
     * you can use method "getDetails" in class "Dice"(not necessary, but recommended )
     */
    public String getDiceDetail(int playerNumber) {
        Player curPlayer = gameState.getPlayer(playerNumber);
        return curPlayer.getDice().getDetails();
    }
}
