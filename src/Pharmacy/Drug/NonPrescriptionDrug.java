package Pharmacy.Drug;

import java.io.Serializable;

public class NonPrescriptionDrug extends Drug implements Serializable {
    String leaflet;

    public NonPrescriptionDrug(int drugID, String drugName, double price) {
        super(drugID, drugName, price);
    }


    @Override
    public String getDescription() {
        return "Nr: " + drugID + " " + drugName + " ";
    }

    @Override
    public String toString() {
        return "Nr: " + drugID + " " + drugName + " Amount: " + countStock() + " Price: " + price;
    }
}
