package services;

import domain.dto.Coordinate;
import domain.model.board.Board;
import domain.model.cell.Cell;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class BoardService {

    CellService cellService = new CellService();

    public Board updateState(Board oldState) {

        Map<Coordinate, Cell> state = new ConcurrentHashMap<>();

        mapCellsToCoordinates(oldState)
                .entrySet()
                .parallelStream()
                .forEach(cellEntry -> {
                    Cell cell = cellEntry.getValue();
                    if (dieBecauseUnderpopulation(cell) || dieBecauseOvercrowding(cell)) {
                        state.put(cellEntry.getKey(), cellService.getCell(false));
                    }
                    if (liveNextGeneration(cell) || liveReproduction(cell)) {
                        state.put(cellEntry.getKey(), cellService.getCell(true));
                    }
                });

        Board newState = oldState.copy();
        applyState(state, newState);
        updateNeighbours(newState);

        return newState;
    }

    public void updateNeighbours(Board board) {
        Map<Coordinate, Cell> coordinateCellMap = mapCellsToCoordinates(board);
        Cell[][] cells = board.getCells();
        coordinateCellMap.entrySet().parallelStream().forEach(cellEntry -> {
            addNeighbour(cells, new Coordinate(cellEntry.getKey().getX() - 1, cellEntry.getKey().getY() - 1), cellEntry.getValue());
            addNeighbour(cells, new Coordinate(cellEntry.getKey().getX(), cellEntry.getKey().getY() - 1), cellEntry.getValue());
            addNeighbour(cells, new Coordinate(cellEntry.getKey().getX() + 1, cellEntry.getKey().getY() - 1), cellEntry.getValue());
            addNeighbour(cells, new Coordinate(cellEntry.getKey().getX() - 1, cellEntry.getKey().getY()), cellEntry.getValue());
            addNeighbour(cells, new Coordinate(cellEntry.getKey().getX() + 1, cellEntry.getKey().getY()), cellEntry.getValue());
            addNeighbour(cells, new Coordinate(cellEntry.getKey().getX() - 1, cellEntry.getKey().getY() + 1), cellEntry.getValue());
            addNeighbour(cells, new Coordinate(cellEntry.getKey().getX(), cellEntry.getKey().getY() + 1), cellEntry.getValue());
            addNeighbour(cells, new Coordinate(cellEntry.getKey().getX() + 1, cellEntry.getKey().getY() + 1), cellEntry.getValue());
        });
    }

    public Cell getCellFromCoordinate(Coordinate coordinate, Board board) {
        try {
            return board.getCells()[coordinate.getX()][coordinate.getY()];
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return null;
        }
    }

    public Cell getCellFromCoordinate(Coordinate coordinate, Cell[][] cells) {
        try {
            return cells[coordinate.getX()][coordinate.getY()];
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return null;
        }
    }

    public Map<Coordinate, Cell> mapCellsToCoordinates(Board board) {
        Map<Coordinate, Cell> result = new HashMap<>();
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                result.put(new Coordinate(i, j), board.getCells()[i][j]);
            }
        }
        return Map.copyOf(result);
    }


    private Boolean dieBecauseUnderpopulation(Cell c) {
        if (c.isLiving()) {
            return c.getNeighbours().stream().filter(Cell::isLiving).count() < 2;
        }
        return c.isLiving();
    }

    private Boolean liveNextGeneration(Cell c) {
        if (c.isLiving()) {
            long liveNeighbours = c.getNeighbours().stream().filter(Cell::isLiving).count();
            return liveNeighbours > 1 && liveNeighbours < 4;
        }
        return c.isLiving();
    }

    private Boolean dieBecauseOvercrowding(Cell c) {
        if (c.isLiving()) {
            return c.getNeighbours().stream().filter(Cell::isLiving).count() > 3;
        }
        return c.isLiving();
    }

    private Boolean liveReproduction(Cell c) {
        if (c.isDead()) {
            return c.getNeighbours().stream().filter(Cell::isLiving).count() == 3;
        }
        return c.isDead();
    }

    private void applyState(Map<Coordinate, Cell> state, Board board) {
        state.forEach(board::updateCell);
    }

    private void addNeighbour(Board board, Coordinate coordinate, Cell cell) {
        Cell neighbour = getCellFromCoordinate(coordinate, board);
        if (Objects.nonNull(neighbour)) {
            cell.addNeighbor(neighbour);
        }
    }

    private void addNeighbour(Cell[][] cells, Coordinate coordinate, Cell cell) {
        Cell neighbour = getCellFromCoordinate(coordinate, cells);
        if (Objects.nonNull(neighbour)) {
            cell.addNeighbor(neighbour);
        }
    }

}
