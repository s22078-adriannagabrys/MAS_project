package Pharmacy.GUI;

import Pharmacy.GUI.SaleController;
import Pharmacy.Models.Sale;
import Pharmacy.Models.ObjectPlus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.UUID;

/**
 * The controller class for managing invoices.
 */
public class InvoiceController {
    public Button confirmButton;
    public Button abortButton;
    public Label invoiceLabel;
    public Label invoiceNumberLabel;
    public TextField invoiceNumberTextField;
    public Label nameLabel;
    public TextField nameTextField;
    public Label surnameLabel;
    public TextField surnameTextField;
    public Label companyNameLabel;
    public TextField companyNameTextField;
    public Label nipLabel;
    public TextField nipTextField;
    public Label dateLabel;
    public TextField dateTextField;
    private SaleController saleController;

    /**
     * Sets the parent controller
     * @param saleController
     */
    public void setParentController(SaleController saleController) {
        this.saleController = saleController;
    }

    /**
     * It sets the initial values for the invoice number and date fields
     */
    @FXML
    public void initialize(){
        invoiceNumberTextField.setText(UUID.randomUUID().toString());
        dateTextField.setText(LocalDate.now().toString());
    }

    /**
     * Event handler for the confirmButton
     * Generates the invoice description based on the provided information and adds it as a document to the sale
     * Finally writes extents and finishes the usecase
     * @param actionEvent
     */
    public void eventGetDescriptionForInvoice(ActionEvent actionEvent){
        if(nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty() || companyNameTextField.getText().isEmpty() || nipTextField.getText().isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please provide information!");
            alert.showAndWait();
        }else{
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Name: ").append(nameTextField.getText()).append("\n");
            stringBuilder.append("Surname: ").append(surnameTextField.getText()).append("\n");
            stringBuilder.append("Company Name: ").append(companyNameTextField.getText()).append("\n");
            stringBuilder.append("Date: ").append(dateTextField.getText()).append("\n");
            stringBuilder.append("Medication: ").append(saleController.saleList.getItems());

            String result = stringBuilder.toString();

            saleController.getSale().createDocument(invoiceNumberTextField.getText(), Sale.DocumentType.INVOICE, result);

            try{
                ObjectPlus.writeExtents(new ObjectOutputStream(new FileOutputStream("Pharmacy.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.exit(0);
        }
    }

    /**
     * Closes the window associated with the button
     * @param actionEvent
     */
    public void eventClose(ActionEvent actionEvent) {
        Stage stage = (Stage) abortButton.getScene().getWindow();
        stage.close();
    }
}
