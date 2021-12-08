package ui;

import domain.model.board.Board;
import domain.model.cell.Cell;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.Curses;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TerminalRenderer implements Renderer<Board> {

    private final Terminal terminal;

    public TerminalRenderer() throws IOException {

        this.terminal = TerminalBuilder.terminal();

    }

    public void clear() throws IOException {
        try {
            terminal.output().write(Curses.tputs(terminal.getStringCapability(InfoCmp.Capability.clear_screen)).getBytes(StandardCharsets.UTF_8));
        } catch (NullPointerException npe) {
            newLine();
        }
    }

    public void render(Board board) throws IOException {

        Cell[][] cells = board.getCells();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                terminal.output().write(getBlock(cells[x][y].isLiving()).toString().getBytes(StandardCharsets.UTF_8));
                space();
            }
            newLine();
        }
        newLine();
    }

    private void space()throws IOException {
        terminal.output().write(" ".getBytes(StandardCharsets.UTF_8));
    }

    private void newLine() throws IOException {
        terminal.output().write("\r\n".getBytes(StandardCharsets.UTF_8));
    }

    private Character getBlock(Boolean full) {
        char full_block = (char) 9632;
        char open_block = (char) 9633;
        return full ? full_block : open_block;
    }
}

