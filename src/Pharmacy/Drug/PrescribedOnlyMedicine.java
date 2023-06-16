package Pharmacy.Drug;

import java.io.Serializable;


/**
 * Class representing one type of drug - POM(Prescribed Only Medicine) that extends Drug
 * It implements the Serializable interface
 */
public class PrescribedOnlyMedicine extends Drug implements Serializable {
    int licenceNumber;
    String theSummaryOfTheMedicinalProduct;

    /**
     * Creates a new POM(Prescribed Only Medicine) instance.
     * @param drugName
     * @param price
     * @param licenceNumber
     */
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
