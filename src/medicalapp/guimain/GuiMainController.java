/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.guimain;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javax.swing.event.ChangeEvent;
import medicalapp.data.Appointment;
import static medicalapp.data.Appointment.doubleToDuration;
import medicalapp.data.DBConnection;
import medicalapp.data.Doctor;
import medicalapp.data.Patient;
import static medicalapp.data.Patient.insertPatient;
import static medicalapp.data.Patient.searchPatients;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author Tony
 */
public class GuiMainController implements Initializable {

    /**
     * Fields for timetable
     */
    private static ScrollPane timetablePane;

    //FXML for the Search
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private ListView searchList;

    //FXMLs for the AnchorPanes
    @FXML
    private AnchorPane MedicalAppMenu;
    @FXML
    private AnchorPane MedicalAppCalendar;
    @FXML
    private AnchorPane MedicalAppSearch;
    @FXML
    private AnchorPane timetableAnchorPane; //child of MedicalAppCalendar
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

    private void handleSearchTextField(ChangeEvent event) {
        refreshSearchList();
    }

    private void refreshSearchList() {

        searchList.setVisible(true);
        String searchTerm = searchTextField.getText().toLowerCase();
        String[] splitTerm = searchTerm.split("\\s+");

        //remove duplicate results
        ArrayList<Patient> patients = searchPatients(splitTerm);
        for (int i = 0; i < patients.size(); i++) {
            if (i != patients.size() - 1) {
                if (patients.get(i).getPatientID() == patients.get(i + 1).getPatientID()) {
                    patients.remove(patients.get(i));
                }
            }
        }
        ObservableList<StackPane> searchResultItems = FXCollections.observableArrayList();
        ArrayList<Label> labels = new ArrayList<>();
        if (patients != null || !patients.isEmpty()) {
            for (Patient p : patients) {

                Label label = new Label(p.getFirstName() + " " + p.getLastName() + "\t" + p.getAddress());
                labels.add(label);
                StackPane itemPane = new StackPane();

                HBox labelBox = new HBox();
                labelBox.setAlignment(Pos.CENTER_LEFT);

                boolean match = true; //all search terms match patient fields

                for (String term : splitTerm) {
                    if (!label.getText().toLowerCase().contains(term.toLowerCase())) {
                        match = false;
                        System.out.println("false");
                    }
                }
                
                if (match ) {
                    System.out.println("match");
                    labelBox.getChildren().add(label);

                    HBox buttonBox = new HBox();
                    buttonBox.setAlignment(Pos.CENTER_RIGHT);

                    Button appointmentButton = new Button("Set Appointment");
                    buttonBox.getChildren().add(appointmentButton);

                    itemPane.getChildren().add(labelBox);

                    itemPane.getChildren().add(buttonBox);
                    searchResultItems.add(itemPane);
                }
            }
            // remove duplicates
            HashSet hs = new HashSet();
            hs.addAll(searchResultItems);
            searchResultItems.clear();
            searchResultItems.addAll(hs);

            searchList.getItems().clear();
            searchList.setItems(searchResultItems);
            searchList.setMaxHeight(searchList.USE_PREF_SIZE);
            searchList.setPrefHeight(searchResultItems.size() * 40 + 2); //row height is 24 px by default
        }
        if (searchTextField.getText().equals("")) {
            searchList.setVisible(false);
        }
    }

//    private void refreshSearchList() {
//
//        searchList.setVisible(true);
//        String searchTerm = searchTextField.getText().toLowerCase();
//
//        ArrayList<Patient> patients = searchPatients(searchTerm);
//        ObservableList<StackPane> searchResultItems = FXCollections.observableArrayList();
//
//        if (patients != null || !patients.isEmpty()) {
//            for (Patient p : patients) {
//                Label lbl = new Label(p.getFirstName() + " " + p.getLastName() + "\t" + p.getAddress());
//                StackPane sp = new StackPane();
//                sp.getChildren().add(lbl);
//                searchResultItems.add(sp);
//            }
//            searchList.getItems().clear();
//            searchList.setItems(searchResultItems);
//            searchList.setPrefHeight(searchResultItems.size() * 30 + 2); //row height is 24 px by default
//
//        }
//        if (searchTextField.getText().equals("")) {
//            searchList.setVisible(false);
//
//        }
//    }
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
                || PatientAddPrimaryContactNumber.getText() == null || PatientAddDateOfBirth.getValue() == null) {

//            //A new stage for popup dialog box0
//            final Stage dialog = new Stage();
//            dialog.initModality(Modality.APPLICATION_MODAL);
//            //dialog.initOwner(primaryStage);
//            VBox dialogVbox = new VBox(20);
//            dialogVbox.getChildren().add(new Text("Pls enter stuff into fields"));
//            Scene dialogScene = new Scene(dialogVbox, 300, 200);
//            dialog.setScene(dialogScene);
//            dialog.show();
            Dialogs.create()
                    .owner(null)
                    .title("Input Error")
                    .masthead(null)
                    .message("You seem to have input missing in some field(s). Please complete the fields correctly.")
                    .showInformation();

            System.out.println("Submit clicked");
            System.out.println(PatientAddFirstName.getText());
        } else {

            String firstName = PatientAddFirstName.getText();
            String lastName = PatientAddLastName.getText();
            Date dateOfBirth = convertLocalDateToDate(PatientAddDateOfBirth.getValue());
            String address = PatientAddAddressStreetNumber.getText() + " "
                    + PatientAddAddressStreetName.getText() + " "
                    + PatientAddAddressSuburb.getText() + " "
                    + PatientAddAddressPostcode.getText();
            String contactNumber = PatientAddPrimaryContactNumber.getText() + " / "
                    + PatientAddSecondaryContactNumber.getText();

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

    public static ScrollPane initTimetable() {
        timetablePane = new ScrollPane();
        timetablePane.setPrefSize(800, 400);
        refreshTimetable();
        return timetablePane;
    }

    public static void refreshTimetable() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        ArrayList<Appointment> appointments = new ArrayList<>();

        for (int i = 1; i <= Doctor.getMaxID(); i++) {
            doctors.add(Doctor.getDoctor(i));
        }
        for (Doctor doctor : doctors) {
            try {
                Connection conn = DBConnection.getInstance().getConnection();
                String query = "SELECT * FROM Appointment "
                        + "WHERE Appointment.doctorID = ? AND expired <> true ";
                PreparedStatement stm = conn.prepareStatement(query);
                stm.setInt(1, doctor.getDoctorID());

                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    int appointmentID = rs.getInt("appointmentID");
                    Date date = rs.getTimestamp("date");
                    int patientID = rs.getInt("patientID");
                    int doctorID = rs.getInt("doctorID");
                    String purpose = rs.getString("purpose");
                    Duration duration = doubleToDuration(rs.getDouble("duration")); //DB stores duration as a double. Convert double -> long -> Duration (minutes)
                    String referringGP = rs.getString("referringGP");
                    Boolean expired = rs.getBoolean("expired");
                    Boolean finished = rs.getBoolean("finished");
                    appointments.add(new Appointment(appointmentID, date, patientID, doctorID, purpose,
                            duration, referringGP, expired, finished));
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                }
            } catch (SQLException ex) {
                Logger.getLogger(GuiMainController.class.getName()).log(Level.SEVERE, "Error getting appointments from doctor" + doctor.getDoctorID(), ex);
            }
            if (!appointments.isEmpty()) {

                for (Appointment a : appointments) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(a.getDate());
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);
                    System.out.println("timetable: read " + appointments.get(appointments.size() - 1).getAppointmentID());

                    if (day == CalendarController.getCurrentDay() && month == CalendarController.getCurrentMonth()
                            && year == CalendarController.getCurrentYear()) {
                        Label label = new Label(Patient.getPatient(a.getPatientID()).getFirstName() + " "
                                + Patient.getPatient(a.getPatientID()).getFirstName());
                        label.setLayoutX((doctors.indexOf(doctor) / doctors.size() - 1) * timetablePane.getWidth()); //set layout according to placement of doctor in table column

                        Date date = a.getDate();
                        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        String time = timeFormat.format(date);
                        label.setLayoutY((toMins(time) / 8 * 4 * 15) * timetablePane.getHeight()); //set layout according to proportion of 9-5pm time slots (32 of 15 min. slots)

                        label.setPrefHeight((a.getDuration().toMinutes() / 8 * 4 * 15) * timetablePane.getHeight()); //set height of label reflecting its duration vs. height of timetable
                        label.setStyle("-fx-border-color: black;");

                        timetablePane.setContent(label);
                        System.out.println("theres an appointment on that date");

                    }

                }
            }
        }
    }

    /**
     * @param s H:m timestamp, i.e. [Hour in day (0-23)]:[Minute in hour (0-59)]
     * @return total minutes after 00:00
     */
    private static int toMins(String s) {
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
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
        //searchTextField.setOnInputMethodTextChanged(this::handleSearchTextField);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            refreshSearchList();
        });

//        /*
//         * This listener will resize the ListView when items are added or removed
//         * from the ObservableList that is backing the ListView:
//         */
//        searchResultItems.addListener(new ListChangeListener() {
//            @Override
//            public void onChanger(ListChangeListener.Change change) {
//                searchResultItems.setPrefHeight(searchResultItems.size() * ROW_HEIGHT + 2);
//            }
//        });
        searchList.setVisible(false);
        MedicalAppSearch.setMinSize(0, 0);
        HBox timetableBox = new HBox();
        timetableBox.getChildren().add(initTimetable());
        timetableAnchorPane.getChildren().add(timetableBox);

    }

//    public static group getGroup(AnchorPane parent){
//        Group g = new Group();
//        
//    }
//    public static String getSearchText(){
//        return MedicalAppSearch.getText();
//    }
}
