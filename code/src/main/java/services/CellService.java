package services;

import domain.model.cell.Cell;
import domain.model.cell.suppliers.DeadCellSupplier;
import domain.model.cell.suppliers.LiveCellSupplier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class CellService {

    Supplier<Cell> deadCellSupplier = new DeadCellSupplier();
    Supplier<Cell> livingCellSupplier = new LiveCellSupplier();
    Map<Boolean, Supplier<Cell>> cellSupplierMap = new ConcurrentHashMap<>();

    public CellService() {
        cellSupplierMap.put(Boolean.TRUE, livingCellSupplier);
        cellSupplierMap.put(Boolean.FALSE, deadCellSupplier);
    }

    public Cell getCell(Boolean alive) {
        return cellSupplierMap.get(alive).get();
    }

    public void applyState(Cell source, Cell target) {
        if (source.isDead()) {
            target.ceasesLiving();
        } else {
            target.startsLiving();
        }
    }
}
