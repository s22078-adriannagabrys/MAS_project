package Pharmacy.Action;

import Pharmacy.Action.Action;
import Pharmacy.StockItem;

import java.io.Serializable;
import java.time.LocalDate;

public class WarehouseUpdate extends Action implements Serializable {
    private String description;

    public WarehouseUpdate(int id, LocalDate date, String description) {
        super(id, date);
        this.description = description;
    }
}
