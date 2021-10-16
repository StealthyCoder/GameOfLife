package domain.model.cell;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cell {

    private Boolean living;
    private final List<Cell> neighbours;

    public Cell() {
        this(false);
    }

    public Cell(Boolean isLiving) {
        this.living = isLiving;
        this.neighbours = new CopyOnWriteArrayList<>();
    }

    public void startsLiving() {
        living = Boolean.TRUE;
    }

    public void ceasesLiving() {
        living = Boolean.FALSE;
    }

    public Boolean isLiving() {
        return living;
    }

    public Boolean isDead() {
        return !living;
    }

    public void addNeighbor(Cell cell) {
        neighbours.add(cell);
    }

    public List<Cell> getNeighbours() {
        return List.copyOf(neighbours);
    }

}
