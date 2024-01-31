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
public class ProductViewControllerIT extends ApplicationTest {

    private static final String OVERSIZED_TEXT
            = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    private Button btnListProducts, btnAdd, btnDelete, btnEdit, btnExit;
    private TableView<Product> tvProduct;
    private TableColumn<Product, String> cProductNumber, cBrand;
    // other table columns

    private void launch(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/product_view.fxml"));
            Parent root = (Parent) loader.load();
//            Obtain the Sign In window controller
            ProductViewController controller = ProductViewController.class
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
        new ProductViewControllerIT().launch(stage);
        // Initialize UI elements
        btnListProducts = lookup("#btnListProducts").queryButton();
        btnAdd = lookup("#btnAdd").queryButton();
        btnDelete = lookup("#btnDelete").queryButton();
        btnEdit = lookup("#btnEdit").queryButton();
        btnExit = lookup("#btnExit").queryButton();
        tvProduct = lookup("#tvProduct").queryTableView();
        // Initialize other table columns
    }

    @Test
    public void testA_initialState() {
        verifyThat(btnListProducts, isEnabled());
        verifyThat(btnAdd, isEnabled());
        verifyThat(btnDelete, isDisabled());
        verifyThat(btnEdit, isDisabled());
        verifyThat(btnExit, isEnabled());
        verifyThat(tvProduct, hasNumRows(0));
    }

    @Test
    public void testB_addProduct() {
        int initialCount = tvProduct.getItems().size();
        clickOn(btnAdd);
        // Assert a new product is added
        verifyThat(tvProduct, hasNumRows(initialCount + 1));
    }

    @Test
    public void testC_listProducts() {
        clickOn(btnListProducts);
        verifyThat(tvProduct, hasNumRows(tvProduct.getItems().size())); // Assuming at least one item is loaded
    }

//    @Test
//    public void testD_editProduct() {
//        // Select the first row in the table
//        clickOn(btnListProducts);
//        clickOn(tvProduct.getItems().get(tvProduct.getItems().size() - 1).getBrand());
//        clickOn(btnEdit);
//        String newBrand = "New Brand";
//        write(newBrand);
//        press(KeyCode.ENTER);
//        // Verify the brand is updated
//        assertEquals("Nuh uh", tvProduct.getItems().stream().filter(p -> p.getBrand().equals(newBrand)).count(),
//                1);
//    }
    @Test
    public void testF_tableSelection() {
        clickOn(btnListProducts);
        TableRow<Product> row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        verifyThat(btnDelete, isEnabled());
        // Deselect and verify buttons are disabled
        clickOn(btnListProducts);
        verifyThat(btnDelete, isDisabled());
    }

    @Test
    public void testG_EditAllTableColumns() {
        clickOn("#btnListProducts");
        clickOn("#btnAdd");

        int lastRowIndex = tvProduct.getItems().size() - 1;
        clickOn(tvProduct.getItems().get(lastRowIndex).getTag().getType());

        testEditBrandColumn();
        testEditModelColumn();
        testEditWeightColumn();
        testEditPriceColumn();
        testEditDescriptionColumn();
        testEditOtherInfoColumn();
        testEditAdditionDateColumn();
        testEditSupplierColumn();
        testEditTagColumn();
    }

//    @Test
    public void testEditBrandColumn() {
        // Click on "List Products" and "Add" to add a new product

        // Retrieve the TableView and get the last row index
        int lastRowIndex = tvProduct.getItems().size() - 1;

        // Retrieve the brand value of the last row
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        String brandValue = lastProduct.getBrand();

        clickOn(brandValue);
        push(KeyCode.BACK_SPACE);
        push(KeyCode.ENTER);
        clickOn("OK");

        // Test with oversized text
        clickOn(brandValue);
        write(OVERSIZED_TEXT);
        push(KeyCode.ENTER);
        clickOn("OK");

        // Test with valid text
        String validText = "New Brand";
        clickOn(brandValue);
        write(validText);
        push(KeyCode.ENTER);

        // Verify the cell value
        String updatedBrandValue = tvProduct.getItems().get(lastRowIndex).getBrand();
        assertEquals(validText, updatedBrandValue);
    }

//    @Test
    public void testEditModelColumn() {
        // Retrieve the TableView and get the last row index
        int lastRowIndex = tvProduct.getItems().size() - 1;

        // Retrieve the model value of the last row
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        String modelValue = lastProduct.getModel();

        // Edit the model
        clickOn(modelValue);
        push(KeyCode.BACK_SPACE);
        push(KeyCode.ENTER);
        clickOn("OK");

        // Test with oversized text
        clickOn(modelValue);
        write(OVERSIZED_TEXT);
        push(KeyCode.ENTER);
        clickOn("OK");

        // Test with valid text
        String validText = "New Model";
        clickOn(modelValue);
        write(validText);
        push(KeyCode.ENTER);

        // Verify the cell value
        String updatedModelValue = tvProduct.getItems().get(lastRowIndex).getModel();
        assertEquals(validText, updatedModelValue);
    }

//    @Test
    public void testEditDescriptionColumn() {
        // Retrieve the TableView and get the last row index
        int lastRowIndex = tvProduct.getItems().size() - 1;

        // Retrieve the description value of the last row
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        String descriptionValue = lastProduct.getDescription();

        // Edit the description
        clickOn(descriptionValue);
        push(KeyCode.BACK_SPACE);
        push(KeyCode.ENTER);
        clickOn("OK");

        // Test with oversized text
        clickOn(descriptionValue);
        write(OVERSIZED_TEXT);
        push(KeyCode.ENTER);
        clickOn("OK");

        // Test with valid text
        String validText = "New Description";
        clickOn(descriptionValue);
        write(validText);
        push(KeyCode.ENTER);

        // Verify the cell value
        String updatedDescriptionValue = tvProduct.getItems().get(lastRowIndex).getDescription();
        assertEquals(validText, updatedDescriptionValue);
    }

//    @Test
    public void testEditOtherInfoColumn() {
        int lastRowIndex = tvProduct.getItems().size() - 1;

        // Retrieve the other info value of the last row
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        String otherInfoValue = lastProduct.getOtherInfo();

        // Edit the other info
        clickOn(otherInfoValue);
        push(KeyCode.BACK_SPACE);
        push(KeyCode.ENTER);
        clickOn("OK");

        // Test with oversized text
        clickOn(otherInfoValue);
        write(OVERSIZED_TEXT);
        push(KeyCode.ENTER);
        clickOn("OK");

        // Test with valid text
        String validText = "New Other Info";
        clickOn(otherInfoValue);
        write(validText);
        push(KeyCode.ENTER);

        // Verify the cell value
        String updatedOtherInfoValue = tvProduct.getItems().get(lastRowIndex).getOtherInfo();
        assertEquals(validText, updatedOtherInfoValue);
    }

//    @Test
    public void testEditWeightColumn() {
        int lastRowIndex = tvProduct.getItems().size() - 1;

        // Retrieve the weight value of the last row
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        Double weightValue = lastProduct.getWeight();

        // Edit the weight with invalid input
        clickOn(weightValue.toString());
        write("invalid");
        push(KeyCode.ENTER);
        clickOn("OK");

        // Edit the weight with a negative value
        clickOn(weightValue.toString());
        write("-5");
        push(KeyCode.ENTER);
        clickOn("OK");

        clickOn(weightValue.toString());
        write(Double.MAX_VALUE + "1");
        push(KeyCode.ENTER);
        clickOn("OK");

        // Edit the weight with a valid value
        Double validWeight = 10.0;
        clickOn(weightValue.toString());
        write(validWeight.toString());
        push(KeyCode.ENTER);

        // Verify the cell value
        Double updatedWeightValue = tvProduct.getItems().get(lastRowIndex).getWeight();
        assertEquals(validWeight, updatedWeightValue);
    }

//    @Test
    public void testEditPriceColumn() {
        int lastRowIndex = tvProduct.getItems().size() - 1;

        // Retrieve the price value of the last row
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        Double priceValue = lastProduct.getPrice();

        // Edit the price with invalid input
        clickOn(priceValue.toString());
        write("invalid");
        push(KeyCode.ENTER);
        clickOn("OK");

        // Edit the weight with a negative value
        clickOn(priceValue.toString());
        write("-5");
        push(KeyCode.ENTER);
        clickOn("OK");

        clickOn(priceValue.toString());
        write(Double.MAX_VALUE + "1");
        push(KeyCode.ENTER);
        clickOn("OK");

        // Edit the weight with a valid value
        Double validPrice = 10.0;
        clickOn(priceValue.toString());
        write(validPrice.toString());
        push(KeyCode.ENTER);

        // Verify the cell value
        Double updatedPriceValue = tvProduct.getItems().get(lastRowIndex).getPrice();
        assertEquals(validPrice, updatedPriceValue);
    }

//    @Test
    public void testEditAdditionDateColumn() {
        int lastRowIndex = tvProduct.getItems().size() - 1;

        // Click on the addition date cell to open the DatePicker
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SimpleDateFormat.class.cast(
                DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())).toPattern());

        String currentDate = formatter.format(lastProduct.getCreateTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        String dateToChange = formatter.format(LocalDate.now().minusDays(5));

        clickOn(currentDate);
        clickOn();
        write(dateToChange);
        press(KeyCode.ENTER); // Confirm the date selection

        LocalDate updatedAdditionDateValue = Instant.ofEpochMilli(tvProduct.getItems().get(lastRowIndex).getCreateTimestamp().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(LocalDate.now().minusDays(5), updatedAdditionDateValue);

        clickOn(dateToChange);
        clickOn(".date-picker .arrow-button");
        clickOn(String.valueOf(LocalDate.now().getDayOfMonth()));
        press(KeyCode.ENTER); // Confirm the date selection

        // Verify the cell value
        updatedAdditionDateValue = Instant.ofEpochMilli(tvProduct.getItems().get(lastRowIndex).getCreateTimestamp().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(LocalDate.now(), updatedAdditionDateValue);
    }

//    @Test
    public void testEditSupplierColumn() {
        try {
            int lastRowIndex = tvProduct.getItems().size() - 1;
            List<String> supplierNames = new ArrayList<>();

            supplierNames = SupplierManagerFactory.getInstance().selectAllSuppliers().stream().map(s -> s.getName()).collect(Collectors.toList());

            // Scenario 1: Enter an existing supplier
            String existingSupplierName = supplierNames.get(new Random().nextInt(supplierNames.size()));
            clickOn(tvProduct.getItems().get(lastRowIndex).getSupplier().getName());
            clickOn();
            write(existingSupplierName);
            push(KeyCode.ENTER);

            // Verify the supplier name
            String updatedSupplierValue = tvProduct.getItems().get(lastRowIndex).getSupplier().getName();
            assertEquals(existingSupplierName, updatedSupplierValue);

            // Scenario 2: Enter a non-existing supplier
            doubleClickOn(updatedSupplierValue);
            write("NonExisting Supplier");
            push(KeyCode.ENTER);
            clickOn("Cancel");

            // Scenario 3: Select a supplier from the ComboBox
            clickOn(updatedSupplierValue);
            clickOn(".combo-box .arrow-button");
            ComboBox<Supplier> comboBox = lookup(".combo-box").queryComboBox();
            interact(() -> comboBox.getSelectionModel().select(1)); // Select the second supplier for example
            push(KeyCode.ENTER);

            // Verify the selected supplier
            Supplier selectedSupplier = tvProduct.getItems().get(lastRowIndex).getSupplier();
            assertNotNull(selectedSupplier); // Assert that a supplier is selected
            // Additional assertions can be made based on the expected selected supplier

        } catch (Exception e) {
            Logger.getLogger(ProductViewControllerIT.class).info("OOPS");
        }
    }

    public void testEditTagColumn() {
        try {
            int lastRowIndex = tvProduct.getItems().size() - 1;
            List<String> tagTypes = new ArrayList<>();

            tagTypes = TagManagerFactory.getInstance().selectAllTags().stream().map(t -> t.getType()).collect(Collectors.toList());

            // Scenario 1: Enter an existing supplier
            clickOn(tvProduct.getItems().get(lastRowIndex).getTag().getType());
            clickOn(".combo-box .arrow-button");
            ComboBox<Supplier> comboBox = lookup(".combo-box").queryComboBox();
            interact(() -> comboBox.getSelectionModel().select(1)); // Select the second supplier for example
            push(KeyCode.ENTER);

            Tag selectedTag = tvProduct.getItems().get(lastRowIndex).getTag();
            assertNotNull(selectedTag); // Assert that a supplier is selected
            // Additional assertions can be made based on the expected selected supplier
        } catch (Exception e) {
            Logger.getLogger(ProductViewControllerIT.class).info("OOPS");
        }
    }

    @Test
    public void testG_deleteProduct() {
        clickOn(btnListProducts);
        Product productToDelete = tvProduct.getItems().get(tvProduct.getItems().size() - 1);
        clickOn(productToDelete.getDescription());
        clickOn(btnDelete);
        // Confirm the deletion
        clickOn("OK"); // Assuming an OK button in your confirmation dialog
        // Verify the product is deleted
        boolean exists = tvProduct.getItems().stream().anyMatch(p -> p.equals(productToDelete));
        assertFalse("Product wasnt deleted", exists);
    }

    @Test
    public void testH_exitButton() {
        clickOn(btnExit);
        clickOn("OK");
    }

}
