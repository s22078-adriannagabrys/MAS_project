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
import Pharmacy.StockItem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;

public class Main extends Application {
    private static CommunityPharmacyEmployee pharmacyEmployee;
    private static CommunityPharmacyEmployee pharmacyEmployee2;

    @Override
    public void start(Stage primaryStage) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SaleGUI.fxml"));
            Parent root = loader.load();
            SaleController controller = loader.getController();
            controller.setUser(pharmacyEmployee);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try{
//            ObjectPlus.readExtents(new ObjectInputStream(new FileInputStream("Pharmacy.txt")));
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        launch();
    }

    public static void initData() throws Exception {
        pharmacyEmployee = new CommunityPharmacyEmployee(1, "Anna", "Nowak", "123456789", LocalDate.of(2000, 12, 21), 31.00, LocalDate.of(2005, 12, 21));
        pharmacyEmployee2 = new CommunityPharmacyEmployee(2, "Jolanta", "Nowak", "493728436", LocalDate.of(2000, 12, 21), 28.00, LocalDate.of(2017, 12, 21));


        pharmacyEmployee.changeClassToPharmacist(12345);
        pharmacyEmployee2.changeClassToPharmacist(23456);
        try {
            pharmacyEmployee2.changeClassToManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        }}, pharmacyEmployee.getPharmacists());

        Action action = new Action(LocalDate.now(), pharmacyEmployee);
        Order order = new Order(LocalDate.now(),pharmacyEmployee,"Hasco", Order.Status.NEW);
        WarehouseUpdate warehouseUpdate = new WarehouseUpdate(LocalDate.now(),pharmacyEmployee, "Initial");

        StockItem stockItem1 = new StockItem(5, warehouseUpdate, drug1);
        StockItem stockItem2 = new StockItem(5, warehouseUpdate, drug2);
        StockItem stockItem3 = new StockItem(5, warehouseUpdate, drug3);
        StockItem stockItem4 = new StockItem(5, warehouseUpdate, drug4);
        StockItem stockItem5 = new StockItem(5, warehouseUpdate, drug5);
        StockItem stockItem6 = new StockItem(5, warehouseUpdate, drug6);
        StockItem stockItem7 = new StockItem(5, warehouseUpdate, drug7);
        StockItem stockItem8 = new StockItem(5, warehouseUpdate, drug8);
        StockItem stockItem9 = new StockItem(5, warehouseUpdate, drug9);
        StockItem stockItem10 = new StockItem(5, warehouseUpdate, drug10);

        try{
            ObjectPlus.writeExtents(new ObjectOutputStream(new FileOutputStream("Pharmacy.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
