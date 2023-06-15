package Pharmacy.Action.Sale;

import Pharmacy.Action.Action;
import Pharmacy.Action.Order;
import Pharmacy.Action.WarehouseUpdate;
import Pharmacy.CommunityPharmacyEmployee;
import Pharmacy.Drug.CompoundedDrug;
import Pharmacy.Drug.Drug;
import Pharmacy.Drug.NonPrescriptionDrug;
import Pharmacy.Drug.PrescribedOnlyMedicine;
import Pharmacy.ObjectPlus;
import Pharmacy.Prescription.RegisteredPrescriptions;
import Pharmacy.StockItem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("SaleGUI.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //initData();
        try{
            ObjectPlus.readExtents(new ObjectInputStream(new FileInputStream("Pharmacy.txt")));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        launch();
    }

    public static void initData(){
        Drug drug1 = new NonPrescriptionDrug(1, "Ibuprofen", 3.00);
        Drug drug2 = new NonPrescriptionDrug(2, "Paracetamol", 5.46);
        Drug drug3 = new NonPrescriptionDrug(3, "Aspirin", 8.00);
        Drug drug4 = new NonPrescriptionDrug(4, "Magne B6", 16.00);
        Drug drug5 = new NonPrescriptionDrug(5, "Gripex", 14.00);
        Drug drug6 = new PrescribedOnlyMedicine(6, "Amoxicillin", 13.59, 1234);
        Drug drug7 = new PrescribedOnlyMedicine(7, "Lamitrin", 21.99, 4567);
        Drug drug8 = new PrescribedOnlyMedicine(8, "Acard", 40.89, 7890);
        Drug drug9 = new PrescribedOnlyMedicine(9, "Minovivax", 23.99, 4345);
        Drug drug10 = new CompoundedDrug(10, "Labetalol oral suspension", 23.55, "solution", "box", 10, new HashMap<String, Integer>(){{
            put("Lactose", 5);
            put("Sugar", 10);
            put("Salt", 3);
        }});

        StockItem stockItem1 = new StockItem(5);
        StockItem stockItem2 = new StockItem(5);
        StockItem stockItem3 = new StockItem(5);
        StockItem stockItem4 = new StockItem(5);
        StockItem stockItem5 = new StockItem(5);
        StockItem stockItem6 = new StockItem(5);
        StockItem stockItem7 = new StockItem(5);
        StockItem stockItem8 = new StockItem(5);
        StockItem stockItem9 = new StockItem(5);
        StockItem stockItem10 = new StockItem(5);
        drug1.addStockItem(stockItem1);
        drug2.addStockItem(stockItem2);
        drug3.addStockItem(stockItem3);
        drug4.addStockItem(stockItem4);
        drug5.addStockItem(stockItem5);
        drug6.addStockItem(stockItem6);
        drug7.addStockItem(stockItem7);
        drug8.addStockItem(stockItem8);
        drug9.addStockItem(stockItem9);
        drug10.addStockItem(stockItem10);


        //czy tu zrobić puste konstruktory
        Action action = new Action(LocalDate.now());
        Order order = new Order(LocalDate.now(), "Hasco", Order.Status.NEW);
        WarehouseUpdate warehouseUpdate = new WarehouseUpdate(LocalDate.now(), "Initial");
        warehouseUpdate.addStockItem(stockItem1);
        warehouseUpdate.addStockItem(stockItem2);
        warehouseUpdate.addStockItem(stockItem3);
        warehouseUpdate.addStockItem(stockItem4);
        warehouseUpdate.addStockItem(stockItem5);
        warehouseUpdate.addStockItem(stockItem6);
        warehouseUpdate.addStockItem(stockItem7);
        warehouseUpdate.addStockItem(stockItem8);
        warehouseUpdate.addStockItem(stockItem9);
        warehouseUpdate.addStockItem(stockItem10);

        CommunityPharmacyEmployee pharmacyEmployee = new CommunityPharmacyEmployee(1, "Anna", "Nowak", "123456789", LocalDate.of(2000, 12, 21), 31.00, LocalDate.of(2005, 12, 21));
        CommunityPharmacyEmployee pharmacyEmployee2 = new CommunityPharmacyEmployee(2, "Jolanta", "Nowak", "493728436", LocalDate.of(2000, 12, 21), 28.00, LocalDate.of(2017, 12, 21));

        pharmacyEmployee.addAction(order);
        pharmacyEmployee.addAction(warehouseUpdate);

        pharmacyEmployee.changeClassToPharmacist(12345);
        pharmacyEmployee2.changeClassToPharmacist(23456);
        try {
            pharmacyEmployee2.changeClassToManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            pharmacyEmployee.getPharmacists().addCompoundedDrug((CompoundedDrug) drug10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            ObjectPlus.writeExtents(new ObjectOutputStream(new FileOutputStream("Pharmacy.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
