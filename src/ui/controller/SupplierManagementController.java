package ui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.factories.SupplierManagerFactory;
import logic.interfaces.SupplierManager;
import transfer.objects.Supplier;

public class SupplierManagementController extends GenericController {

    @FXML
    private Button btnListSuppliers;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExit;

    @FXML
    private TableView<Supplier> tvSupplier;

    @FXML
    private TableColumn<Supplier, String> nameColumnId;

    @FXML
    private TableColumn<Supplier, String> phoneColumnId;

    @FXML
    private TableColumn<Supplier, String> countryColumnId;

    @FXML
    private TableColumn<Supplier, String> zipColumnId;

    @FXML
    private TableColumn<Supplier, String> dateColumnId;

    @FXML
    private MenuBar menuBar;

    private SupplierManager supplierManager;

    public void initStage(Parent root) {
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        supplierManager = SupplierManagerFactory.getInstance();

        btnExit.setOnAction(event -> handleExitButtonAction());

        // Agrega manejadores para los botones
        btnListSuppliers.setOnAction(event -> handleListButtonAction());
        btnAdd.setOnAction(event -> handleAddButtonAction());
        btnEdit.setOnAction(event -> handleEditButtonAction());
        btnDelete.setOnAction(event -> handleDeleteButtonAction());

        // Agrega un manejador para el evento de cierre de la ventana
        stage.setOnCloseRequest(event -> handleCloseRequest(event, stage));

        // Agrega un listener para activar/desactivar los botones y el menú de contexto
        tvSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnEdit.setDisable(false);
                btnDelete.setDisable(false);
            } else {
                btnEdit.setDisable(true);
                btnDelete.setDisable(true);
            }
        });

        // Configura el menú de contexto
        ContextMenu contextMenu = new ContextMenu();
        MenuItem addSupplierMenuItem = new MenuItem("Add Supplier");
        MenuItem editMenuItem = new MenuItem("Edit");
        MenuItem deleteMenuItem = new MenuItem("Delete Row");

        addSupplierMenuItem.setOnAction(event -> handleAddButtonAction());
        editMenuItem.setOnAction(event -> handleEditButtonAction());
        deleteMenuItem.setOnAction(event -> handleDeleteButtonAction());

        contextMenu.getItems().addAll(addSupplierMenuItem, editMenuItem, deleteMenuItem);

        // Configura el evento para mostrar el menú de contexto al hacer clic derecho
        tvSupplier.setOnContextMenuRequested(event -> {
            contextMenu.show(tvSupplier, event.getScreenX(), event.getScreenY());
        });
        
        nameColumnId.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumnId.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryColumnId.setCellValueFactory(new PropertyValueFactory<>("country"));
        zipColumnId.setCellValueFactory(new PropertyValueFactory<>("zip"));
        dateColumnId.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        stage.show();
    }

    private void handleListButtonAction() {
        try {
            ObservableList<Supplier> suppliers = FXCollections.observableArrayList(supplierManager.selectAllSuppliers());
            tvSupplier.setItems(suppliers);
        } catch (Exception e) {
            showErrorAlert("Error Listing Suppliers", "An error occurred while listing suppliers.", e.getMessage());
        }
    }

    private void handleAddButtonAction() {
        try {
            // TODO: Crear un nuevo objeto Supplier con la información necesaria
            Supplier newSupplier = new Supplier(/*...*/);

            SupplierManager supplierManager = SupplierManagerFactory.getInstance();
            supplierManager.insertSupplier(newSupplier);

            tvSupplier.getItems().add(newSupplier);

        } catch (Exception e) {
            showErrorAlert("Error Adding Supplier", "An error occurred while adding a supplier.", e.getMessage());
        }
    }

    private void handleEditButtonAction() {
        try {
            // Obtén la celda seleccionada
            TablePosition<Supplier, ?> selectedCell = tvSupplier.getSelectionModel().getSelectedCells().get(0);

            // Hacer que la celda seleccionada entre en modo de edición
            tvSupplier.edit(selectedCell.getRow(), selectedCell.getTableColumn());

        } catch (Exception e) {
            showErrorAlert("Error Editing Supplier", "An error occurred while editing a supplier.", e.getMessage());
        }
    }

    private void handleDeleteButtonAction() {
        try {
            Supplier selectedSupplier = tvSupplier.getSelectionModel().getSelectedItem();
            if (selectedSupplier != null) {
                SupplierManager supplierManager = SupplierManagerFactory.getInstance();
                supplierManager.deleteSupplier(selectedSupplier.getSupplier_id());

                tvSupplier.getItems().remove(selectedSupplier);
            } else {
                showErrorAlert("Error Deleting Supplier", "Please select a supplier to delete.", null);
            }

        } catch (Exception e) {
            showErrorAlert("Error Deleting Supplier", "An error occurred while deleting a supplier.", e.getMessage());
        }
    }

    private void handleCloseRequest(javafx.stage.WindowEvent event, Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Close");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close the application?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() != ButtonType.OK) {
            event.consume();
        } else {
            Platform.exit();
        }
    }

    private void handleLogOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Log Out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage currentStage = (Stage) menuBar.getScene().getWindow();
            // TODO: Mostrar la ventana de inicio de sesión (Log In)
        }
    }

    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Supplier Management App\nVersion 1.0\nDeveloped by Your Company");

        alert.showAndWait();
    }

    private void showUserManual() {
        // TODO: Lógica para abrir la ventana con el manual de usuario
        // Puedes abrir una nueva ventana o mostrar el manual en una ventana existente.
        // Aquí puedes utilizar FXML y otro controlador para la ventana del manual.
    }

    private void generateReport() {
        // TODO: Lógica para generar e imprimir el reporte
    }

    private void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    // Método para validar el campo "Name"
    private void validateName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty.");
        }

        if (name.length() > 255) {
            throw new ValidationException("Name cannot exceed 255 characters.");
        }
    }

    // Método para validar el campo "Phone"
    private void validatePhone(String phone) throws ValidationException {
        if (phone == null || phone.trim().isEmpty() || !phone.matches("\\+\\d{11}")) {
            throw new ValidationException("Phone must start with '+' followed by 11 numbers.");
        }
    }

    // Método para validar el campo "Country"
    private void validateCountry(String country) throws ValidationException {
        if (country == null || country.trim().isEmpty()) {
            throw new ValidationException("Country cannot be empty.");
        }

        if (country.length() > 255) {
            throw new ValidationException("Country cannot exceed 255 characters.");
        }
    }

    // Clase de excepción personalizada para la validación
    class ValidationException extends Exception {

        public ValidationException(String message) {
            super(message);
        }
    }

    // Método para manejar la edición de la columna "ZIP"
// Método para validar el campo "ZIP"
    private void validateZip(String zip) throws ValidationException {
        if (zip == null || zip.trim().isEmpty() || !zip.matches("\\d{5}")) {
            throw new ValidationException("ZIP must be a numeric value with 5 characters.");
        }
    }

    private void validateDate(LocalDate date) throws ValidationException {
        // Puedes personalizar la validación del formato de la fecha según tus necesidades
        if (date != null) {
            // Verifica si la fecha tiene el formato deseado
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = dateFormatter.format(date);

            if (!formattedDate.equals(date.toString())) {
                throw new ValidationException("Invalid date format. Please use the format dd-MM-yyyy.");
            }
        }
    }

    private void handleExitButtonAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit the application?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }

    }
}
