package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

import javax.print.attribute.standard.Destination;
import java.util.Currency;

public class Piece {
    private Cell currentCell;
    private final Color color;
    private final Player player;
    private boolean isSelected;

    public Piece(Player player, Color color) {
        this.color = color;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    /**
     * @return "true" if your movement is valid  , else return " false"
     * <p>
     * In this method, you should check if the movement is valid of not.
     * <p>
     * You can use some methods ( they are recommended )
     * <p>
     * 1) "canEnter" method in class "Cell"
     * <p>
     * if your movement is valid, return "true" , else return " false"
     */
    public boolean isValidMove(Cell destination, int diceNumber) {
        Cell currentCell = this.getCurrentCell();
        int dirx = destination.getX() - currentCell.getX();
        int diry = destination.getY() - currentCell.getY();
        if(dirx != 0)
            dirx /= Math.abs(dirx);
        if(diry != 0)
            diry /= Math.abs(diry);
        for (int move = 0; move < diceNumber; move++){
            if(currentCell.adjacentOpen(dirx, diry) != null)
                currentCell = currentCell.adjacentOpen(dirx, diry);
            else
                return false;
        }
        return currentCell.canEnter(this);
    }

    /**
     * @param destination move selected piece from "currentCell" to "destination"
     */
    public void moveTo(Cell destination) {
        this.getCurrentCell().setPiece(null);
        setCurrentCell(destination);
        this.getCurrentCell().setPiece(this);
        setSelected(false);
    }
}
