package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Bomber extends Piece{
    public Bomber(Player player, Color color) {
        super(player, color);
    }

    private void Destroy(Cell curCell) {
        for (Cell cell : curCell.getDiagonalAdjacentCells()) {
            if(cell.getPrize() != null)
                cell.getPrize().destroy();
            if(cell.getPiece() != null)
                cell.getPiece().setAlive(false);
        }
        curCell.setColor(Color.BLACK);
    }
    @Override
    public boolean thisTurnMove(Cell curCell) {
        if(curCell.getPiece() == null)
            return super.thisTurnMove(curCell);
        if(!curCell.equals(this.getCurrentCell()))
            return false;
        Destroy(curCell);
        return true;
    }

    @Override
    public boolean hasMove(int diceNumber) {
        if(!isAlive())
            return false;
        if(super.hasMove(diceNumber))
            return true;
        return hasOption();
    }

    @Override
    public String getDetails() {
        return "Bomber\n" + super.getDetails();
    }
}
