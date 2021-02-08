package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private final String name;
    private int score;
    public final List<Piece> pieces;
    private final Dice dice;
    private Player rival;
    private final int id;
    private int playerNumber;
    private boolean isReady;
    private boolean dicePlayedThisTurn;
    private int moveLeft;
    private Piece selectedPiece;

    public Player(String name, int score, int id, int playerNumber) {
        this.name = name;
        this.score = score;
        this.id = id;
        this.playerNumber = playerNumber;
        this.dice = new Dice();
        this.pieces = new ArrayList<>();
        this.pieces.add(new Bomber(this, Color.RED));
        this.pieces.add(new Sniper(this, Color.BLUE));
        this.pieces.add(new Healer(this, Color.GREEN));
        this.pieces.add(new Thief(this, Color.YELLOW));
        this.moveLeft = 0;
        this.selectedPiece = null;
    }

    public Player(String name, int score, int id, int playerNumber, Dice dice, List<Piece> pieces) {
        this.name = name;
        this.score = score;
        this.id = id;
        this.playerNumber = playerNumber;
        this.dice = dice;
        this.pieces = pieces;
    }

    public Piece getBomber() {
        for (Piece piece : pieces)
            if(piece.getColor() == Color.RED)
                return piece;
        return null;
    }

    public Piece getHealer() {
        for (Piece piece : pieces)
            if(piece.getColor() == Color.GREEN)
                return piece;
        return null;
    }

    public Piece getThief() {
        for (Piece piece : pieces)
            if(piece.getColor() == Color.YELLOW)
                return piece;
        return null;
    }

    public Piece getSniper() {
        for (Piece piece : pieces)
            if(piece.getColor() == Color.BLUE)
                return piece;
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Dice getDice() {
        return dice;
    }

    public int getScore() {
        return score;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Player getRival() {
        return rival;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public boolean isDicePlayedThisTurn() {
        return dicePlayedThisTurn;
    }

    public void setDicePlayedThisTurn(boolean dicePlayedThisTurn) {
        this.dicePlayedThisTurn = dicePlayedThisTurn;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public void setMoveLeft(int moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setRival(Player rival) {
        this.rival = rival;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void applyOnScore(int score) {
        this.score += score;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    /**
     * @param prize according to input prize , apply necessary changes to score and dice chance
     *              <p>
     *              you can use method "addChance" in class "Dice"(not necessary, but recommended)
     */
    public void usePrize(Prize prize) {
        applyOnScore(prize.getPoint());
        Dice thisDice = getDice();
        thisDice.addChance(prize.getDiceNumber(), prize.getChance());
    }


    /**
     * check if any of player pieces can move to another cell.
     *
     * @return true if at least 1 piece has a move , else return false
     * <p>
     * you can use method "isValidMove" in class "Piece"(not necessary, but recommended)
     */
    public boolean hasMove(Board board, int diceNumber) {
        for (Piece piece : getPieces())
            if(piece.hasMove(diceNumber))
                return true;
        return false;
    }


    /**
     * Deselect selectedPiece and make some changes in this class fields.
     */
    // **
    public void endTurn() {
        //Deselect this player's piece.
        Piece thisPiece = getSelectedPiece();
        if(thisPiece != null)
            thisPiece.setSelected(false);
        setMoveLeft(0);
        setSelectedPiece(null);
        setDicePlayedThisTurn(false);
    }


    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

