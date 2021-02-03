package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class NormalSnake extends Transmitter{
    protected final Color color = Color.BLUE;

    public NormalSnake(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }

    public void transmit(Piece piece) {
        if(this.getLastCell().canEnter(piece))
            super.transmit(piece);
    }

    public Color getColor() {
        return color;
    }
}
