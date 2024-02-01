/**
 * Controller class for Supplier Management in a JavaFX application.
 * This class manages the UI components and interactions related to supplier management.
 * Author: Alex Irusta
 */
package ui.controller;

import app.App;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import util.DataGenerator;

/**
 * Controller class for managing suppliers. Extends GenericController.
 */
public class SupplierManagementController extends GenericController {

    // FXML elements
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
    private TableColumn<Supplier, LocalDate> dateColumnId;
    @FXML
    private HBox menuBox;

    // Data related to suppliers
    private ObservableList<Supplier> supplierList;
    private SupplierManager supplierManager;

    /**
     * Initializes the stage with UI components.
     *
     * @param root The root of the UI scene.
     */
    public void initStage(Parent root) {
        try {
            // Set up the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.setTitle("Supplier Management");
            stage.setResizable(false);

            // Initialize supplier manager
            supplierManager = SupplierManagerFactory.getInstance();

            // Set up button actions
            btnExit.setOnAction(event -> handleExitButtonAction());
            btnListSuppliers.setOnAction(event -> handleListButtonAction());
            btnAdd.setOnAction(event -> handleAddButtonAction(event));
            btnEdit.setOnAction(event -> handleEditButtonAction(event));
            btnDelete.setOnAction(event -> handleDeleteButtonAction());

            // Set initial visibility for the edit button
            btnEdit.setVisible(false);

            // Set up close request event
            stage.setOnCloseRequest(event -> handleCloseRequest(event, stage));

            // Enable/disable buttons based on selection in the TableView
            tvSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    btnEdit.setDisable(false);
                    btnDelete.setDisable(false);
                } else {
                    btnEdit.setDisable(true);
                    btnDelete.setDisable(true);
                }
            });

            // Set up context menu actions
            miAdd.setOnAction(event -> handleAddButtonAction(event));
            miEdit.setOnAction(event -> handleEditButtonAction(event));
            miDelete.setOnAction(event -> handleDeleteButtonAction());

            // Set up menu action
            Menu.class.cast(MenuBar.class.cast(menuBox.getChildren().get(0)).getMenus().get(0))
                    .getItems().get(0).setOnAction(super::handleLogOutAction);
            Menu.class.cast(MenuBar.class.cast(menuBox.getChildren().get(0))
                    .getMenus().get(1)).getItems().get(0).setOnAction(this::showAboutDialog);
            Menu.class.cast(MenuBar.class.cast(menuBox.getChildren().get(0))
                    .getMenus().get(2)).getItems().get(0).setOnAction(this::handlePrintReportAction);

            // Set up TableView columns
            nameColumnId.setCellValueFactory(new PropertyValueFactory<>("name"));
            phoneColumnId.setCellValueFactory(new PropertyValueFactory<>("phone"));
            countryColumnId.setCellValueFactory(new PropertyValueFactory<>("country"));
            zipColumnId.setCellValueFactory(new PropertyValueFactory<>("zip"));
            dateColumnId.setCellValueFactory(factory -> getDateToLocalDateValueFactory(factory));

            // Set cell factories for editable columns
            nameColumnId.setCellFactory(TextFieldTableCell.forTableColumn());
            dateColumnId.setCellFactory(getSupplierDatePickerCellFactory());
            countryColumnId.setCellFactory(TextFieldTableCell.forTableColumn());
            dateColumnId.setCellFactory(getSupplierDatePickerCellFactory());
            phoneColumnId.setCellFactory(TextFieldTableCell.forTableColumn());
            zipColumnId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

            zipColumnId.setOnEditCommit(this::handleZipCellEdition);
            nameColumnId.setOnEditCommit(this::handleNameCellEdition);
            countryColumnId.setOnEditCommit(this::handleCountryCellEdition);
            dateColumnId.setOnEditCommit(this::handleAdditionDateCellEdition);
            phoneColumnId.setOnEditCommit(this::handlePhoneCellEdition);

            // Set up initial menu item for product management
            MenuItem mitProductManagement = new MenuItem();
            mitProductManagement.setText("Product Management");
            mitProductManagement.setMnemonicParsing(false);
            Menu.class.cast(MenuBar.class.cast(menuBox.getChildren().get(0)).getMenus().get(0)).getItems().add(mitProductManagement);
            mitProductManagement.setOnAction(event -> handleProductMenuItemAction(event));

            // Add a listener to handle selected item changes
            tvSupplier.getSelectionModel().selectedItemProperty().addListener(event -> handleSelectedItem(event));

            // Initialize supplier list and show the stage
            supplierList = FXCollections.observableArrayList();
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            showErrorAlert("ERROR", "An error occurred while initializing the Supplier Managament window", e.getMessage());
        }
    }

    /**
     * Launches the product window by loading the associated FXML file and
     * initializing the corresponding controller.
     */
    private void launchProductWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/product_view.fxml"));
            Parent root = (Parent) loader.load();
            ProductViewController.class.cast(loader.getController()).setStage(stage);
            ProductViewController.class.cast(loader.getController()).initStage(root);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the action when the product menu item is selected. It prompts the
     * user with a confirmation dialog and opens the product window if the user
     * chooses to proceed.
     *
     * @param event The ActionEvent triggered by selecting the product menu
     * item.
     */
    private void handleProductMenuItemAction(ActionEvent event) {
        if (showConfirmationDialog("Do you want to open the Supplier Management window?")) {
            launchProductWindow();
        }
    }

    /**
     * Handles the selection of an item in the TableView. It enables or disables
     * various buttons and menu items based on whether an item is selected or
     * not.
     *
     * @param event The Observable representing the selection event.
     */
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

    /**
     * Handles the action when the "List Suppliers" button is pressed. Retrieves
     * the list of suppliers and updates the TableView.
     */
    private void handleListButtonAction() {
        try {
            supplierList.clear();
            supplierList = FXCollections.observableArrayList(supplierManager.selectAllSuppliers());
            tvSupplier.setItems(supplierList);
        } catch (Exception e) {
            showErrorAlert("Error Listing Suppliers", "An error occurred while listing suppliers.", e.getMessage());
        }
    }

    /**
     * Handles the action when the "Add" button is pressed. Logs the button
     * press, generates a new random supplier, inserts it into the database, and
     * updates the TableView with the latest supplier list. Displays an error
     * alert if an exception occurs during the process.
     *
     * @param event The ActionEvent triggered by pressing the "Add" button.
     */
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

    /**
     * Handles the action when the "Edit" button is pressed. Logs the button
     * press, gets the selected cell in the TableView, and enters edit mode for
     * the selected cell. Displays an error alert if an exception occurs during
     * the process.
     *
     * @param event The ActionEvent triggered by pressing the "Edit" button.
     */
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

    /**
     * Handles the action when the "Delete" button is pressed. Deletes the
     * selected supplier from the database and removes it from the TableView.
     * Displays an error alert if an exception occurs during the process or if
     * no supplier is selected.
     */
    private void handleDeleteButtonAction() {
        try {
            Supplier selectedSupplier = tvSupplier.getSelectionModel().getSelectedItem();
            if (selectedSupplier != null) {
                if (showConfirmationDialog("Are you sure you want to delete " + selectedSupplier.toString())) {
                    SupplierManager supplierManager = SupplierManagerFactory.getInstance();
                    supplierManager.deleteSupplier(selectedSupplier.getSupplier_id());

                    tvSupplier.getItems().remove(selectedSupplier);
                }
            } else {
                showErrorAlert("Error Deleting Supplier", "Please select a supplier to delete.", null);
            }

        } catch (Exception e) {
            showErrorAlert("Error Deleting Supplier", "An error occurred while deleting a supplier.", e.getMessage());
        }
    }

    /**
     * Handles the close request for the application. Displays a confirmation
     * dialog asking the user if they are sure they want to close the
     * application. If the user confirms (presses OK), the application is
     * exited; otherwise, the close event is consumed, preventing the
     * application from closing.
     *
     * @param event The WindowEvent representing the close request.
     * @param stage The main Stage of the application.
     */
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

    /**
     * Handles the cell edit event for the "Name" column in the TableView. Edits
     * the name information of the selected supplier and updates the database.
     *
     * @param event The CellEditEvent object for the event.
     */
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

    /**
     * Handles the cell edit event for the "Country" column in the TableView.
     * Edits the country information of the selected supplier and updates the
     * database.
     *
     * @param event The CellEditEvent object for the event.
     */
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

    /**
     * Displays an "About" dialog providing information about the Supplier
     * Management App, including the version and the development details.
     */
    private void showAboutDialog(ActionEvent event) {
        Alert aboutAlert = new Alert(AlertType.INFORMATION);
        aboutAlert.setTitle("About");
        aboutAlert.setHeaderText("Supplier Management System");
        aboutAlert.setContentText("This application is designed for managing suppliers efficiently.\nVersion: 1.0.0\nAuthor: Alex Irusta");

        aboutAlert.showAndWait();
    }

    /**
     * Validates the specified "Name" field, throwing a ValidationException if
     * it is empty or exceeds the maximum allowed length (255 characters).
     *
     * @param name The name to be validated.
     * @throws ValidationException If the name is empty or exceeds 255
     * characters.
     */
    private void validateName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty.");
        }

        if (name.length() > 255) {
            throw new ValidationException("Name cannot exceed 255 characters.");
        }
    }

    /**
     * Validates the specified "Country" field, throwing a ValidationException
     * if it is empty or exceeds the maximum allowed length (255 characters).
     *
     * @param country The country to be validated.
     * @throws ValidationException If the country is empty or exceeds 255
     * characters.
     */
    private void validateCountry(String country) throws ValidationException {
        if (country == null || country.trim().isEmpty()) {
            throw new ValidationException("Country cannot be empty.");
        }

        if (country.length() > 255) {
            throw new ValidationException("Country cannot exceed 255 characters.");
        }
    }

    /**
     * Custom exception class for validation errors. Represents exceptional
     * cases where validation constraints are not met.
     */
    class ValidationException extends Exception {

        public ValidationException(String message) {
            super(message);
        }
    }

    /**
     * Handles the cell edit event for the "Zip" column in the TableView. Edits
     * the zip information of the selected supplier and updates the database.
     *
     * @param event The CellEditEvent object for the event.
     */
    @FXML
    private void handleZipCellEdition(CellEditEvent<Supplier, Integer> event) {
        try {
            Integer newValue = event.getNewValue();

            if (newValue == null) {
                throw new Exception("The value must be numeric");
            }

            // Validate the new value according to the required format
            validateZip(newValue);

            // Get the Supplier instance associated with the cell
            Supplier supplier = event.getRowValue();

            // Update the value of the property in the Supplier object
            supplier.setZip(newValue);

            // Call the logical method to update the Supplier in the database
            supplierManager.updateSupplier(supplier);

            // Refresh the table to display the new value
        } catch (Exception e) {
            // If there is a validation error, show an alert and do not accept the change
            showErrorAlert("Error", "Invalid ZIP format", e.getMessage());
            event.consume();
        } finally {
            tvSupplier.refresh();
        }
    }

    /**
     * Validates the specified "ZIP" field, throwing a ValidationException if it
     * is not a numeric value with exactly 5 digits.
     *
     * @param zip The ZIP code to be validated.
     * @throws ValidationException If the ZIP code is not a numeric value with 5
     * digits.
     */
    private void validateZip(Integer zip) throws ValidationException {
        if (zip == null || zip.toString().length() != 5) {
            throw new ValidationException("ZIP must be a numeric value with 5 digits.");
        }

    }

    /**
     * Validates the specified date, ensuring it adheres to the desired format.
     * Customizable based on specific date format requirements.
     *
     * @param date The date to be validated.
     * @throws ValidationException If the date format is invalid.
     */
    private void validateDate(LocalDate date) throws ValidationException {
        // You can customize the validation of the date format based on your needs
        if (date != null) {
            // Check if the date has the desired format
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = dateFormatter.format(date);

            if (!formattedDate.equals(date.toString())) {
                throw new ValidationException("Invalid date format. Please use the format dd-MM-yyyy.");
            }
        }
    }

    /**
     * Handles the action when the exit button is pressed. Displays a
     * confirmation dialog to confirm the user's intention to exit the
     * application. If the user confirms (presses OK), the application is
     * exited.
     */
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

    /**
     * Handles the cell edit event for the "Addition Date" column in the
     * TableView. Edits the addition date information of the selected supplier
     * and updates the database.
     *
     * @param event The CellEditEvent object for the event.
     */
    @FXML
    private void handleAdditionDateCellEdition(CellEditEvent<Supplier, LocalDate> event) {
        try {
            Supplier editedSupplier = event.getRowValue();
            editedSupplier.setCreateTimestamp(Date.from(event.getNewValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            supplierManager.updateSupplier(editedSupplier);

            // Set the value of the cell with the new value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setCreateTimestamp(Date.from(event.getNewValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } catch (Exception e) {
            showErrorAlert("Error", "An error occurred while editing the cell", e.getMessage());

            // Set the value of the cell to the initial value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setCreateTimestamp(Date.from(event.getNewValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } finally {
            tvSupplier.refresh();
        }
    }

    /**
     * Handles the cell edit event for the "Phone" column in the TableView.
     * Edits the phone information of the selected supplier and updates the
     * database.
     *
     * @param event The CellEditEvent object for the event.
     */
    @FXML
    private void handlePhoneCellEdition(CellEditEvent<Supplier, String> event) {
        try {
            String newValue = event.getNewValue();

            // Validate the new phone value
            validatePhone(newValue);

            Supplier supplier = event.getRowValue();

            // Update the phone value in the Supplier object
            supplier.setPhone(newValue);

            // Update the Supplier in the database
            supplierManager.updateSupplier(supplier);

        } catch (Exception e) {
            // If there is a validation error, show an alert and do not accept the change
            showErrorAlert("Error", "Invalid phone format", e.getMessage());
            event.consume();
        } finally {
            tvSupplier.refresh();
        }
    }

    /**
     * Validates the specified "Phone" field, throwing a ValidationException if
     * it is empty, contains only whitespace, or does not match the required
     * format (must start with '+' followed by 11 numbers).
     *
     * @param phone The phone number to be validated.
     * @throws ValidationException If the phone number is invalid.
     */
// Método para validar el campo "Phone"
    private void validatePhone(String phone) throws ValidationException {
        if (phone == null || phone.trim().isEmpty() || !phone.matches("\\+\\d{11}")) {
            throw new ValidationException("Phone must start with '+' followed by 11 numbers.");
        }
    }

    /**
     * Retrieves a Callback for creating DatePicker cells within a TableColumn
     * for the "Creation Date" column. Used for specifying the appearance and
     * behavior of cells in this column.
     *
     * @return A Callback for creating DatePicker cells.
     */
    private Callback<TableColumn<Supplier, LocalDate>, TableCell<Supplier, LocalDate>> getSupplierDatePickerCellFactory() {
        return (TableColumn<Supplier, LocalDate> param) -> new SupplierDatePickerTableCell();
    }

    /**
     * Retrieves an ObservableValue for the "Creation Date" column's cell data.
     * Converts the creation timestamp from the Supplier into a LocalDate value
     * for display in the TableView.
     *
     * @param factory The CellDataFeatures containing information about the
     * cell.
     * @return An ObservableValue representing the LocalDate value for the cell.
     */
    private ObservableValue<LocalDate> getDateToLocalDateValueFactory(TableColumn.CellDataFeatures<Supplier, LocalDate> factory) {
        return new SimpleObjectProperty<LocalDate>(factory.getValue().getCreateTimestamp()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    private void handlePrintReportAction(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getClassLoader().getResourceAsStream("ui/report/SupplierReport.jrxml"));
            JRBeanCollectionDataSource items = new JRBeanCollectionDataSource((Collection<Supplier>) tvSupplier.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, items);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ProductViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
