/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.guimain;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Tony
 */
public class GuiMainController implements Initializable {

    @FXML
    private CalendarController calendarContoller;

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
    private void handleAddPatientMouse(MouseEvent event){
        MedicalAppCalendar.setVisible(false);
        MedicalAppNewPatient.setVisible(true);
    }
    //Event handler when add patient icon is clicked
    private void handleMenuHomeMouse(MouseEvent event){
        MedicalAppCalendar.setVisible(true);
        MedicalAppNewPatient.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        for (Node n : MedicalAppCalendar.getChildren()) {
//            System.out.println("hello world");  
//        }
            MenuPatientAdd.setOnMouseClicked(this::handleAddPatientMouse);
            MenuHome.setOnMouseClicked(this::handleMenuHomeMouse);

    }

//    public static group getGroup(AnchorPane parent){
//        Group g = new Group();
//        
//    }
}
