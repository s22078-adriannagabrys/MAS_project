package Pharmacy.GUI;

import Pharmacy.Models.*;
import Pharmacy.Models.CommunityPharmacyEmployee;
import Pharmacy.Models.ObjectPlus;
import Pharmacy.DTOs.RegisteredPrescriptions;
import Pharmacy.Models.StockItem;
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

/**
 * The controller class for managing sale.
 */
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

    /**
     * Initializes the SaleController.
     */
    @FXML
    void initialize() {

        registeredPrescriptions = new RegisteredPrescriptions();
        // Add all drugs from extent
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

        // Add payment methods to methodOfPaymentComboBox
        methodOfPaymentComboBox.getItems().addAll(
                "Cash",
                "Card"
        );

    }

    /**
     * Sets the user (employee) for the Sale
     * @param employee
     */
    public void setUser(CommunityPharmacyEmployee employee){
        this.employee = employee;
        sale = new Sale(LocalDate.now(), employee);
    }

    /**
     * Adds a drug from a prescription to the sale list (can be either POM or Compounded drug)
     * @param refundLevel
     * @param selectedAmount
     * @param drugName
     */
    public void addDrugFromPrescriptionToSaleList(String refundLevel, int selectedAmount, String drugName) {
        isDrugOnList = false;
        Drug newDrug = null;

        //Check if drug with this name exists and has stock
        for(int i = 0; i < allDrugsList.getItems().size(); i++) {
            newDrug = (Drug) allDrugsList.getItems().get(i);
            if(newDrug.getDrugName().equals(drugName) && newDrug.getStock() >= Math.abs(selectedAmount)){
                isDrugOnList = true;
                break;
            }
        }
        if(isDrugOnList) {
            addDrugToSaleList(refundLevel, selectedAmount, newDrug);
        }
    }

    /**
     * Adds a non-prescribed drug to the sale list
     * @param actionEvent
     */
    public void addNonPrescribedDrugToSaleList(ActionEvent actionEvent){
        //Check if any drug is selected and the amount is selected
        if(!amountTextField.getText().isEmpty() && !allDrugsList.getSelectionModel().isEmpty()){
            Drug newDrug = (Drug) allDrugsList.getSelectionModel().getSelectedItem();
            //The selected drug is on prescription (cannot add without prescription)
            if(!newDrug.getClass().equals(NonPrescriptionDrug.class)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("You are not allowed to add without prescription!");
                alert.showAndWait();
            }else{
                //Since it is the non-prescribed drug there is no refund
                String refundLevel = "No refund";
                int selectedAmount = -Integer.parseInt(amountTextField.getText());
                //The selected amount is out of range, or is not number
                if(!amountTextField.getText().matches("\\d+") || Integer.parseInt(amountTextField.getText()) > newDrug.getStock() || Integer.parseInt(amountTextField.getText()) <= 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Amount is out of range!");
                    alert.showAndWait();
                }else {
                    //adding drug to sale list
                        addDrugToSaleList(refundLevel, selectedAmount, newDrug);
                    }
                }
        }
    }

    /**
     *  Adds a drug to the sale list
     * @param refundLevel    The refund level for the drug.
     * @param selectedAmount The selected amount of the drug.
     * @param newDrug        The drug to add.
     */
    private void addDrugToSaleList(String refundLevel, int selectedAmount, Drug newDrug) {
        StockItem stockItem = null;
        boolean isAlreadyInList = false;
        //check if this drug was already added
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
        //set price
        priceTextField.setText(String.valueOf(sale.calculatePrice()));
    }

    /**
     * Removes a drug from the sale list
     * @param actionEvent
     */
    @FXML
    private void removeDrugFromSaleList(ActionEvent actionEvent){
        //check if any drug is selected to remove
        if(!saleList.getSelectionModel().isEmpty()){
            StockItem stockItem = (StockItem) saleList.getSelectionModel().getSelectedItem();;
            //The selected drug is on prescription
            if(!stockItem.getDrug().getClass().equals(NonPrescriptionDrug.class)){
                //Update the prescription list
                prescriptionController.getDrugBack(stockItem.getDrug().getDrugName(), Math.abs(stockItem.getAmount()));
            }
            //Remove StockItem from sale list
            saleList.getItems().remove(stockItem);
            //Remove associations
            stockItem.delete();
            allDrugsList.refresh();
            priceTextField.setText(String.valueOf(sale.calculatePrice()));
        }
    }

    /**
     * Handles the event of checking the prescription code and opening prescription window if correct
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * Handles the event of checking the document type and opens the invoice window if selected
     * @param actionEvent
     * @throws IOException
     */
    public void eventCheckTheDocumentType(ActionEvent actionEvent) throws IOException {
        if (!methodOfPaymentComboBox.getSelectionModel().isEmpty() && !saleList.getItems().isEmpty()) {
            sale.setMethodOfPayment(methodOfPaymentComboBox.getSelectionModel().getSelectedItem().toString());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog with Custom Actions");
            alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
            alert.setContentText("Choose your option.");

            ButtonType invoiceButton = new ButtonType("Invoice");
            ButtonType paymentConfirmationButton = new ButtonType("Receipt");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(invoiceButton, paymentConfirmationButton, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == invoiceButton) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("InvoiceGUI.fxml"));
                Parent root = loader.load();
                InvoiceController invoiceController = loader.getController();
                invoiceController.setParentController(this);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else if (result.get() == paymentConfirmationButton) {
                //if this document is selected creates document and writes extent
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
