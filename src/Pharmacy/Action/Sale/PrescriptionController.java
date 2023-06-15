package Pharmacy.Action.Sale;

import Pharmacy.Prescription.Prescription;
import Pharmacy.Prescription.RegisteredPrescriptions;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    private RegisteredPrescriptions registeredPrescriptions;
    private SaleController saleController;
    private String code;

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

    public void setParentController (SaleController saleController){
        this.saleController = saleController;
    }

    public void clickAddPrescribedDrugToSaleList(ActionEvent actionEvent) {
        System.out.println(saleController.getRegisteredPrescriptions().getAllRegisteredPrescriptions());
        int selectedID;
        if(!this.prescriptionList.getSelectionModel().isEmpty() && !amountTextField.getText().isEmpty()) {
            String selectedAmount = amountTextField.getText();
            Prescription prescription = (Prescription) prescriptionList.getSelectionModel().getSelectedItem();
            //The selected amount is out of range
            if(!amountTextField.getText().matches("\\d+") || Integer.parseInt(amountTextField.getText()) <= 0 || Integer.parseInt(selectedAmount) > prescription.getAmount()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Amount is out of range!");
                alert.showAndWait();
            }else {
                saleController.addDrugFromPrescriptionToSaleList( this.refundComboBox.getSelectionModel().isEmpty() ? "No refund" : this.refundComboBox.getSelectionModel().getSelectedItem().toString(), Integer.parseInt("-"+ this.amountTextField.getText()), prescription.getDrugName());
                if(!saleController.isDrugOnList){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("No drug in stock");
                    alert.showAndWait();
                }else{
                    for(Prescription pres : saleController.getRegisteredPrescriptions().getAllRegisteredPrescriptions().get(code)){
                        if(pres.equals(prescription)){
                            pres.setAmount(prescription.getAmount() - Integer.parseInt(selectedAmount));
                            break;
                        }
                    }
                    prescriptionList.refresh();
                }
            }
        }
        System.out.println(saleController.getRegisteredPrescriptions().getAllRegisteredPrescriptions());
    }

    public void getDrugBack(String drugName, int selectedAmount){
        for(Prescription pres : saleController.getRegisteredPrescriptions().getAllRegisteredPrescriptions().get(code)){
            if(pres.getDrugName().equals(drugName)){
                pres.setAmount(pres.getAmount() + selectedAmount);
                break;
            }
        }
        prescriptionList.refresh();
    }

    public void eventClose(ActionEvent actionEvent) {
        Stage stage = (Stage) finishButton.getScene().getWindow();
        stage.close();
    }

    public ListView getPrescriptionList() {
        return prescriptionList;
    }
}
