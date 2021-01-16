package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

public class Thief extends Piece {
    Prize hisPrize;

    public Thief(Player player, Color color) {
        super(player, color);
    }

    public void moveTo(Cell destination) {
        if (destination.getPiece() != null)
            return;
        this.getCurrentCell().setPiece(null);
        setCurrentCell(destination);
        this.getCurrentCell().setPiece(this);
        setSelected(false);
        if (destination.getColor().equals(this.getColor()))
            this.getPlayer().applyOnScore(4);
        if (destination.getPrize() != null && hisPrize != null)
            destination.getPrize().using(this);
        if (destination.getTransmitter() != null)
            destination.getTransmitter().transmit(this);
    }

    public boolean isValidMove(Cell destination, int diceNumber) {
        Cell currentCell = this.getCurrentCell();
        int dirx = destination.getX() - currentCell.getX();
        int diry = destination.getY() - currentCell.getY();
        int distance = Math.abs(dirx) + Math.abs(diry);
        return (dirx == 0 || diry == 0) && (destination.getPiece() == null) && (distance == diceNumber);
    }

    public void setOption(boolean option) {
        hasOption = true;
    }

    public void getDamagedBySnake() {
        hisPrize = null;
    }

    public boolean thisTurnMove(Cell curCell) {
        if (curCell.getPiece() == null)
            return super.thisTurnMove(curCell);

        if (!curCell.equals(this.getCurrentCell()))
            return false;

        if(this.hisPrize == null) {
            if(curCell.getPrize() == null)
                return false;

            this.hisPrize = curCell.getPrize();
            curCell.getPrize().destroy();
            return true;
        }
        if(curCell.getPrize() != null)
            curCell.getPrize().destroy();

        this.hisPrize.setCell(curCell);
        curCell.setPrize(this.hisPrize);
        this.hisPrize = null;
        return true;
    }

    @Override
    public boolean hasMove(int diceNumber) {
        if(!isAlive())
            return false;
        if(super.hasMove(diceNumber))
            return true;
        return this.hisPrize != null || this.getCurrentCell().getPrize() != null;
    }

    @Override
    public String getDetails() {
        return "Thief\n" + super.getDetails();
    }
}
