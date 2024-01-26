package ui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import logic.factories.SupplierManagerFactory;
import logic.interfaces.SupplierManager;
import transfer.objects.Supplier;
import ui.controls.SupplierDatePickerTableCell;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;
import util.DataGenerator;

public class SupplierManagementController extends GenericController {

    @FXML
    ContextMenu contextMenu;

    @FXML
    MenuItem miAdd;

    @FXML
    MenuItem miEdit;

    @FXML
    MenuItem miDelete;

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
    private TableColumn<Supplier, Integer> zipColumnId;

    @FXML
    private TableColumn<Supplier, Date> dateColumnId;

    @FXML
    private HBox menuBox;

    private ObservableList<Supplier> supplierList;

    private SupplierManager supplierManager;

    public void initStage(Parent root) {

        Scene scene = new Scene(root);
        stage.setScene(scene);

        supplierManager = SupplierManagerFactory.getInstance();

        btnExit.setOnAction(event -> handleExitButtonAction());

        // Agrega manejadores para los botones
        btnListSuppliers.setOnAction(event -> handleListButtonAction());
        btnAdd.setOnAction(event -> handleAddButtonAction(event));
        btnEdit.setOnAction(event -> handleEditButtonAction(event));
        btnDelete.setOnAction(event -> handleDeleteButtonAction());
       
        
        
        //btnEdit.setVisible(false);

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

        miAdd.setOnAction(event -> handleAddButtonAction(event));
        miEdit.setOnAction(event -> handleEditButtonAction(event));
        miDelete.setOnAction(event -> handleDeleteButtonAction());

        Menu.class.cast(MenuBar.class.cast(menuBox.getChildren().get(0))
                .getMenus().get(0)).getItems().get(0).setOnAction(super::handleLogOutAction);

        nameColumnId.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumnId.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryColumnId.setCellValueFactory(new PropertyValueFactory<>("country"));
        zipColumnId.setCellValueFactory(new PropertyValueFactory<>("zip"));
        //dateColumnId.setCellValueFactory(getSupplierDatePickerCellFactory());

        // Ajusta el nombre de la columna según tus necesidades
        nameColumnId.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumnId.setCellFactory(getSupplierDatePickerCellFactory());
        countryColumnId.setCellFactory(TextFieldTableCell.forTableColumn());

        phoneColumnId.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumnId.setOnEditCommit(this::handlePhoneCellEdition);

        zipColumnId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        zipColumnId.setOnEditCommit(this::handleZipCellEdition);

        nameColumnId.setOnEditCommit(this::handleNameCellEdition);
        countryColumnId.setOnEditCommit(this::handleCountryCellEdition);

        // Añadir la columna a la tabla
        // (asumiendo que tu tabla ya está creada y tiene un modelo de datos asociado)
        tvSupplier.getSelectionModel().selectedItemProperty().addListener(event -> handleSelectedItem(event));

        supplierList = FXCollections.observableArrayList();
        
        stage.show();
    }

    private void handleSelectedItem(Observable event) {
        if (event != null) {
            // Enable edit and delete buttons and menu items
            btnEdit.setDisable(false);
            btnDelete.setDisable(false);
            miEdit.setDisable(false);
            miDelete.setDisable(false);
        } else {
            // Disable edit and delete buttons and menu items
            btnEdit.setDisable(true);
            btnDelete.setDisable(true);
            miEdit.setDisable(true);
            miDelete.setDisable(true);
        }
    }

    private void handleListButtonAction() {
        try {
            supplierList.clear();
            supplierList = FXCollections.observableArrayList(supplierManager.selectAllSuppliers());
            tvSupplier.setItems(supplierList);
        } catch (Exception e) {
            showErrorAlert("Error Listing Suppliers", "An error occurred while listing suppliers.", e.getMessage());
        }
    }

  @FXML
    private void handleAddButtonAction(ActionEvent event) {
        try {
            // Log button press and add a new random supplier
            LOGGER.info("Adding a new supplier");

            // Generate a random supplier and insert it into the database
            Supplier newSupplier = DataGenerator.getRandomSupplier();
            supplierManager.insertSupplier(newSupplier);

            // Clear and update the supplier list in the TableView
            supplierList.clear();
            supplierList.addAll(supplierManager.selectAllSuppliers());
            tvSupplier.setItems(supplierList);
        } catch (Exception e) {
            // Log and show an error alert if an exception occurs
            LOGGER.severe("Error adding a supplier");
            showErrorAlert("ERROR", "Error adding a new supplier", e.getMessage());
        } finally {
            tvSupplier.refresh();
        }
    }


    @FXML
    private void handleEditButtonAction(ActionEvent event) {
        try {
            // Log button press and enter the selected cell into edit mode
            LOGGER.info("Editing Supplier");

            // Get the selected cell and enter edit mode
            TablePosition<Supplier, ?> selectedCell = tvSupplier.getSelectionModel().getSelectedCells().get(0);
            tvSupplier.edit(selectedCell.getRow(), selectedCell.getTableColumn());
        } catch (Exception e) {
            // Show an error alert if an exception occurs during editing
            showErrorAlert("Error Editing Supplier", "An error occurred while editing a Supplier.", e.getMessage());
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

    @FXML
    private void handleNameCellEdition(CellEditEvent<Supplier, String> event) {
        try {
            String newValue = event.getNewValue();

            // Check conditions
            if (newValue == null || newValue.trim().isEmpty() || newValue.length() > 255) {
                throw new Exception("The cell is empty or exceeds 255 characters.");
            }

            // Update the product's other information and call the logic method to update the product
            Supplier s = event.getRowValue();
            s.setName(newValue);
            supplierManager.updateSupplier(s);

            // Set the cell value to the new value entered if successful
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setName(event.getNewValue());
        } catch (Exception e) {
            // Catch the exception and show an error alert
            showErrorAlert("Error", "An error occurred while editing the cell", e.getMessage());

            // Set the cell value back to the initial value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setName(event.getOldValue());
        } finally {
            tvSupplier.refresh();
        }
    }

    @FXML
    private void handleCountryCellEdition(CellEditEvent<Supplier, String> event) {
        try {
            String newValue = event.getNewValue();

            // Check conditions
            if (newValue == null || newValue.trim().isEmpty() || newValue.length() > 255) {
                throw new Exception("The cell is empty or exceeds 255 characters.");
            }

            // Update the product's other information and call the logic method to update the product
            Supplier s = event.getRowValue();
            s.setCountry(newValue);
            supplierManager.updateSupplier(s);

            // Set the cell value to the new value entered if successful
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setCountry(event.getNewValue());
        } catch (Exception e) {
            // Catch the exception and show an error alert
            showErrorAlert("Error", "An error occurred while editing the cell", e.getMessage());

            // Set the cell value back to the initial value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setCountry(event.getOldValue());
        } finally {
            tvSupplier.refresh();
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

    // Método para validar el campo "Name"
    private void validateName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty.");
        }

        if (name.length() > 255) {
            throw new ValidationException("Name cannot exceed 255 characters.");
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

    @FXML
    private void handleZipCellEdition(CellEditEvent<Supplier, Integer> event) {
        try {
            Integer newValue = event.getNewValue();

            // Validar el nuevo valor según el formato requerido
            validateZip(newValue);

            // Obtener la instancia de Supplier asociada a la celda
            Supplier supplier = event.getRowValue();

            // Actualizar el valor de la propiedad en el objeto Supplier
            supplier.setZip(newValue);

            // Llamar al método lógico para actualizar el Supplier en la base de datos
            supplierManager.updateSupplier(supplier);

            // Refrescar la tabla para que muestre el nuevo valor
            tvSupplier.refresh();
        } catch (Exception e) {
            // Si hay un error de validación, mostrar una alerta y no aceptar el cambio
            showErrorAlert("Error", "Invalid ZIP format", e.getMessage());
            event.consume();
        }
    }

// Método para validar el campo "ZIP"
    private void validateZip(Integer zip) throws ValidationException {
        if (zip == null || zip.toString().length() != 5) {
            throw new ValidationException("ZIP must be a numeric value with 5 digits.");
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

    @FXML
    private void handlePhoneCellEdition(CellEditEvent<Supplier, String> event) {
        try {
            String newValue = event.getNewValue();

            validatePhone(newValue);

            Supplier supplier = event.getRowValue();

            supplier.setPhone(newValue);

            supplierManager.updateSupplier(supplier);

            tvSupplier.refresh();
        } catch (Exception e) {
            showErrorAlert("Error", "Invalid phone format", e.getMessage());
            event.consume();
        }
    }

    // Método para validar el campo "Phone"
    private void validatePhone(String phone) throws ValidationException {
        if (phone == null || phone.trim().isEmpty() || !phone.matches("\\+\\d{11}")) {
            throw new ValidationException("Phone must start with '+' followed by 11 numbers.");
        }
    }

    private Callback<TableColumn<Supplier, Date>, TableCell<Supplier, Date>> getSupplierDatePickerCellFactory() {
        return (TableColumn<Supplier, Date> param) -> new SupplierDatePickerTableCell();
    }
}
