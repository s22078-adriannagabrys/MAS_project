package Pharmacy.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Abstract class representing a drug
 * It extends the ObjectPlus class and implements the Serializable interface.
 */
public abstract class Drug extends ObjectPlus implements Serializable {
    private static int counter = 0;
    protected String drugName;
    protected int drugID;
    protected double price;

    private List<StockItem> stockItems = new ArrayList<>();

    /**
     * Creates a new Drug instance and sets the unique Id.
     * @param drugName
     * @param price
     */
    protected Drug(String drugName, double price) {
        super();
        this.drugName = drugName;
        this.drugID = generateUniqueId();
        this.price = price;
    }

    private int generateUniqueId() {
        counter++;
        return counter;
    }

    public String getDrugName() {
        return drugName;
    }

    public int getDrugID() {
        return drugID;
    }

    public abstract String getDescription();

    public List<StockItem> getStockItems() {
        return stockItems;
    }

    /**
     * Adds a new association with StockItem
     * @param newStockItem
     */
    void addStockItem(StockItem newStockItem) {
        if(newStockItem != null){
            if(!stockItems.contains(newStockItem)) {
                stockItems.add(newStockItem);
                newStockItem.addDrug(this);
            }
        }else throw new NullPointerException();
    }

    /**
     * Removes selected association with StockItem
     * @param toRemove
     */
    void removeStockItem(StockItem toRemove) {
        if(stockItems.contains(toRemove)) {
            stockItems.remove(toRemove);
            toRemove.removeDrug();
        }
    }

    /**
     * Calculates the complete stock amount for this drug
     * @return calculated stock amount from all associations
     */
    public int getStock(){
        int stock = 0;
        for(int i = 0; i< stockItems.size(); i++){
            stock+= stockItems.get(i).getAmount();
        }
        return stock;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Nr: " + drugID + " " + drugName + " Amount: " + getStock() + " Price: " + price;
    }
}
