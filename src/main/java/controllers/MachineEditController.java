package controllers;

import entities.Machine;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceMachine;

import java.io.*;
import java.sql.Timestamp;

public class MachineEditController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<String> etatComboBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField priceField;
    @FXML private ComboBox<String> brandComboBox;
    @FXML private ImageView imagePreview;
    @FXML private Label imageLabel;

    private final ServiceMachine serviceMachine = new ServiceMachine();
    private String relativeImagePath;
    private Machine machine;

    public void setMachine(Machine machine) {
        this.machine = machine;
        populateFields(machine);
    }

    @FXML
    public void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList("Tracteur", "Moissonneuse", "Semoir"));
        etatComboBox.setItems(FXCollections.observableArrayList("Disponible", "En maintenance", "Louée"));
        brandComboBox.setItems(FXCollections.observableArrayList("Massey Ferguson", "John Deere", "Claas"));
    }

    private void populateFields(Machine machine) {
        nameField.setText(machine.getName());
        typeComboBox.setValue(machine.getType());
        etatComboBox.setValue(machine.getEtat());
        datePicker.setValue(machine.getDateLastCheckup().toLocalDateTime().toLocalDate());
        priceField.setText(String.valueOf(machine.getPricePerDay()));
        brandComboBox.setValue(machine.getBrand());
        relativeImagePath = machine.getImage_url();

        if (relativeImagePath != null && new File(relativeImagePath).exists()) {
            imagePreview.setImage(new Image(new File(relativeImagePath).toURI().toString()));
            imageLabel.setText(new File(relativeImagePath).getName());
        }
    }

    @FXML
    private void handleImageUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                File destDir = new File("images");
                if (!destDir.exists()) destDir.mkdir();

                String fileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                File destFile = new File(destDir, fileName);

                try (FileInputStream fis = new FileInputStream(selectedFile);
                     FileOutputStream fos = new FileOutputStream(destFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                }

                relativeImagePath = "images/" + fileName;
                imageLabel.setText(fileName);
                imagePreview.setImage(new Image(destFile.toURI().toString()));

            } catch (IOException e) {
                e.printStackTrace();
                imageLabel.setText("Erreur lors de l'importation");
            }
        }
    }

    @FXML
    private void handleAddMachine(ActionEvent event) {
        if (machine == null) return;

        if (nameField.getText().isEmpty() ||
                typeComboBox.getValue() == null ||
                etatComboBox.getValue() == null ||
                datePicker.getValue() == null ||
                priceField.getText().isEmpty() ||
                brandComboBox.getValue() == null) {

            showAlert("❌ Veuillez remplir tous les champs obligatoires.");
            return;
        }

        int price;
        try {
            price = Integer.parseInt(priceField.getText());
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("❌ Le prix doit être un entier positif.");
            return;
        }

        machine.setName(nameField.getText());
        machine.setType(typeComboBox.getValue());
        machine.setEtat(etatComboBox.getValue());
        machine.setDateLastCheckup(Timestamp.valueOf(datePicker.getValue().atStartOfDay()));
        machine.setPricePerDay(price);
        machine.setBrand(brandComboBox.getValue());
        machine.setImage_url(relativeImagePath);

        serviceMachine.editMachine(machine);

        showAlert("✅ Machine modifiée avec succès !");

        // Go back to machine index
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pijihene/machine-index.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("🌿 Liste des Machines");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pijihene/machine-home.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("🌾 Gestion des Machines");
        stage.show();
    }
}