/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.guimain;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Richard
 */
public class PatientAddController implements Initializable {

    @FXML
    private AnchorPane MedicalAppNewPatient;
    @FXML
    private AnchorPane MedicalAppCalendar;

    //FXMLs for the ADD PATIENT Screen
    @FXML
    private TextField PatientAddFirstName;
    @FXML
    private TextField PatientAddLastName;
    @FXML
    private DatePicker PatientAddDateOfBirth;
    @FXML
    private ChoiceBox PatientAddGender;
    @FXML
    private TextField PatientAddAddressStreetNumber;
    @FXML
    private TextField PatientAddAddressStreetName;
    @FXML
    private TextField PatientAddAddressSuburb;
    @FXML
    private TextField PatientAddAddressPostcode;
    @FXML
    private TextField PatientAddPrimaryContactNumber;
    @FXML
    private TextField PatientAddSecondaryContactNumber;
    @FXML
    private Button PatientAddSubmit;

    //Event handler when add patient icon is clicked
    private void handleSubmitButton(MouseEvent event) {

        String firstName;
        String lastName;
        Date dateOfBirth;
        String streetNumber;

//        if (PatientAddFirstName.getText() != "" && PatientAddLastName.getText() != ""
//                && PatientAddAddressStreetNumber.getText() != "" && PatientAddAddressStreetName.getText() != ""
//                && PatientAddAddressSuburb.getText() != "" && PatientAddAddressPostcode.getText() != ""
//                && PatientAddPrimaryContactNumber.getText() != "") {
        MedicalAppNewPatient.setVisible(false);
        MedicalAppCalendar.setVisible(true);
        System.out.println("Submit clicked");
//        } else {
//            Dialogs.create()
//                    .owner(MedicalAppNewPatient)
//                    .title("Information Dialog")
//                    .masthead(null)
//                    .message("I have a great message for you!")
//                    .showInformation();
//
//        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PatientAddSubmit.setOnMouseClicked(this::handleSubmitButton);
    }
}
