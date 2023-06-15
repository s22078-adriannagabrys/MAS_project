package Pharmacy;

import Pharmacy.Action.Action;
import Pharmacy.Drug.Drug;

import java.io.Serializable;

public class StockItem extends ObjectPlus implements Serializable {
    private int amount;
    private String refundLevel;
    private Drug drug;
    private Action action;

    public StockItem(int amount) {
        super();
        this.amount = amount;
    }

    public void addAction(Action newAction) {
        action = newAction;
        action.addStockItem(this);
    }

    public void removeAction() {
        action.removeStockItem(this);
        action = null;
    }
    public void addDrug(Drug newDrug) {
        drug = newDrug;
        drug.addStockItem(this);
    }

    public void removeDrug() {
        drug.removeStockItem(this);
        drug = null;
    }

    public double calculatePriceWithRefund() {
        double price = drug.getPrice() * Math.abs(amount);
        switch (refundLevel){
            case "Free":
                price = 0;
                break;
            case "30%":
                price = price*0.7;
                break;
            case "50%":
                price = price*0.5;
                break;
            case "No refund":
                break;
        }
        return (double) Math.round(price * 100) / 100;
    }

    public String getRefundLevel() {
        return refundLevel;
    }

    public void setRefundLevel(String refundLevel) {
        this.refundLevel = refundLevel;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public int getAmount() {
        return amount;
    }


    public Drug getDrug() {
        return drug;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "" + drug.getDescription() + "Qty: " + Math.abs(amount);
    }
}
