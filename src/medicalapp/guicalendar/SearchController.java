/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.guicalendar;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import medicalapp.data.Patient;
import static medicalapp.data.Patient.getPatient;

/**
 * FXML Controller class
 *
 * @author Richard
 */
public class SearchController implements Initializable {

    @FXML
    private AnchorPane searchPane;
    @FXML
    private static TextField searchText;

    private static TableView table = new TableView();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VBox vBox = new VBox();

        vBox.getChildren().add(initSearch());
        searchPane.getChildren().add(vBox);
    }

    public static ScrollPane initSearch() {
        TextField tf = new TextField();
//        searchText.setText(getSearchText()); //Get text from search bar

        ScrollPane searchScrollPane = new ScrollPane();
        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");

        table.getColumns().addAll(firstNameCol, lastNameCol);

        searchScrollPane.setContent(table);

        ObservableList<Patient> data = FXCollections.observableArrayList();
        String[] splitArray = tf.getText().split("\\s+");
        
        data.add(getPatient(splitArray[0], splitArray[1]));
        

        return searchScrollPane;

    }

    private static void refreshSearch(int month, int year) {

    }

    public static void renderSearch() {

    }
}
