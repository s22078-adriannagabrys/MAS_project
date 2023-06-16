package Pharmacy.Action;

import Pharmacy.Drug.*;
import Pharmacy.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an action performed in a community pharmacy.
 * It extends the ObjectPlus class and implements the Serializable interface.
 */
public class Action extends ObjectPlus implements Serializable {
    private String id;
    private LocalDate date;
    private double price;

    private List<StockItem> stockItems = new ArrayList<>();
    private CommunityPharmacyEmployee communityPharmacyEmployee;


    /**
     * Creates a new Action instance with the specified date and associated community pharmacy employee
     * @param date
     * @param communityPharmacyEmployee
     */
    public Action(LocalDate date, CommunityPharmacyEmployee communityPharmacyEmployee) {
        super();
        this.id = UUID.randomUUID().toString();
        this.date = date;
        addEmployee(communityPharmacyEmployee);
    }

    /**
     * Registers a drug with the specified amount and refund level under this action
     * @param drug
     * @param amount
     * @param refundLevel
     * @return The created stock item representing the registered drug
     */
    public StockItem registerDrug(Drug drug, int amount, String refundLevel){
        StockItem stockItem = new StockItem(amount, this, drug);
        stockItem.setRefundLevel(refundLevel);
        return stockItem;
    }

    /**
     * Adds a new association with StockItem
     * @param newStockItem
     */
    public void addStockItem(StockItem newStockItem) {
        if(!stockItems.contains(newStockItem)) {
            stockItems.add(newStockItem);
            newStockItem.addAction(this);
        }
    }
    /**
     * Removes selected association with StockItem
     * @param toRemove
     */
    public void removeStockItem(StockItem toRemove) {
        if(stockItems.contains(toRemove)) {
            stockItems.remove(toRemove);
            toRemove.removeAction();
        }
    }

    /**
     * Adds a new association with CommunityPharmacyEmployee
     * @param newEmployee
     */
    public void addEmployee(CommunityPharmacyEmployee newEmployee) {
        communityPharmacyEmployee = newEmployee;
        communityPharmacyEmployee.addAction(this);
    }

    /**
     * Removes selected association with CommunityPharmacyEmployee
     */
    public void removeEmployee() {
        communityPharmacyEmployee.removeAction(this);
        communityPharmacyEmployee = null;
    }

    public List<StockItem> getStockItems() {
        return stockItems;
    }
    public CommunityPharmacyEmployee getCommunityPharmacyEmployee() {
        return communityPharmacyEmployee;
    }

    /**
     * Calculates the total price of all stock items
     * @return The total price of all stock items
     */
    public double calculatePrice() {
        double currentPrice = 0;
        StockItem currentStockItem;
        for (int i = 0; i < getStockItems().size(); i++) {
            currentStockItem = getStockItems().get(i);
            currentPrice += currentStockItem.calculatePriceWithRefund();
        }
        return currentPrice;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
