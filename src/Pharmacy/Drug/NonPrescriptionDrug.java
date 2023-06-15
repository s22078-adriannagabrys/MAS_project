package Pharmacy.Drug;

import java.io.Serializable;

public class NonPrescriptionDrug extends Drug implements Serializable {
    String leaflet;

    public NonPrescriptionDrug(String drugName, double price) {
        super(drugName, price);
    }


    @Override
    public String getDescription() {
        return "Nr: " + drugID + " " + drugName + " ";
    }

    @Override
    public String toString() {
        return "Nr: " + drugID + " " + drugName + " Amount: " + getStock() + " Price: " + price;
    }
}
