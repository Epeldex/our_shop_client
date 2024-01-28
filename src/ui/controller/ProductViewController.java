/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import app.App;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import ui.controls.EditableComboBoxTableCell;
import ui.controls.ProductDatePickerTableCell;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import logic.exceptions.LogicException;
import logic.factories.ProductManagerFactory;
import logic.factories.SupplierManagerFactory;
import logic.factories.TagManagerFactory;
import logic.interfaces.ProductManager;
import transfer.objects.Product;
import transfer.objects.Supplier;
import transfer.objects.Tag;
import util.DataGenerator;

/**
 * Controller class for managing products in the view. It contains event
 * handlers and initialization code for the view defined in ProductView.fxml
 * file. Author: alexa
 */
public class ProductViewController extends GenericController {

    // Logger for logging messages
    private static final Logger LOGGER = Logger.getLogger("ProductViewController");

    // Reference to the ProductManager for managing products
    private static ProductManager productManager;

    /**
     * Label for displaying information about product management.
     */
    @FXML
    private Label labelProductManagement;

    /**
     * Button for listing products.
     */
    @FXML
    private Button btnListProducts;

    /**
     * Button for adding a new product.
     */
    @FXML
    private Button btnAdd;

    /**
     * Button for deleting a product.
     */
    @FXML
    private Button btnDelete;

    /**
     * Button for editing a product.
     */
    @FXML
    private Button btnEdit;

    /**
     * Button for exiting the application.
     */
    @FXML
    private Button btnExit;

    /**
     * TableView for displaying a list of products.
     */
    @FXML
    private TableView<Product> tvProduct;

    /**
     * TableColumn for product number in the TableView.
     */
    @FXML
    private TableColumn<Product, String> cProductNumber;

    /**
     * TableColumn for brand in the TableView.
     */
    @FXML
    private TableColumn<Product, String> cBrand;

    /**
     * TableColumn for model in the TableView.
     */
    @FXML
    private TableColumn<Product, String> cModel;

    /**
     * TableColumn for weight in the TableView.
     */
    @FXML
    private TableColumn<Product, Double> cWeight;

    /**
     * TableColumn for price in the TableView.
     */
    @FXML
    private TableColumn<Product, Double> cPrice;

    /**
     * TableColumn for product description in the TableView.
     */
    @FXML
    private TableColumn<Product, String> cDescription;

    /**
     * TableColumn for additional product information in the TableView.
     */
    @FXML
    private TableColumn<Product, String> cOtherInfo;

    /**
     * TableColumn for the date of product addition in the TableView.
     */
    @FXML
    private TableColumn<Product, LocalDate> cAdditionDate;

    /**
     * TableColumn for supplier information in the TableView.
     */
    @FXML
    private TableColumn<Product, Supplier> cSupplier;

    /**
     * TableColumn for product tags in the TableView.
     */
    @FXML
    private TableColumn<Product, Tag> cTag;

    /**
     * ContextMenu for providing additional options in the TableView.
     */
    @FXML
    private ContextMenu contextMenu;

    /**
     * MenuItem for adding a new product in the TableView.
     */
    @FXML
    private MenuItem miAdd;

    /**
     * MenuItem for editing a product in the TableView.
     */
    @FXML
    private MenuItem miEdit;

    /**
     * MenuItem for deleting a product in the TableView.
     */
    @FXML
    private MenuItem miDelete;

    /**
     * HBox for holding menu components.
     */
    @FXML
    private HBox menuBox;

    /**
     * ObservableList to hold products for the TableView.
     */
    private ObservableList<Product> productList;

    /**
     * List to hold suppliers for product management.
     */
    private List<Supplier> supplierList;

    /**
     * Initializes the stage for product management, setting up UI components,
     * event handlers, and loading necessary data.
     *
     * @param root The Parent object representing the root node of the view
     * graph.
     * @throws Exception If an error occurs during initialization.
     */
    public void initStage(Parent root) throws Exception {
        // Log initialization message
        LOGGER.info("Initializing window");

        // Create a scene and set it on the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Configure stage properties
        stage.setTitle("Product Management");
        stage.setResizable(false);

        // Configure date addition pattern based on user's system language
        // Obtain the business logic implementation object
        productManager = ProductManagerFactory.getInstance();
        supplierList = SupplierManagerFactory.getInstance().selectAllSuppliers();
        ObservableList<Tag> tagList = FXCollections.observableArrayList(TagManagerFactory.getInstance().selectAllTags());

        // Configure table columns
        cProductNumber.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
        cBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        cModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        cWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        cPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        cDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        cOtherInfo.setCellValueFactory(new PropertyValueFactory<>("otherInfo"));
        cAdditionDate.setCellValueFactory(factory -> {
            return getDateToLocalDateValueFactory(factory);
        });
        cSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        cTag.setCellValueFactory(new PropertyValueFactory<>("tag"));

        // Configure cell factories for editable columns
        cBrand.setCellFactory(TextFieldTableCell.forTableColumn());
        cModel.setCellFactory(TextFieldTableCell.forTableColumn());
        cWeight.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        cPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        cDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        cOtherInfo.setCellFactory(TextFieldTableCell.forTableColumn());
        cSupplier.setCellFactory(getEditableComboBoxCellFactory());
        cAdditionDate.setCellFactory(getDatePickerCellFactory());
        cTag.setCellFactory(ComboBoxTableCell.forTableColumn(tagList));

        // Configure selection listener for the TableView
        tvProduct.getSelectionModel().selectedItemProperty().addListener(event -> handleSelectedItem(event));
        // Initialize product list and set it to the TableView
        productList = FXCollections.observableArrayList();
        tvProduct.setItems(productList);

        //Add a menu item to the actions Menu
        MenuItem mitSupplierManagement = new MenuItem();
        mitSupplierManagement.setText("Supplier Management");
        mitSupplierManagement.setMnemonicParsing(false);
        Menu.class.cast(MenuBar.class.cast(menuBox.getChildren().get(0))
                .getMenus().get(0)).getItems().add(mitSupplierManagement);

        // Set actions for the menu bar's menus' items
        Menu.class.cast(MenuBar.class.cast(menuBox.getChildren().get(0))
                .getMenus().get(0)).getItems().get(0).setOnAction(super::handleLogOutAction); //Logout menu item
        Menu.class.cast(MenuBar.class.cast(menuBox.getChildren().get(0))
                .getMenus().get(0)).getItems().get(1).setOnAction(this::handleSupplierMenuItemAction); //Supplier Management menu item
        Menu.class.cast(MenuBar.class.cast(menuBox.getChildren().get(0))
                .getMenus().get(1)).getItems().get(0).setOnAction(this::handleAboutAction); //About menu item
        Menu.class.cast(MenuBar.class.cast(menuBox.getChildren().get(0))
                .getMenus().get(1)).getItems().get(1).setOnAction(this::handleHelpAction); //Help menu item, user manual
        Menu.class.cast(MenuBar.class.cast(menuBox.getChildren().get(0))
                .getMenus().get(2)).getItems().get(0).setOnAction(this::handlePrintReportAction); // Print report menu item, prints report

        btnEdit.setVisible(false);
        miEdit.setVisible(false);
        // Set close request handler for the stage
        stage.setOnCloseRequest(super::handleCloseRequest);
        stage.centerOnScreen();
        // Show the stage
        stage.show();
    }

    /**
     * Handles the selection of an item in the TableView, enabling or disabling
     * corresponding buttons and menu items based on the selection.
     *
     * @param event The Observable event representing the item selection.
     */
    private void handleSelectedItem(Observable event) {
        if (tvProduct.getSelectionModel().getSelectedItem() != null) {
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
     * Handles the action event when the "List Products" button is pressed.
     * Retrieves the list of products and updates the TableView with the data.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleListProducts(ActionEvent event) {
        try {
            // Log button press and retrieve products
            LOGGER.info("List products button pressed, retrieving products");

            // Clear and update the product list in the TableView
            productList.clear();
            productList.addAll(productManager.selectAllProducts());
            tvProduct.setItems(productList);
        } catch (LogicException ex) {
            // Log and show an error alert if an exception occurs
            LOGGER.severe("Error adding products to the table");
            showErrorAlert("ERROR", "Error listing products", ex.getMessage());
        } finally {
            tvProduct.getSelectionModel().clearSelection();
            tvProduct.refresh();
        }
    }

    /**
     * Handles the action event when the "Exit" button is pressed. Shows the
     * exit confirmation dialog.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleExitButton(ActionEvent event) {
        // Log button press and show exit dialog
        LOGGER.info("Exit Button pressed");
        showExitDialog();
    }

    /**
     * Handles the action event when the "Add Product" button is pressed. Adds a
     * new random product to the database and updates the TableView.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleAddProduct(ActionEvent event) {
        try {
            // Log button press and add a new random product
            LOGGER.info("Adding new product");

            // Generate a random product and insert it into the database
            Product newProduct = DataGenerator.getRandomProduct();
            productManager.insertProduct(newProduct);

            // Clear and update the product list in the TableView
            productList.clear();
            productList.addAll(productManager.selectAllProducts());
            tvProduct.setItems(productList);
        } catch (Exception e) {
            // Log and show an error alert if an exception occurs
            LOGGER.severe("Error adding a product");
            showErrorAlert("ERROR", "Error adding a new product", e.getMessage());
        }
    }

    /**
     * Handles the action event when the "Edit Product" button is pressed.
     * Enters the selected cell into edit mode.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleEditProduct(ActionEvent event) {
        try {
            // Log button press and enter the selected cell into edit mode
            LOGGER.info("Editing product");

            // Get the selected cell and enter edit mode
            TablePosition<Product, ?> selectedCell = tvProduct.getSelectionModel().getSelectedCells().get(0);
            tvProduct.edit(selectedCell.getRow(), selectedCell.getTableColumn());
        } catch (Exception e) {
            // Show an error alert if an exception occurs during editing
            showErrorAlert("Error Editing Product", "An error occurred while editing a Product.", e.getMessage());
        } finally {
            tvProduct.refresh();
        }
    }

    /**
     * Handles the action event when the "Delete Product" button is pressed.
     * Deletes the selected product from the database and updates the TableView.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleDeleteProduct(ActionEvent event) {
        try {
            // Log button press and delete the selected product
            LOGGER.info("Deleting product");

            // Get the selected product and prompt for confirmation
            Product selectedProduct = tvProduct.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                if (showConfirmationDialog("Are you sure you want to delete " + selectedProduct.toString())) {
                    // Delete the product and update the TableView
                    productManager.deleteProduct(selectedProduct.getProduct_id());
                    tvProduct.getItems().remove(selectedProduct);
                }
            }
        } catch (Exception e) {
            // Show an error alert if an exception occurs during deletion
            showErrorAlert("Error Deleting Product", "An error occurred while deleting a Product.", e.getMessage());
        } finally {
            tvProduct.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void handleSupplierCellEdition(CellEditEvent<Product, Supplier> event) {
        try {
            Supplier supplier = event.getNewValue();
            //Check if what the user has introduced is either empty or not an available supplier
            //see EditableComboBoxTableCell fromString() implementation
            if (supplier.isEmpty()) {
                if (showConfirmationDialog("The supplier you have selected does not exist, do you want to add a new one?")) {
                    launchSupplierWindow();
                }
            } else {
                Product editedProduct = event.getRowValue();
                editedProduct.setSupplier(supplier);
                productManager.updateProduct(editedProduct);

                event.getTableView().getItems().get(event.getTablePosition().getRow())
                        .setSupplier(event.getNewValue());
            }
        } catch (Exception e) {
            showErrorAlert("Error", "Ocurrió un error al editar la celda", e.getMessage());
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setSupplier(event.getOldValue());
        } finally {
            tvProduct.refresh();
        }
    }

    /**
     * Handles the cell edit event for the "Brand" column in the TableView.
     * Edits the brand information of the selected product and updates the
     * database.
     *
     * @param event The CellEditEvent object for the event.
     */
    @FXML
    private void handleBrandCellEdition(CellEditEvent<Product, String> event) {
        try {
            String newValue = event.getNewValue();

            // Check conditions
            if (newValue == null || newValue.trim().isEmpty() || newValue.length() > 255) {
                throw new Exception("The cell is empty or exceeds 255 characters.");
            }

            // Update the product's brand and call the logic method to update the product
            Product p = event.getRowValue();
            p.setBrand(newValue);
            productManager.updateProduct(p);

            // Set the cell value to the new value entered if successful
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setBrand(event.getNewValue());
        } catch (Exception e) {
            // Catch the exception and show an error alert
            showErrorAlert("Error", "An error occurred while editing the cell", e.getMessage());

            // Set the cell value back to the initial value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setBrand(event.getOldValue());
        } finally {
            tvProduct.refresh();
        }
    }

    /**
     * Handles the cell edit event for the "Model" column in the TableView.
     * Edits the model information of the selected product and updates the
     * database.
     *
     * @param event The CellEditEvent object for the event.
     */
    @FXML
    private void handleModelCellEdition(CellEditEvent<Product, String> event) {
        try {
            String newValue = event.getNewValue();

            // Check conditions
            if (newValue == null || newValue.trim().isEmpty() || newValue.length() > 255) {
                throw new Exception("The cell is empty or exceeds 255 characters.");
            }

            // Update the product's model and call the logic method to update the product
            Product p = event.getRowValue();
            p.setModel(newValue);
            productManager.updateProduct(p);

            // Set the cell value to the new value entered if successful
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setModel(event.getNewValue());
        } catch (Exception e) {
            // Catch the exception and show an error alert
            showErrorAlert("Error", "An error occurred while editing the cell", e.getMessage());

            // Set the cell value back to the initial value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setModel(event.getOldValue());
        } finally {
            tvProduct.refresh();
        }
    }

    /**
     * Handles the cell edit event for the "Description" column in the
     * TableView. Edits the description information of the selected product and
     * updates the database.
     *
     * @param event The CellEditEvent object for the event.
     */
    @FXML
    private void handleDescriptionCellEdition(CellEditEvent<Product, String> event) {
        try {
            String newValue = event.getNewValue();

            // Check conditions
            if (newValue == null || newValue.trim().isEmpty() || newValue.length() > 255) {
                throw new Exception("The cell is empty or exceeds 255 characters.");
            }

            // Update the product's description and call the logic method to update the product
            Product p = event.getRowValue();
            p.setDescription(newValue);
            productManager.updateProduct(p);

            // Set the cell value to the new value entered if successful
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setDescription(event.getNewValue());
        } catch (Exception e) {
            // Catch the exception and show an error alert
            showErrorAlert("Error", "An error occurred while editing the cell", e.getMessage());

            // Set the cell value back to the initial value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setDescription(event.getOldValue());
        } finally {
            tvProduct.refresh();
        }
    }

    /**
     * Handles the cell edit event for the "Other Info" column in the TableView.
     * Edits the other information of the selected product and updates the
     * database.
     *
     * @param event The CellEditEvent object for the event.
     */
    @FXML
    private void handleOtherInfoCellEdition(CellEditEvent<Product, String> event) {
        try {
            String newValue = event.getNewValue();

            // Check conditions
            if (newValue == null || newValue.trim().isEmpty() || newValue.length() > 255) {
                throw new Exception("The cell is empty or exceeds 255 characters.");
            }

            // Update the product's other information and call the logic method to update the product
            Product p = event.getRowValue();
            p.setOtherInfo(newValue);
            productManager.updateProduct(p);

            // Set the cell value to the new value entered if successful
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setOtherInfo(event.getNewValue());
        } catch (Exception e) {
            // Catch the exception and show an error alert
            showErrorAlert("Error", "An error occurred while editing the cell", e.getMessage());

            // Set the cell value back to the initial value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setOtherInfo(event.getOldValue());
        } finally {
            tvProduct.refresh();
        }
    }

    /**
     * Handles the cell edit event for the "Weight" column in the TableView.
     * Edits the weight information of the selected product and updates the
     * database.
     *
     * @param event The CellEditEvent object for the event.
     */
    @FXML
    private void handleWeightCellEdition(CellEditEvent<Product, Double> event) {
        try {
            Double newValue = event.getNewValue();

            // Check if the content is numeric (see DoubleStringConverter fromString() implementation)
            if (newValue == null) {
                throw new Exception("The value must be numeric");
            }

            // Check if the content is empty
            if (newValue == 0D) {
                event.getTableView().getItems().get(event.getTablePosition().getRow())
                        .setWeight(event.getNewValue());
            }

            // Check if the value is negative
            if (newValue < 0D) {
                throw new Exception("The weight cannot be negative");
            }

            // Check if the value exceeds the supported range
            if (newValue > Double.MAX_VALUE) {
                throw new Exception("The entered value is too high, reduce it");
            }

            // Update the product with the new weight
            Product editedProduct = event.getRowValue();
            editedProduct.setWeight(newValue);
            productManager.updateProduct(editedProduct);

            // Set the value of the cell with the new value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setWeight(newValue);
        } catch (Exception e) {
            // Catch the exception and show an alert message
            showErrorAlert("Error", "An error occurred while editing the cell", e.getMessage());

            // Set the value of the cell to the initial value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setWeight(event.getOldValue());
        } finally {
            tvProduct.refresh();
        }
    }

    /**
     * Handles the cell edit event for the "Price" column in the TableView.
     * Edits the price information of the selected product and updates the
     * database.
     *
     * @param event The CellEditEvent object for the event.
     */
    @FXML
    private void handlePriceCellEdition(CellEditEvent<Product, Double> event) {
        try {
            Double newValue = event.getNewValue();

            // Check if the content is numeric (see DoubleStringConverter fromString() implementation)
            if (newValue == null) {
                throw new Exception("The value must be numeric");
            }

            // Check if the content is empty
            if (newValue == 0D) {
                event.getTableView().getItems().get(event.getTablePosition().getRow())
                        .setPrice(event.getNewValue());
            }

            // Check if the value is negative
            if (newValue < 0D) {
                throw new Exception("The price cannot be negative");
            }

            // Check if the value exceeds the supported range
            if (newValue > Double.MAX_VALUE) {
                throw new Exception("The entered value is too high, reduce it");
            }

            // Update the product with the new price
            Product editedProduct = event.getRowValue();
            editedProduct.setPrice(newValue);
            productManager.updateProduct(editedProduct);

            // Set the value of the cell with the new value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setPrice(newValue);
        } catch (Exception e) {
            // Catch the exception and show an alert message
            showErrorAlert("Error", "An error occurred while editing the cell", e.getMessage());

            // Set the value of the cell to the initial value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setPrice(event.getOldValue());
        } finally {
            tvProduct.refresh();
        }
    }

    /**
     * Handles the cell edit event for the "Tag" column in the TableView. Edits
     * the tag information of the selected product and updates the database.
     *
     * @param event The CellEditEvent object for the event.
     */
    @FXML
    private void handleTagCellEdition(CellEditEvent<Product, Tag> event) {
        try {
            Product editedProduct = event.getRowValue();
            editedProduct.setTag(event.getNewValue());
            productManager.updateProduct(editedProduct);

            // Set the value of the cell with the new value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setTag(event.getNewValue());
        } catch (Exception e) {
            showErrorAlert("Error", "An error occurred while editing the cell", e.getMessage());

            // Set the value of the cell to the initial value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setTag(event.getOldValue());
        } finally {
            tvProduct.refresh();
        }
    }

    /**
     * Handles the cell edit event for the "Addition Date" column in the
     * TableView. Edits the addition date information of the selected product
     * and updates the database.
     *
     * @param event The CellEditEvent object for the event.
     */
    @FXML
    private void handleAdditionDateCellEdition(CellEditEvent<Product, LocalDate> event) {
        try {
            Product editedProduct = event.getRowValue();
            editedProduct.setCreateTimestamp(Date.from(event.getNewValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            productManager.updateProduct(editedProduct);

            // Set the value of the cell with the new value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setCreateTimestamp(Date.from(event.getNewValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } catch (Exception e) {
            showErrorAlert("Error", "An error occurred while editing the cell", e.getMessage());

            // Set the value of the cell to the initial value
            event.getTableView().getItems().get(event.getTablePosition().getRow())
                    .setCreateTimestamp(Date.from(event.getNewValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } finally {
            tvProduct.refresh();
        }
    }

    /**
     * Returns a Callback for creating a ProductDatePickerTableCell for the
     * "Addition Date" column.
     *
     * @return A Callback for creating a ProductDatePickerTableCell.
     */
    private Callback<TableColumn<Product, LocalDate>, TableCell<Product, LocalDate>> getDatePickerCellFactory() {
        return (TableColumn<Product, LocalDate> param) -> new ProductDatePickerTableCell();
    }

    /**
     * Returns a Callback for creating an EditableComboBoxTableCell for the
     * "Supplier" column.
     *
     * @return A Callback for creating an EditableComboBoxTableCell.
     */
    private Callback<TableColumn<Product, Supplier>, TableCell<Product, Supplier>> getEditableComboBoxCellFactory() {
        return (TableColumn<Product, Supplier> param) -> new EditableComboBoxTableCell(supplierList);
    }

    private ObservableValue<LocalDate> getDateToLocalDateValueFactory(TableColumn.CellDataFeatures<Product, LocalDate> factory) {
        return new SimpleObjectProperty<LocalDate>(factory.getValue().getCreateTimestamp()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

    }

    private void handleSupplierMenuItemAction(ActionEvent event) {
        if (showConfirmationDialog("Do you want to open the Supplier Management window?")) {
            launchSupplierWindow();
        }
    }

    /**
     * Handles the action event for the "Help" menu item. Displays the window
     * tutorial.
     *
     * @param event The ActionEvent object for the event.
     */
    private void handleHelpAction(ActionEvent event) {
        // Show window tutorial
    }

    /**
     * Handles the action event for the "About" menu item. Displays the about
     * dialog.
     *
     * @param event The ActionEvent object for the event.
     */
    private void handleAboutAction(ActionEvent event) {
        Alert aboutAlert = new Alert(AlertType.INFORMATION);
        aboutAlert.setTitle("About");
        aboutAlert.setHeaderText("Product Management System");
        aboutAlert.setContentText("This application is designed for managing products efficiently.\nVersion: 1.0.0\nAuthor: Alexander Epelde");

        aboutAlert.showAndWait();
    }

    /**
     * Handles the action event for the "Print Report" menu item. Prints the
     * report.
     *
     * @param event The ActionEvent object for the event.
     */
    private void handlePrintReportAction(ActionEvent event) {
        // Print report
    }

    /**
     * Launches the supplier window. TODO: Implement this method.
     */
    private void launchSupplierWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/supplier_view.fxml"));
            Parent root = (Parent) loader.load();
            SupplierManagementController.class.cast(loader.getController()).setStage(stage);
            SupplierManagementController.class.cast(loader.getController()).initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
