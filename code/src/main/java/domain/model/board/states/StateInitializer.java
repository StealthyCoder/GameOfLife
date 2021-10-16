package domain.model.board.states;

import domain.model.board.Board;
import domain.dto.Coordinate;
import domain.model.cell.Cell;
import services.BoardService;
import services.CellService;

import java.util.HashMap;
import java.util.Map;

public class StateInitializer {
    private final static BoardService boardService = new BoardService();
    private final static CellService cellService = new CellService();

    public static void gliderState(Board board, Coordinate topLeft) {
        gliderState().forEach((coordinate, cell) -> {
            cellService.applyState(cell, boardService.getCellFromCoordinate(topLeft.add(coordinate), board));
        });
    }

    private static Map<Coordinate, Cell> gliderState() {
        Map<Coordinate, Cell> state = new HashMap<>();
        state.put(new Coordinate(0, 0), new Cell());
        state.put(new Coordinate(1, 0), new Cell(true));
        state.put(new Coordinate(2, 0), new Cell());

        state.put(new Coordinate(0, 1), new Cell());
        state.put(new Coordinate(1, 1), new Cell());
        state.put(new Coordinate(2, 1), new Cell(true));

        state.put(new Coordinate(0, 2), new Cell(true));
        state.put(new Coordinate(1, 2), new Cell(true));
        state.put(new Coordinate(2, 2), new Cell(true));

        return Map.copyOf(state);
    }
}

