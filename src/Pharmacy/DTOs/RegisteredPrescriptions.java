package Pharmacy.DTOs;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The RegisteredPrescriptions class represents a collection of registered prescriptions.
 */
public class RegisteredPrescriptions {
    private Map<String, List<Prescription>> allRegisteredPrescriptions;

    /**
     * Constructs a new RegisteredPrescriptions instance
     * Initializes map with sample prescription data
     */
    public RegisteredPrescriptions(){
        Prescription prescription1 = new Prescription(LocalDate.of(2000, 11, 16), "Anna Kowalska", "Jolanta Nowak", "Lamitrin", "2,5mg");

        Prescription prescription2 = new Prescription(LocalDate.of(2000, 11, 16), "Anna Kowalska", "Jolanta Nowak",
                "Acard", "10mg" );

        Prescription prescription3 = new Prescription(LocalDate.of(2000, 11, 16), "Anna Kowalska", "Jolanta Nowak",
                "Minovivax", "50ml" );

        prescription1.setAmount(3);
        prescription2.setAmount(4);
        prescription3.setAmount(4);

        List<Prescription> allPrescriptions1 = new ArrayList();
        allPrescriptions1.add(prescription1);
        allPrescriptions1.add(prescription2);
        allPrescriptions1.add(prescription3);

        Prescription prescription4 = new Prescription(LocalDate.of(2023, 11, 16), "Anna Nowak", "Stanisława Roman",
                "Amoxicilin", "5,6mg" );
        Prescription prescription5 = new Prescription(LocalDate.of(2023, 11, 16), "Anna Nowak", "Stanisława Roman",
                "Labetalol oral suspension", "10mg" );
        Prescription prescription6 = new Prescription(LocalDate.of(2023, 11, 16), "Anna Nowak", "Stanisława Roman",
                "Acard", "10mg" );

        prescription4.setAmount(1);
        prescription5.setAmount(4);
        prescription6.setAmount(4);

        List<Prescription> allPrescriptions2 = new ArrayList();
        allPrescriptions2.add(prescription4);
        allPrescriptions2.add(prescription5);
        allPrescriptions2.add(prescription6);

        allRegisteredPrescriptions = new HashMap<>();

        try{
            allRegisteredPrescriptions.put("1234", allPrescriptions1);
        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            allRegisteredPrescriptions.put("4321", allPrescriptions2);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public Map<String, List<Prescription>> getAllRegisteredPrescriptions() {
        return allRegisteredPrescriptions;
    }

    public void SetAmountForPrescription(String code, String drugName, int selectedAmount){
        for(Prescription pres : allRegisteredPrescriptions.get(code)){
            if(pres.getDrugName().equals(drugName)){
                pres.setAmount(pres.getAmount() + selectedAmount);
                break;
            }
        }
    }




}
