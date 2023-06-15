package Pharmacy.Action;

import Pharmacy.Action.Sale.Sale;
import Pharmacy.Drug.*;
import Pharmacy.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Action extends ObjectPlus implements Serializable {
    private String id;
    private LocalDate date;
    private double price;

    private List<StockItem> stockItems = new ArrayList<>();
    private CommunityPharmacyEmployee communityPharmacyEmployee;


    public Action(LocalDate date) {
        super();
        this.id = UUID.randomUUID().toString();
        this.date = date;
    }

    public StockItem registerDrug(Drug drug, int amount, String refundLevel){
        StockItem stockItem = new StockItem(amount);
        stockItem.setRefundLevel(refundLevel);
        addStockItem(stockItem);
        stockItem.addDrug(drug);
        return stockItem;
    }

    public void addStockItem(StockItem newStockItem) {
        if(!stockItems.contains(newStockItem)) {
            stockItems.add(newStockItem);
            newStockItem.addAction(this);
        }
    }

    public void removeStockItem(StockItem toRemove) {
        if(stockItems.contains(toRemove)) {
            stockItems.remove(toRemove);
            toRemove.removeAction();
        }
    }

    public void addEmployee(CommunityPharmacyEmployee newEmployee) {
        communityPharmacyEmployee = newEmployee;
        communityPharmacyEmployee.addAction(this);
    }

    public void removeEmployee() {
        communityPharmacyEmployee.removeAction(this);
        communityPharmacyEmployee = null;
    }

    public List<StockItem> getStockItems() {
        return stockItems;
    }

    public StockItem findStockItemForSelectedDrug (String drugName, String refundLevel){
        if (getStockItems() == null) {
            return null;
        }

        for (StockItem stockItem : getStockItems()) {
            if (stockItem.getDrug().getDrugName().equals(drugName) && stockItem.getAction().equals(this) && stockItem.getRefundLevel().equals(refundLevel)) {
                return stockItem;
            }
        }

        return null;
    }

    public CommunityPharmacyEmployee getCommunityPharmacyEmployee() {
        return communityPharmacyEmployee;
    }

    public boolean isReady(){
        return true;
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
