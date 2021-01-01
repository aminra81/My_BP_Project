package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
    private final Color color;
    private final int x, y;
    private Transmitter transmitter;
    private Prize prize;
    private Piece piece;
    private List<Cell> adjacentOpenCells;
    private List<Cell> adjacentCells;

    public Cell(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.transmitter = null;
        this.prize = null;
        this.piece = null;
        this.adjacentOpenCells = new ArrayList<>();
        this.adjacentCells = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public List<Cell> getAdjacentCells() {
        return adjacentCells;
    }

    public void setAdjacentCells (List<Cell> adjacentCells) {
        this.adjacentCells = adjacentCells;
    }

    public List<Cell> getAdjacentOpenCells() {
        return adjacentOpenCells;
    }

    public void setAdjacentOpenCells (List<Cell> adjacentOpenCells) {
        this.adjacentOpenCells = adjacentOpenCells;
    }

    public Piece getPiece() {
        return piece;
    }

    public Prize getPrize() {
        return prize;
    }

    public Transmitter getTransmitter() {
        return transmitter;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public void setTransmitter(Transmitter transmitter) {
        this.transmitter = transmitter;
    }

    /**
     * @return true if piece can enter this cell, else return false
     */
    public boolean canEnter(Piece piece) {
        if(getColor().equals(Color.BLACK))
            return false;
        if(getPiece() != null)
            return false;
        if(getColor().equals(Color.WHITE) || getColor().equals(piece.getColor()))
            return true;
        return false;
    }

    public Cell adjacentOpen(int dirx, int diry){
        for (Cell cell : getAdjacentOpenCells())
            if(cell.getX() == this.getX() + dirx && cell.getY() == this.getY() + diry)
                return cell;
        return null;
    }


    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
