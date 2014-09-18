/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.guimain;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import medicalapp.data.Patient;
import static medicalapp.data.Patient.insertPatient;

/**
 * FXML Controller class
 *
 * @author Tony
 */
public class GuiMainController implements Initializable {

    @FXML
    private CalendarController calendarContoller;
    @FXML
    private PatientAddController patientAddController;

    //FXML for the Search
    @FXML
    private TextField MedicalAppSearch;

    //FXMLs for the AnchorPanes
    @FXML
    private AnchorPane MedicalAppMenu;
    @FXML
    private AnchorPane MedicalAppCalendar;
    @FXML
    private AnchorPane MedicalAppNewPatient;

    //FXMLs for the QuickLinks NAV MENU
    @FXML
    private ImageView MenuHome;
    @FXML
    private ImageView MenuPatientAdd;
    @FXML
    private ImageView MenuAppointmentAdd;
    @FXML
    private ImageView MenuMessenger;
    @FXML
    private ImageView MenuPatientDocuments;
    @FXML
    private ImageView MenuSettings;

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
    private void handleAddPatientMouse(MouseEvent event) {
        MedicalAppCalendar.setVisible(false);
        MedicalAppNewPatient.setVisible(true);
    }

    //Event handler when add patient icon is clicked
    private void handleMenuHomeMouse(MouseEvent event) {
        MedicalAppCalendar.setVisible(true);
        MedicalAppNewPatient.setVisible(false);
    }

    //Event handler when add patient icon is clicked
    private void handleAddPatientButton(MouseEvent event) {

        //If fields are entered correctly
        if (PatientAddFirstName.getText() == "" || PatientAddLastName.getText() == ""
                || PatientAddAddressStreetNumber.getText() == "" || PatientAddAddressStreetName.getText() == ""
                || PatientAddAddressSuburb.getText() == "" || PatientAddAddressPostcode.getText() == ""
                || PatientAddPrimaryContactNumber.getText() == ""
                || PatientAddFirstName.getText() == null || PatientAddLastName.getText() == null
                || PatientAddAddressStreetNumber.getText() == null || PatientAddAddressStreetName.getText() == null
                || PatientAddAddressSuburb.getText() == null || PatientAddAddressPostcode.getText() == null
                || PatientAddPrimaryContactNumber.getText() == null || PatientAddDateOfBirth.getValue()==null){

            //A new stage for popup dialog box
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            //dialog.initOwner(primaryStage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("Pls enter stuff into fields"));
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();

            System.out.println("Submit clicked");
            System.out.println(PatientAddFirstName.getText());
        } else {

            String firstName = PatientAddFirstName.getText();
            String lastName = PatientAddLastName.getText();
            Date dateOfBirth = convertLocalDateToDate(PatientAddDateOfBirth.getValue());
            String address = PatientAddAddressStreetNumber.getText() + " " +
                    PatientAddAddressStreetName.getText() + " " +
                    PatientAddAddressSuburb.getId() + " " + 
                    PatientAddAddressPostcode.getText();
            String contactNumber = PatientAddPrimaryContactNumber.getText() + " / "+ 
                    PatientAddSecondaryContactNumber.getText();

            //Connect to DB and insertPatient
            insertPatient(new Patient(1, null, null, 1, firstName, lastName,
                    true, false, address, dateOfBirth, contactNumber, false));

            MedicalAppNewPatient.setVisible(false);
            MedicalAppCalendar.setVisible(true);

        }
    }

    public static Date convertLocalDateToDate(LocalDate ld) {
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date res = Date.from(instant);
        return res;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        for (Node n : MedicalAppCalendar.getChildren()) {
//            System.out.println("hello world");  
//        }
        MenuPatientAdd.setOnMouseClicked(this::handleAddPatientMouse);
        MenuHome.setOnMouseClicked(this::handleMenuHomeMouse);
        PatientAddSubmit.setOnMouseClicked(this::handleAddPatientButton);

    }

//    public static group getGroup(AnchorPane parent){
//        Group g = new Group();
//        
//    }
}
