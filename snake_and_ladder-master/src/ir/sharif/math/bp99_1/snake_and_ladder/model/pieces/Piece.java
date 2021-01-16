package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;


public class Piece implements TurnMove{
    protected Cell currentCell;
    protected final Color color;
    protected final Player player;
    protected boolean isSelected;
    protected boolean isAlive;
    protected boolean hasOption;

    public Piece(Player player, Color color) {
        this.color = color;
        this.player = player;
        isAlive = true;
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

    public boolean isAlive() { return isAlive; }

    public void setAlive(boolean alive) {
        if (!alive)
            hasOption = false;
        isAlive = alive;
    }

    public boolean hasOption() { return hasOption; }

    public void setOption(boolean option) {
        if(!option || isAlive)
            hasOption = option;
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
        if (dirx != 0 && diry != 0)
            return false;
        if (Math.abs(dirx) + Math.abs(diry) != diceNumber)
            return false;
        if (dirx != 0)
            dirx /= Math.abs(dirx);
        if (diry != 0)
            diry /= Math.abs(diry);
        for (int move = 0; move < diceNumber; move++) {
            if (currentCell.adjacentOpen(dirx, diry) != null)
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
        if(!destination.canEnter(this))
            return;
        this.getCurrentCell().setPiece(null);
        setCurrentCell(destination);
        this.getCurrentCell().setPiece(this);
        setSelected(false);
        if (destination.getColor().equals(this.getColor()))
            this.getPlayer().applyOnScore(4);
        if (destination.getPrize() != null)
            destination.getPrize().using(this);
        if (destination.getTransmitter() != null)
            destination.getTransmitter().transmit(this);
    }

    @Override
    public boolean thisTurnMove(Cell curCell) {
        Player curPlayer = getPlayer();
        if(!curPlayer.getSelectedPiece().isValidMove(curCell, curPlayer.getMoveLeft()))
            return false;
        curPlayer.getSelectedPiece().moveTo(curCell);
        return true;
    }

    @Override
    public boolean hasMove(int diceNumber) {
        int[] X = {0, 0, diceNumber, -diceNumber};
        int[] Y = {diceNumber, -diceNumber, 0, 0};
        for (int dir = 0; dir < 4; dir++) {
            Cell destination = Cell.CellFinder(this.getCurrentCell().getX() + X[dir], this.getCurrentCell().getY() + Y[dir]);
            if(destination != null && this.isValidMove(destination, diceNumber))
                return true;
        }
        return false;
    }

    public void getDamagedBySnake() {}
    public String getDetails() {
        return "isAlive: " + isAlive() + "\nhasOption: " + hasOption();
    }
}
