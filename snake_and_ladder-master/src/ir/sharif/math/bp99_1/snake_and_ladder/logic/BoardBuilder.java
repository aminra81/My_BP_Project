package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Wall;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.*;

import java.util.*;


public class BoardBuilder {
    Scanner src;
    private final Board finalBoard;
    private int sizeX;
    private int sizeY;
    private Cell[][] cells;

    public BoardBuilder(Scanner src) {
        this.src = src;
        finalBoard = new Board();
    }

    /**
     * give you a string in constructor.
     * <p>
     * you should read the string and create a board according to it.
     */
    private void findDimensions() {
        src.next();
        sizeX = src.nextInt();
        sizeY = src.nextInt();
        cells = new Cell[sizeX + 1][sizeY + 1];
        src.next();
    }

    private Color getColor(String color) {
        return Color.valueOf(color);
    }

    private void buildCells() {
        for (int x = 1; x <= sizeX; x++)
            for (int y = 1; y <= sizeY; y++) {
                String color = src.next();
                Color curColor = getColor(color);
                cells[x][y] = new Cell(curColor, x, y);
            }
    }

    private void setStartCells() {
        Map<Cell, Integer> startingCells = new HashMap<>();
        src.next();
        int sizeOfStartingCells = src.nextInt();
        src.next();
        for (int numOfStartingCells = 0; numOfStartingCells < sizeOfStartingCells; numOfStartingCells++) {
            int x = src.nextInt();
            int y = src.nextInt();
            int playerNumber = src.nextInt();
            startingCells.put(cells[x][y], playerNumber);
        }
        finalBoard.setStartingCells(startingCells);
    }

    private void buildWalls() {
        src.next();
        List<Wall> walls = new LinkedList<>();
        int sizeOfWalls = src.nextInt();
        src.next();
        for (int numOfWalls = 0; numOfWalls < sizeOfWalls; numOfWalls++) {
            int x1 = src.nextInt();
            int y1 = src.nextInt();
            int x2 = src.nextInt();
            int y2 = src.nextInt();
            Wall curWall = new Wall(cells[x1][y1], cells[x2][y2]);
            walls.add(curWall);
        }
        finalBoard.setWalls(walls);
    }

    private void buildTransmitters() {
        src.next();
        List<Transmitter> transmitters = new LinkedList<>();
        int sizeOfTransmitters = src.nextInt();
        src.next();
        for (int numOfTransmitters = 0; numOfTransmitters < sizeOfTransmitters; numOfTransmitters++) {
            int x1 = src.nextInt();
            int y1 = src.nextInt();
            int x2 = src.nextInt();
            int y2 = src.nextInt();
            String type = src.next();
            Transmitter curTransmitter;
            switch(type) {
                case "U":
                    curTransmitter = new EarthWorm(cells[x1][y1], cells[x2][y2]);
                    break;
                case "O":
                    curTransmitter = new NormalSnake(cells[x1][y1], cells[x2][y2]);
                    break;
                case "M":
                    curTransmitter = new MagicSnake(cells[x1][y1], cells[x2][y2]);
                    break;
                case "P":
                    curTransmitter = new KillerSnake(cells[x1][y1], cells[x2][y2]);
                    break;
                default:
                    curTransmitter = new Transmitter(cells[x1][y1], cells[x2][y2]);
                    break;
            }
            transmitters.add(curTransmitter);
            cells[x1][y1].setTransmitter(curTransmitter);
        }
        finalBoard.setTransmitters(transmitters);
    }

    public void buildPrizes() {
        src.next();
        int sizeOfPrizes = src.nextInt();
        src.next();
        for (int numOfPrizes = 0; numOfPrizes < sizeOfPrizes; numOfPrizes++) {
            int x = src.nextInt();
            int y = src.nextInt();
            int point = src.nextInt();
            int chance = src.nextInt();
            int diceNumber = src.nextInt();
            Prize curPrize = new Prize(cells[x][y], point, chance, diceNumber);
            cells[x][y].setPrize(curPrize);
        }
    }
    boolean isOpen(Cell firstCell, Cell secondCell) {
        for (Wall wall : finalBoard.getWalls()) {
            if (wall.getCell1().equals(firstCell) && wall.getCell2().equals(secondCell))
                return false;
            if(wall.getCell2().equals(firstCell) && wall.getCell1().equals(secondCell))
                return false;
        }
        return true;
    }
    public void completeCells() {
        //first let's create adjacent list for each cell.
        int[] dirx = {-1, 1, 0, 0};
        int[] diry = {0, 0, 1, -1};
        for (int x = 1; x <= sizeX; x++)
            for (int y = 1; y <= sizeY; y++) {
                List<Cell> adjacentCells = new ArrayList<>();
                for (int adj = 0; adj < 4; adj++) {
                    int newX = x + dirx[adj];
                    int newY = y + diry[adj];
                    if(1 <= newX && newX <= sizeX && 1 <= newY && newY <= sizeY)
                        adjacentCells.add(cells[newX][newY]);
                }
                cells[x][y].setAdjacentCells(adjacentCells);
            }
        //now it's time to create adjacent open list for the cells.
        for (int x = 1; x <= sizeX; x++)
            for (int y = 1; y <= sizeY; y++) {
                List<Cell> adjacentOpenCells = new ArrayList<>();
                for (Cell adjacentCell : cells[x][y].getAdjacentCells())
                    if(isOpen(cells[x][y], adjacentCell))
                        adjacentOpenCells.add(adjacentCell);
                cells[x][y].setAdjacentOpenCells(adjacentOpenCells);
            }
        //and the last one: diagonalAdjacentCells.
        for (int x = 1; x <= sizeX; x++)
            for (int y = 1; y <= sizeY; y++) {
                List<Cell> diagonalAdjacentCells = new ArrayList<>();
                for (int deltax = -1; deltax <= 1; deltax++)
                    for (int deltay = -1; deltay <= 1; deltay++) {
                        int newX = x + deltax;
                        int newY = y + deltay;
                        if(1 <= newX && newX <= sizeX && 1 <= newY && newY <= sizeY)
                            diagonalAdjacentCells.add(cells[newX][newY]);
                    }
                cells[x][y].setDiagonalAdjacentCells(diagonalAdjacentCells);
            }
        //finalizing Board.
        List<Cell> finalCells = new LinkedList<>();
        for (int x = 1; x <= sizeX; x++)
            finalCells.addAll(Arrays.asList(cells[x]).subList(1, sizeY + 1));
        finalBoard.setCells(finalCells);
    }
    public Board build() {
        findDimensions();
        buildCells();
        setStartCells();
        buildWalls();
        buildTransmitters();
        buildPrizes();
        completeCells();
        return finalBoard;
    }

}
