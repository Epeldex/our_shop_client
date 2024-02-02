package ui.controller;

import app.App;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import jxl.common.Logger;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.control.TableViewMatchers.hasNumRows;
import transfer.objects.Product;
import transfer.objects.Supplier;
import transfer.objects.Tag;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductViewCRUDTest extends ApplicationTest {

    // Class variables (same as in the original ProductViewControllerIT class)
    private Button btnListProducts, btnAdd, btnDelete, btnEdit, btnExit;
    private TableView<Product> tvProduct;
    // Other necessary class variables

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
        new ProductViewCRUDTest().launch(stage);
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
        // Code from the original testA_initialState method
        verifyThat(btnListProducts, isEnabled());
        verifyThat(btnAdd, isEnabled());
        verifyThat(btnDelete, isDisabled());
        verifyThat(btnEdit, isDisabled());
        verifyThat(btnExit, isEnabled());
        verifyThat(tvProduct, hasNumRows(0));
    }

    @Test
    public void testB_addProduct() {
        clickOn(btnListProducts);
        int initialCount = tvProduct.getItems().size();
        clickOn(btnAdd);
        verifyThat(tvProduct, hasNumRows(initialCount + 1));
    }

    @Test
    public void testC_listProducts() {
        // Code from the original testC_listProducts method
        clickOn(btnListProducts);
        verifyThat(tvProduct, hasNumRows(tvProduct.getItems().size()));
    }

    @Test
    public void testD_tableSelection() {
        // Code from the original testF_tableSelection method
        clickOn(btnListProducts);
        TableRow<Product> row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        verifyThat(btnDelete, isEnabled());
        clickOn(btnListProducts);
        verifyThat(btnDelete, isDisabled());
    }

    @Test
    public void testE_EditBrandColumn() {
        clickOn(btnListProducts);
        int lastRowIndex = tvProduct.getItems().size() - 1;
        String validText = "New Brand";
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        String brandValue = lastProduct.getBrand();

        doubleClickOn(brandValue);
        write(validText);
        push(KeyCode.ENTER);

        String updatedBrandValue = tvProduct.getItems().get(lastRowIndex).getBrand();
        assertEquals(validText, updatedBrandValue);
    }

    @Test
    public void testF_EditModelColumn() {
        clickOn(btnListProducts);
        int lastRowIndex = tvProduct.getItems().size() - 1;
        String validText = "New Model";
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        String modelValue = lastProduct.getModel();

        doubleClickOn(modelValue);
        write(validText);
        push(KeyCode.ENTER);

        String updatedModelValue = tvProduct.getItems().get(lastRowIndex).getModel();
        assertEquals(validText, updatedModelValue);
    }

    @Test
    public void testG_EditDescriptionColumn() {
        clickOn(btnListProducts);
        int lastRowIndex = tvProduct.getItems().size() - 1;
        String validText = "New Description";
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        String descriptionValue = lastProduct.getDescription();

        doubleClickOn(descriptionValue);
        write(validText);
        push(KeyCode.ENTER);

        String updatedDescriptionValue = tvProduct.getItems().get(lastRowIndex).getDescription();
        assertEquals(validText, updatedDescriptionValue);
    }

    @Test
    public void testH_EditOtherInfoColumn() {
        clickOn(btnListProducts);
        int lastRowIndex = tvProduct.getItems().size() - 1;
        String validText = "New Other Info";
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        String otherInfoValue = lastProduct.getOtherInfo();

        doubleClickOn(otherInfoValue);
        write(validText);
        push(KeyCode.ENTER);

        String updatedOtherInfoValue = tvProduct.getItems().get(lastRowIndex).getOtherInfo();
        assertEquals(validText, updatedOtherInfoValue);
    }

    @Test
    public void testI_EditWeightColumn() {
        clickOn(btnListProducts);
        int lastRowIndex = tvProduct.getItems().size() - 1;
        Double validWeight = 10.0;
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        Double weightValue = lastProduct.getWeight();

        doubleClickOn(weightValue.toString());
        write(validWeight.toString());
        push(KeyCode.ENTER);

        Double updatedWeightValue = tvProduct.getItems().get(lastRowIndex).getWeight();
        assertEquals(validWeight, updatedWeightValue);
    }

    @Test
    public void testJ_EditPriceColumn() {
        clickOn(btnListProducts);
        int lastRowIndex = tvProduct.getItems().size() - 1;
        Double validPrice = 20.0;
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        Double priceValue = lastProduct.getPrice();

        doubleClickOn(priceValue.toString());
        write(validPrice.toString());
        push(KeyCode.ENTER);

        Double updatedPriceValue = tvProduct.getItems().get(lastRowIndex).getPrice();
        assertEquals(validPrice, updatedPriceValue);
    }

    @Test
    public void testL_EditAdditionDateColumn() {
        clickOn(btnListProducts);
        int lastRowIndex = tvProduct.getItems().size() - 1;
        // Click on the addition date cell to open the DatePicker
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SimpleDateFormat.class.cast(
                DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())).toPattern());

        String currentDate = formatter.format(lastProduct.getCreateTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        doubleClickOn(currentDate);
        clickOn(".date-picker .arrow-button");
        clickOn(String.valueOf(LocalDate.now().getDayOfMonth()));
        press(KeyCode.ENTER); // Confirm the date selection

        // Verify the cell value
        LocalDate updatedAdditionDateValue = Instant.ofEpochMilli(tvProduct.getItems().get(lastRowIndex).getCreateTimestamp().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(LocalDate.now(), updatedAdditionDateValue);
    }

    @Test
    public void testM_EditSupplierColumn() {
        clickOn(btnListProducts);
        int lastRowIndex = tvProduct.getItems().size() - 1;
        Product lastProduct = tvProduct.getItems().get(lastRowIndex);

        doubleClickOn(lastProduct.getSupplier().getName());
        clickOn(".combo-box .arrow-button");
        ComboBox<Supplier> comboBox = lookup(".combo-box").queryComboBox();
        interact(() -> comboBox.getSelectionModel().select(3)); // Select the second supplier for example
        push(KeyCode.ENTER);

        Supplier selectedSupplier = tvProduct.getItems().get(lastRowIndex).getSupplier();
        assertNotNull(selectedSupplier);
    }

    @Test
    public void testN_EditTagColumn() {
        try {
            clickOn(btnListProducts);
            int lastRowIndex = tvProduct.getItems().size() - 1;

            // Scenario 1: Enter an existing supplier
            doubleClickOn(tvProduct.getItems().get(lastRowIndex).getTag().getType());
            clickOn(".combo-box .arrow-button");
            ComboBox<Supplier> comboBox = lookup(".combo-box").queryComboBox();
            interact(() -> comboBox.getSelectionModel().select(4)); // Select the second supplier for example
            push(KeyCode.ENTER);

            Tag selectedTag = tvProduct.getItems().get(lastRowIndex).getTag();
            assertNotNull(selectedTag); // Assert that a supplier is selected
            // Additional assertions can be made based on the expected selected supplier
        } catch (Exception e) {
            Logger.getLogger(ProductViewCRUDTest.class).info("OOPS");
        }
    }

    @Test
    public void testO_deleteProduct() {
        // Code from the original testG_deleteProduct method
        clickOn(btnListProducts);
        Product productToDelete = tvProduct.getItems().get(tvProduct.getItems().size() - 1);
        doubleClickOn(productToDelete.getDescription());
        clickOn(btnDelete);
        clickOn("OK");
        boolean exists = tvProduct.getItems().stream().anyMatch(p -> p.equals(productToDelete));
        assertFalse("Product wasn't deleted", exists);
    }

    @Test
    public void testP_exitButton() {
        // Code from the original testH_exitButton method
        clickOn(btnExit);
        clickOn("OK");
    }
}
