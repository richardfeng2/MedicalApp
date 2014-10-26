/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.guimain;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import medicalapp.data.Addendum;
import static medicalapp.data.Addendum.getAddendum;
import medicalapp.data.Appointment;
import static medicalapp.data.Appointment.getAppointment;
import medicalapp.data.ChangeLog;
import static medicalapp.data.ChangeLog.getChangeLog;
import medicalapp.data.DBConnection;
import medicalapp.data.Docos;
import static medicalapp.data.Docos.getDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import static medicalapp.data.Docos.insertDocument;
import medicalapp.data.Doctor;
import static medicalapp.data.Doctor.getDoctor;
import static medicalapp.data.Doctor.getDoctor;
import static medicalapp.data.Doctor.getDoctor;
import static medicalapp.data.Doctor.getDoctor;
import static medicalapp.data.Doctor.getDoctor;
import medicalapp.data.Invoice;
import static medicalapp.data.Invoice.getInvoice;
import medicalapp.data.InvoiceService;
import static medicalapp.data.InvoiceService.getInvoiceService;
import medicalapp.data.Note;
import static medicalapp.data.Note.getNote;
import static medicalapp.data.Note.getNoteByAppointment;
import static medicalapp.data.Note.insertNote;
import medicalapp.data.Patient;
import static medicalapp.data.Patient.getPatient;
import static medicalapp.data.Patient.insertPatient;
import static medicalapp.data.Patient.searchPatients;
import medicalapp.data.Service;
import static medicalapp.data.Service.getService;
import medicalapp.data.Staff;
import static medicalapp.data.Staff.getStaff;
import medicalapp.data.TestResult;
import static medicalapp.data.TestResult.getTestResult;
import static medicalapp.data.TestResult.insertTestResult;
import static medicalapp.data.TestResult.updateTestResult;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.fxmisc.richtext.*;

/**
 * FXML Controller class
 *
 * @author Tony
 */
public class GuiMainController implements Initializable {

    /**
     * Fields for calendar.
     */
    @FXML
    private AnchorPane homePane;
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
     * Fields for patient file
     */
    @FXML
    private AnchorPane patientFile;
    @FXML
    private Label pfName;
    @FXML
    private Label pfDOB;
    @FXML
    private Label pfAddress;

    @FXML
    private TableView visitHistoryTable;
    @FXML
    private Label fileNameLabel;
    @FXML
    private AnchorPane visitHistoryPane;
    @FXML
    private TableColumn visitDateCol;
    @FXML
    private TableColumn visitDoctorCol;
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
    private static ArrayList<ToggleButton> timeBtn;
    final private static ToggleGroup timeBtnGroup = new ToggleGroup();

    private static HBox calDashBoard;

    private static Appointment currentAppointment;
    /**
     * Fields for timetable
     */
    private static AnchorPane timetableAnchor = new AnchorPane();
    private static ScrollPane timetableScroll;
    private static HBox timetableLabelBox;
    private static TilePane docTile;

    /**
     * Fields for search
     */
    private static Patient currentPatient = null;
    private static Label currentPatientName = new Label("");

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
    private AnchorPane newAppointment;

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

    //FXMLS for login screen
    @FXML
    private AnchorPane loginScreen;
    @FXML
    private BorderPane mainScreen;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label message;
    @FXML
    private Label loginErrorLabel;
    private static Staff currentUser;
    @FXML
    private Label welcomeLabel;

    @FXML
    private void loginAction(ActionEvent event) {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            String usernameText = usernameField.getText();
            String passwordText = passwordField.getText();
            String passwordDB = "";
            String firstName = "";

            String query = "SELECT * FROM Staff "
                    + " INNER JOIN Person ON Staff.personID = Person.personID"
                    + " WHERE username = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, usernameText);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                passwordDB = rs.getString("password");
                firstName = rs.getString("firstName");
                int staffID = rs.getInt("staffID");
                currentUser = getStaff(staffID);
            }
            if (passwordDB.equals(passwordText) && !passwordText.equals("")) {
                loginErrorLabel.setVisible(false);
                loginScreen.setVisible(false);
                mainScreen.setVisible(true);
                homePane.setVisible(true);
                if (currentUser.isDoctor()) {
                    welcomeLabel.setText("Welcome,\n" + "Dr. " + currentUser.getFirstName() + " " + currentUser.getLastName());
                } else {
                    welcomeLabel.setText("Welcome,\n" + currentUser.getFirstName() + " " + currentUser.getLastName());
                }
            } else {
                loginErrorLabel.setVisible(true);
            }
            timetableAnchorPane.getChildren().add(initTimetable());
            timetableAnchorPane.setTopAnchor(timetableAnchorPane.getChildren().get(0), 20.0);
            timetableAnchorPane.setLeftAnchor(timetableAnchorPane.getChildren().get(0), 100.0);
            timetableAnchorPane.setRightAnchor(timetableAnchorPane.getChildren().get(0), 10.0);
        } catch (SQLException ex) {
            Logger.getLogger(GuiMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    AnchorPane workSpace;

    private void handleAppointmentLabel(MouseEvent event) {
        patientFile.setVisible(true);
        timetableAnchorPane.setVisible(true);
    }

    //Event handler when add patient icon is clicked
    private void handleAddPatientMouse(MouseEvent event) {
        setAllInvisible();
        MedicalAppNewPatient.setVisible(true);
        MenuPatientAdd.effectProperty().bind(
                Bindings
                .when(MedicalAppNewPatient.visibleProperty())
                .then((Effect) new Glow(1.0))
                .otherwise((Effect) new Glow(0))
        );
    }

    //Event handler when add patient icon is clicked
    private void handleMenuHomeMouse(MouseEvent event) {
        setAllInvisible();
        MenuHome.effectProperty().bind(
                Bindings
                .when(homePane.visibleProperty())
                .then((Effect) new Glow(1.0))
                .otherwise((Effect) new Glow(0))
        );

        /**
         * Refresh calendar dashboard
         */
        currentDay = realDay;
        currentMonth = realMonth;
        currentYear = realYear;
        HBox calDashBoard = new HBox();
        calDashBoard.getChildren().add(initCalendar());
        MedicalAppCalendar.getChildren().clear();
        MedicalAppCalendar.getChildren().add(calDashBoard);

        homePane.setVisible(true);
    }

    @FXML
    private PaymentsController paymentsController;

    @FXML
    private void handleMenuPayments() {
        setAllInvisible();
        initPayments();
        paymentsPane.setVisible(true);
        currentPatient = null;
        MenuPayments.effectProperty().bind(
                Bindings
                .when(MenuPayments.visibleProperty())
                .then((Effect) new Glow(1.0))
                .otherwise((Effect) new Glow(0))
        );
    }
    @FXML
    private ComboBox invoicePatientCombo;
    @FXML
    private ComboBox invoiceStatusCombo;
    @FXML
    private TableView invoiceTable;
    @FXML
    private TableColumn invoiceIDCol;
    @FXML
    private TableColumn invoicePatientCol;
    @FXML
    private TableColumn invoiceAddressCol;
    @FXML
    private TableColumn invoiceDateIssuedCol;
    @FXML
    private TableColumn invoiceDatePaidCol;
    @FXML
    private TableColumn invoiceAmountCol;
    @FXML
    private TextField totalPaidTF;
    @FXML
    private TextField balanceDueTF;

    public void initPayments() {
        invoiceTable.setPlaceholder(new Text("No Invoices to be displayed"));
        invoiceIDCol.setCellValueFactory(
                new PropertyValueFactory<>("invoiceID")
        );
        //Set patients name in column
        invoicePatientCol.setCellValueFactory(new Callback<CellDataFeatures<Invoice, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Invoice, String> i) {
                return new ReadOnlyObjectWrapper(getPatient(getAppointment(i.getValue().getAppointmentID()).getPatientID()).getFirstName()
                        + " " + getPatient(getAppointment(i.getValue().getAppointmentID()).getPatientID()).getLastName());
            }
        });
        invoiceAddressCol.setCellValueFactory(new Callback<CellDataFeatures<Invoice, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Invoice, String> i) {
                return new ReadOnlyObjectWrapper(getPatient(getAppointment(i.getValue().getAppointmentID()).getPatientID()).getAddress());
            }
        });

//        invoiceDatePaidCol.setCellValueFactory(
//                new PropertyValueFactory<>("isPaid")
//        );
        invoiceDateIssuedCol.setCellValueFactory(
                new PropertyValueFactory<>("dateIssued")
        );

        invoiceDatePaidCol.setCellValueFactory(new Callback<CellDataFeatures<Invoice, Invoice>, ObservableValue<Invoice>>() {
            @Override
            public ObservableValue<Invoice> call(CellDataFeatures<Invoice, Invoice> features) {
                return new ReadOnlyObjectWrapper(features.getValue());
            }
        });

        invoiceDatePaidCol.setCellFactory(new Callback<TableColumn<Invoice, Invoice>, TableCell<Invoice, Invoice>>() {
            @Override
            public TableCell<Invoice, Invoice> call(TableColumn<Invoice, Invoice> btnCol) {
                return new TableCell<Invoice, Invoice>() {
                    final ImageView buttonGraphic = new ImageView();
                    final Button button = new Button();

                    {
                        button.setGraphic(buttonGraphic);
                        button.setMinWidth(70);
                    }
                    final Label label = new Label();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");

                    @Override
                    public void updateItem(final Invoice invoice, boolean empty) {
                        super.updateItem(invoice, empty);
                        if (invoice != null) {
                            if (invoice.isPaid()) {
                                label.setText(sdf.format(invoice.getDatePaid()));
                                setGraphic(label);
//                      buttonGraphic.setImage(fruitImage);
                            } else {
                                button.setText("Pay Now");
                                setGraphic(button);

//                      buttonGraphic.setImage(coffeeImage);
                            }

                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Calendar cal = Calendar.getInstance();
                                    Date date = cal.getTime();
                                    //Update invoice with isPaid & datePaid
                                    Invoice i = new Invoice(invoice.getInvoiceID(), invoice.getAppointmentID(),
                                            true, invoice.getDateIssued(), date, false);

                                    Action response = Dialogs.create()
                                            .owner(null)
                                            .title("Confirm Dialog")
                                            .masthead("Look, a Confirm Dialog")
                                            .message("Do you want to continue?")
                                            .showConfirm();

                                    if (response == Dialog.Actions.YES) {
                                        Invoice.updateInvoice(i);
//                                        button.setText("Paid");
//                                        button.setDisable(true);
                                        label.setText(sdf.format(invoice.getDatePaid()));
                                        setGraphic(label);
                                        ChangeLog.insertChangeLog(new ChangeLog(1, date, "Payment", "InvoiceID: " + invoice.getInvoiceID() + " - payment "
                                                + "has been confirmed", currentUser.getStaffID()));
                                    } else {
                                        // ... user chose NO, CANCEL, or closed the dialog
                                    }
                                }
                            });
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

//        invoiceDatePaidCol.setCellValueFactory(new Callback<CellDataFeatures<Invoice, Boolean>, ObservableValue<Boolean>>() {
//            public ObservableValue<Boolean> call(CellDataFeatures<Invoice, Boolean> i) {
//                if (i.getValue().isPaid()) {
//                    return new ReadOnlyObjectWrapper("Yes");
//                } else {
//                    invoiceDatePaidCol.setCellFactory(
//                            new Callback<TableColumn<Invoice, Boolean>, TableCell<Invoice, Boolean>>() {
//                                @Override
//                                public TableCell<Invoice, Boolean> call(TableColumn<Invoice, Boolean> p) {
//                                    return new ButtonCell();
//                                }
//                            });
//                }
//                return null;
//            }
//        });
        invoiceAmountCol.setCellValueFactory(new Callback<CellDataFeatures<Invoice, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Invoice, String> i) {
                return new ReadOnlyObjectWrapper(String.valueOf(getTotalAmount(i.getValue())));
            }
        });
        final ObservableList<Invoice> masterData = FXCollections.observableArrayList();

        try {
            invoicePatientTF.setText("All Patients");
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM Invoice ";
            PreparedStatement stm = conn.prepareStatement(query);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(("InvoiceID"));

                masterData.add(getInvoice(id));
                invoiceTable.setItems(masterData);
            }
            refreshPayments();
        } catch (SQLException ex) {
            Logger.getLogger(GuiMainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Populate status combo
        invoiceStatusCombo.getItems().clear();
        invoiceStatusCombo.getItems().add("All");
        invoiceStatusCombo.getItems().add("Paid");
        invoiceStatusCombo.getItems().add("In Arrears");
        invoiceStatusCombo.setValue("All");

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Invoice> filteredData = new FilteredList<>(masterData, i -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        invoiceStatusCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(invoice -> {

                if (currentPatient == null || invoicePatientTF.getText().equals("All Patients")) {
                    if (newValue == "All") {
                        return true; // Filter all invoices with all patient
                    }
                    if (newValue == "In Arrears" && !invoice.isPaid()) {

                        return true; // Filter in arrears and all patients
                    } else if (newValue == "Paid" && invoice.isPaid()) {
                        return true; // Filter paid all patients
                    }
                } else if ((currentPatient.getPatientID()) == getAppointment(invoice.getAppointmentID()).getPatientID()) {
                    if (newValue == "All") {
                        System.out.println("All");

                        return true; // Filter all invoices with all patients
                    }
                    if (newValue == "In Arrears" && !invoice.isPaid()) {
                        System.out.println("not paid");
                        return true; // Filter in arrears and all patients.
                    } else if (newValue == "Paid" && invoice.isPaid()) {
                        System.out.println("paid");
                        return true; // Filter paid and all patients.
                    }

                }
                return false; // Does not match.
            });
            refreshPayments();
        });
        // 2. Set the filter Predicate whenever the patient textfield changes.
        invoicePatientTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(invoice -> {
                // If text is all patients, display all invoices.
                if (newValue == "All Patients") {
                    return true;
                }
                if (newValue != "All Patients" && (currentPatient.getPatientID()) == getAppointment(invoice.getAppointmentID()).getPatientID()) {
                    return true; // Filter currentPatient's invoice.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Invoice> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(invoiceTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        invoiceTable.setItems(sortedData);

//        invoiceTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
//            //Check whether item is selected and set value of selected item to Label
//            if (visitHistoryTable.getSelectionModel().getSelectedItem() != null) {
//                selectedAppointment = (Appointment) visitHistoryTable.getSelectionModel().getSelectedItem();
//                refreshDocumentList();
//                System.out.println(selectedAppointment.getAppointmentID());
//            }
//        });
    }
    @FXML
    Button viewInvoiceBtn;

    @FXML
    private void handleViewInvoiceBtn() {
        Invoice selectedInvoice = (Invoice) invoiceTable.getSelectionModel().getSelectedItem();
        showInvoice(selectedInvoice);
    }

    public void refreshPayments() {

        //Populate totalpaid and balanceDue textfields
        ObservableList<Invoice> list = invoiceTable.getSelectionModel().getTableView().getItems();
        double totalPaid = 0;
        double totalDue = 0;
        for (Invoice i : list) {
            if (i.isPaid()) {
                totalPaid += getTotalAmount(i);
            } else {
                totalDue += getTotalAmount(i);
            }
        }
        totalPaidTF.setText(String.valueOf(totalPaid));
        balanceDueTF.setText(String.valueOf(totalDue));
        balanceDueTF.setStyle("-fx-text-fill: red");
    }

    public void refreshPayments(Patient patient) {

        String query = "SELECT * FROM Invoice "
                + "INNER JOIN Appointment ON Invoice.AppointmentID = Appointment.AppointmentID "
                + "WHERE PatientID = ?";
        try {
            final ObservableList data = FXCollections.observableArrayList();
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, patient.getPatientID());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(("InvoiceID"));
                data.add(getInvoice(id));
                invoiceTable.setItems(data);
            }
            refreshPayments();
        } catch (SQLException ex) {
            Logger.getLogger(GuiMainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Return the total amount of services within an invoice
    public double getTotalAmount(Invoice invoice) {
        double totalAmount = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT * FROM Invoice_Service "
                    + "INNER JOIN Service ON Invoice_Service.serviceID = Service.serviceID "
                    + "WHERE InvoiceID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, invoice.getInvoiceID());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("serviceID");
                Service service = getService(serviceID);
                totalAmount += service.getPrice();

            }
        } catch (SQLException ex) {
            Logger.getLogger(GuiMainController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (getAppointment(invoice.getAppointmentID()).getDuration().toMinutes() == (15)) {
            totalAmount += 50; // short appointent
        }
        if (getAppointment(invoice.getAppointmentID()).getDuration().toMinutes() == (30)) {
            totalAmount += 100; // short appointent
        }
        return totalAmount;
    }

    @FXML
    AnchorPane newInvoice;
    @FXML
    ListView servicesList;
    @FXML
    Button updateServicesButton;
    @FXML
    Button createInvoiceButton;
    @FXML
    Label newInvoicePatientLbl;
    @FXML
    Label newInvoiceAppLbl;

    public void initNewInvoice(int appointmentID) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YY  HH:mm:");

        newInvoicePatientLbl.setText(getPatient(getAppointment(appointmentID).getPatientID()).getFirstName()
                + " " + getPatient(getAppointment(appointmentID).getPatientID()).getLastName());
        newInvoiceAppLbl.setText(dateFormat.format(getAppointment(appointmentID).getDate()));

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        Invoice currentInvoice = new Invoice(Invoice.getNextID(), appointmentID, false, date, null, false);

        ObservableList<String> services = FXCollections.observableArrayList();
        for (int i = 1; i < Service.getNextID(); i++) {
            services.add(getService(i).getDescription());
        }
        servicesList.setItems(services);
        servicesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        servicesList.getFocusModel().focus(0);
//        updateServicesButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//
//                ObservableList<String> selectedServiceDescriptions = servicesList.getSelectionModel().getSelectedItems();
//                for (String description : selectedServiceDescriptions) {
//                    Service service = getService(description);
//                    selectedServices.add(service);
//                }
//            }
//        });
        createInvoiceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Invoice.insertInvoice(currentInvoice);
                ArrayList<Service> selectedServices = new ArrayList<>();
                ObservableList<String> selectedServiceDescriptions = servicesList.getSelectionModel().getSelectedItems();

                if (!selectedServiceDescriptions.isEmpty()) {
                    for (String description : selectedServiceDescriptions) {
                        Service service = getService(description);
                        selectedServices.add(service);
                    }
                }

                if (!selectedServices.isEmpty()) {
                    for (Service s : selectedServices) { //Insert a number of services
                        InvoiceService is = new InvoiceService(currentInvoice.getInvoiceID(), s.getServiceID());
                        InvoiceService.insertInvoiceService(is);
                    }
                }
                initPayments();
                Dialogs.create()
                        .owner(null)
                        .title("Invoice Created")
                        .masthead(null)
                        .message("Invoice successfully created.")
                        .showInformation();

                ChangeLog.insertChangeLog(new ChangeLog(1, date, "Payment", "InvoiceID: " + currentInvoice.getInvoiceID() + " - invoice "
                        + "has been created", currentUser.getStaffID()));

                newInvoice.setVisible(false);
                paymentsPane.setVisible(true);
            }
        });
    }
    @FXML
    AnchorPane pdfPreviewPane;

    public void showInvoice(Invoice invoice) {
        createInvoiceCopy(invoice);
        pdfPreviewPane.setVisible(true);
        invoiceCopy.setVisible(true);
        invoiceCopy.setStyle("-fx-background-color: white ;");
        pdfPreviewPane.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5) ; -fx-background-radius: 10 ;");
    }

    public void handleClosePreviewButton(ActionEvent event) {
        pdfPreviewPane.setVisible(false);
    }

    public void handleExportButton(ActionEvent event) {
        saveAsPng(invoiceCopy, true);
        pdfPreviewPane.setVisible(false);
    }

    @FXML
    AnchorPane paymentsPane;
    @FXML
    TextField invoicePatientTF;
    @FXML
    AnchorPane invoiceCopy;
    @FXML
    VBox invoiceCopyServicesBox;
    @FXML
    Label invoiceCopyDateLbl;
    @FXML
    Label invoiceCopyIDLbl;
    @FXML
    Label invoiceCopyPatientLbl;
    @FXML
    Label totalServicesLabel;
    @FXML
    Label deductionLabel;
    @FXML
    Label amountPayableLabel;

    public void createInvoiceCopy(Invoice invoice) {
        if (!invoiceCopyServicesBox.getChildren().isEmpty()) {
            invoiceCopyServicesBox.getChildren().clear();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YY");
        invoiceCopyDateLbl.setText(dateFormat.format(invoice.getDateIssued()));
        invoiceCopyIDLbl.setText(String.valueOf(invoice.getInvoiceID()));
        invoiceCopyPatientLbl.setText(getPatient(getAppointment(invoice.getAppointmentID()).getPatientID()).getFirstName()
                + " " + getPatient(getAppointment(invoice.getAppointmentID()).getPatientID()).getLastName());

        //Get services from an invoice (if any)
        ArrayList<Service> services = getInvoiceService(invoice.getInvoiceID());
        double totalServicesAmount = 0;

        //Add base service (long/short appointment)
        if ((double) getAppointment(invoice.getAppointmentID()).getDuration().toMinutes() == 30) {
            StackPane pane = new StackPane();
            HBox descBox = new HBox();
            HBox priceBox = new HBox();
            descBox.setAlignment(Pos.CENTER_LEFT);
            priceBox.setAlignment(Pos.CENTER_RIGHT);

            Label desc = new Label("Long Appointment (Base)");
            desc.setAlignment(Pos.CENTER_LEFT);
            Label price = new Label("100.00");
            price.setAlignment(Pos.CENTER_RIGHT);

            descBox.getChildren().add(desc);
            priceBox.getChildren().add(price);
            pane.getChildren().addAll(descBox, priceBox);

            invoiceCopyServicesBox.getChildren().add(pane);
            totalServicesAmount += 100;
        } else {
            StackPane pane = new StackPane();
            HBox descBox = new HBox();
            HBox priceBox = new HBox();
            descBox.setAlignment(Pos.CENTER_LEFT);
            priceBox.setAlignment(Pos.CENTER_RIGHT);

            Label desc = new Label("Short Appointment (Base)");
            desc.setAlignment(Pos.CENTER_LEFT);
            Label price = new Label("50.00");
            price.setAlignment(Pos.CENTER_RIGHT);

            descBox.getChildren().add(desc);
            priceBox.getChildren().add(price);
            pane.getChildren().addAll(descBox, priceBox);

            invoiceCopyServicesBox.getChildren().add(pane);
            totalServicesAmount += 50;
        }
        if (!services.isEmpty()) {
            for (Service s : services) {
                StackPane pane = new StackPane();
                HBox descBox = new HBox();
                HBox priceBox = new HBox();
                descBox.setAlignment(Pos.CENTER_LEFT);
                priceBox.setAlignment(Pos.CENTER_RIGHT);

                Label desc = new Label(s.getDescription());
                desc.setAlignment(Pos.CENTER_LEFT);
                Label price = new Label(String.format("%.2f", s.getPrice()));
                price.setAlignment(Pos.CENTER_RIGHT);

                descBox.getChildren().add(desc);
                priceBox.getChildren().add(price);
                pane.getChildren().addAll(descBox, priceBox);

                invoiceCopyServicesBox.getChildren().add(pane);
                totalServicesAmount += s.getPrice();
            }
        }
        totalServicesLabel.setText(String.format("%.2f", totalServicesAmount));
        deductionLabel.setText("- " + String.format("%.2f", totalServicesAmount / 2));  //assuming all healthfunds deduct 50%
        amountPayableLabel.setText(String.format("%.2f", totalServicesAmount - totalServicesAmount / 2));
    }

    private void handleSearchTextField(ChangeEvent event) {
        refreshSearchList();
    }

    private void refreshSearchList() {
        searchList.setVisible(true);
        searchList.setPrefWidth(searchTextField.getWidth());
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

                HBox healthFundBox = new HBox();
                Label healthFundLabel = new Label(p.getBillingInfo());

                StackPane itemPane = new StackPane();

                HBox labelBox = new HBox();
                labelBox.setAlignment(Pos.CENTER_LEFT);

                boolean match = true; //all search terms match patient fields

                for (String term : splitTerm) {
                    if (!label.getText().toLowerCase().contains(term.toLowerCase())
                            && !healthFundLabel.getText().toLowerCase().contains(term)) {
                        match = false;
                    }
                }
                if (match) {
                    labelBox.getChildren().add(label);

                    healthFundBox.getChildren().add(healthFundLabel);
                    healthFundBox.setAlignment(Pos.CENTER);

                    HBox buttonBox = new HBox(5);
                    buttonBox.setAlignment(Pos.CENTER_RIGHT);

                    Button paymentsButton = new Button("Payments");
                    paymentsButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            setAllInvisible();
                            paymentsPane.setVisible(true); //Set schedule visible false

                            currentPatient = p; //set the selected patient corresponding to button
                            currentPatientName.setText(p.getFirstName() + " " + p.getLastName());
                            invoicePatientTF.setText(currentPatientName.getText());
//                            refreshPayments(p);
                            MenuPayments.effectProperty().bind(
                                    Bindings
                                    .when(MenuPayments.visibleProperty())
                                    .then((Effect) new Glow(1.0))
                                    .otherwise((Effect) new Glow(0))
                            );
                        }
                    });
                    buttonBox.getChildren().add(paymentsButton);
                    Button appointmentButton = new Button("Set Appointment");
                    appointmentButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            setAllInvisible();
                            newAppointment.setVisible(true); //Set schedule visible false

                            currentPatient = p; //set the selected patient corresponding to button
                            currentPatientName.setText(p.getFirstName() + " " + p.getLastName());
                        }
                    });

                    buttonBox.getChildren().add(appointmentButton);
                    itemPane.getChildren().add(labelBox);
                    itemPane.getChildren().add(healthFundBox);
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

    static Date convertLocalDateToDate(LocalDate ld) {
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date res = Date.from(instant);
        return res;
    }

    public AnchorPane initTimetable() {
        AnchorPane timetableBox = new AnchorPane();
        timetableScroll = new ScrollPane();
        timetableScroll.setPrefSize(700, 550);
        timetableScroll.setPrefWidth(searchTextField.getWidth());

        timetableLabelBox = new HBox();
        timetableLabelBox.setPrefWidth(timetableScroll.getPrefWidth() - 19); //discount for scrollbar width
        timetableLabelBox.setStyle("-fx-background-color: rgba(157, 185, 245, 0.7);"); //4th rgba parameter sets opacity
        Label timeLabel = new Label("Time");
        timeLabel.setPrefWidth(50);
        timetableLabelBox.getChildren().add(timeLabel);

        docTile = new TilePane();
        docTile.setPrefTileWidth(100);
        if (!currentUser.isDoctor()) {
            ArrayList<Doctor> doctors = new ArrayList<>();
            for (int i = 1; i <= Doctor.getMaxID(); i++) {
                doctors.add(Doctor.getDoctor(i));
            }
            for (Doctor doctor : doctors) {
                docTile.getChildren().add(new Label("Dr. " + doctor.getFirstName() + " " + doctor.getLastName()));
            }
        } else {
            docTile.getChildren().add(new Label("Dr. " + currentUser.getFirstName() + " " + currentUser.getLastName()));
        }
        timetableLabelBox.getChildren().add(docTile);

        timetableBox.getChildren().add(timetableScroll);
        timetableBox.getChildren().add(timetableLabelBox);

        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                refreshTimetable();
            }
        }), new KeyFrame(Duration.seconds(10)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        timetableBox.setOpacity(0.95);
        timetableBox.setMaxHeight(Double.MAX_VALUE);
        timetableBox.setMaxWidth(Double.MAX_VALUE);
        return timetableBox;
    }

    public void refreshTimetable() {
        if (!timetableAnchor.getChildren().isEmpty()) {
            timetableAnchor.getChildren().clear();
        }
        VBox timeBox = new VBox();
        for (int i = 0; i < 48; i++) { //48 15 minute timeslots
            //Set time for timeslot
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, 8);
            cal.set(Calendar.MINUTE, 45);
            cal.set(Calendar.AM_PM, Calendar.AM);
            cal.add(Calendar.MINUTE, 15 * i); //15 minute intervals

            Date date = cal.getTime();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            Label timeLbl = new Label(timeFormat.format(date));
            if (cal.get(Calendar.HOUR_OF_DAY) == 8) {
                timeLbl.setText("");
            }
            timeLbl.prefWidthProperty().bind(timeBox.widthProperty());
            timeLbl.prefHeightProperty().bind(timeBox.heightProperty());

            timeLbl.setStyle("-fx-border-color: lightgrey; -fx-border-width: 0.25");
            timeLbl.setPadding(new Insets(0, 0, 25, 0));
            timeBox.getChildren().add(timeLbl);
        }
        timetableAnchor.getChildren().add(timeBox);

        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.clear();
        ArrayList<Doctor> doctors = new ArrayList<>();
        doctors.clear();

        timeBox.setPrefSize(timetableLabelBox.getPrefWidth(), 48 * 50);
        timeBox.setStyle("-fx-border-color: lightgrey; -fx-background-color: white;");

        for (int i = 1; i <= Doctor.getMaxID(); i++) {
            if (currentUser.isDoctor() && getDoctor(i).getStaffID() == currentUser.getStaffID()) {
                doctors.add(Doctor.getDoctor(i));
                break; //Doctor sees his own appointments only
            } else {
                doctors.add(Doctor.getDoctor(i));
            }
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
                    appointments.add(getAppointment(appointmentID));

                }
            } catch (SQLException ex) {
                Logger.getLogger(GuiMainController.class
                        .getName()).log(Level.SEVERE, "Error getting appointments from doctor" + doctor.getDoctorID(), ex);
            }
            if (!appointments.isEmpty()) {
                for (Appointment a : appointments) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(a.getDate());
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);

                    if (day == currentDay && month == currentMonth && year == currentYear) {
                        Label label = new Label(Patient.getPatient(a.getPatientID()).getFirstName() + " "
                                + Patient.getPatient(a.getPatientID()).getLastName());

                        label.setLayoutX(50 + docTile.getTileWidth() * doctors.indexOf(doctor)); //set layout according to placement of doctor in table column

                        Date date = a.getDate();
                        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        String time = timeFormat.format(date);
                        label.setLayoutY((toMins(time) - (8.75 * 60)) / 15 * 50); //(Time of appointment minus 9.00am) * height of 15 minute time labels
                        label.prefHeightProperty().bind(timeBox.heightProperty().multiply(a.getDuration().toMinutes()).divide(15).divide(48));

                        if (currentUser.isDoctor()) {
                            label.setPrefWidth(timetableLabelBox.getPrefWidth() - 50 - 10);
                            label.setLayoutX(50);
                        }
                        label.setStyle("-fx-border-color: green; -fx-text-fill: green; "
                                + "-fx-background-color: lightgreen; -fx-opacity: 0.6;"
                                + "  -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");

                        label.setPadding(new Insets(0, 0, 70, 0));
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText("Time: " + timeFormat.format(a.getDate())
                                + "\n" + a.getPurpose());

                        if (currentUser.isDoctor()) {
                            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    currentPatient = getPatient(a.getPatientID());
                                    currentAppointment = a;
                                    setAllInvisible();
                                    patientFile.setVisible(true);

                                    refreshPatientFile();
                                }
                            });
                        }
                        timetableAnchor.getChildren().add(label);

                        Button button = new Button("Invoice");
                        button.setVisible(false);

                        button.setOnAction((ActionEvent event) -> {
                            initNewInvoice(a.getAppointmentID());
                            newInvoice.setVisible(true);
                            homePane.setVisible(false);
                        });

                        timetableAnchor.getChildren().add(button);

                        label.setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                currentAppointment = a; //init appointment when secretary creates invoice
                                Point2D p = label.localToScreen(label.getLayoutBounds().getMaxX(), label.getLayoutBounds().getMaxY()); //I position the tooltip at bottom right of the node (see below for explanation)  
                                tooltip.show(label, p.getX(), p.getY());
                                button.setLayoutX(label.getLayoutX() + label.getWidth() - 75);
                                button.setLayoutY(label.getLayoutY() + label.getHeight() - 50);
                                button.setVisible(true);
                            }
                        });
                        label.setOnMouseExited(new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent event) {
                                button.setVisible(false);
                                tooltip.hide();
//                                timetableAnchor.getChildren().remove(button);
                            }

                        });
                        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
//                                timetableAnchor.getChildren().add(button);
                                button.setVisible(true);
                            }
                        });

                    }
                }
            }
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY, 8);
        cal1.set(Calendar.MINUTE, 45);

        Calendar cal3 = Calendar.getInstance();
        cal3.set(Calendar.HOUR_OF_DAY, 20);
        cal3.set(Calendar.MINUTE, 45);
        double timeRange = cal3.getTimeInMillis() - cal1.getTimeInMillis();  //range of timeinmillis in timetable (8.45-8.30)

        Calendar cal2 = Calendar.getInstance();

        //Draw a realtime line depicting today's real time.
        if (cal2.compareTo(cal1)
                >= 0 && cal2.get(Calendar.DAY_OF_MONTH) == currentDay && cal2.get(Calendar.MONTH) == currentMonth
                && cal2.get(Calendar.YEAR) == currentYear) {
            double y = timeBox.getPrefHeight() * ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / timeRange);
            Line line = new Line();
            line.setStartX(0);
            line.setStartY(y);
            line.setEndX(timeBox.getPrefWidth() - 1);
            line.setEndY(y);
            line.setStroke(Color.DODGERBLUE);

            //Real time label
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Date date = cal2.getTime();
            Label realTime = new Label(timeFormat.format(date));
            realTime.setLayoutX(line.getEndX() - 50);
            realTime.setLayoutY(line.getStartY() - 20);
            timetableAnchor.getChildren().addAll(line, realTime);
//            timetableScroll.setVvalue((timetableScroll.getVmax()-timetableScroll.getVmin())*((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / timeRange)); //set scrollbar 
        }
        timetableScroll.setContent(timetableAnchor);
    }

    private HBox weeklyTimetableLabelBox;
    private AnchorPane weeklyTimetableScroll;

    public AnchorPane initWeeklyTimetable() {
        AnchorPane timetableBox = new AnchorPane();
        weeklyTimetableScroll = new AnchorPane();
        weeklyTimetableScroll.setPrefSize(1000, 2000);

        weeklyTimetableLabelBox = new HBox();
        weeklyTimetableLabelBox.setPrefWidth(weeklyTimetableScroll.getPrefWidth() - 19); //discount for scrollbar width
        weeklyTimetableLabelBox.setPrefSize(weeklyTimetableScroll.getPrefWidth() - 19, 49);
        weeklyTimetableLabelBox.setStyle("-fx-background-color: rgba(157, 185, 245, 0.7);"); //4th rgba parameter sets opacity
        Label timeLabel = new Label("Time");
        timeLabel.setPrefWidth(50);
        weeklyTimetableLabelBox.getChildren().add(timeLabel);

        TilePane dayTile = new TilePane();
        dayTile.setPrefColumns(7);
        dayTile.setPrefTileWidth((weeklyTimetableLabelBox.getPrefWidth() - 50) / 7);
        dayTile.getChildren().add(new Label("Sunday"));
        dayTile.getChildren().add(new Label("Monday"));
        dayTile.getChildren().add(new Label("Tuesday"));
        dayTile.getChildren().add(new Label("Wednesday"));
        dayTile.getChildren().add(new Label("Thursday"));
        dayTile.getChildren().add(new Label("Friday"));
        dayTile.getChildren().add(new Label("Saturday"));
        weeklyTimetableLabelBox.getChildren().add(dayTile);

        timetableBox.getChildren().add(weeklyTimetableScroll);
        timetableBox.getChildren().add(weeklyTimetableLabelBox);

        refreshWeeklyTimetable();

        return timetableBox;
    }

    public void refreshWeeklyTimetable() {

        AnchorPane timetableAnchor = new AnchorPane();

        VBox timeBox = new VBox();
        for (int i = 0; i < 48; i++) { //48 15 minute timeslots
            //Set time for timeslot
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, 8);
            cal.set(Calendar.MINUTE, 45);
            cal.set(Calendar.AM_PM, Calendar.AM);
            cal.add(Calendar.MINUTE, 15 * i); //15 minute intervals

            Date date = cal.getTime();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            Label timeLbl = new Label(timeFormat.format(date));
            if (cal.get(Calendar.HOUR_OF_DAY) == 8) {
                timeLbl.setText("");
            }
            timeLbl.prefWidthProperty().bind(timeBox.widthProperty());
            timeLbl.prefHeightProperty().bind(timeBox.heightProperty());

            timeLbl.setStyle("-fx-border-color: lightgrey; -fx-border-width: 0.25");
            if (cal.get(Calendar.MINUTE) == 0) {
                timeLbl.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1 0.25 0.25 0.25");
            }

            timeLbl.setPadding(new Insets(0, 0, 25, 0));
            timeBox.getChildren().add(timeLbl);
        }
        timetableAnchor.getChildren().add(timeBox);

        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.clear();
        ArrayList<Doctor> doctors = new ArrayList<>();
        doctors.clear();

        timeBox.setPrefSize(weeklyTimetableLabelBox.getPrefWidth(), 48 * 50);
        timeBox.setStyle("-fx-border-color: lightgrey; -fx-background-color: white;");

        for (int i = 1; i <= Doctor.getMaxID(); i++) {
            if (currentUser.isDoctor() && getDoctor(i).getStaffID() == currentUser.getStaffID()) {
                doctors.add(Doctor.getDoctor(i));
                break; //Doctor sees his own appointments only
            }
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
                    appointments.add(getAppointment(appointmentID));

                }
            } catch (SQLException ex) {
                Logger.getLogger(GuiMainController.class
                        .getName()).log(Level.SEVERE, "Error getting appointments from doctor" + doctor.getDoctorID(), ex);
            }
            if (!appointments.isEmpty()) {
                for (Appointment a : appointments) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(a.getDate());
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);
                    int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
                    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

                    Calendar realCal = Calendar.getInstance();
                    int realWeekOfYear = realCal.get(Calendar.WEEK_OF_YEAR);

                    for (int i = 1; i <= 7; i++) { //days of week

                        if (i == dayOfWeek && weekOfYear == realWeekOfYear && year == currentYear) {

                            Label label = new Label(Patient.getPatient(a.getPatientID()).getFirstName() + " "
                                    + Patient.getPatient(a.getPatientID()).getLastName());

                            Date date = a.getDate();
                            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            String time = timeFormat.format(date);
                            label.setLayoutY((toMins(time) - (8.75 * 60)) / 15 * 50); //(Time of appointment minus 9.00am) * height of 15 minute time labels
                            label.prefHeightProperty().bind(timeBox.heightProperty().multiply(a.getDuration().toMinutes()).divide(15).divide(48));
                            label.setPrefWidth((weeklyTimetableLabelBox.getPrefWidth() - 50) / 7 - 10);
                            label.setLayoutX(i * ((weeklyTimetableLabelBox.getPrefWidth() - 50) / 7 - 10));
                            label.setStyle("-fx-border-color: green; -fx-text-fill: green; "
                                    + "-fx-background-color: lightgreen; -fx-opacity: 0.6;"
                                    + "  -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");

                            label.setPadding(new Insets(0, 0, 70, 0));
                            timetableAnchor.getChildren().addAll(label);
                        }
                    }
                }
            }
        }
        weeklyTimetableScroll.getChildren().add(timetableAnchor);
    }

    @FXML
    TableView changeLogTable;
    @FXML
    TableColumn changeLogDateCol;
    @FXML
    TableColumn changeLogTypeCol;
    @FXML
    TableColumn changeLogDescCol;
    @FXML
    TableColumn changeLogStaffCol;
    @FXML
    ImageView MenuChangeLog;
    @FXML
    AnchorPane changeLogPane;

    public void initChangeLog() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY HH:mm");

        changeLogDateCol.setCellValueFactory(new Callback<CellDataFeatures<ChangeLog, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<ChangeLog, String> i) {
                return new ReadOnlyObjectWrapper(sdf.format(i.getValue().getDate()));
            }
        });

        changeLogTypeCol.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );
        changeLogDescCol.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );
        changeLogStaffCol.setCellValueFactory(new Callback<CellDataFeatures<ChangeLog, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<ChangeLog, String> i) {
                return new ReadOnlyObjectWrapper(getStaff(i.getValue().getStaffID()).getFirstName()
                        + " " + getStaff(i.getValue().getStaffID()).getLastName());
            }
        });

        final ObservableList<ChangeLog> masterData = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM ChangeLog";
            PreparedStatement stm = conn.prepareStatement(query);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(("changeLogID"));
                masterData.add(getChangeLog(id));
                changeLogTable.setItems(masterData);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GuiMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        changeLogTable.setPlaceholder(new Text("No changes to be displayed"));

        //Handle icon mouse action
        MenuChangeLog.setOnMouseClicked(this::handleChangeLogMouse);
    }

    private void handleChangeLogMouse(MouseEvent event) {
        setAllInvisible();
        changeLogPane.setVisible(true);
        initChangeLog();
        MenuChangeLog.effectProperty().bind(
                Bindings
                .when(changeLogPane.visibleProperty())
                .then((Effect) new Glow(1.0))
                .otherwise((Effect) new Glow(0))
        );
    }

    @FXML
    private AnchorPane weeklySchedule;

    @FXML
    private void handleWeeklyBtn() {
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(initWeeklyTimetable());
        refreshWeeklyTimetable();
        weeklySchedule.getChildren().add(pane);
        weeklySchedule.setVisible(true);
        saveAsPng(weeklySchedule, false);
        weeklySchedule.setVisible(false);
    }

    public void saveAsPng(Node n, Boolean isInvoice) {
        WritableImage image = n.snapshot(new SnapshotParameters(), null);

        // TODO: probably use a file chooser here
        File file = new File("chart.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
        }
        imageToPDF(file, isInvoice);
    }

    public void imageToPDF(File file, Boolean isInvoice) {
        Document document = new Document();
        String path = "";
        try {
            if (isInvoice) {
                path = "Invoice.pdf";
            } else {
                path = "Timetable.pdf";
            }
            FileOutputStream fos = new FileOutputStream(path);
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.open();
            document.open();
            Image img = Image.getInstance(file.getPath());
            if (!isInvoice) {
                img.scalePercent(33);
            } else {
                img.scalePercent(70);
            }
            document.add(img);
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File myFile = new File(path);
        openFile(myFile);
    }

    private void refreshPatientFile() {
        pfName.setText(currentPatient.getFirstName() + " " + currentPatient.getLastName());
        pfAddress.setText(currentPatient.getAddress());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        pfDOB.setText("DOB:" + dateFormat.format(currentPatient.getDateOfBirth()));
        refreshVisitHistory();
    }

    @FXML
    Button submitNote;
    @FXML
    TextArea noteArea;

    //Event handler when add patient icon is clicked
    private void handleNoteAddButton(MouseEvent event) {

        if (submitNote.getText().contains("Submit")) {
            //If fields are entered correctly
            if (noteArea.getText().equals("") || noteArea.getText().isEmpty()) {
                Dialogs.create()
                        .owner(null)
                        .title("Submit Note Error")
                        .masthead(null)
                        .message("Please enter text in the note area provided.")
                        .showInformation();
            } else {
                String note = noteArea.getText();

                //Connect to DB and insertNote
                insertNote(new Note(1, currentAppointment.getAppointmentID(), note, false, false));
                submitNote.setDisable(true);

                Date changeLogDate = Calendar.getInstance().getTime();
                ChangeLog.insertChangeLog(new ChangeLog(1, changeLogDate, "Note", "Note added for Appointment at "
                        + currentAppointment.getDate() + ", Patient: " + getPatient(currentAppointment.getPatientID()).getFirstName()
                        + " " + getPatient(currentAppointment.getPatientID()).getLastName(), currentUser.getStaffID()));
            }
        } else { //bring up addendum dialog
            showNoteDialog(getNoteByAppointment(selectedAppointment.getAppointmentID()));
        }
    }

    private static void refreshGeneralInfo() {
        Label pName = new Label(currentPatient.getFirstName() + " " + currentPatient.getLastName());
//        patientFileGeneral.getChildren().add(pName, 0,1);
    }

    private void refreshVisitHistory() {
        try {
            final ObservableList data = FXCollections.observableArrayList();
            Connection conn = DBConnection.getInstance().getConnection();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
            String query = "SELECT * FROM Appointment "
                    + " INNER JOIN Doctor ON Appointment.doctorID = Doctor.doctorID"
                    + " INNER JOIN Staff ON Staff.StaffID = Doctor.StaffID"
                    + " INNER JOIN Person ON Person.personID = Staff.personID"
                    + " WHERE patientID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, currentPatient.getPatientID());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(("appointmentID"));
                Date date = rs.getTimestamp("date");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                data.add(getAppointment(id));
                visitHistoryTable.setItems(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GuiMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Appointment selectedAppointment;

    private void initVisitHistory() {
        visitDateCol.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        visitDoctorCol.setCellValueFactory(
                new PropertyValueFactory<>("doctorID")
        );
        //Set date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY HH:mm");
        visitDateCol.setCellValueFactory(new Callback<CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Appointment, String> a) {
                return new ReadOnlyObjectWrapper(sdf.format(a.getValue().getDate()));
            }
        });
        //Set doctor's name in column
        visitDoctorCol.setCellValueFactory(new Callback<CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Appointment, String> a) {
                return new ReadOnlyObjectWrapper(getDoctor(a.getValue().getDoctorID()).getFirstName() + " "
                        + getDoctor(a.getValue().getDoctorID()).getLastName());
            }
        });
        if (currentPatient != null) {
            refreshVisitHistory();
        }
        visitHistoryTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (visitHistoryTable.getSelectionModel().getSelectedItem() != null) {
                selectedAppointment = (Appointment) visitHistoryTable.getSelectionModel().getSelectedItem();
                refreshDocumentList();
                refreshTestResult();
                refreshNote();
            }
        });
        visitHistoryTable.setMaxHeight(200);
    }

    public void refreshNote() {
        noteArea.setText(null);
        ArrayList<Note> notes = new ArrayList<>();
        for (int i = 1; i < Note.getNextID(); i++) {
            notes.add(getNote(i));
        }
        ArrayList<Addendum> addendums = new ArrayList<>();
        for (int i = 1; i < Addendum.getNextID(); i++) {
            addendums.add(getAddendum(i));
        }
        if (selectedAppointment.getAppointmentID() == currentAppointment.getAppointmentID()) { //Notes for current appointment
            if (!notes.isEmpty()) {
                for (Note note : notes) {
                    if (note.getAppointmentID() == selectedAppointment.getAppointmentID()) { //A note exists already for current appt
                        String text = note.getText();

                        for (Addendum a : addendums) {
                            if (a.getNoteID() == note.getNoteID()) {
                                text += "\n\nAddendum: ";
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                text += sdf.format(a.getDate());
                                text += "\tDr. " + getStaff(a.getStaffID()).getFirstName() + " " + getStaff(a.getStaffID()).getLastName();
                                text += "\n" + a.getText();

                            }
                        }
                        noteArea.setText(text);
                        noteArea.setEditable(false);
                        submitNote.setText("Update");
                        submitNote.setVisible(true);
                        break;
                    } else {
                        noteArea.setText(null);
                        noteArea.setEditable(true);
                        submitNote.setText("Submit");
                        submitTestBtn.setVisible(true);
                    }
                }
            }
        } else {     //Notes for selected (not current) appointment
            if (!notes.isEmpty()) {
                for (Note note : notes) {
                    if (note.getAppointmentID() == selectedAppointment.getAppointmentID()) {
                        String text = note.getText();
                        for (Addendum a : addendums) {
                            if (a.getNoteID() == note.getNoteID()) {
                                text += "\n\nAddendum: ";
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                text += sdf.format(a.getDate());
                                text += "\tDr. " + getStaff(a.getStaffID()).getFirstName() + " " + getStaff(a.getStaffID()).getLastName();
                                text += "\n" + a.getText();
                            }
                        }
                        noteArea.setText(text);
                        noteArea.setEditable(false);
                        submitNote.setText("Update");
                        submitNote.setVisible(true);
                        break;
                    } else {
                        noteArea.setText("No notes taken");
                        noteArea.setEditable(false);
                        submitNote.setVisible(false);
                    }
                }
            }
        }
    }

    public void refreshTestResult() {
        weightTF.clear();
        bloodTF1.clear();
        bloodTF2.clear();
        heartTF.clear();
        oxygenLevelTF.clear();
        lungTF.clear();
        oxygenUptakeTF.clear();
        ArrayList<TestResult> results = new ArrayList<>();
        for (int i = 1; i < TestResult.getNextID(); i++) {
            if (getTestResult(i).getAppointmentID() == selectedAppointment.getAppointmentID()) {
                results.add(getTestResult(i));
            }
        }
        if (selectedAppointment.getAppointmentID() == currentAppointment.getAppointmentID()) {
            if (!results.isEmpty()) {

                for (TestResult result : results) {
                    if (result.getAppointmentID() == selectedAppointment.getAppointmentID()) {
                        weightTF.setText(String.valueOf(result.getWeight()));
                        String s = result.getBloodPressure();
                        String[] bloodPressure = s.split("/");
                        bloodTF1.setText(bloodPressure[0]);
                        bloodTF2.setText(bloodPressure[1]);
                        heartTF.setText(String.valueOf(result.getHeartRate()));
                        oxygenLevelTF.setText(String.valueOf(result.getOxygenLevel()));
                        lungTF.setText(String.valueOf(result.getLungCapacity()));
                        oxygenUptakeTF.setText(String.valueOf(result.getOxygenUptake()));

                        submitTestBtn.setVisible(true);
                        submitTestBtn.setText("Update");
                    }
                }
            } else {
                submitTestBtn.setVisible(true);
            }
        } else {
            if (!results.isEmpty()) {
                for (TestResult result : results) {
                    if (result.getAppointmentID() == selectedAppointment.getAppointmentID()) {
                        weightTF.setText(String.valueOf(result.getWeight()));
                        String s = result.getBloodPressure();
                        String[] bloodPressure = s.split("/");
                        bloodTF1.setText(bloodPressure[0]);
                        bloodTF2.setText(bloodPressure[1]);
                        heartTF.setText(String.valueOf(result.getHeartRate()));
                        oxygenLevelTF.setText(String.valueOf(result.getOxygenLevel()));
                        lungTF.setText(String.valueOf(result.getLungCapacity()));
                        oxygenUptakeTF.setText(String.valueOf(result.getOxygenUptake()));

                        submitTestBtn.setVisible(false);
                    }
                }
            } else {
                submitTestBtn.setVisible(false);
            }
        }
    }

    public boolean showNoteDialog(Note note) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GuiMain.class.getResource("NoteDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();

            dialogStage.setTitle("New Addendum");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            dialogStage.initOwner(null);
            Scene scene = new Scene(page);

            dialogStage.setScene(scene);

            // Set the note into the controller.
            NoteDialogController controller = loader.getController();

            controller.setDialogStage(dialogStage);

            controller.setNote(note);
            controller.setUser(currentUser);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isCancelClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

//    @FXML
//    private void handleViewNoteBtn() {
//        Appointment selectedAppointment = (Appointment) visitHistoryTable.getSelectionModel().getSelectedItem();
//        showNoteDialog(getNoteByAppointment(selectedAppointment.getAppointmentID()));
//    }
    private File selectedFile;

    @FXML
    private void handleChooseFileBtn() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("Text Files", "*.pdf"),
                new ExtensionFilter("All Files", "*.*"));
        Stage dialogStage = new Stage();
        dialogStage.setTitle("View Note");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(null);
        selectedFile = fileChooser.showOpenDialog(dialogStage);
        if (selectedFile != null) {
            fileNameLabel.setText(selectedFile.getName());
            fileTitleTextField.setText(selectedFile.getName());
        }
    }

    @FXML
    private void handlePreviewFileBtn() {
        if (selectedFile != null) {
            openFile(selectedFile);
        }
    }

    @FXML
    private TextField fileTitleTextField;

    @FXML
    private void handleSaveFileBtn() {
        if (selectedFile != null) {
            insertDocument(currentAppointment.getAppointmentID(), fileTitleTextField.getText(),
                    true, selectedFile);
            Dialogs.create()
                    .owner(null)
                    .title("File saved")
                    .masthead(null)
                    .message("File saved.")
                    .showInformation();
            fileNameLabel.setText("File saved to database)");
            fileTitleTextField.setText("");

            Date changeLogDate = Calendar.getInstance().getTime();
            ChangeLog.insertChangeLog(new ChangeLog(1, changeLogDate, "Document", "File: " + selectedFile.getName() + ", added for Appointment at "
                    + currentAppointment.getDate() + ", Patient: " + getPatient(currentAppointment.getPatientID()).getFirstName()
                    + " " + getPatient(currentAppointment.getPatientID()).getLastName(), currentUser.getStaffID()));

            selectedFile = null;
            refreshDocumentList();
        } else {
            Dialogs.create()
                    .owner(null)
                    .title("No file selected")
                    .masthead(null)
                    .message("No file selected, please select a file")
                    .showInformation();
        }
    }

    private void openFile(File file) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);

        } catch (IOException ex) {
            Logger.getLogger(GuiMainController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private ListView documentList;
    @FXML
    private Label noDocsLabel;

    private File selectedHistoricFile;

    private void refreshDocumentList() {

        ArrayList<Docos> documents = new ArrayList<>();
        for (int i = 1; i < Docos.getNextID(); i++) {
            if (getDocument(i).getAppointmentID() == selectedAppointment.getAppointmentID()) {
                documents.add(getDocument(i));
            }
        }
        ObservableList<Label> documentItems = FXCollections.observableArrayList();
        ArrayList<Label> labels = new ArrayList<>();
        for (Docos document : documents) {
            if (documents != null) {
                Label label = new Label(document.getTitle());
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        FileOutputStream fos = null;
                        try {
                            selectedHistoricFile = Docos.getFile(document.getDocumentID());
                            openFile(selectedHistoricFile);

                        } catch (IOException ex) {
                            Logger.getLogger(GuiMainController.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                documentItems.add(label);
            }
        }
        if (!documentItems.isEmpty()) {
            noDocsLabel.setVisible(false);
            documentList.setItems(documentItems);
        } else {
            noDocsLabel.setVisible(true);
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

    private VBox initCalendar() {

        //Controls
        monthLabel = new Label("Januray");
//        monthLabel.setPrefSize(50, 20);
        yearCombo = new ComboBox();
//        yearCombo.setPrefSize(40, 20);
        prevButton = new Button("<");
        prevButton.setPrefSize(20, 20);
        nextButton = new Button(">");
        nextButton.setPrefSize(20, 20);
        yearCombo.setPrefSize(55, 20);

        monthLabel.setStyle("-fx-font-size: 10");
        yearCombo.setStyle("-fx-font-size: 10");
        prevButton.setStyle("-fx-font-size: 10");
        nextButton.setStyle("-fx-font-size: 10");

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
//        HBox dateBox = new HBox();
//        dateBox.getChildren().add(monthLabel);
//        dateBox.getChildren().add(yearCombo);
//        dateBox.setAlignment(Pos.CENTER);
        prevButton.setAlignment(Pos.CENTER_LEFT);
        nextButton.setAlignment(Pos.CENTER_RIGHT);

//        controlPane.prefWidthProperty().bind(calendarGrid.widthProperty());
        controlPane.getChildren().add(prevButton);
        controlPane.getChildren().add(monthLabel);
        controlPane.getChildren().add(yearCombo);
        controlPane.getChildren().add(nextButton);
        controlPane.setPrefSize(200, 20);
        calendarBox.getChildren().add(controlPane);

        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Current month and year is what the calendar is viewing
        currentYear = realYear;

        //GridPane for headers
        TilePane headerBox = new TilePane();
        headerBox.setPrefColumns(7);
        headerBox.setPrefTileWidth(32);
        Label sunLabel = new Label("Sun");
        Label monLabel = new Label("Mon");
        Label tueLabel = new Label("Tue");
        Label wedLabel = new Label("Wed");
        Label thuLabel = new Label("Thu");
        Label friLabel = new Label("Fri");
        Label satLabel = new Label("Sat");
        sunLabel.setPrefSize(50 / 2, 30 / 2);
        monLabel.setPrefSize(50 / 2, 30 / 2);
        tueLabel.setPrefSize(50 / 2, 30 / 2);
        wedLabel.setPrefSize(50 / 2, 30 / 2);
        thuLabel.setPrefSize(50 / 2, 30 / 2);
        friLabel.setPrefSize(50 / 2, 30 / 2);
        satLabel.setPrefSize(50 / 2, 30 / 2);
        headerBox.getChildren().add(sunLabel);
        headerBox.getChildren().add(monLabel);
        headerBox.getChildren().add(tueLabel);
        headerBox.getChildren().add(wedLabel);
        headerBox.getChildren().add(thuLabel);
        headerBox.getChildren().add(friLabel);
        headerBox.getChildren().add(satLabel);
        sunLabel.setAlignment(Pos.CENTER);
        monLabel.setAlignment(Pos.CENTER);
        tueLabel.setAlignment(Pos.CENTER);
        wedLabel.setAlignment(Pos.CENTER);
        thuLabel.setAlignment(Pos.CENTER);
        friLabel.setAlignment(Pos.CENTER);
        satLabel.setAlignment(Pos.CENTER);
        sunLabel.setStyle("-fx-font-size: 10");
        monLabel.setStyle("-fx-font-size: 10");
        tueLabel.setStyle("-fx-font-size: 10");
        wedLabel.setStyle("-fx-font-size: 10");
        thuLabel.setStyle("-fx-font-size: 10");
        friLabel.setStyle("-fx-font-size: 10");
        satLabel.setStyle("-fx-font-size: 10");

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

    private void refreshCalendar(int month, int year) {
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
                cellButton[i][j].setPrefSize(30, 30);
//                cellButton[i][j].setPrefSize(50, 50);

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

                refreshTimetable();

                //refresh schedule
                if (doctorCombo.getValue() != "Select doctor" && !doctorCombo.getValue().toString().isEmpty()) {
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
                            cellButton[x][y].setStyle("-fx-font-size: 12; -fx-background-color: lightcyan; -fx-alignment: center;"
                                    + " -fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0;");
                        } else if (!cellButton[x][y].getText().equals("") && currentYear > realYear) {
                            cellButton[x][y].setDisable(false);
                            cellButton[x][y].setStyle("-fx-font-size: 12; -fx-background-color: lightcyan; -fx-alignment: center; "
                                    + "-fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0;");
                        } //highlight future months in current year
                        else if (!cellButton[x][y].getText().equals("") && currentMonth > realMonth && currentYear == realYear) {
                            cellButton[x][y].setDisable(false);
                            cellButton[x][y].setStyle("-fx-font-size: 12; -fx-background-color: lightcyan; -fx-alignment: center; -fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0;");
                        } //highlight today's cell
                        else if (!cellButton[x][y].getText().equals("") && Integer.parseInt(cellButton[x][y].getText()) == realDay && currentMonth
                                == realMonth && currentYear == realYear) { //Today
                            cellButton[x][y].setDisable(false);
                            cellButton[x][y].setStyle("-fx-font-size: 12; -fx-background-color: lightsalmon; -fx-alignment: center; -fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0;");

                        } else if (cellButton[x][y].getText().isEmpty()) {
                            cellButton[x][y].setDisable(false); //purely for presentation purposes
                        }
                    }
                }
                //render selected cell
                cellButton[column][row].setStyle("-fx-font-size: 12; -fx-background-color: cyan; -fx-alignment: center; -fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0;");
            });
            //highlight future days in month
            if (Integer.parseInt(cellButton[column][row].getText()) > realDay && currentMonth == realMonth && currentYear >= realYear) { //Today
                cellButton[column][row].setStyle("-fx-font-size: 12; -fx-background-color: lightcyan; -fx-alignment: center; -fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0;");
            } //highlight all future years 
            else if (currentYear > realYear) {
                cellButton[column][row].setStyle("-fx-font-size: 12; -fx-background-color: lightcyan; -fx-alignment: center; -fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0;");
            } //highlight future months in current year
            else if (currentMonth > realMonth && currentYear == realYear) {
                cellButton[column][row].setStyle("-fx-font-size: 12; -fx-background-color: lightcyan; -fx-alignment: center; -fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0;");
            } //highlight today's cell
            else if (Integer.parseInt(cellButton[column][row].getText()) == realDay && currentMonth
                    == realMonth && currentYear == realYear) { //Today
                cellButton[column][row].setStyle("-fx-font-size: 12; -fx-background-color: lightsalmon; -fx-alignment: center; -fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0;");
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
                control.setStyle("-fx-font-size: 12; -fx-background-color: azure; -fx-alignment: center;"
                        + " -fx-border-radius: 0 0 0 0; -fx-background-radius: 0 0 0 0;");
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
    public VBox initSchedule() {
        VBox scheduleBox = new VBox();
        HBox scheduleTitlePane = new HBox(10);
        //scheduleTitlePane.setPrefColumns(3);

        scheduleLabel = new Label("Times for: ");
        scheduleDateLabel = new Label(currentDay + getDateSuffix(currentDay)
                + " " + months.get(currentMonth) + " " + currentYear);

        scheduleDateLabel.setTextFill(Color.web("#0076a3"));

        //Populate doctor combobox
        doctorCombo = new ComboBox();
//        doctorCombo.getItems().add("All doctors");
//        doctorCombo.setValue("All doctors");

        ArrayList<Doctor> doctors = new ArrayList<>();
        for (int i = 1; i <= Doctor.getMaxID(); i++) {
            doctors.add(Doctor.getDoctor(i));
        }

        doctorCombo.setValue("Select doctor");

        for (Doctor doctor : doctors) {
            doctorCombo.getItems().add("Dr. " + doctor.getFirstName() + " "
                    + doctor.getLastName());
        }
        ScrollPane scheduleScroll = new ScrollPane();
        scheduleTile = new TilePane();
        scheduleTile.setPrefColumns(4);
        scheduleTile.setDisable(true);
        doctorCombo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (doctorCombo.getValue() != "Select doctor" && !doctorCombo.getValue().toString().isEmpty()) {
                    scheduleTile.setDisable(false);
                    String name = doctorCombo.getValue().toString();
                    String[] splitName = name.split("\\s+"); //split when at least one whitespace is identified

                    currentDoctor = getDoctor(splitName[1], splitName[2]);
                    refreshSchedule(currentDay, currentMonth, currentYear, Doctor.getDoctor(splitName[1], splitName[2]));
                    refreshSchedule(currentDay, currentMonth, currentYear, currentDoctor);

                    if (timeBtnGroup.getSelectedToggle() != null) {
                        timeBtnGroup.getSelectedToggle().setSelected(false);
                    }
                } else if (doctorCombo.getValue() == "Select doctor") {
                    scheduleTile.setDisable(true);
                }
            }
        });

        timeBtn = new ArrayList<>();
        //Create times 9-4.45pm, 15 min intervals
        for (int i = 0; i < 48; i++) {
            timeBtn.add(new ToggleButton(""));
            timeBtn.get(i).setToggleGroup(timeBtnGroup);
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
                    renderSchedule();
                    refreshSchedule(currentDay, currentMonth, currentYear, currentDoctor);
                    SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
                    SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
                    currentHour = Integer.parseInt(hourFormat.format(date));
                    currentMinute = Integer.parseInt(minuteFormat.format(date));

                }
            });
            scheduleTile.getChildren().add(timeBtn.get(i));
        }
        scheduleTile.setHgap(2.5);
        scheduleTile.setVgap(2.5);

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
                System.out.println(date);
                int duration = 15;

                if (shortRadioBtn.isSelected()) {
                    duration = 15;
                } else {
                    duration = 30;
                }
                Appointment.insertAppointment(currentPatient, currentDoctor, date, duration, purposeText.getText());
                refreshSchedule(currentDay, currentMonth, currentYear);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:MM");
                Dialogs.create()
                        .owner(null)
                        .title("Appointment Scheduled")
                        .masthead(null)
                        .message("Appointment for " + currentPatient.getFirstName()
                                + " " + currentPatient.getLastName() + " with"
                                + " Dr. " + currentDoctor.getLastName() + " at " + dateFormat.format(date)
                                + " " + " (" + duration
                                + " mins) has been successfully scheduled.")
                        .showInformation();

                Date changeLogDate = Calendar.getInstance().getTime();
                ChangeLog.insertChangeLog(new ChangeLog(1, changeLogDate, "Appointment", "Appointment for " + currentPatient.getFirstName()
                        + " " + currentPatient.getLastName() + " with"
                        + " Dr. " + currentDoctor.getLastName() + " has been scheduled",
                        currentUser.getStaffID()));

                newAppointment.setVisible(false);
                homePane.setVisible(true);
            }
        });
        createButton.setAlignment(Pos.CENTER_RIGHT);
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newAppointment.setVisible(false);
                homePane.setVisible(true);
            }
        });
        cancelButton.setAlignment(Pos.CENTER_RIGHT);

        buttonsBox.getChildren().add(createButton);
        buttonsBox.getChildren().add(cancelButton);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        VBox nameBox = new VBox();
        Label title = new Label("Patient: ");
        Label name = new Label();
        name.textProperty().bind(currentPatientName.textProperty());
        nameBox.getChildren().add(title);
        nameBox.getChildren().add(name);

        controlsGrid.add(nameBox, 1, 0);

        controlsGrid.add(radiosBox, 0, 0);
        controlsGrid.add(purposeBox, 0, 1);
        controlsGrid.add(buttonsBox, 1, 1);

        //Add padding
        buttonsBox.setPadding(new Insets(10, 10, 10, 10));
        purposeBox.setPadding(new Insets(10, 10, 10, 10));
        radiosBox.setPadding(new Insets(10, 10, 10, 10));
        nameBox.setPadding(new Insets(10, 10, 10, 10));

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
            bookedTimes.add(timeFormat.format(appointments.get(i).getDate()));
        }
        for (int i = 0; i < 48; i++) {//Scan time slots for any booked appointments, then disable
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
        if ("".equals(PatientAddFirstName.getText()) || "".equals(PatientAddLastName.getText())
                || "".equals(PatientAddAddressStreetNumber.getText()) || "".equals(PatientAddAddressStreetName.getText())
                || "".equals(PatientAddAddressSuburb.getText()) || "".equals(PatientAddAddressPostcode.getText())
                || "".equals(PatientAddPrimaryContactNumber.getText()) || PatientAddFirstName.getText().isEmpty() || PatientAddLastName.getText().isEmpty()
                || PatientAddAddressStreetNumber.getText().isEmpty() || PatientAddAddressStreetName.getText().isEmpty()
                || PatientAddAddressSuburb.getText().isEmpty() || PatientAddAddressPostcode.getText().isEmpty()
                || PatientAddPrimaryContactNumber.getText().isEmpty() || PatientAddDateOfBirth.getValue() == null) {

            Dialogs.create()
                    .owner(null)
                    .title("Input Error")
                    .masthead(null)
                    .message("You seem to have input missing in some field(s). Please complete the fields correctly.")
                    .showInformation();
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

            Date changeLogDate = Calendar.getInstance().getTime();
            ChangeLog.insertChangeLog(new ChangeLog(1, changeLogDate, "Patient", "Patient: " + firstName + " "
                    + lastName + " created and stored to system", currentUser.getStaffID()));

            setAllInvisible();
            homePane.setVisible(true);
        }
    }

    private static final class DragContext {

        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    private final BooleanProperty dragModeActiveProperty
            = new SimpleBooleanProperty(this, "dragModeActive", true);

    private Node makeDraggable(final Node node) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = new Group(node);

        wrapGroup.addEventFilter(
                MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        if (dragModeActiveProperty.get()) {
                            // disable mouse events for all children
                            mouseEvent.consume();
                        }
                    }
                });

        wrapGroup.addEventFilter(
                MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        if (dragModeActiveProperty.get()) {
                            // remember initial mouse cursor coordinates
                            // and node position
                            dragContext.mouseAnchorX = mouseEvent.getX();
                            dragContext.mouseAnchorY = mouseEvent.getY();
                            dragContext.initialTranslateX
                            = node.getTranslateX();
                            dragContext.initialTranslateY
                            = node.getTranslateY();
                        }
                    }
                });

        wrapGroup.addEventFilter(
                MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        if (dragModeActiveProperty.get()) {
                            // shift node from its initial position by delta
                            // calculated from mouse cursor movement
                            node.setTranslateX(
                                    dragContext.initialTranslateX
                                    + mouseEvent.getX()
                                    - dragContext.mouseAnchorX);
                            node.setTranslateY(
                                    dragContext.initialTranslateY
                                    + mouseEvent.getY()
                                    - dragContext.mouseAnchorY);
                        }
                    }
                });

        return wrapGroup;
    }

    @FXML
    private TextField weightTF;
    @FXML
    private TextField bloodTF1;
    @FXML
    private TextField bloodTF2;
    @FXML
    private TextField heartTF;
    @FXML
    private TextField oxygenLevelTF;
    @FXML
    private TextField lungTF;
    @FXML
    private TextField oxygenUptakeTF;
    @FXML
    private Button submitTestBtn;

    @FXML
    private void handleSubmitTestBtn() {
//        
//        insertTestResult(int appointmentID, double weight, String bloodPressure,
//            int heartRate, double oxygenLevel, double lungCapacity, double oxygenUptake) 

        if (!weightTF.getText().isEmpty()
                && !bloodTF1.getText().isEmpty()
                && !bloodTF2.getText().isEmpty()
                && !heartTF.getText().isEmpty()
                && !oxygenLevelTF.getText().isEmpty()
                && !lungTF.getText().isEmpty()
                && !oxygenUptakeTF.getText().isEmpty()
                && isNumeric(weightTF.getText())
                && isNumeric(bloodTF1.getText())
                && isNumeric(bloodTF2.getText())
                && isNumeric(heartTF.getText())
                && isNumeric(oxygenLevelTF.getText())
                && isNumeric(lungTF.getText())
                && isNumeric(oxygenUptakeTF.getText())) {

            int appointmentID = currentAppointment.getAppointmentID();
            double weight = Double.valueOf(weightTF.getText());
            String bloodPressure = bloodTF1.getText() + "/" + bloodTF2.getText();
            int heartRate = Integer.valueOf(heartTF.getText());
            double oxygenLevel = Double.valueOf(oxygenLevelTF.getText());
            double lungCapacity = Double.valueOf(lungTF.getText());
            double oxygenUptake = Double.valueOf(oxygenUptakeTF.getText());

            if (submitTestBtn.getText().contains("Update")) {
                int testID = 0;
                for (int i = 1; i < TestResult.getNextID(); i++) {
                    if (getTestResult(i).getAppointmentID() == currentAppointment.getAppointmentID()) {
                        testID = getTestResult(i).getTestResultID();
                    }
                }
                TestResult updatedResult = new TestResult(testID, weight, bloodPressure, heartRate, oxygenLevel, lungCapacity, oxygenUptake, appointmentID, false, false);
                updateTestResult(updatedResult);
                Dialogs.create()
                        .owner(null)
                        .title("Update Successful")
                        .masthead(null)
                        .message("Test data has been updated.")
                        .showInformation();
                Date changeLogDate = Calendar.getInstance().getTime();
                ChangeLog.insertChangeLog(new ChangeLog(1, changeLogDate, "Test", "Test results updated for appointment at "
                        + currentAppointment.getDate() + ", Patient: " + getPatient(currentAppointment.getPatientID()).getFirstName()
                        + " " + getPatient(currentAppointment.getPatientID()).getLastName(), currentUser.getStaffID()));

            } else {
                insertTestResult(appointmentID, weight, bloodPressure, heartRate, oxygenLevel, lungCapacity, oxygenUptake);

                Date changeLogDate = Calendar.getInstance().getTime();
                ChangeLog.insertChangeLog(new ChangeLog(1, changeLogDate, "Test", "Test added for appointment at "
                        + currentAppointment.getDate() + ", Patient: " + getPatient(currentAppointment.getPatientID()).getFirstName()
                        + " " + getPatient(currentAppointment.getPatientID()).getLastName(), currentUser.getStaffID()));

                submitTestBtn.setDisable(true);
                submitTestBtn.setText("Update");
            }
            refreshTestResult();

        } else {
            Dialogs.create()
                    .owner(null)
                    .title("Submit Test Error")
                    .masthead(null)
                    .message("Please enter numeric values in all text fields provided.")
                    .showInformation();
        }
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @FXML
    private AnchorPane searchPane;

    @FXML
    private ImageView MenuPayments;

    public void setAllInvisible() {
        MedicalAppNewPatient.setVisible(false);
        patientFile.setVisible(false);
        newAppointment.setVisible(false);
        homePane.setVisible(false);
        paymentsPane.setVisible(false);
        newInvoice.setVisible(false);
        searchList.setVisible(false);
        changeLogPane.setVisible(false);
        pdfPreviewPane.setVisible(false);
//        searchPane.setVisible(false);

        MenuHome.effectProperty().bind(
                Bindings
                .when(MenuHome.hoverProperty())
                .then((Effect) new Glow(1.0))
                .otherwise((Effect) new Glow(0))
        );

        MenuHome.imageProperty().bind(
                Bindings
                .when(homePane.visibleProperty())
                .then(new javafx.scene.image.Image("medicalapp/guimain/res/calendar_icon_y.png"))
                .otherwise(new javafx.scene.image.Image("medicalapp/guimain/res/calendar_icon_w.png"))
        );
        MenuPatientAdd.effectProperty().bind(
                Bindings
                .when(MenuPatientAdd.hoverProperty())
                .then((Effect) new Glow(1.0))
                .otherwise((Effect) new Glow(0))
        );

        MenuPatientAdd.imageProperty().bind(
                Bindings
                .when(MedicalAppNewPatient.visibleProperty())
                .then(new javafx.scene.image.Image("medicalapp/guimain/res/addPatient_icon_y.png"))
                .otherwise(new javafx.scene.image.Image("medicalapp/guimain/res/addPatient_icon_w.png"))
        );
        MenuPayments.effectProperty().bind(
                Bindings
                .when(MenuPayments.hoverProperty())
                .then((Effect) new Glow(1.0))
                .otherwise((Effect) new Glow(0))
        );

        MenuPayments.imageProperty().bind(
                Bindings
                .when(paymentsPane.visibleProperty())
                .then(new javafx.scene.image.Image("medicalapp/guimain/res/payment_icon_y.png"))
                .otherwise(new javafx.scene.image.Image("medicalapp/guimain/res/payment_icon_w.png"))
        );
        MenuMessenger.effectProperty().bind(
                Bindings
                .when(MenuMessenger.hoverProperty())
                .then((Effect) new Glow(1.0))
                .otherwise((Effect) new Glow(0))
        );
        MenuChangeLog.effectProperty().bind(
                Bindings
                .when(MenuChangeLog.hoverProperty())
                .then((Effect) new Glow(1.0))
                .otherwise((Effect) new Glow(0))
        );
        MenuChangeLog.imageProperty().bind(
                Bindings
                .when(changeLogPane.visibleProperty())
                .then(new javafx.scene.image.Image("medicalapp/guimain/res/settings_icon_y.png"))
                .otherwise(new javafx.scene.image.Image("medicalapp/guimain/res/settings_icon_w.png"))
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginScreen.setVisible(true);
        mainScreen.setVisible(false);

        setAllInvisible();

        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH);
        currentDay = cal.get(Calendar.DAY_OF_MONTH);

        calDashBoard = new HBox();
        calDashBoard.getChildren().add(initCalendar());

        newAppointment.getChildren().add(initSchedule());

        MedicalAppCalendar.getChildren().add(calDashBoard);

        MenuPatientAdd.setOnMouseClicked(this::handleAddPatientMouse);
        MenuHome.setOnMouseClicked(this::handleMenuHomeMouse);
        PatientAddSubmit.setOnMouseClicked(this::handleAddPatientButton);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            refreshSearchList();
        });
        calDashBoard.requestFocus();

        MedicalAppSearch.setMinSize(0, 0);

        submitNote.setOnMouseClicked(this::handleNoteAddButton);
        initPayments();
        initChangeLog();
        initVisitHistory();

        MenuHome.effectProperty().bind(
                Bindings
                .when(MenuHome.visibleProperty())
                .then((Effect) new Glow(1.0))
                .otherwise((Effect) new Glow(0))
        );
    }
}
