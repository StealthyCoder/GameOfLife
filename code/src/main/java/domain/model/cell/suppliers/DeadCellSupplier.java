package domain.model.cell.suppliers;

import domain.model.cell.Cell;

import java.util.function.Supplier;

public class DeadCellSupplier implements Supplier<Cell> {

    @Override
    public Cell get() {
        return new Cell();
    }
}
