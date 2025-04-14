package Controlles;

import Entites.Zone;
import Services.ZoneService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListeZones implements Initializable {

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnRetour;

    @FXML
    private TableColumn<?, ?> colActions;

    @FXML
    private TableColumn<?, ?> colLocalisation;

    @FXML
    private TableColumn<?, ?> colNom;

    @FXML
    private TableColumn<?, ?> colSuperficie;

    @FXML
    private TableView<Zone> tableZones;

    private ZoneService zoneService = new ZoneService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colSuperficie.setCellValueFactory(new PropertyValueFactory<>("superficie_zone"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom_zone"));
        colLocalisation.setCellValueFactory(new PropertyValueFactory<>("localisation_zone"));

        List<Zone> zones = zoneService.find();
        ObservableList<Zone> observableList = FXCollections.observableArrayList(zones);
        tableZones.setItems(observableList);
    }

    @FXML
    void retourAccueil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterZone.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) tableZones.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    @FXML
    private void ajouterZone(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterZone.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et changer le contenu
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter une zone");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
