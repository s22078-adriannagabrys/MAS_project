package Pharmacy.Models;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * Represents an warehouse update action in a community pharmacy
 * An warehouse update is a type of action
 * It implements the Serializable interface
 */
public class WarehouseUpdate extends Action implements Serializable {
    private String description;

    /**
     * Creates a new WarehouseUpdate instance
     * @param date
     * @param employee
     * @param description
     */
    public WarehouseUpdate(LocalDate date, CommunityPharmacyEmployee employee, String description) {
        super(date, employee);
        this.description = description;
    }
}
