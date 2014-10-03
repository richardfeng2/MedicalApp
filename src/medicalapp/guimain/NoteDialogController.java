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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import medicalapp.data.Note;

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
    private Button previousNoteBtn;
    @FXML
    private Button nextNoteBtn;

    private Note note;
    private Stage dialogStage;
    private boolean cancelClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setNote(Note note) {
        this.note = note;
        noteTextArea.setText(note.getText());
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

    /**
     * Called when the user clicks previous.
     */
    @FXML
    private void handlePreviousNote() {
        Note currentNote = note;

    }

    /**
     * Called when the user clicks next.
     */
    @FXML
    private void handleNextNote() {

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
