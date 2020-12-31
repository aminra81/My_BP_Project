package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board {
    private List<Cell> cells;
    private List<Transmitter> transmitters;
    private List<Wall> walls;
    private Map<Cell, Integer> startingCells;

    public Board() {
        cells = new LinkedList<>();
        transmitters = new LinkedList<>();
        walls = new LinkedList<>();
        startingCells = new HashMap<>();
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

    public Map<Cell, Integer> getStartingCells() {
        return startingCells;
    }

    public void setStartingCells(Map<Cell, Integer> startingCells) {
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
