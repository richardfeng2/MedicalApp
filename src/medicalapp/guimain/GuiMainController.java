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
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javax.swing.event.ChangeEvent;
import medicalapp.data.Appointment;
import static medicalapp.data.Appointment.doubleToDuration;
import medicalapp.data.DBConnection;
import medicalapp.data.Doctor;
import static medicalapp.data.Doctor.getDoctor;
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
     * Fields for calendar.
     */
    private static ArrayList<String> months = new ArrayList<>();
    private static int realDay;
    private static int realMonth;
    private static int realYear;
    private static int currentMonth;
    private static int currentYear;
    private static Label monthLabel;
    private static int currentDay;
    private static GridPane calendarGrid;
    private static ComboBox yearCombo;
    private static VBox calendarBox;
    private static Button prevButton;
    private static Button nextButton;

    /**
     * Fields for schedule
     */
    private static int currentHour;
    private static int currentMinute;
    private static Doctor currentDoctor;
    private static Label scheduleLabel;
    private static Label scheduleDateLabel;
    private static ComboBox doctorCombo;
    private static TilePane scheduleTile = new TilePane();
    private static ArrayList<Button> timeBtn;

    private static HBox calDashBoard;

    /**
     * Fields for timetable
     */
    private static ScrollPane timetablePane;

    /**
     * Fields for search
     */
    private static Patient currentPatient;

    //FXML for the Search
    @FXML
    private TextField searchTextField;
    @FXML
    private ListView searchList;

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
    @FXML
    private AnchorPane MedicalAppNewAppointment;

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
                if (match) {
                    labelBox.getChildren().add(label);

                    HBox buttonBox = new HBox();
                    buttonBox.setAlignment(Pos.CENTER_RIGHT);

                    Button appointmentButton = new Button("Set Appointment");
                    appointmentButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            searchList.setVisible(false);
                            timetableAnchorPane.setVisible(false);
                            calDashBoard.getChildren().get(1).setVisible(true); //Set schedule visible false
//                            MedicalAppNewAppointment.setVisible(true);

                            currentPatient = p; //set the selected patient corresponding to button
                        }
                    });

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
            searchList.setPrefHeight(0); //row height is 24 px by default
            searchList.setVisible(false);
        }
    }

//    //Event handler when add patient icon is clicked
//    private void handleAddPatientButton(MouseEvent event) {
//
//        //If fields are entered correctly
//        if (PatientAddFirstName.getText() == "" || PatientAddLastName.getText() == ""
//                || PatientAddAddressStreetNumber.getText() == "" || PatientAddAddressStreetName.getText() == ""
//                || PatientAddAddressSuburb.getText() == "" || PatientAddAddressPostcode.getText() == ""
//                || PatientAddPrimaryContactNumber.getText() == ""
//                || PatientAddFirstName.getText() == null || PatientAddLastName.getText() == null
//                || PatientAddAddressStreetNumber.getText() == null || PatientAddAddressStreetName.getText() == null
//                || PatientAddAddressSuburb.getText() == null || PatientAddAddressPostcode.getText() == null
//                || PatientAddPrimaryContactNumber.getText() == null || PatientAddDateOfBirth.getValue() == null) {
//
////            //A new stage for popup dialog box0
////            final Stage dialog = new Stage();
////            dialog.initModality(Modality.APPLICATION_MODAL);
////            //dialog.initOwner(primaryStage);
////            VBox dialogVbox = new VBox(20);
////            dialogVbox.getChildren().add(new Text("Pls enter stuff into fields"));
////            Scene dialogScene = new Scene(dialogVbox, 300, 200);
////            dialog.setScene(dialogScene);
////            dialog.show();
//            Dialogs.create()
//                    .owner(null)
//                    .title("Input Error")
//                    .masthead(null)
//                    .message("You seem to have input missing in some field(s). Please complete the fields correctly.")
//                    .showInformation();
//
//            System.out.println("Submit clicked");
//            System.out.println(PatientAddFirstName.getText());
//        } else {
//
//            String firstName = PatientAddFirstName.getText();
//            String lastName = PatientAddLastName.getText();
//            Date dateOfBirth = convertLocalDateToDate(PatientAddDateOfBirth.getValue());
//            String address = PatientAddAddressStreetNumber.getText() + " "
//                    + PatientAddAddressStreetName.getText() + " "
//                    + PatientAddAddressSuburb.getText() + " "
//                    + PatientAddAddressPostcode.getText();
//            String contactNumber = PatientAddPrimaryContactNumber.getText() + " / "
//                    + PatientAddSecondaryContactNumber.getText();
//
//            //Connect to DB and insertPatient
//            insertPatient(new Patient(1, null, null, 1, firstName, lastName,
//                    true, false, address, dateOfBirth, contactNumber, false));
//
//            MedicalAppNewPatient.setVisible(false);
//            MedicalAppCalendar.setVisible(true);
//
//        }
//    }
    public static Date convertLocalDateToDate(LocalDate ld) {
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date res = Date.from(instant);
        return res;
    }

    public static ScrollPane initTimetable() {
        timetablePane = new ScrollPane();
        timetablePane.setPrefSize(800, 200);
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

                    if (day == getCurrentDay() && month == getCurrentMonth()
                            && year == getCurrentYear()) {
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

    public VBox initCalendar() {
        //Controls
        monthLabel = new Label("Januray");
        yearCombo = new ComboBox();
        prevButton = new Button("<<");
        prevButton.setPrefWidth(50);
        nextButton = new Button(">>");
        nextButton.setPrefWidth(50);
        calendarGrid = new GridPane();
        calendarBox = new VBox();
        calendarBox.setPadding(new Insets(10, 10, 10, 10));

        //Set actionlistners
        prevButton.setOnAction((ActionEvent event) -> {
            if (currentMonth == 0) { //Back a year
                currentMonth = 11;
                currentYear -= 1;
                yearCombo.setValue(Integer.toString(currentYear));
            } else { //foward a month
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        });
        nextButton.setOnAction((ActionEvent event) -> {
            if (currentMonth == 11) { //Foward one year
                currentMonth = 0;
                currentYear += 1;
                yearCombo.setValue(Integer.toString(currentYear));
            } else { //Foward one month
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        });
        yearCombo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (yearCombo.getValue() != null && !yearCombo.getValue().toString().isEmpty()) {
                    String b = yearCombo.getValue().toString();
                    currentYear = Integer.parseInt(b);
                    refreshCalendar(currentMonth, currentYear);
                }
            }
        });

        TilePane controlPane = new TilePane();
        controlPane.setPrefColumns(4);
        controlPane.getChildren().add(prevButton);
        controlPane.getChildren().add(monthLabel);
        controlPane.getChildren().add(yearCombo);
        controlPane.getChildren().add(nextButton);

        calendarBox.getChildren().add(controlPane);

        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Current month and year is what the calendar is viewing
        currentYear = realYear;

        //GridPane for headers
        HBox headerBox = new HBox();
        Label sunLabel = new Label("Sun");
        Label monLabel = new Label("Mon");
        Label tueLabel = new Label("Tue");
        Label wedLabel = new Label("Wed");
        Label thuLabel = new Label("Thu");
        Label friLabel = new Label("Fri");
        Label satLabel = new Label("Sat");
        sunLabel.setPrefSize(50, 30);
        monLabel.setPrefSize(50, 30);
        tueLabel.setPrefSize(50, 30);
        wedLabel.setPrefSize(50, 30);
        thuLabel.setPrefSize(50, 30);
        friLabel.setPrefSize(50, 30);
        satLabel.setPrefSize(50, 30);
        headerBox.getChildren().add(sunLabel);
        headerBox.getChildren().add(monLabel);
        headerBox.getChildren().add(tueLabel);
        headerBox.getChildren().add(wedLabel);
        headerBox.getChildren().add(thuLabel);
        headerBox.getChildren().add(friLabel);
        headerBox.getChildren().add(satLabel);

        calendarBox.getChildren().add(headerBox);

        //Add Calendar Scroll Grid to box
        calendarBox.getChildren().add(calendarGrid);

        //Populate year combobox
        for (int i = realYear - 3; i <= realYear + 3; i++) {
            yearCombo.getItems().add(String.valueOf(i));
        }
        yearCombo.setValue(currentYear);

        //Refresh calendar
        refreshCalendar(realMonth, realYear);

        return calendarBox;
    }

    private static void refreshCalendar(int month, int year) {

        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        int numberOfDays;
        int startOfMonth;

        prevButton.setDisable(false);
        nextButton.setDisable(false);

        if (month == 0 && year <= realYear - 5) {
            prevButton.setDisable(true); //too early
        }

        if (month == 11 && year >= realYear + 5) {
            nextButton.setDisable(true); //too late
        }
        monthLabel.setText(months.get(month)); //refresh month label
        //TO:DO re-align month label

        // yearCombo.setValue(String.valueOf(year)); //select correct year in combo box
        //Clear table
        calendarGrid.getChildren().removeAll();
        calendarGrid.getChildren().clear();

        Button[][] cellButton = new Button[7][6];

        //Column is a vertical line and row is a horizontal line
        //Two FOR loops used for creating 2D array of buttons with values i,j
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {

                //Initializing 2D buttons with values i,j
                cellButton[i][j] = new Button("");
                cellButton[i][j].setPrefSize(50, 50);

                calendarGrid.add(cellButton[i][j], i, j);
            }
        }
        renderCalendar();

        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        numberOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);

        //Draw calendar
        for (int i = 1; i <= numberOfDays; i++) {
            int row = ((i + startOfMonth - 2) / 7);
            int column = (i + startOfMonth - 2) % 7;

            cellButton[column][row].setText(String.valueOf(i));

            //Event handler setting day label in schedule & refresh schedule
            cellButton[column][row].setOnAction((ActionEvent event) -> { //Select a cell button
                currentDay = Integer.parseInt(cellButton[column][row].getText());
                System.out.println("sup");

                //refresh timetable
                refreshTimetable();

                //refresh schedule
                if (doctorCombo.getValue() != "All doctors" && !doctorCombo.getValue().toString().isEmpty()) {
                    String name = doctorCombo.getValue().toString();
                    String[] splitName = name.split("\\s+"); //split when at least one whitespace is identified

                    refreshSchedule(currentDay, currentMonth, currentYear, Doctor.getDoctor(splitName[1], splitName[2]));
                } else {
                    refreshSchedule(currentDay, currentMonth, currentYear);
                }
                //render all cells other than currently selected
                for (int x = 0; x < 7; x++) {
                    for (int y = 0; y < 6; y++) {

                        cellButton[x][y].setDisable(true);
                        //highlight future days
                        if (!cellButton[x][y].getText().equals("") && Integer.parseInt(cellButton[x][y].getText()) > realDay && currentMonth
                                == realMonth && currentYear >= realYear) {
                            cellButton[x][y].setDisable(false);
                            cellButton[x][y].setStyle("-fx-background-color: lightcyan; -fx-alignment: center;");
                        } else if (!cellButton[x][y].getText().equals("") && currentYear > realYear) {
                            cellButton[x][y].setDisable(false);
                            cellButton[x][y].setStyle("-fx-background-color: lightcyan; -fx-alignment: center;");
                        } //highlight future months in current year
                        else if (!cellButton[x][y].getText().equals("") && currentMonth > realMonth && currentYear == realYear) {
                            cellButton[x][y].setDisable(false);
                            cellButton[x][y].setStyle("-fx-background-color: lightcyan; -fx-alignment: center;");
                        } //highlight today's cell
                        else if (!cellButton[x][y].getText().equals("") && Integer.parseInt(cellButton[x][y].getText()) == realDay && currentMonth
                                == realMonth && currentYear == realYear) { //Today
                            cellButton[x][y].setDisable(false);
                            cellButton[x][y].setStyle("-fx-background-color: lightsalmon; -fx-alignment: center;");

                        } else if (cellButton[x][y].getText().isEmpty()) {
                            cellButton[x][y].setDisable(false); //purely for presentation purposes
                        }
                    }
                }
                //render selected cell
                cellButton[column][row].setStyle("-fx-background-color: cyan; -fx-alignment: center;");

            });

            //highlight future days in month
            if (Integer.parseInt(cellButton[column][row].getText()) > realDay && currentMonth
                    == realMonth && currentYear >= realYear) { //Today
                cellButton[column][row].setStyle("-fx-background-color: lightcyan; -fx-alignment: center;");
            } //highlight all future years 
            else if (currentYear > realYear) {
                cellButton[column][row].setStyle("-fx-background-color: lightcyan; -fx-alignment: center;");
            } //highlight future months in current year
            else if (currentMonth > realMonth && currentYear == realYear) {
                cellButton[column][row].setStyle("-fx-background-color: lightcyan; -fx-alignment: center;");
            } //highlight today's cell
            else if (Integer.parseInt(cellButton[column][row].getText()) == realDay && currentMonth
                    == realMonth && currentYear == realYear) { //Today
                cellButton[column][row].setStyle("-fx-background-color: lightsalmon; -fx-alignment: center;");
                currentDay = i; //initializes the schedule date label
            } else {
                cellButton[column][row].setDisable(true);
            }
        }
    }

    public static void renderCalendar() {
        // make all of the Controls and Panes inside the grid fill their grid cell, 
        // align them in the center and give them a filled background.
        // you could also place each of them in their own centered StackPane with 
        // a styled background to achieve the same effect.
        for (Node n : calendarGrid.getChildren()) {
            if (n instanceof Control) {
                Control control = (Control) n;
                control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                control.setStyle("-fx-background-color: azure; -fx-alignment: center;");
            }

            if (n instanceof Pane) {
                Pane pane = (Pane) n;
                pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                pane.setStyle("-fx-background-color: darkturquoise; -fx-alignment: center;");
            }
        }
        // style the grid so that it has a background and gaps around the grid and between the 
        // grid cells so that the background will show through as grid lines.
        calendarGrid.setStyle("-fx-background-color: lightgrey; -fx-padding: 2; -fx-hgap: 2; -fx-vgap: 2;");
        // turn layout pixel snapping off on the grid so that grid lines will be an even width.
        calendarGrid.setSnapToPixel(false);

    }

    /**
     * Initialize the Schedule Box
     *
     * @return VBox
     */
    public static VBox initSchedule() {
        VBox scheduleBox = new VBox();
        HBox scheduleTitlePane = new HBox();
        //scheduleTitlePane.setPrefColumns(3);

        scheduleLabel = new Label("Times for: ");
        scheduleDateLabel = new Label(currentDay + getDateSuffix(currentDay)
                + " " + months.get(currentMonth) + " " + currentYear);

        scheduleDateLabel.setTextFill(Color.web("#0076a3"));

        //Populate doctor combobox
        doctorCombo = new ComboBox();
        doctorCombo.getItems().add("All doctors");
        doctorCombo.setValue("All doctors");

        ArrayList<Doctor> doctors = new ArrayList<>();
        for (int i = 1; i <= Doctor.getMaxID(); i++) {
            doctors.add(Doctor.getDoctor(i));
        }
        for (Doctor doctor : doctors) {
            doctorCombo.getItems().add("Dr. " + doctor.getFirstName() + " "
                    + doctor.getLastName());
        }
        doctorCombo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (doctorCombo.getValue() != "All doctors" && !doctorCombo.getValue().toString().isEmpty()) {
                    String name = doctorCombo.getValue().toString();
                    String[] splitName = name.split("\\s+"); //split when at least one whitespace is identified

                    currentDoctor = getDoctor(splitName[1], splitName[2]);
                    System.out.println(currentDoctor);
                    refreshSchedule(currentDay, currentMonth, currentYear, Doctor.getDoctor(splitName[1], splitName[2]));

                } else if (doctorCombo.getValue() == "All doctors") {
                    //Check all timeslots & render.
                }
            }
        });
        ScrollPane scheduleScroll = new ScrollPane();
        scheduleTile = new TilePane();
        scheduleTile.setPrefColumns(4);

        timeBtn = new ArrayList<>();
        //Create times 9-4.45pm, 15 min intervals
        for (int i = 0; i < 48; i++) {
            timeBtn.add(new Button(""));

            //Set time for timeslot
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, 9);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.AM_PM, Calendar.AM);
            cal.add(Calendar.MINUTE, 15 * i); //15 minute intervals

            Date date = cal.getTime();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            timeBtn.get(i).setText(timeFormat.format(date));
            timeBtn.get(i).setPrefSize(90, 50);

            timeBtn.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
            
                    SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
                    SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
                    currentHour = Integer.parseInt(hourFormat.format(date));
                    currentMinute = Integer.parseInt(minuteFormat.format(date));
                    System.out.println(currentHour + ":" + currentMinute);
                }
            });
            scheduleTile.getChildren().add(timeBtn.get(i));
        }

        scheduleScroll.setPrefSize(380, 300);
        scheduleScroll.setContent(scheduleTile);

        GridPane controlsGrid = new GridPane();
        HBox optionsBox = new HBox();
        VBox buttonsBox = new VBox();

        VBox radiosBox = new VBox();

        Label radioLabel = new Label("Pick an appointment length: ");
        RadioButton shortRadioBtn = new RadioButton();
        shortRadioBtn.setText("Short Appointment (15 mins)");
        RadioButton longRadioBtn = new RadioButton();
        longRadioBtn.setText("Long Appointment (30 mins)");

        //Toggle between 15 mins or 30 mins appointments
        int duration = 15;
        final ToggleGroup group = new ToggleGroup();
        shortRadioBtn.setToggleGroup(group);
        shortRadioBtn.setSelected(true);
        longRadioBtn.setToggleGroup(group);
        
        radiosBox.getChildren().add(radioLabel);
        radiosBox.getChildren().add(shortRadioBtn);
        radiosBox.getChildren().add(longRadioBtn);
        radiosBox.setAlignment(Pos.CENTER_LEFT);

        VBox purposeBox = new VBox();
        Label purposeLabel = new Label("Enter the purpose of the appointment:");
        TextField purposeText = new TextField();
        purposeText.setText("General Appointment");

        purposeBox.getChildren().add(purposeLabel);
        purposeBox.getChildren().add(purposeText);
        purposeBox.setAlignment(Pos.CENTER);

        optionsBox.getChildren().add(radiosBox);
        optionsBox.getChildren().add(purposeBox);
        optionsBox.setAlignment(Pos.CENTER_LEFT);

        Button createButton = new Button("Create");
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Calendar cal = new GregorianCalendar();
                cal.set(currentYear, currentMonth, currentDay, currentHour, currentMinute);
                Date date = cal.getTime();
                int duration = 15;
                
                if(shortRadioBtn.isSelected()){
                    duration = 15;
                } else {
                    duration = 30;
                }
                System.out.println(currentPatient);
                System.out.println(currentDoctor);
                System.out.println(date);
                System.out.println(duration);
                System.out.println(purposeText.getText());
                Appointment.insertAppointment(currentPatient, currentDoctor, date,duration, purposeText.getText());
                refreshSchedule(currentDay, currentMonth, currentYear);
                
            }
        });
        createButton.setAlignment(Pos.CENTER_RIGHT);
        Button cancelButton = new Button("Cancel");
        cancelButton.setAlignment(Pos.CENTER_RIGHT);

        buttonsBox.getChildren().add(createButton);
        buttonsBox.getChildren().add(cancelButton);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        buttonsBox.setPadding(new Insets(10, 10, 10, 10));
        purposeBox.setPadding(new Insets(10, 10, 10, 10));
        radiosBox.setPadding(new Insets(10, 10, 10, 10));

        controlsGrid.add(radiosBox, 0, 0);
        controlsGrid.add(purposeBox, 0, 1);
        controlsGrid.add(buttonsBox, 1, 1);

        //Add padding
        scheduleTitlePane.setPadding(new Insets(20, 10, 10, 0));
        scheduleBox.setPadding(new Insets(10, 10, 10, 10));

        scheduleTitlePane.getChildren().add(scheduleLabel);
        scheduleTitlePane.getChildren().add(scheduleDateLabel);
        scheduleTitlePane.getChildren().add(doctorCombo);
        scheduleBox.getChildren().add(scheduleTitlePane);
        scheduleBox.getChildren().add(scheduleScroll);
        scheduleBox.getChildren().add(controlsGrid);

        renderSchedule();

        return scheduleBox;
    }

    public static void refreshSchedule(int day, int month, int year) {
        scheduleDateLabel.setText(currentDay + getDateSuffix(currentDay)
                + " " + months.get(currentMonth) + " " + year);
        renderSchedule();

    }

    public static void renderSchedule() {

        for (int i = 0; i < 48; i++) {
            timeBtn.get(i).setDisable(true);
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Calendar realCal = new GregorianCalendar();
            Calendar selectedCal = GregorianCalendar.getInstance();
            selectedCal.set(Calendar.YEAR, currentYear);
            selectedCal.set(Calendar.MONTH, currentMonth);
            selectedCal.set(Calendar.DATE, currentDay);
            String time = timeBtn.get(i).getText();
            String[] splitTime = time.split(":");
            int currentHour = Integer.parseInt(splitTime[0]);
            int currentMinute = Integer.parseInt(splitTime[1]);
            selectedCal.set(Calendar.HOUR_OF_DAY, currentHour);
            selectedCal.set(Calendar.MINUTE, currentMinute);
            if (selectedCal.compareTo(realCal) >= 0) {
                timeBtn.get(i).setStyle("-fx-base: honeydew; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6),5,0.0 ,0,1);");
                timeBtn.get(i).setDisable(false);
            } else {
//                    timeBtn.get(i).setStyle(" -fx-base: expectsomeerrorlulz;"); //throws an css error, but resulting in a disabled button.
//                    timeBtn.get(i).setDisable(true);
            }
        }
    }

    public static void refreshSchedule(int day, int month, int year, Doctor doctor) {

        refreshSchedule(day, month, year);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        //refresh timeslots\
        //populate an arraylist of appointments
        ArrayList<Appointment> appointments = new ArrayList<>();
        Appointment appointment;
        for (int i = 1; i <= Appointment.getMaxID(); i++) {
            appointment = Appointment.getAppointment(i);
            System.out.println(appointment.getAppointmentID());

            //Get appointments only on dictated day, month, and year
            Calendar cal = Calendar.getInstance();
            cal.setTime(appointment.getDate());
            int apptDay = cal.get(Calendar.DAY_OF_MONTH);
            int apptMonth = cal.get(Calendar.MONTH);
            int apptYear = cal.get(Calendar.YEAR);
            String name = doctorCombo.getValue().toString();
            String[] splitName = name.split("\\s+"); //split when at least one whitespace is identified

            if (Doctor.getDoctor(splitName[1], splitName[2]).getDoctorID() == appointment.getDoctorID()
                    && !appointment.isExpired() && apptDay == day && apptMonth == month
                    && apptYear == year) { //ignore expired appointments
                appointments.add(appointment);
            }
        }
        //Populate a list of booked out times from existing appointments
        ArrayList<String> bookedTimes = new ArrayList<>();
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println(timeFormat.format(appointments.get(i).getDate()));
            bookedTimes.add(timeFormat.format(appointments.get(i).getDate()));
        }
        for (int i = 0; i < 32; i++) {//Scan time slots for any booked appointments, then disable
            if (bookedTimes.contains(timeBtn.get(i).getText())) { //appointment booked out at subject timeslot
//                timeBtn.get(i).setStyle(" -fx-base: expectsomeerrorlulz;"); //throws an css error, but resulting in a disabled button.
                timeBtn.get(i).setDisable(true);
            }
        }
    }

    public static String getDateSuffix(int day) {
        switch (day) {
            case 1:
            case 21:
            case 31:
                return ("st");
            case 2:
            case 22:
                return ("nd");
            case 3:
            case 23:
                return ("rd");
            default:
                return ("th");
        }
    }

    public static int getCurrentMonth() {
        return currentMonth;
    }

    public static int getCurrentYear() {
        return currentYear;
    }

    public static int getCurrentDay() {
        return currentDay;
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
                || PatientAddPrimaryContactNumber.getText() == null || PatientAddDateOfBirth.getValue() == null) {

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
            PatientAddSubmit.getParent().setVisible(false);
            MedicalAppCalendar.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        calDashBoard = new HBox();
        calDashBoard.getChildren().add(initCalendar());
        calDashBoard.getChildren().add(initSchedule());
        calDashBoard.getChildren().get(1).setVisible(false); //Set schedule visible false

        MedicalAppCalendar.getChildren().add(calDashBoard);

        MenuPatientAdd.setOnMouseClicked(this::handleAddPatientMouse);

        MenuHome.setOnMouseClicked(this::handleMenuHomeMouse);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            refreshSearchList();
        });

        searchList.setVisible(false);
        MedicalAppNewAppointment.setVisible(false);

        MedicalAppSearch.setMinSize(0, 0);
        HBox timetableBox = new HBox();
        timetableBox.getChildren().add(initTimetable());
        timetableAnchorPane.getChildren().add(timetableBox);

        PatientAddSubmit.setOnMouseClicked(this::handleAddPatientButton);
    }
}
