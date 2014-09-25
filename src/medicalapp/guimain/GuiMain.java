/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.guimain;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Tony
 */
public class GuiMain extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("GuiMain.fxml"));
        primaryStage.setTitle("MedicalApp");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) {
            launch(args);
        }
    }

