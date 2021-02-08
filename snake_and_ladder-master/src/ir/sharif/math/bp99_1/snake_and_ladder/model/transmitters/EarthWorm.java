package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

import java.util.Random;

public class EarthWorm extends Transmitter{

    protected final Color color = Color.GREEN;

    public EarthWorm(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }

    public void transmit(Piece piece) {
        final int boardSizeX = 7;
        final int boardSizeY = 16;
        Random randomCoordinateGenerator = new Random();
        int x = randomCoordinateGenerator.nextInt(boardSizeX) + 1;
        int y = randomCoordinateGenerator.nextInt(boardSizeY) + 1;
        setLastCell(Cell.CellFinder(x, y));
        super.transmit(piece);
        setLastCell(this.getFirstCell());
    }

    public Color getColor() {
        return color;
    }

    public String getType() {
        return "U";
    }
}
