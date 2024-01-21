package ui.controls;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import transfer.objects.Product;
import transfer.objects.Supplier;

// Custom cell class for the ComboBox
public class EditableComboBoxTableCell extends TableCell<Product, Supplier> {

    private final ComboBox<Supplier> comboBox = new ComboBox<>();

    //static list because we will not be adding suppliers while this window is still open,
    // so it is enough to do once in the init stage 
    private static ObservableList<Supplier> observableSupplierList;

    private static String oldValue = new String(" ");

    public EditableComboBoxTableCell(List<Supplier> sList) {

        observableSupplierList = FXCollections.observableArrayList(sList);
        comboBox.setEditable(true);
        comboBox.setConverter(new StringConverter<Supplier>() {
            @Override
            public String toString(Supplier supplier) {
                return supplier == null ? "" : supplier.getName();
            }

            // Essential method for this implementation to work, since the TableCell we are 
            // replacing is of <Product, Supplier> type, we cant exchange it with String, so 
            // we need to somehow get that string value for the ComboBox and get the selected item
            @Override
            public Supplier fromString(String string) {
                for (Supplier s : observableSupplierList) {
                    if (s.getName().equals(string)) {
                        return s;
                    }
                }
                return new Supplier(); // return a Supplier instance because a null pointer creates a lot of problems
            }

        });
        comboBox.setOnAction(event -> commitEdit(comboBox.getValue()));
    }

    @Override
    public void startEdit() {
        super.startEdit();
        setText(null);
        setGraphic(comboBox);
        oldValue = getItem().getName(); //save the old selection in case there is a selection cancel

        // Populate the ComboBox with supplier names
        comboBox.setItems(observableSupplierList);

    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
        updateItem(getItem(), isEmpty());
    }

    @Override
    public void updateItem(Supplier item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(comboBox);
            } else {
                if (item.isEmpty()) {
                    setText(oldValue); // if the supplier is empty (see fromString()), set the cell label value to the old one
                } else {
                    setText(item.getName()); // Display only the name of the supplier
                }
                setGraphic(null);
            }
        }

    }
}
