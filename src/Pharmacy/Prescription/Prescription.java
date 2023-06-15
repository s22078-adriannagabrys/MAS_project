package Pharmacy.Prescription;

import Pharmacy.Drug.*;

import java.time.LocalDate;

public class Prescription {
    private LocalDate dateOfPrescription;
    private String patientName;
    private String doctorName;
    private String drugName;
    private String dose;
    private int amount;

    public Prescription(LocalDate dateOfPrescription, String patientName, String doctorName, String drugName, String dose) {
        this.dateOfPrescription = dateOfPrescription;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.drugName = drugName;
        this.dose = dose;
    }

    public String getDrugName() {
        return drugName;
    }

    public LocalDate getDateOfPrescription() {
        return dateOfPrescription;
    }

    public void setDateOfPrescription(LocalDate dateOfPrescription) {
        this.dateOfPrescription = dateOfPrescription;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
       return drugName + " " + dose + "\n" + "Qty: " + amount;
    }
}
