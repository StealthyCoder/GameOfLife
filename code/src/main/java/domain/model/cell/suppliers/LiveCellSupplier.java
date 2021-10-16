package domain.model.cell.suppliers;

import domain.model.cell.Cell;

import java.util.function.Supplier;

public class LiveCellSupplier implements Supplier<Cell> {

    @Override
    public Cell get() {
        return new Cell(Boolean.TRUE);
    }
}
