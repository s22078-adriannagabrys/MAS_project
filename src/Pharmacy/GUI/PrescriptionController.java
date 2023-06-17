package Pharmacy.GUI;
import Pharmacy.DTOs.Prescription;
import Pharmacy.GUI.SaleController;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * The controller class for managing prescriptions.
 */
public class PrescriptionController {
    public Button addButton;
    public Label prescriptionListLabel;
    public ListView prescriptionList;
    public Button finishButton;
    public RadioButton refundRadioButton;
    public ComboBox refundComboBox;
    public RadioButton noRefundRadioButton;
    public Label amountLabel;
    public TextField amountTextField;
    private SaleController saleController;
    private String code;

    /**
     * Initializes the list with prescriptions form this code and also initializes refundComboBox
     * @param code
     */
    public void init(String code){
        this.code = code;
        for(Prescription prescription : saleController.getRegisteredPrescriptions().getAllRegisteredPrescriptions().get(this.code)){
            prescriptionList.getItems().add(prescription);
        }
        refundComboBox.getItems().addAll(
                "Free",
                "30%",
                "50%"
        );
        ToggleGroup toggleGroup = new ToggleGroup();
        refundRadioButton.setToggleGroup(toggleGroup);
        noRefundRadioButton.setToggleGroup(toggleGroup);
    }

    public void eventIsRefunded(ActionEvent actionEvent){
        refundComboBox.setDisable(false);
    }

    public void eventIsNotRefunded(ActionEvent actionEvent){
        refundComboBox.getSelectionModel().clearSelection();
        refundComboBox.setDisable(true);
    }

    /**
     * Sets the parent controller
     * @param saleController The parent SaleController.
     */
    public void setParentController (SaleController saleController){
        this.saleController = saleController;
    }

    /**
     * Event handler for the action when a prescribed drug is added to the sale list
     * @param actionEvent
     */
    public void clickAddPrescribedDrugToSaleList(ActionEvent actionEvent) {
        //check if drug and amount is selected
        if(!this.prescriptionList.getSelectionModel().isEmpty() && !amountTextField.getText().isEmpty()) {
            String selectedAmount = amountTextField.getText();
            Prescription prescription = (Prescription) prescriptionList.getSelectionModel().getSelectedItem();
            //The selected amount is out of range, or is not number
            if(!amountTextField.getText().matches("\\d+") || Integer.parseInt(amountTextField.getText()) <= 0 || Integer.parseInt(selectedAmount) > prescription.getAmount()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Amount is out of range!");
                alert.showAndWait();
            }else {
                //adding drug from prescription to sale list taking to account refund level, amount and drug name
                saleController.addDrugFromPrescriptionToSaleList( this.refundComboBox.getSelectionModel().isEmpty() ?
                        "No refund" : this.refundComboBox.getSelectionModel().getSelectedItem().toString(),
                        -Integer.parseInt(this.amountTextField.getText()), prescription.getDrugName());
                //check if this drug is on stock list
                if(!saleController.isDrugOnList){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("No drug in stock");
                    alert.showAndWait();
                }else{
                    //if is take the amount from prescription
                    prescription.setAmount(prescription.getAmount() - Integer.parseInt(selectedAmount));
                    prescriptionList.refresh();
                }
            }
        }
    }

    /**
     * Retrieves a drug back by updating its amount
     * @param drugName
     * @param selectedAmount
     */
    public void getDrugBack(String drugName, int selectedAmount){
        for(Prescription pres : saleController.getRegisteredPrescriptions().getAllRegisteredPrescriptions().get(code)){
            if(pres.getDrugName().equals(drugName)){
                pres.setAmount(pres.getAmount() + selectedAmount);
                break;
            }
        }
        prescriptionList.refresh();
    }

    /**
     * Closes the window associated with the button
     * @param actionEvent
     */
    public void eventClose(ActionEvent actionEvent) {
        Stage stage = (Stage) finishButton.getScene().getWindow();
        stage.close();
    }

    public ListView getPrescriptionList() {
        return prescriptionList;
    }
}
