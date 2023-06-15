package Pharmacy.Action;

import Pharmacy.Action.Action;

import java.io.Serializable;
import java.time.LocalDate;

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

    public Order(LocalDate date, String supplier, Status status) {
        super(date);
        this.supplier = supplier;
        this.status = status;
    }

    public boolean isReady(){
        if(status == Status.PREPARED){
            return true;
        }
        return false;
    }
}
