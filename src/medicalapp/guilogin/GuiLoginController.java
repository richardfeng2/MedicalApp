/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.guilogin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import medicalapp.data.DBConnection;

/**
 *
 * @author Tony
 */
public class GuiLoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label message;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void loginAction(ActionEvent event) {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            String usernameText = usernameField.getText();
            String passwordText = passwordField.getText();
            String passwordDB = "";
            String firstName = "";

            String query = "SELECT password, firstName FROM Staff "
                    + " INNER JOIN Person ON Staff.personID = Person.personID"
                    + " WHERE username = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, usernameText);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                passwordDB = rs.getString("password");
                firstName = rs.getString("firstName");
            }
            if (passwordDB.equals(passwordText) && !passwordText.equals("")) {
                message.setText("\tWelcome " + firstName + " !");
            }else {
                message.setText("Invalid login details. \n Please contact Tony Lu for IT support.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GuiLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
