package Pharmacy.Models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents an order action in a community pharmacy
 * An order is a type of action and can have different statuses
 * It implements the Serializable interface
 */
public class Order extends Action implements Serializable {

    public enum Status {
        NEW("New"), AUTHORISATION("Authorisation"), PREPARED("Prepared"), SENT("Sent"), CANCELLED("Cancelled"), REALISED("Realised");
        public String label;
        private Status(String label){
            this.label = label;
        }
        public String getLabel(){
            return label;
        }

    }

    private String supplier;
    private Status status;

    /**
     * Creates a new Order instance with the specified date, employee, supplier, and status
     * @param date
     * @param employee
     * @param supplier
     * @param status
     */
    public Order(LocalDate date, CommunityPharmacyEmployee employee, String supplier, Status status) {
        super(date, employee);
        this.supplier = supplier;
        this.status = status;
    }

    /**
     * Checks if its status is "Prepared"
     * @return true if status is "Prepared"
     */
    public boolean isReady(){
        if(status == Status.PREPARED){
            return true;
        }
        return false;
    }
}
