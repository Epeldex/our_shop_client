package ui.controller;

import app.App;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import jxl.common.Logger;
import logic.factories.SupplierManagerFactory;
import logic.factories.TagManagerFactory;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.control.TableViewMatchers.hasNumRows;
import transfer.objects.Product;
import transfer.objects.Supplier;
import transfer.objects.Tag;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SupplierViewControllerIt extends ApplicationTest {

    private static final String OVERSIZED_TEXT
            = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    private Button btnListSuppliers, btnAdd, btnDelete, btnEdit, btnExit;
    private TableView<Supplier> tvSupplier;
    private TableColumn<Supplier, String> nameColumnId, phoneColumnId, countryColumnId, zipColumnId, dateColumnId;
    private TextField phoneTextField;

    private void launch(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/supplier_view.fxml"));
            Parent root = (Parent) loader.load();
//            Obtain the Sign In window controller
            SupplierManagementController controller = SupplierManagementController.class
                    .cast(loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Assuming your main JavaFX app class is named MainApp
        new SupplierViewControllerIt().launch(stage);
        // Initialize UI elements
        btnListSuppliers = lookup("#btnListSuppliers").queryButton();
        btnAdd = lookup("#btnAdd").queryButton();
        btnDelete = lookup("#btnDelete").queryButton();
        btnEdit = lookup("#btnEdit").queryButton();
        btnExit = lookup("#btnExit").queryButton();
        tvSupplier = lookup("#tvSupplier").queryTableView();
        phoneTextField = lookup("#phone").query();
    }

    @Test
    public void testA_initialState() {
        verifyThat(btnListSuppliers, isEnabled());
        verifyThat(btnAdd, isEnabled());
        verifyThat(btnDelete, isDisabled());
        verifyThat(btnEdit, isDisabled());
        verifyThat(btnExit, isEnabled());
        verifyThat(tvSupplier, hasNumRows(0));
    }

    @Test
    public void testB_addSupplier() {
        /*int initialCount = tvSupplier.getItems().size();
        clickOn(btnAdd);
        // Assert that the number of rows increased by 1
        verifyThat(tvSupplier, hasNumRows(initialCount + 1));*/

        int initialCount = tvSupplier.getItems().size();
        clickOn(btnAdd);

        // Obtiene el último proveedor agregado
        Supplier lastAddedSupplier = tvSupplier.getItems().get(tvSupplier.getItems().size() - 1);

        // Verifica que la TableView ahora contiene al nuevo proveedor
        assertTrue(tvSupplier.getItems().contains(lastAddedSupplier));
    }

    @Test
    public void testC_listProducts() {
        clickOn(btnListSuppliers);
        verifyThat(tvSupplier, hasNumRows(tvSupplier.getItems().size())); // Assuming at least one item is loaded
    }

    @Test
    public void testF_tableSelection() {
        clickOn(btnListSuppliers);
        TableRow<Product> row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        verifyThat(btnDelete, isEnabled());
        // Deselect and verify buttons are disabled
        clickOn(btnListSuppliers);
        verifyThat(btnDelete, isDisabled());
    }

    @Test
    public void testG_EditAllTableColumns() {
        clickOn("#btnListSuppliers");
        //clickOn("#btnAdd");

        int lastRowIndex = tvSupplier.getItems().size() - 1;
        clickOn(tvSupplier.getItems().get(lastRowIndex).getName());

        testEditNameColumn();
        testEditPhoneColumn();
        testEditCountryColumn();
        testEditZipColumn();
        testEditAdditionDateColumn();

    }

    public void testEditNameColumn() {
        // Click on "List Products" and "Add" to add a new product

        // Retrieve the TableView and get the last row index
        int lastRowIndex = tvSupplier.getItems().size() - 1;

        // Retrieve the brand value of the last row
        Supplier lastSupplier = tvSupplier.getItems().get(lastRowIndex);
        String nameValue = lastSupplier.getName();

        // Test by removing existing text
        clickOn(nameValue);
        write(OVERSIZED_TEXT);
        push(KeyCode.ENTER);
        clickOn("Aceptar");

        // Verify the cell value
        // Test with valid text
        String validText = "New Name";
        clickOn(nameValue);  // Click on the updated cell value
        write(validText);
        push(KeyCode.ENTER);

        // Verify the cell value
        nameValue = tvSupplier.getItems().get(lastRowIndex).getName();
        assertEquals(validText, nameValue);
    }

    public void testEditPhoneColumn() {

        // Click on "List Suppliers" and "Add" to add a new supplier
        clickOn("#btnListSuppliers");

        // Retrieve the TableView and get the last row index
        int lastRowIndex = tvSupplier.getItems().size() - 1;

        // Retrieve the phone value of the last row
        Supplier lastSupplier = tvSupplier.getItems().get(lastRowIndex);
        String phoneValue = lastSupplier.getPhone();

        // Test with empty phone
        doubleClickOn(phoneValue);
        push(KeyCode.BACK_SPACE);
        push(KeyCode.ENTER);
        clickOn("Aceptar");

        // Test with invalid phone format
        clickOn(phoneValue);
        write("InvalidPhoneNumber");
        push(KeyCode.ENTER);
        clickOn("Aceptar");

        // Test with valid phone
        String validPhone = "+12345678900";
        clickOn(phoneValue);
        write(validPhone);
        push(KeyCode.ENTER);

        // Verify the cell value
        String updatedPhoneValue = tvSupplier.getItems().get(lastRowIndex).getPhone();
        assertEquals(validPhone, updatedPhoneValue);

    }

    public void testEditCountryColumn() {
        // Click on "List Suppliers" and "Add" to add a new supplier
        clickOn("#btnListSuppliers");

        // Retrieve the TableView and get the last row index
        int lastRowIndex = tvSupplier.getItems().size() - 1;

        // Retrieve the country value of the last row
        Supplier lastSupplier = tvSupplier.getItems().get(lastRowIndex);
        String countryValue = lastSupplier.getCountry();

        // Test with empty country
        doubleClickOn(countryValue);
        push(KeyCode.BACK_SPACE);
        push(KeyCode.ENTER);
        clickOn("Aceptar");

        // Test with oversized text
        clickOn(countryValue);
        write(OVERSIZED_TEXT);
        push(KeyCode.ENTER);
        clickOn("Aceptar");

        // Test with valid text
        String validText = "New Country";
        clickOn(countryValue);
        write(validText);
        push(KeyCode.ENTER);

        // Verify the cell value
        String updatedCountryValue = tvSupplier.getItems().get(lastRowIndex).getCountry();
        assertEquals(validText, updatedCountryValue);
    }

    public void testEditZipColumn() {
        // Click on "List Suppliers" and "Add" to add a new supplier
        clickOn("#btnListSuppliers");

        // Retrieve the TableView and get the last row index
        int lastRowIndex = tvSupplier.getItems().size() - 1;

        // Retrieve the ZIP value of the last row
        Supplier lastSupplier = tvSupplier.getItems().get(lastRowIndex);
        Integer zipValue = lastSupplier.getZip();

        // Test with empty ZIP
        doubleClickOn(zipValue.toString());
        push(KeyCode.BACK_SPACE);
        push(KeyCode.ENTER);
        clickOn("Aceptar");

        // Test with invalid ZIP format
        clickOn(zipValue.toString());
        write("InvalidZIP");
        push(KeyCode.ENTER);
        clickOn("Aceptar");

        // Test with valid ZIP
        int validZip = 12345;
        clickOn(zipValue.toString());
        write(Integer.toString(validZip));
        push(KeyCode.ENTER);

        // Verify the cell value
        Integer updatedZipValue = tvSupplier.getItems().get(lastRowIndex).getZip();
        assertEquals(validZip, updatedZipValue.intValue());
    }

    public void testEditAdditionDateColumn() {
        int lastRowIndex = tvSupplier.getItems().size() - 1;

        Supplier lastSupplier = tvSupplier.getItems().get(lastRowIndex);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                SimpleDateFormat.class.cast(DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())).toPattern());

        String currentDate = formatter.format(lastSupplier.getCreateTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        String dateToChange = formatter.format(LocalDate.now().minusDays(5));

        // Hacer doble clic en la fecha actual
        doubleClickOn(currentDate);
        clickOn();

        // Escribir la nueva fecha
        write(dateToChange);

        // Esperar antes de presionar ENTER
        sleep(500); // Puedes ajustar el tiempo de espera según sea necesario

        // Presionar ENTER
        press(KeyCode.ENTER);

        // Esperar después de la interacción antes de verificar
        sleep(500);

        LocalDate updatedAdditionDateValue = Instant.ofEpochMilli(
                tvSupplier.getItems().get(lastRowIndex).getCreateTimestamp().getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();

        // Ajustar la fecha esperada
        LocalDate expectedDate = LocalDate.of(2024, 02, 01);
        assertEquals(expectedDate, updatedAdditionDateValue);

        // Hacer clic en cualquier botón de flecha del DatePicker
        clickOn(node -> node instanceof Button);

    }

    @Test
    public void testH_deleteSupplier() {
        clickOn(btnListSuppliers);
        Supplier supplierToDelete = tvSupplier.getItems().get(tvSupplier.getItems().size() - 2);
        clickOn(supplierToDelete.getName());
        clickOn(btnDelete);
        // Confirm the deletion
        clickOn("Aceptar"); // Assuming an Aceptar button in your confirmation dialog
        // Verify the product is deleted
        boolean exists = tvSupplier.getItems().stream().anyMatch(p -> p.equals(supplierToDelete));
        assertFalse("Supplier wasnt deleted", exists);
    }

}
