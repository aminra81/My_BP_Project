package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.*;

public class Board {
    private List<Cell> cells;
    private List<Transmitter> transmitters;
    private List<Wall> walls;
    private List<Cell> startingCells;

    public Board() {
        cells = new LinkedList<>();
        transmitters = new LinkedList<>();
        walls = new LinkedList<>();
        startingCells = new ArrayList<>();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells (List<Cell> cells) {
        this.cells = cells;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Cell> getStartingCells() {
        return startingCells;
    }

    public void setStartingCells(List<Cell> startingCells) {
        this.startingCells = startingCells;
    }

    public List<Transmitter> getTransmitters() {
        return transmitters;
    }

    public void setTransmitters(List<Transmitter> transmitters) {
        this.transmitters = transmitters;
    }


    /**
     * give x,y , return a cell with that coordinates
     * return null if not exist.
     */
    public Cell getCell(int x, int y) {
        for (Cell cell : getCells())
            if (cell.getX() == x && cell.getY() == y)
                return cell;
        return null;
    }
}
