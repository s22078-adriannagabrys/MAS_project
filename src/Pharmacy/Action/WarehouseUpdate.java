package Pharmacy.Action;

import Pharmacy.Action.Action;
import Pharmacy.CommunityPharmacyEmployee;
import Pharmacy.StockItem;

import java.io.Serializable;
import java.time.LocalDate;

public class WarehouseUpdate extends Action implements Serializable {
    private String description;

    public WarehouseUpdate(LocalDate date, CommunityPharmacyEmployee employee, String description) {
        super(date, employee);
        this.description = description;
    }
}
