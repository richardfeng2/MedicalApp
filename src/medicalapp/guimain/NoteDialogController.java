/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.guimain;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import medicalapp.data.Addendum;
import static medicalapp.data.Addendum.insertAddendum;
import static medicalapp.data.Appointment.getAppointment;
import medicalapp.data.ChangeLog;
import medicalapp.data.Note;
import static medicalapp.data.Patient.getPatient;
import medicalapp.data.Staff;

/**
 * FXML Controller class
 *
 * @author Richard
 */
public class NoteDialogController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextArea noteTextArea;
    @FXML
    private Button cancelNoteBtn;
    @FXML
    private Button submitAddendum;

    private Note note;
    private Staff user;
    private Stage dialogStage;
    private boolean cancelClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public void setUser(Staff user) {
        this.user = user;
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleSubmitAddendum() {
        Addendum addendum = new Addendum(0, note.getNoteID(), noteTextArea.getText(), null, user.getStaffID());
        insertAddendum(addendum);
        Date changeLogDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY, HH:mm");
        ChangeLog.insertChangeLog(new ChangeLog(1, changeLogDate, "Note", "Note addendum created for Appointment: "
                + sdf.format(getAppointment(note.getAppointmentID()).getDate()) + ", Patient: " + getPatient(getAppointment(note.getAppointmentID()).getPatientID()).getFirstName()
                + " " + getPatient(getAppointment(note.getAppointmentID()).getPatientID()).getLastName(), user.getStaffID()));
        dialogStage.close();
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isCancelClicked() {
        return cancelClicked;
    }

}
