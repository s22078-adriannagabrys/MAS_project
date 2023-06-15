package Pharmacy.Drug;

import java.io.Serializable;

public class PrescribedOnlyMedicine extends Drug implements Serializable {
    int licenceNumber;
    String theSummaryOfTheMedicinalProduct;

    public PrescribedOnlyMedicine( String drugName, double price, int licenceNumber) {
        super( drugName, price);
        this.licenceNumber = licenceNumber;
    }

    @Override
    public String getDescription() {
        return "Nr: " + drugID + " " + drugName  + " LicenceNumber: " + licenceNumber + " ";
    }

    @Override
    public String toString() {
      return "Nr: " + drugID + " " + drugName + " LicenceNumber: " + licenceNumber + " Amount: " + getStock() + " Price: " + price ;
    }
}
