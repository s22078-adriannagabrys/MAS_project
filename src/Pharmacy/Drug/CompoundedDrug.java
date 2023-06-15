package Pharmacy.Drug;

import Pharmacy.*;

import java.io.Serializable;
import java.util.Map;

public class CompoundedDrug extends Drug implements Serializable {
    private String formula;
    private String typeOfPackage;
    private double dose;
    private Map<String, Integer> ingredients;
    private CommunityPharmacyEmployee.Pharmacist pharmacist;

    public CompoundedDrug(String drugName, double price, String formula, String typeOfPackage, double dose, Map<String, Integer> ingredients, CommunityPharmacyEmployee.Pharmacist pharmacist) {
        super(drugName, price);
        this.formula = formula;
        this.typeOfPackage = typeOfPackage;
        this.dose = dose;
        this.ingredients = ingredients;
        addPharmacist(pharmacist);
    }

    public void addPharmacist(CommunityPharmacyEmployee.Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
        this.pharmacist.addCompoundedDrug(this);
    }

    public void removePharmacist() {
        pharmacist.removeCompoundedDrug(this);
        pharmacist = null;

    }

    public CommunityPharmacyEmployee.Pharmacist getPharmacist() {
        return pharmacist;
    }

    @Override
    public String getDescription() {
        return "Nr: " + drugID + " " + drugName + " ";
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(drugID).append("\n");
        sb.append("Name: ").append(drugName).append("\n");
        sb.append("Formula: ").append(formula).append("\n");
        sb.append("Type of Package: ").append(typeOfPackage).append("\n");
        sb.append("Dose: ").append(dose).append("\n");
        sb.append("Ingredients: \n");
        for (Map.Entry<String, Integer> ingredient : ingredients.entrySet()) {
            sb.append("- ").append(ingredient.getKey()).append(": ").append(ingredient.getValue()).append("\n");
        }
        return sb.toString();
    }

}
