package Pharmacy.Action.Sale;

import Pharmacy.ObjectPlus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
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

    public void setParentController(SaleController saleController) {
        this.saleController = saleController;
    }

    @FXML
    public void initialize(){
        invoiceNumberTextField.setText(UUID.randomUUID().toString());
        dateTextField.setText(LocalDate.now().toString());
    }

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

    public void eventClose(ActionEvent actionEvent) {
        Stage stage = (Stage) abortButton.getScene().getWindow();
        stage.close();
    }
}
