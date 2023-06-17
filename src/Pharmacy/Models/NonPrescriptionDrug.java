package Pharmacy.Models;

import Pharmacy.Models.Drug;

import java.io.Serializable;

/**
 * Class representing one type of drug - Non-prescription drug that extends Drug
 * It implements the Serializable interface
 */
public class NonPrescriptionDrug extends Drug implements Serializable {
    String leaflet;

    /**
     * Creates a new Non-prescription drug instance.
     * @param drugName
     * @param price
     */
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
