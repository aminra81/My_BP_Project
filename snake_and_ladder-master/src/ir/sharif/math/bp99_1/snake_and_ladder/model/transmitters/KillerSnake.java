package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class KillerSnake extends Transmitter {

    protected final Color color = Color.BLACK;

    public KillerSnake(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }

    public void transmit(Piece piece) {
        piece.setAlive(false);
        super.transmit(piece);
    }

    public Color getColor() {
        return color;
    }

    public String getType() {
        return "P";
    }
}
