package Pharmacy.Action;

import Pharmacy.Action.Action;
import Pharmacy.StockItem;

import java.io.Serializable;
import java.time.LocalDate;

public class WarehouseUpdate extends Action implements Serializable {
    private String description;

    public WarehouseUpdate(LocalDate date, String description) {
        super(date);
        this.description = description;
    }
}
