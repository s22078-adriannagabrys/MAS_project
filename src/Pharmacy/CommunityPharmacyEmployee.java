package Pharmacy;

import Pharmacy.Action.Action;
import Pharmacy.Drug.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * The CommunityPharmacyEmployee class represents an employee in a community pharmacy.
 * It extends the ObjectPlus class and implements the Serializable interface.
 */
public class CommunityPharmacyEmployee extends ObjectPlus implements Serializable {
    private String id;
    private String name; //atrybut powtarzalny
    private String surName; //atrybut prosty, pojedynczy
    private String phoneNumber;
    private LocalDate birthDate; //atrybut konkretny
    private double salaryPerHour;
    private LocalDate dateOfDismissal; //atrybut opcjonalny
    private LocalDate dateOfEmployment; //atrybut złożony

    /**
     * Creates a new CommunityPharmacyEmployee instance.
     * @param id
     * @param name
     * @param surName
     * @param phoneNumber
     * @param birthDate
     * @param salaryPerHour
     * @param dateOfEmployment
     */
    public CommunityPharmacyEmployee(String id, String name, String surName, String phoneNumber, LocalDate birthDate, double salaryPerHour, LocalDate dateOfEmployment) {
        super();
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.salaryPerHour = salaryPerHour;
        this.dateOfEmployment = dateOfEmployment;
    }

    /**
     * Calculates the monthly salary based on the number of hours worked per month.
     * @param numberOfHoursWorkedPerMonth
     * @return
     */
    public double calculateMonthlySalary(int numberOfHoursWorkedPerMonth){
        return salaryPerHour * numberOfHoursWorkedPerMonth;
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setDateOfDismissal(LocalDate dateOfDismissal) {
        this.dateOfDismissal = dateOfDismissal;
    }

    public LocalDate getDateOfDismissal() {
        return dateOfDismissal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getSurName() {
        return surName;
    }
    public void setSurName(String surName) {
        this.surName = surName;
    }


    private List<Action> actions = new ArrayList<>();

    /**
     * Adds a new association with Action
     * @param newAction
     */
    public void addAction(Action newAction) {
        if(!actions.contains(newAction)) {
            actions.add(newAction);
            newAction.addEmployee(this);
        }
    }

    /**
     * Removes selected association with Action
     * @param toRemove
     */
    public void removeAction(Action toRemove) {
        if(actions.contains(toRemove)) {
            actions.remove(toRemove);
            toRemove.removeEmployee();
        }
    }

    /**
     * The pharmacist associated with the employee (dynamic association).
     */
    Pharmacist pharmacists;
    /**
     * Removes the pharmacist association.
     */
    public void removePharmacist(){
        pharmacists = null;

    }
    /**
     * The pharmacy manager associated with the employee (dynamic association).
     */
    PharmacyManager manager;
    /**
     * Removes the pharmacy manager association.
     */
    public void removeManager(){
        manager = null;
    }


    /**
     * Changes the employee's class to manager if qualified.
     * @throws Exception when pharmacist has less than 5 years of experience in pharmacy
     */
    public void changeClassToManager() throws Exception {
        if(pharmacists.has5YearsOfExperience()){
            manager = new PharmacyManager();
            removePharmacist();
        } else throw new Exception("Is not qualified to become manager");

    }


    /**
     * Changes the employee's class to pharmacist.
     * @param diplomaIndex
     */
    public void changeClassToPharmacist(int diplomaIndex){
        pharmacists = new Pharmacist(diplomaIndex);
        removeManager();
    }

    public PharmacyManager getManager() throws Exception{
        if(manager != null){
            return manager;
        } else throw new Exception("Is not Manager");
    }

    public Pharmacist getPharmacists() throws Exception{
        if(pharmacists != null){
            return pharmacists;
        } else throw new Exception("Is not Pharmacist");
    }

    public class Pharmacist implements Serializable{
        private int diplomaIndex;
        private double degreeBonus;

        /**
         * Creates a new Pharmacist instance.
         * @param diplomaIndex
         */
        public Pharmacist(int diplomaIndex) {
            this.diplomaIndex = diplomaIndex;
            this.degreeBonus = 5.00;
        }

        public double calculateMonthlySalary() {
            return getSalaryPerHour() * degreeBonus;
        }

        public boolean has5YearsOfExperience() {
            Period period = Period.between(dateOfEmployment, LocalDate.now());
            int yearsOfExperience = period.getYears();
            return yearsOfExperience >= 5;
        }

        public int getDiplomaIndex() {
            return diplomaIndex;
        }

        private List<CompoundedDrug> compoundedDrugs = new ArrayList<>();


        /**
         *  Adds new association with compounded drugs.
         * @param newCompoundedDrug
         */
        public void addCompoundedDrug(CompoundedDrug newCompoundedDrug) {
            if(!compoundedDrugs.contains(newCompoundedDrug)) {
                compoundedDrugs.add(newCompoundedDrug);
                newCompoundedDrug.addPharmacist(this);
            }
        }

        /**
         * Removes selected compounded drug from association
         * @param toRemove
         */
        public void removeCompoundedDrug(CompoundedDrug toRemove) {
            if(compoundedDrugs.contains(toRemove)) {
                compoundedDrugs.remove(toRemove);
                toRemove.removePharmacist();
            }
        }

        public List<CompoundedDrug> getCompoundedDrugs() {
            return compoundedDrugs;
        }
    }

    public class PharmacyManager implements Serializable{
        private double degreeBonus;

        /**
         * Creates a new Pharmacy manager instance.
         */
        public PharmacyManager() {
            this.degreeBonus = 10.00;
        }

        public double calculateMonthlySalary() {
            return getSalaryPerHour() * degreeBonus;
        }
    }
}
