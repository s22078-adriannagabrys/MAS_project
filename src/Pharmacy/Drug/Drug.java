package Pharmacy.Drug;

import Pharmacy.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Drug extends ObjectPlus implements Serializable {
    private static int counter = 0;
    protected String drugName;
    protected int drugID;
    protected double price;

    private List<StockItem> stockItems = new ArrayList<>();

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

    public void addStockItem(StockItem newStockItem) {
        if(!stockItems.contains(newStockItem)) {
            stockItems.add(newStockItem);
            newStockItem.addDrug(this);
        }
    }

    public void removeStockItem(StockItem toRemove) {
        if(stockItems.contains(toRemove)) {
            stockItems.remove(toRemove);
            toRemove.removeDrug();
        }
    }

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
