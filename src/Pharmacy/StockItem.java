package Pharmacy;

import Pharmacy.Action.Action;
import Pharmacy.Drug.Drug;

import java.io.Serializable;


/**
 * Represents a stock item
 * Extends ObjectPlus class and implements Serializable interface.
 */
public class StockItem extends ObjectPlus implements Serializable {
    private int amount;
    private String refundLevel;
    private Drug drug;
    private Action action;

    /**
     * Creates a new StockItem instance in association with Action and Drug.
     * @param amount
     * @param action
     * @param drug
     */
    public StockItem(int amount, Action action, Drug drug) {
        super();
        this.amount = amount;
        addAction(action);
        addDrug(drug);
    }

    /**
     * Adds a new association with Action
     * @param newAction
     */
    public void addAction(Action newAction) {
        this.action = newAction;
        action.addStockItem(this);
    }

    /**
     * Removes selected association with Action
     */
    public void removeAction() {
        this.action.removeStockItem(this);
        action = null;
    }

    /**
     * Adds a new association with Drug
     * @param newDrug
     */
    public void addDrug(Drug newDrug) {
        this.drug = newDrug;
        drug.addStockItem(this);
    }

    /**
     * Removes selected association with Drug
     */
    public void removeDrug() {
        this.drug.removeStockItem(this);
        drug = null;
    }

    /**
     * Calculates the price of the stock item with the refund level
     * @return the calculated price with refund level
     */
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
    public Drug getDrug() {
        return drug;
    }

    public Action getAction() {
        return action;
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


    @Override
    public String toString() {
        return "" + drug.getDescription() + "Qty: " + Math.abs(amount);
    }
}
