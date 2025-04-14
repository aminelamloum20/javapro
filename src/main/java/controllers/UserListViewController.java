package controllers;

import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.UserService;

import java.io.IOException;

public class UserListViewController {
    @FXML
    private ComboBox<String> filterRole;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Void> actionsColumn;

    @FXML
    private TextField searchField;

    private final UserService userService = new UserService();
    private final ObservableList<User> masterUserList = FXCollections.observableArrayList();
    @FXML
    private void handleAddUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjava/register-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) usersTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Load data
        masterUserList.addAll(userService.getAllUsers());

        // Setup filtering
        FilteredList<User> filteredData = new FilteredList<>(masterUserList, p -> true);
        usersTable.setItems(filteredData);

        // Fill role filter options
        filterRole.getItems().addAll("All", "Admin", "Agriculteur", "Fournisseur", "Client");
        filterRole.setValue("All");

        // 🔎 Filtering logic
        Runnable updateFilter = () -> {
            String emailText = searchField.getText().toLowerCase().trim();
            String selectedRole = filterRole.getValue();

            filteredData.setPredicate(user -> {
                boolean matchesEmail = emailText.isEmpty()
                        || user.getEmail().toLowerCase().contains(emailText);

                boolean matchesRole = true;
                if (!"All".equalsIgnoreCase(selectedRole)) {
                    String roleKey = selectedRole.toUpperCase();
                    matchesRole = user.getRole() != null &&
                            (user.getRole().equalsIgnoreCase(roleKey) ||
                                    user.getRole().equalsIgnoreCase("ROLE_" + roleKey));
                }

                return matchesEmail && matchesRole;
            });
        };

        // Listeners for search and role change
        searchField.textProperty().addListener((obs, oldVal, newVal) -> updateFilter.run());
        filterRole.valueProperty().addListener((obs, oldVal, newVal) -> updateFilter.run());

        // Add buttons column
        addEditDeleteButtons();
    }

    private void addEditDeleteButtons() {
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("edit");
            private final Button deleteButton = new Button("X");

            {
                editButton.setStyle("-fx-background-color: #ffc107; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                editButton.setPrefWidth(40);
                deleteButton.setPrefWidth(40);

                editButton.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    System.out.println("Edit clicked for: " + user.getEmail());
                    // TODO: Open edit window
                });

                deleteButton.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    userService.deleteUser(user);
                    masterUserList.remove(user);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("User Deleted");
                    alert.setHeaderText(null);
                    alert.setContentText("✅ User \"" + user.getEmail() + "\" was deleted successfully.");
                    alert.getDialogPane().setStyle(
                            "-fx-font-size: 14px; -fx-font-family: 'Segoe UI'; -fx-background-color: #f9f9f9; -fx-border-color: #cccccc;"
                    );
                    alert.showAndWait();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(10, editButton, deleteButton);
                    buttons.setAlignment(Pos.CENTER);
                    setGraphic(buttons);
                }
            }
        });
    }
}
