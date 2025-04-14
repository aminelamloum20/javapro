package controllers;

import entities.Machine;
import entities.Reservation;
import entities.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import services.ServiceMachine;
import services.ServiceReservation;
import services.UserService;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class ReservationViewController {

    @FXML private ComboBox<Machine> machineComboBox;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Label messageLabel;

    // Machine card section
    @FXML private VBox machineCard;
    @FXML private ImageView machineImage;
    @FXML private Label machineName;
    @FXML private Label machineType;
    @FXML private Label machineEtat;
    @FXML private Label machinePrix;

    private final ServiceMachine serviceMachine = new ServiceMachine();
    private final ServiceReservation serviceReservation = new ServiceReservation();
    private final UserService userService = new UserService();

    @FXML
    public void initialize() {
        List<Machine> machines = serviceMachine.getMachines();
        machineComboBox.setItems(FXCollections.observableArrayList(machines));

        // Display names in dropdown
        machineComboBox.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Machine machine, boolean empty) {
                super.updateItem(machine, empty);
                setText(empty || machine == null ? null : machine.getName());
            }
        });
        machineComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Machine machine, boolean empty) {
                super.updateItem(machine, empty);
                setText(empty || machine == null ? null : machine.getName());
            }
        });

        // Show machine card when selected
        machineComboBox.setOnAction(e -> showMachineDetails(machineComboBox.getValue()));

        machineCard.setVisible(false); // hidden by default
    }

    private void showMachineDetails(Machine machine) {
        if (machine == null) {
            machineCard.setVisible(false);
            return;
        }

        machineName.setText(machine.getName());
        machineType.setText("Type: " + machine.getType());
        machineEtat.setText("État: " + machine.getEtat());
        machinePrix.setText("Prix: " + machine.getPricePerDay() + " DT / jour");

        String imagePath = (machine.getImage_url() != null && new File(machine.getImage_url()).exists())
                ? new File(machine.getImage_url()).toURI().toString()
                : new File("images/default.png").toURI().toString();

        machineImage.setImage(new Image(imagePath));
        machineCard.setVisible(true);
    }

    @FXML
    private void handleReserve() {
        Machine selectedMachine = machineComboBox.getValue();
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        messageLabel.setText("");

        if (selectedMachine == null || start == null || end == null) {
            messageLabel.setText("❌ Veuillez remplir tous les champs.");
            return;
        }

        if (end.isBefore(start)) {
            messageLabel.setText("❌ La date de fin doit être après la date de début.");
            return;
        }

        User user = userService.getUser(2); // static for now
        if (user == null) {
            messageLabel.setText("❌ Utilisateur non trouvé (ID 2).");
            return;
        }

        Reservation reservation = new Reservation(
                Timestamp.valueOf(start.atStartOfDay()),
                Timestamp.valueOf(end.atStartOfDay()),
                selectedMachine,
                user
        );

        serviceReservation.addReservation(reservation);
        messageLabel.setStyle("-fx-text-fill: #27ae60;");
        messageLabel.setText("✅ Réservation enregistrée avec succès !");
        clearForm();
    }

    private void clearForm() {
        machineComboBox.getSelectionModel().clearSelection();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        machineCard.setVisible(false);
    }
}
