/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.guimain;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Richard
 */
public class PaymentsController implements Initializable {

    @FXML
    private AnchorPane paymentsPane;
    @FXML
    private ComboBox invoicePatientCombo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initPayments() {

        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("All patients");
        invoicePatientCombo = new ComboBox();

        invoicePatientCombo.setItems(items);
//        invoicePatientCombo.getItems().add("All patients");
    }

    public AnchorPane getPaymentsPane() {
        return paymentsPane;
    }

    public ComboBox getInvoicePatientCombo() {
        return invoicePatientCombo;
    }

}
