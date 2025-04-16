package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Acceuil {

    @FXML
    private Button btnZones;

    @FXML
    private Button btnGranges;

    @FXML
    private AnchorPane rootPane;


    @FXML
    private void consulterZones(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/example/projectjava/ListeZones.fxml"));
        rootPane.getChildren().setAll(pane);
    }




    @FXML
    private void consulterGranges(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/example/projectjava/ListeGranges.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
