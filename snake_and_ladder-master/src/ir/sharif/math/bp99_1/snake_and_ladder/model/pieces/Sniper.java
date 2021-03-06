package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Sniper extends Piece{
    public Sniper(Player player, Color color) {
        super(player, color);
    }

    public Sniper(Player player, Color color, boolean isAlive, boolean hasOption) {
        super(player, color, isAlive, hasOption);
    }

    @Override
    public boolean thisTurnMove(Cell curCell) {
        if(!isAlive())
            return false;
        Player curPlayer = this.getPlayer();
        if(curCell.getPiece() == null)
            return super.thisTurnMove(curCell);
        if(!hasOption())
            return false;
        if(curCell.getPiece().getPlayer().equals(curPlayer))
            return false;
        int dirx = curCell.getX() - this.getCurrentCell().getX();
        int diry = curCell.getY() - this.getCurrentCell().getY();
        if (dirx != 0 && diry != 0)
            return false;
        if(Math.abs(dirx) + Math.abs(diry) <= curPlayer.getMoveLeft() && curCell.getPiece().isAlive()) {
            curCell.getPiece().setAlive(false);
            this.setOption(false);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean hasMove(int diceNumber) {
        if (!isAlive())
            return false;
        if (super.hasMove(diceNumber))
            return true;
        if (!hasOption())
            return false;
        for (int distance = 1; distance <= diceNumber; distance++) {
            int[] X = {0, 0, distance, -distance};
            int[] Y = {distance, -distance, 0, 0};
            for (int dir = 0; dir < 4; dir++) {
                Cell destination = Cell.CellFinder(this.getCurrentCell().getX() + X[dir], this.getCurrentCell().getY() + Y[dir]);
                if (destination != null && destination.getPiece() != null &&
                        destination.getPiece().getPlayer() == this.getPlayer().getRival() && destination.getPiece().isAlive())
                    return true;
            }
        }
        return false;
    }

    @Override
    public String getDetails() {
        return "Sniper\n" + super.getDetails();
    }
}
