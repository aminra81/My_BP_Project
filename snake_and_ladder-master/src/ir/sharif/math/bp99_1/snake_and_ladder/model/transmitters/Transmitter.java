package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Transmitter implements Transmitting {
    private final Cell firstCell;
    private Cell lastCell;
    protected Color color = Color.WHITE;

    public Transmitter(Cell firstCell, Cell lastCell) {
        this.firstCell = firstCell;
        this.lastCell = lastCell;

    }

    public Cell getFirstCell() {
        return firstCell;
    }

    public Cell getLastCell() {
        return lastCell;
    }

    public void setLastCell(Cell cell) { lastCell = cell; }

    /**
     * transmit piece to lastCell
     */

    public void transmit(Piece piece) {
        if(!piece.getCurrentCell().equals(getFirstCell()))
            return;
        Player curPlayer = piece.getPlayer();
        curPlayer.applyOnScore(-3);
        piece.getDamagedBySnake();
        if(this.getLastCell().canEnter(piece))
            piece.moveTo(getLastCell());
    }

    public Color getColor() {
        return color;
    }

    public String getType() {
        return "";
    }
}
