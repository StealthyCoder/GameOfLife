package domain.model.board;

import domain.dto.Coordinate;
import domain.model.cell.Cell;

import java.util.Arrays;
import java.util.Map;

public class Board {

    private final int width;
    private final int height;
    private final Cell[][] cells;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
    }

    public Board(int width, int height, Map<Coordinate, Cell> state) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        state.forEach(this::addCell);
    }

    public int size() {
        return height * width;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addCell(Coordinate coordinate, Cell cell) {
        cells[coordinate.getX()][coordinate.getY()] = new Cell(cell.isLiving());
    }

    public void updateCell(Coordinate coordinate, Cell cell) {
        addCell(coordinate, cell);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Board copy() {
        Board copy = new Board(this.width, this.height);
        Cell[][] copyCells = copy.getCells();
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                copyCells[x][y] = new Cell(cells[x][y].isLiving());
            }
        }
        return copy;
    }
}
