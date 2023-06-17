package Pharmacy.GUI;

import Pharmacy.Models.*;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SaleGUI.fxml"));
            Parent root = loader.load();
            SaleController controller = loader.getController();
            for (CommunityPharmacyEmployee employee : ObjectPlus.getExtent(CommunityPharmacyEmployee.class)) {
                if(employee.getPhoneNumber().equals("123456789")){
                    controller.setUser(employee);
                }
            }
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        try {
//            initData();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try{
            ObjectPlus.readExtents(new ObjectInputStream(new FileInputStream("Pharmacy.txt")));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        launch();
    }

    public static void initData() throws Exception {
        CommunityPharmacyEmployee pharmacyEmployee;
        CommunityPharmacyEmployee pharmacyEmployee2;

        pharmacyEmployee = new CommunityPharmacyEmployee(UUID.randomUUID().toString(), "Anna", "Nowak", "123456789", LocalDate.of(2000, 12, 21), 31.00, LocalDate.of(2005, 12, 21));
        pharmacyEmployee2 = new CommunityPharmacyEmployee(UUID.randomUUID().toString(), "Jolanta", "Nowak", "493728436", LocalDate.of(2000, 12, 21), 28.00, LocalDate.of(2017, 12, 21));


        pharmacyEmployee.changeClassToPharmacist(12345);
        pharmacyEmployee2.changeClassToPharmacist(23456);
        try {
            pharmacyEmployee2.changeClassToManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

        NonPrescriptionDrug drug1 = new NonPrescriptionDrug( "Ibuprofen", 3.00);
        NonPrescriptionDrug drug2 = new NonPrescriptionDrug( "Paracetamol", 5.46);
        NonPrescriptionDrug drug3 = new NonPrescriptionDrug( "Aspirin", 8.00);
        NonPrescriptionDrug drug4 = new NonPrescriptionDrug( "Magne B6", 16.00);
        NonPrescriptionDrug drug5 = new NonPrescriptionDrug( "Gripex", 14.00);
        PrescribedOnlyMedicine drug6 = new PrescribedOnlyMedicine( "Amoxicillin", 13.59, 1234);
        PrescribedOnlyMedicine drug7 = new PrescribedOnlyMedicine( "Lamitrin", 21.99, 4567);
        PrescribedOnlyMedicine drug8 = new PrescribedOnlyMedicine( "Acard", 40.89, 7890);
        PrescribedOnlyMedicine drug9 = new PrescribedOnlyMedicine( "Minovivax", 23.99, 4345);
        CompoundedDrug drug10 = new CompoundedDrug( "Labetalol oral suspension", 23.55, "suspension", "box", 10, new HashMap<String, Integer>(){{
            put("Lactose", 5);
            put("Sugar", 10);
            put("Salt", 3);
        }}, pharmacyEmployee.getPharmacists());

        Action action = new Action(LocalDate.now(), pharmacyEmployee);
        Order order = new Order(LocalDate.now(),pharmacyEmployee,"Hasco", Order.Status.NEW);
        WarehouseUpdate warehouseUpdate = new WarehouseUpdate(LocalDate.now(),pharmacyEmployee, "Initial");

        StockItem stockItem1 = new StockItem(53, warehouseUpdate, drug1);
        StockItem stockItem2 = new StockItem(25, warehouseUpdate, drug2);
        StockItem stockItem3 = new StockItem(56, warehouseUpdate, drug3);
        StockItem stockItem4 = new StockItem(57, warehouseUpdate, drug4);
        StockItem stockItem5 = new StockItem(2, warehouseUpdate, drug5);
        StockItem stockItem6 = new StockItem(6, warehouseUpdate, drug6);
        StockItem stockItem7 = new StockItem(1, warehouseUpdate, drug7);
        StockItem stockItem8 = new StockItem(5, warehouseUpdate, drug8);
        StockItem stockItem9 = new StockItem(5, warehouseUpdate, drug9);
        StockItem stockItem10 = new StockItem(3, warehouseUpdate, drug10);


        try{
            ObjectPlus.writeExtents(new ObjectOutputStream(new FileOutputStream("Pharmacy.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
