package Pharmacy.Action.Sale;

import Pharmacy.CommunityPharmacyEmployee;
import Pharmacy.Drug.*;
import Pharmacy.ObjectPlus;
import Pharmacy.Prescription.*;
import Pharmacy.StockItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class SaleController {

    public ListView saleList;
    public ListView allDrugsList;
    public Label saleLabel;
    public Label otcListLabel;
    public Label saleListLabel;
    public Label priceLabel;
    public TextField priceTextField;
    public Button addButton;
    public Button removeButton;
    public Button addPrescribedDrugButton;
    public Button confirmButton;
    public Label amountLabel;
    public TextField amountTextField;
    public ChoiceBox methodOfPaymentComboBox;
    private PrescriptionController prescriptionController;
    private RegisteredPrescriptions registeredPrescriptions;
    private Sale sale;
    public boolean isDrugOnList;
    private CommunityPharmacyEmployee employee;

    @FXML
    void initialize() {

        registeredPrescriptions = new RegisteredPrescriptions();
        try {
            for (Drug drug : ObjectPlus.getExtent(NonPrescriptionDrug.class)) {
                allDrugsList.getItems().add(drug);
            }
            for (Drug drug : ObjectPlus.getExtent(PrescribedOnlyMedicine.class)) {
                allDrugsList.getItems().add(drug);
            }
            for (Drug drug : ObjectPlus.getExtent(CompoundedDrug.class)) {
                allDrugsList.getItems().add(drug);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        methodOfPaymentComboBox.getItems().addAll(
                "Cash",
                "Card"
        );

    }

    public void setUser(CommunityPharmacyEmployee employee){
        this.employee = employee;
        sale = new Sale(LocalDate.now(), this.employee);
    }

    public void addDrugFromPrescriptionToSaleList(String refundLevel, int selectedAmount, String drugName) {
        isDrugOnList = false;

        Drug newDrug = null;
        //Check if drug with this name exists
        for(int i = 0; i < allDrugsList.getItems().size(); i++) {
            newDrug = (Drug) allDrugsList.getItems().get(i);
            if(newDrug.getDrugName().equals(drugName)){
                isDrugOnList = true;
                break;
            }
        }
        if(isDrugOnList) {
            addDrugToSaleList(refundLevel, selectedAmount, newDrug);
        }
    }

    public void addNonPrescribedDrugToSaleList(ActionEvent actionEvent){
        if(!amountTextField.getText().isEmpty() && !allDrugsList.getSelectionModel().isEmpty()){
            Drug newDrug = (Drug) allDrugsList.getSelectionModel().getSelectedItem();
            //The selected drug is on prescription
            if(!newDrug.getClass().equals(NonPrescriptionDrug.class)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("You are not allowed to add without prescription!");
                alert.showAndWait();
            }else{
                String refundLevel = "No refund";
                int selectedAmount = Integer.parseInt("-"+amountTextField.getText());
                //The selected amount is out of range
                if(!amountTextField.getText().matches("\\d+") || Integer.parseInt(amountTextField.getText()) > newDrug.getStock() || Integer.parseInt(amountTextField.getText()) <= 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Amount is out of range!");
                    alert.showAndWait();
                }else {
                        addDrugToSaleList(refundLevel, selectedAmount, newDrug);
                    }
                }
        }
    }

    private void addDrugToSaleList(String refundLevel, int selectedAmount, Drug newDrug) {
        StockItem stockItem = null;
        boolean isAlreadyInList = false;
        for (int i = 0; i < saleList.getItems().size(); i++) {
            stockItem = (StockItem) saleList.getItems().get(i);
            if (stockItem.getDrug().equals(newDrug)) {
                stockItem.setAmount(stockItem.getAmount() + selectedAmount);
                allDrugsList.refresh();
                saleList.refresh();
                isAlreadyInList = true;
                break;
            }
        }
        if (!isAlreadyInList) {
            //create new association (create stockItem)
            stockItem = sale.registerDrug(newDrug, selectedAmount, refundLevel);
            allDrugsList.refresh();
            //add to sales list
            saleList.getItems().add(stockItem);
        }
        //calculate price
        priceTextField.setText(String.valueOf(sale.calculatePrice()));
    }

    @FXML
    private void removeDrugFromSaleList(ActionEvent actionEvent){
        if(saleList.getSelectionModel().isEmpty()){

        }else{
            StockItem stockItem = (StockItem) saleList.getSelectionModel().getSelectedItem();;
            //The selected drug is on prescription
            if(!stockItem.getDrug().getClass().equals(NonPrescriptionDrug.class)){
                prescriptionController.getDrugBack(stockItem.getDrug().getDrugName(), Math.abs(stockItem.getAmount()));
            }
            saleList.getItems().remove(stockItem);
            sale.removeStockItem(stockItem);
            stockItem.removeDrug();
            allDrugsList.refresh();
            priceTextField.setText(String.valueOf(sale.calculatePrice()));
        }
    }

    public void eventCheckPrescriptionCode(ActionEvent actionEvent) throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Prescription");
        dialog.setHeaderText("Provide the code from prescription");
        dialog.setContentText("Enter code:");

        Optional<String> result = dialog.showAndWait();
        String code = "";
        if (result.isPresent()) {
            code = result.get();
        }
        if(registeredPrescriptions.getAllRegisteredPrescriptions().containsKey(code)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PrescriptionGUI.fxml"));
            Parent root = loader.load();
            prescriptionController = loader.getController();
            prescriptionController.setParentController(this);
            prescriptionController.init(code);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Wrong number");
            alert.showAndWait();
        }
    }

    public void eventCheckTheDocumentType(ActionEvent actionEvent) throws IOException {
        if (!methodOfPaymentComboBox.getSelectionModel().isEmpty() && !saleList.getItems().isEmpty()) {
            sale.setMethodOfPayment(methodOfPaymentComboBox.getSelectionModel().getSelectedItem().toString());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog with Custom Actions");
            alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
            alert.setContentText("Choose your option.");

            ButtonType buttonTypeOne = new ButtonType("Invoice");
            ButtonType buttonTypeTwo = new ButtonType("Receipt");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("InvoiceGUI.fxml"));
                Parent root = loader.load();
                InvoiceController invoiceController = loader.getController();
                invoiceController.setParentController(this);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else if (result.get() == buttonTypeTwo) {

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("Date: ").append(LocalDate.now() + "\n");
                stringBuilder.append("Medication: ").append(saleList.getItems());

                sale.createDocument(UUID.randomUUID().toString(), Sale.DocumentType.PAYMENTCONFIRMATION, stringBuilder.toString());

                try{
                    ObjectPlus.writeExtents(new ObjectOutputStream(new FileOutputStream("Pharmacy.txt")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.exit(0);
            }
        }
    }

    public RegisteredPrescriptions getRegisteredPrescriptions() {
        return registeredPrescriptions;
    }

    public Sale getSale() {
        return sale;
    }


}
