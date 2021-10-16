import domain.model.board.Board;
import domain.dto.Coordinate;
import domain.model.board.states.StateInitializer;
import domain.model.cell.Cell;
import services.BoardService;
import ui.TerminalRenderer;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Application {

    static BoardService boardService = new BoardService();

    public static void main(String[] argv) throws IOException, InterruptedException {
        long loopTime = 1000L / 30L;
        TerminalRenderer renderer = new TerminalRenderer();
        Board oldState = new Board(25, 25);
        Application application = new Application();
        application.initializeBoard(oldState);
        application.initializeState(oldState);

        Board newState = boardService.updateState(oldState);
        renderer.render(oldState);
        oldState = newState;

        while(true) {
            newState = boardService.updateState(oldState);
            renderer.clear();
            renderer.render(newState);
            oldState = newState;

            /**
             * The reason why it is doubles is that the average execution time plus normal FPS looptime is
             * about 70ms on average (40ms execution time and 30 ms looptime)
             */
            TimeUnit.MILLISECONDS.sleep(loopTime * 2);
        }


    }

    public void initializeBoard(Board board) {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                board.addCell(new Coordinate(x, y), new Cell());
            }
        }

        boardService.updateNeighbours(board);
    }

    private void initializeState(Board board) {
        StateInitializer.gliderState(board, new Coordinate(12, 12));
    }

}
