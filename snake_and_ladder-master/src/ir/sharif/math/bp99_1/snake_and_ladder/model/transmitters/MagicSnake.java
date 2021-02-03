package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class MagicSnake extends Transmitter {

    protected final Color color = Color.RED;

    public MagicSnake(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }

    public void transmit(Piece piece) {
        piece.getPlayer().applyOnScore(6);
        piece.setOption(true);
        if(this.getLastCell().canEnter(piece))
            super.transmit(piece);
    }

    public Color getColor() {
        return color;
    }
}
