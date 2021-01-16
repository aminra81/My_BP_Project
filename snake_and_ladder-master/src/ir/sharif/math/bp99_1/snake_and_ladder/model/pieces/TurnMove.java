package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;

public interface TurnMove {
    boolean hasMove(int diceNumber);
    boolean thisTurnMove(Cell curCell);

}
